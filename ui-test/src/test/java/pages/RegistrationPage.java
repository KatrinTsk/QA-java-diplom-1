package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class RegistrationPage {
    private final WebDriver driver;

    private final By nameInput = By.xpath(".//input[@name='name']");
    private final By emailInput = By.xpath(".//label[text()='Email']/../input");
    private final By passwordInput = By.xpath(".//input[@name='Пароль']");
    private final By registerButton = By.xpath(".//button[text()='Зарегистрироваться']");
    private final By loginLink = By.xpath(".//a[text()='Войти']");
    private final By passwordError = By.xpath(".//p[contains(@class, 'input__error')]");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Ввести имя: {name}")
    public void setName(String name) {
        driver.findElement(nameInput).sendKeys(name);
    }

    @Step("Ввести email: {email}")
    public void setEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    @Step("Ввести пароль")
    public void setPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    @Step("Нажать кнопку 'Зарегистрироваться'")
    public void clickRegisterButton() {
        driver.findElement(registerButton).click();
    }

    @Step("Нажать ссылку 'Войти'")
    public void clickLoginLink() {
        driver.findElement(loginLink).click();
    }

    public String getPasswordErrorText() {
        waitForPasswordErrorDisplayed();
        return driver.findElement(passwordError).getText();
    }

    public boolean waitForPasswordErrorDisplayed() {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(passwordError))
                .isDisplayed();
    }

    public boolean isRegistrationPage() {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/register"));
    }
}