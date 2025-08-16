package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPage {
    private final WebDriver driver;

    // Локаторы элементов
    private final By loginButton = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By personalAccountButton = By.xpath(".//p[text()='Личный Кабинет']");
    private final By orderButton = By.xpath(".//button[text()='Оформить заказ']");
    private final By bunsSection = By.xpath(".//span[text()='Булки']/..");
    private final By saucesSection = By.xpath(".//span[text()='Соусы']/..");
    private final By fillingsSection = By.xpath(".//span[text()='Начинки']/..");
    private final By selectedSection = By.xpath(".//div[contains(@class, 'tab_tab_type_current')]");
    private final By constructorHeader = By.xpath(".//h1[text()='Соберите бургер']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // Методы взаимодействия с элементами

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public void clickPersonalAccountButton() {
        driver.findElement(personalAccountButton).click();
    }

    public void clickBunsSection() {
        driver.findElement(bunsSection).click();
    }

    public void clickSaucesSection() {
        driver.findElement(saucesSection).click();
    }

    public void clickFillingsSection() {
        driver.findElement(fillingsSection).click();
    }

    // Методы проверок состояния

    public boolean isOrderButtonDisplayed() {
        try {
            return driver.findElement(orderButton).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isAuthorized() {
        return isOrderButtonDisplayed();
    }

    public String getSelectedSectionText() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(selectedSection));
        return driver.findElement(selectedSection).getText();
    }

    public boolean isConstructorVisible() {
        try {
            return driver.findElement(constructorHeader).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // Методы ожидания

    public void waitUntilPageIsLoaded() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(constructorHeader));
    }

    public void waitUntilOrderButtonIsVisible() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(orderButton));
    }
}