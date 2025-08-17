package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.LoginPage;
import pages.MainPage;
import pages.PasswordRecoveryPage;
import pages.RegistrationPage;
import utils.WebDriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static org.junit.Assert.assertTrue;

@Epic("Авторизация пользователя")
@Feature("Формы входа")
public class LoginTest extends BaseTest {

    @Before
    public void setUp() {
        driver = WebDriverFactory.createDriver();
        driver.get("https://stellarburgers.nomoreparties.site/");
        attachPageUrl();
        GenerateUser();
        attachUserData(name, email, password);
    }

    @Test
    @DisplayName("Вход через кнопку 'Войти в аккаунт' на главной")
    @Story("Основной сценарий входа")
    @Description("Проверка входа через основную кнопку входа на главной странице")
    @Severity(SeverityLevel.BLOCKER)
    public void testLoginViaMainPageLoginButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();
        attachScreenshot("Главная страница - нажата кнопка входа");

        performLogin(mainPage);
    }

    @Test
    @DisplayName("Вход через кнопку 'Личный кабинет'")
    @Story("Альтернативные способы входа")
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginViaPersonalAccountButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccountButton();
        attachScreenshot("Главная страница - нажата кнопка личного кабинета");

        performLogin(mainPage);
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    @Story("Альтернативные способы входа")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginViaRegistrationForm() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();
        attachScreenshot("Главная страница - нажата кнопка входа");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRegisterLink();
        attachScreenshot("Страница входа - нажата ссылка регистрации");

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.clickLoginLink();
        attachScreenshot("Страница регистрации - нажата ссылка входа");

        performLogin(mainPage);
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    @Story("Альтернативные способы входа")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginViaPasswordRecoveryForm() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();
        attachScreenshot("Главная страница - нажата кнопка входа");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRecoverPasswordLink();
        attachScreenshot("Страница входа - нажата ссылка восстановления пароля");

        PasswordRecoveryPage passwordRecoveryPage = new PasswordRecoveryPage(driver);
        passwordRecoveryPage.clickLoginLink();
        attachScreenshot("Страница восстановления пароля - нажата ссылка входа");

        performLogin(mainPage);
    }

    @Step("Выполнение входа с email: {email} и паролем: {password}")
    private void performLogin(MainPage mainPage) {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.setEmail(email);
        loginPage.setPassword(password);
        attachScreenshot("Форма входа - заполнены учетные данные");

        loginPage.clickLoginButton();
        attachScreenshot("Форма входа - нажата кнопка входа");

        assertTrue("Кнопка 'Оформить заказ' должна отображаться после входа",
                mainPage.isOrderButtonDisplayed());
        attachScreenshot("Главная страница после успешного входа");
    }

    @Attachment(value = "Скриншот", type = "image/png")
    public byte[] attachScreenshot(String name) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "URL страницы", type = "text/plain")
    public String attachPageUrl() {
        return driver.getCurrentUrl();
    }

    @Attachment(value = "Данные пользователя", type = "text/plain")
    public String attachUserData(String name, String email, String password) {
        return String.format("Name: %s\nEmail: %s\nPassword: %s", name, email, password);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}