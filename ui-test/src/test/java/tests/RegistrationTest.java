package tests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.LoginPage;
import pages.MainPage;
import pages.RegistrationPage;
import utils.WebDriverFactory;

import static org.junit.Assert.assertTrue;

public class RegistrationTest extends BaseTest{

    @Before
    public void setUp() {
        driver = WebDriverFactory.createDriver(); // Исправлено здесь
        driver.get("https://stellarburgers.nomoreparties.site/");
    }

    @Test
    @DisplayName("Успешная регистрация")
    public void testSuccessfulRegistration() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRegisterLink();

        name = "TestUser";
        email = "Testuser" + System.currentTimeMillis() + "@example.com";
        password = "password123";

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.setName(name);
        registrationPage.setEmail(email);
        registrationPage.setPassword(password);
        registrationPage.clickRegisterButton();

        assertTrue(loginPage.isLoginPageDisplayed());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}