package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

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
    @Step("Ввести email: {email}")
    public void setEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    @Step("Ввести пароль")
    public void setPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    @Step("Нажать кнопку 'Войти'")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Нажать ссылку 'Зарегистрироваться'")
    public void clickRegisterLink() {
        driver.findElement(registerLink).click();
    }

    @Step("Нажать ссылку 'Восстановить пароль'")
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

    public boolean isLoginPageUrl() {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/login"));
    }
}