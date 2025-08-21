package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import pages.LoginPage;
import pages.MainPage;
import pages.RegistrationPage;
import com.github.javafaker.Faker;

import static org.junit.Assert.*;

@Epic("Регистрация пользователя")
@Feature("Форма регистрации")
public class RegistrationTest extends BaseTest {

    // Инициализация Faker для генерации случайных тестовых данных
    private static final Faker faker = new Faker();

    @Test
    @DisplayName("Успешная регистрация")
    @Story("Пользователь может зарегистрироваться с валидными данными")
    @Description("Проверка успешной регистрации с паролем из 6 символов")
    @Severity(SeverityLevel.BLOCKER)
    public void testSuccessfulRegistration() {
        performRegistrationTest("password123", true);
        attachUserData();
    }

    @Test
    @DisplayName("Регистрация с паролем из 1 символа")
    @Story("Валидация пароля при регистрации")
    @Description("Проверка отображения ошибки при вводе пароля длиной 1 символ")
    @Severity(SeverityLevel.CRITICAL)
    public void testPasswordWith1Char() {
        performRegistrationTest("a", false);
        attachUserData();
    }

    @Test
    @DisplayName("Регистрация с паролем из 2 символов")
    @Story("Валидация пароля при регистрации")
    @Description("Проверка отображения ошибки при вводе пароля длиной 2 символа")
    @Severity(SeverityLevel.CRITICAL)
    public void testPasswordWith2Chars() {
        performRegistrationTest("ab", false);
        attachUserData();
    }

    @Test
    @DisplayName("Регистрация с паролем из 4 символов")
    @Story("Валидация пароля при регистрации")
    @Description("Проверка отображения ошибки при вводе пароля длиной 4 символа")
    @Severity(SeverityLevel.NORMAL)
    public void testPasswordWith4Chars() {
        performRegistrationTest("abcd", false);
        attachUserData();
    }

    @Test
    @DisplayName("Регистрация с паролем из 5 символов")
    @Story("Валидация пароля при регистрации")
    @Description("Проверка отображения ошибки при вводе пароля длиной 5 символов")
    @Severity(SeverityLevel.NORMAL)
    public void testPasswordWith5Chars() {
        performRegistrationTest("abcde", false);
        attachUserData();
    }

    @Step("Выполнение теста регистрации с паролем: {password} (ожидаемый успех: {shouldSucceed})")
    private void performRegistrationTest(String password, boolean shouldSucceed) {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();
        attachScreenshot("Главная страница - нажата кнопка входа");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRegisterLink();
        attachScreenshot("Страница входа - нажата ссылка регистрации");

        RegistrationPage registrationPage = new RegistrationPage(driver);
        // Генерация случайных данных с помощью JavaFaker
        registrationPage.setName(faker.name().firstName());
        registrationPage.setEmail(faker.internet().emailAddress());
        registrationPage.setPassword(password);
        attachScreenshot("Форма регистрации - заполнены данные");

        registrationPage.clickRegisterButton();
        attachScreenshot("Форма регистрации - нажата кнопка регистрации");

        if (shouldSucceed) {
            checkSuccessfulRegistration(loginPage);
        } else {
            checkFailedRegistration(registrationPage);
        }
    }

    @Step("Проверка успешной регистрации (переход на страницу входа)")
    private void checkSuccessfulRegistration(LoginPage loginPage) {
        loginPage.isLoginPageUrl();
        attachScreenshot("Страница входа после успешной регистрации");
        assertTrue(loginPage.isLoginPageDisplayed());
    }

    @Step("Проверка неудачной регистрации (ожидаемая ошибка валидации пароля)")
    private void checkFailedRegistration(RegistrationPage registrationPage) {
        registrationPage.waitForPasswordErrorDisplayed();
        attachScreenshot("Форма регистрации - отображение ошибки");

        assertTrue("Должны остаться на странице регистрации",
                registrationPage.isRegistrationPage());

        String errorText = registrationPage.getPasswordErrorText();
        assertNotNull("Сообщение об ошибке должно отображаться", errorText);
        assertTrue("Текст ошибки должен содержать 'Некорректный пароль'",
                errorText.contains("Некорректный пароль"));
    }

    @Attachment(value = "Скриншот", type = "image/png")
    public byte[] attachScreenshot(String name) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Данные пользователя", type = "text/plain")
    public String attachUserData() {
        return "Данные для тестов регистрации генерируются через JavaFaker";
    }
}