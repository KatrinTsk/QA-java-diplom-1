package tests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.MainPage;
import utils.WebDriverFactory;

import static org.junit.Assert.assertEquals;

public class ConstructorTest extends BaseTest {

    @Before
    public void setUp() {
        driver = WebDriverFactory.createDriver();
        driver.get("https://stellarburgers.nomoreparties.site/");
    }

    @Test
    @DisplayName("Проверка перехода к разделу 'Булки'")
    public void testNavigateToBunsSection() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickSaucesSection(); // Сначала переходим в другой раздел
        mainPage.clickBunsSection();

        assertEquals("Булки", mainPage.getSelectedSectionText());
    }

    @Test
    @DisplayName("Переход к разделу 'Соусы'")
    public void testNavigateToSaucesSection() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickSaucesSection();

        assertEquals("Соусы", mainPage.getSelectedSectionText());
    }

    @Test
    @DisplayName("Переход к разделу 'Начинки'")
    public void testNavigateToFillingsSection() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickFillingsSection();

        assertEquals("Начинки", mainPage.getSelectedSectionText());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}