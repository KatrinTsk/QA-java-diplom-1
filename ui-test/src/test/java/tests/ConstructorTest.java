package tests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.MainPage;
import utils.WebDriverFactory;
import io.qameta.allure.Step;

import static org.junit.Assert.assertEquals;

public class ConstructorTest extends BaseTest {

    @Before
    @Step("Инициализация драйвера и открытие главной страницы")
    public void setUp() {
        driver = WebDriverFactory.createDriver();
        driver.get("https://stellarburgers.nomoreparties.site/");
    }

    @Test
    @DisplayName("Проверка перехода к разделу 'Булки'")
    public void testNavigateToBunsSection() {
        MainPage mainPage = new MainPage(driver);

        navigateToSaucesSection(mainPage);
        navigateToBunsSection(mainPage);

        verifySectionText(mainPage, "Булки");
    }

    @Test
    @DisplayName("Переход к разделу 'Соусы'")
    public void testNavigateToSaucesSection() {
        MainPage mainPage = new MainPage(driver);

        navigateToSaucesSection(mainPage);

        verifySectionText(mainPage, "Соусы");
    }

    @Test
    @DisplayName("Переход к разделу 'Начинки'")
    public void testNavigateToFillingsSection() {
        MainPage mainPage = new MainPage(driver);

        navigateToFillingsSection(mainPage);

        verifySectionText(mainPage, "Начинки");
    }

    @Step("Переход в раздел 'Соусы'")
    private void navigateToSaucesSection(MainPage mainPage) {
        mainPage.clickSaucesSection();
    }

    @Step("Переход в раздел 'Булки'")
    private void navigateToBunsSection(MainPage mainPage) {
        mainPage.clickBunsSection();
    }

    @Step("Переход в раздел 'Начинки'")
    private void navigateToFillingsSection(MainPage mainPage) {
        mainPage.clickFillingsSection();
    }

    @Step("Проверка текста выбранного раздела (ожидаемый: {expectedText})")
    private void verifySectionText(MainPage mainPage, String expectedText) {
        assertEquals(expectedText, mainPage.getSelectedSectionText());
    }

    @After
    @Step("Закрытие драйвера")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}