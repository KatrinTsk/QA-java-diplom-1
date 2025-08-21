package tests;

import api.UserApiClient;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import utils.WebDriverFactory;
import constants.Endpoints;

public class BaseTest {
    protected WebDriver driver;
    protected String name;
    protected String email;
    protected String password;
    protected String accessToken;

    // Инициализация Faker для генерации случайных тестовых данных
    private static final Faker faker = new Faker();

    @Before
    @Step("Создание тестового пользователя через API")
    public void createTestUser() {
        // Генерация случайных данных пользователя с помощью JavaFaker
        this.name = faker.name().firstName(); // Пример: "Emma"
        this.email = faker.internet().emailAddress(); // Пример: "emma@example.com"
        // Генерация пароля: длина 10-16 символов, с буквами, цифрами и специальными символами
        this.password = faker.internet().password(10, 16, true, true, true); // Пример: "p@ssw0rd123!abc"

        // Создание пользователя через API
        Response response = UserApiClient.createUser(email, password, name);
        this.accessToken = response.then().extract().path("accessToken");

        // Инициализация WebDriver и открытие главной страницы
        this.driver = WebDriverFactory.createDriver();
        driver.get(Endpoints.HOME_PAGE);
    }

    @After
    @Step("Удаление тестового пользователя через API")
    public void deleteTestUser() {
        try {
            if (accessToken != null) {
                UserApiClient.deleteUser(accessToken);
            }
        } catch (Exception e) {
            System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
        } finally {
            if (driver != null) {
                try {
                    driver.quit();
                } catch (Exception e) {
                    System.err.println("Ошибка при закрытии драйвера: " + e.getMessage());
                }
            }
        }
    }
}