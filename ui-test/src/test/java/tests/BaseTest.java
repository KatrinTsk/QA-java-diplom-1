package tests;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.MainPage;
import pages.RegistrationPage;

public class BaseTest {
    protected String name;
    protected String email;
    protected String password;
    protected WebDriver driver;

    @Step("Генерация тестового пользователя")
    public void GenerateUser() {
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
    }
}