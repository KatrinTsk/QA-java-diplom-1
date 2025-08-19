package tests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import pages.MainPage;
import io.qameta.allure.Step;
import io.qameta.allure.Description; // ИЗМЕНЕНО: добавлен импорт

import static org.junit.Assert.assertEquals;

public class ConstructorTest extends BaseTestWithoutAuth {

    private MainPage mainPage;

    @Before
    @Step("Дополнительная настройка для конструктора")
    public void setUpConstructor() {
        mainPage = new MainPage(driver);
        mainPage.waitForConstructorLoaded();
    }

    @Test
    @DisplayName("Проверка перехода к разделу 'Булки'")
    @Description("Проверка корректной работы навигации: Булки → Соусы → Булки") // ИЗМЕНЕНО: добавлен @Description
    public void testNavigateToBunsSection() {
        // Проверяем, что изначально выбран раздел "Булки"
        assertEquals("Булки", mainPage.getSelectedSectionText().trim());

        // Переходим в "Соусы" и проверяем
        navigateToSaucesSection(mainPage);
        assertEquals("Соусы", mainPage.getSelectedSectionText().trim());

        // Возвращаемся в "Булки" и проверяем
        navigateToBunsSection(mainPage);
        assertEquals("Булки", mainPage.getSelectedSectionText().trim());
    }

    @Test
    @DisplayName("Переход к разделу 'Соусы'")
    @Description("Проверка перехода из раздела 'Булки' в раздел 'Соусы'") // ИЗМЕНЕНО: добавлен @Description
    public void testNavigateToSaucesSection() {
        // Проверка начального состояния
        assertEquals("Булки", mainPage.getSelectedSectionText());

        // Клик с ожиданием
        mainPage.clickSaucesSection();
        waitForSectionToLoad("Соусы");

        // Проверка с очисткой текста
        String actualText = mainPage.getSelectedSectionText().trim();
        assertEquals("Ожидался раздел 'Соусы'", "Соусы", actualText);
    }

    @Test
    @DisplayName("Переход к разделу 'Начинки'")
    @Description("Проверка перехода из раздела 'Булки' в раздел 'Начинки'") // ИЗМЕНЕНО: добавлен @Description
    public void testNavigateToFillingsSection() {
        navigateToFillingsSection(mainPage);
        verifySectionText(mainPage, "Начинки");
    }

    @Step("Переход в раздел 'Соусы'")
    private void navigateToSaucesSection(MainPage mainPage) {
        mainPage.clickSaucesSection();
        waitForSectionToLoad("Соусы");
    }

    @Step("Переход в раздел 'Булки'")
    private void navigateToBunsSection(MainPage mainPage) {
        mainPage.clickBunsSection();
        waitForSectionToLoad("Булки");
    }

    @Step("Переход в раздел 'Начинки'")
    private void navigateToFillingsSection(MainPage mainPage) {
        mainPage.clickFillingsSection();
        waitForSectionToLoad("Начинки");
    }

    @Step("Ожидание загрузки раздела: {sectionName}")
    private void waitForSectionToLoad(String sectionName) {
        mainPage.waitForSectionLoaded(sectionName);
    }

    @Step("Проверка текста выбранного раздела (ожидаемый: {expectedText})")
    private void verifySectionText(MainPage mainPage, String expectedText) {
        String actualText = mainPage.getSelectedSectionText();
        assertEquals(
                String.format("Ожидался раздел '%s', но отображается '%s'",
                        expectedText, actualText),
                expectedText,
                actualText
        );
    }
}