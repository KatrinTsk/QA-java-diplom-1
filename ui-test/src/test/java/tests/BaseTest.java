package tests;

import api.UserApiClient;
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

    @Before
    @Step("Создание тестового пользователя через API")
    public void createTestUser() {
        long timestamp = System.currentTimeMillis();
        this.name = "User_" + timestamp;
        this.email = "user_" + timestamp + "@example.com";
        this.password = "password_" + timestamp;

        Response response = UserApiClient.createUser(email, password, name);
        this.accessToken = response.then().extract().path("accessToken");

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