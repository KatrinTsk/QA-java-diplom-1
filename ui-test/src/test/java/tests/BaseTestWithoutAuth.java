package tests;

import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import utils.WebDriverFactory;
import constants.Endpoints;

public class BaseTestWithoutAuth {
    protected WebDriver driver;

    @Before
    @Step("Инициализация драйвера и открытие главной страницы")
    public void setUp() {
        this.driver = WebDriverFactory.createDriver();
        driver.manage().deleteAllCookies();
        driver.get(Endpoints.HOME_PAGE);
    }

    @After
    @Step("Закрытие драйвера")
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.err.println("Ошибка при закрытии драйвера: " + e.getMessage());
            }
        }
    }
}