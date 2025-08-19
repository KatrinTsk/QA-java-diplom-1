package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.MainPage;
import pages.RegistrationPage;
import utils.WebDriverFactory;

import java.time.Duration;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
@Epic("Регистрация пользователя")
@Feature("Форма регистрации")
public class RegistrationTest extends BaseTest {

    @Before
    @Step("Инициализация драйвера и открытие главной страницы")
    public void setUp() {
        driver = WebDriverFactory.createDriver();
        driver.get("https://stellarburgers.nomoreparties.site/");
        attachPageUrl();
    }

    @Test
    @DisplayName("Успешная регистрация")
    @Story("Пользователь может зарегистрироваться с валидными данными")
    @Description("Проверка успешной регистрации с паролем из 6 символов")
    @Severity(SeverityLevel.BLOCKER)
    public void testSuccessfulRegistration() {
        performRegistrationTest("password123", true);
    }

    @Test
    @DisplayName("Регистрация с паролем из 1 символа")
    @Story("Валидация пароля при регистрации")
    @Description("Проверка отображения ошибки при вводе слишком короткого пароля")
    @Severity(SeverityLevel.CRITICAL)
    public void testPasswordWith1Char() {
        performRegistrationTest("a", false);
    }

    @Test
    @DisplayName("Регистрация с паролем из 2 символов")
    @Story("Валидация пароля при регистрации")
    @Severity(SeverityLevel.CRITICAL)
    public void testPasswordWith2Chars() {
        performRegistrationTest("ab", false);
    }

    @Test
    @DisplayName("Регистрация с паролем из 4 символов")
    @Story("Валидация пароля при регистрации")
    @Severity(SeverityLevel.NORMAL)
    public void testPasswordWith4Chars() {
        performRegistrationTest("abcd", false);
    }

    @Test
    @DisplayName("Регистрация с паролем из 5 символов")
    @Story("Валидация пароля при регистрации")
    @Severity(SeverityLevel.NORMAL)
    public void testPasswordWith5Chars() {
        performRegistrationTest("abcde", false);
    }

    @Step("Выполнение теста регистрации с паролем: {password} (ожидаемый успех: {shouldSucceed})")
    private void performRegistrationTest(String password, boolean shouldSucceed) {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();
        attachScreenshot("Главная страница - нажата кнопка входа");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRegisterLink();
        attachScreenshot("Страница входа - нажата ссылка регистрации");

        name = "TestUser";
        email = "Testuser" + System.currentTimeMillis() + "@example.com";
        attachUserData(name, email, password);

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.setName(name);
        registrationPage.setEmail(email);
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
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("/login"));
        attachScreenshot("Страница входа после успешной регистрации");
        assertTrue(loginPage.isLoginPageDisplayed());
    }

    @Step("Проверка неудачной регистрации (ожидаемая ошибка валидации пароля)")
    private void checkFailedRegistration(RegistrationPage registrationPage) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//p[contains(text(), 'Некорректный пароль')]")));
        attachScreenshot("Форма регистрации - отображение ошибки");

        assertTrue("Должны остаться на странице регистрации",
                driver.getCurrentUrl().contains("/register"));

        String errorText = registrationPage.getPasswordErrorText();
        assertNotNull("Сообщение об ошибке должно отображаться", errorText);
        assertTrue("Текст ошибки должен содержать 'Некорректный пароль'",
                errorText.contains("Некорректный пароль"));
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
    @Step("Закрытие драйвера")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}