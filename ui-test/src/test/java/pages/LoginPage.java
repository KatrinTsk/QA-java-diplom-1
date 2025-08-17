package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private final WebDriver driver;

    // Локаторы элементов
    private final By emailInput = By.xpath(".//input[@name='name']");
    private final By passwordInput = By.xpath(".//input[@name='Пароль']");
    private final By loginButton = By.xpath(".//button[contains(@class, 'button_button__33qZ0') and text()='Войти']");
    private final By registerLink = By.xpath(".//a[text()='Зарегистрироваться']");
    private final By recoverPasswordLink = By.xpath(".//a[text()='Восстановить пароль']");
    private final By loginHeader = By.xpath(".//h2[text()='Вход']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Методы взаимодействия с элементами

    public void setEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    public void setPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public void clickRegisterLink() {
        driver.findElement(registerLink).click();
    }

    public void clickRecoverPasswordLink() {
        driver.findElement(recoverPasswordLink).click();
    }

    // Методы проверок состояния

    public boolean isLoginPageDisplayed() {
        try {
            return driver.findElement(loginHeader).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}