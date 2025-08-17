package tests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.MainPage;
import pages.RegistrationPage;
import utils.WebDriverFactory;

import java.time.Duration;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class RegistrationTest extends BaseTest {

    @Before
    public void setUp() {
        driver = WebDriverFactory.createDriver();
        driver.get("https://stellarburgers.nomoreparties.site/");
    }

    @Test
    @DisplayName("Успешная регистрация")
    public void testSuccessfulRegistration() {
        performRegistrationTest("password123", true);
    }


    @Test
    @DisplayName("Регистрация с паролем из 1 символа")
    public void testPasswordWith1Char() {
        performRegistrationTest("a", false);
    }

    @Test
    @DisplayName("Регистрация с паролем из 2 символов")
    public void testPasswordWith2Chars() {
        performRegistrationTest("ab", false);
    }

    @Test
    @DisplayName("Регистрация с паролем из 4 символов")
    public void testPasswordWith4Chars() {
        performRegistrationTest("abcd", false);
    }

    @Test
    @DisplayName("Регистрация с паролем из 5 символов")
    public void testPasswordWith5Chars() {
        performRegistrationTest("abcde", false);
    }

    private void performRegistrationTest(String password, boolean shouldSucceed) {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRegisterLink();

        name = "TestUser";
        email = "Testuser" + System.currentTimeMillis() + "@example.com";

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.setName(name);
        registrationPage.setEmail(email);
        registrationPage.setPassword(password);
        registrationPage.clickRegisterButton();

        if (shouldSucceed) {
            // Проверяем успешную регистрацию
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.urlContains("/login"));
            assertTrue(loginPage.isLoginPageDisplayed());
        } else {
            // Проверяем наличие ошибки
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//p[contains(text(), 'Некорректный пароль')]")));

            assertTrue("Должны остаться на странице регистрации",
                    driver.getCurrentUrl().contains("/register"));

            String errorText = registrationPage.getPasswordErrorText();
            assertNotNull("Сообщение об ошибке должно отображаться", errorText);
            assertTrue("Текст ошибки должен содержать 'Некорректный пароль'",
                    errorText.contains("Некорректный пароль"));
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}