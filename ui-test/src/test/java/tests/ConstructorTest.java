package tests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import pages.MainPage;
import io.qameta.allure.Step;
import io.qameta.allure.Description;

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
    @Description("Проверка корректной работы навигации: Булки → Соусы → Булки")
    public void testNavigateToBunsSection() {
        // Проверяем, что изначально выбран раздел "Булки"
        assertEquals("Булки", mainPage.getSelectedSectionText().trim());

        // Переходим в "Соусы" и проверяем
        mainPage.clickSaucesSection();
        waitForSectionToLoad("Соусы");
        assertEquals("Соусы", mainPage.getSelectedSectionText().trim());

        // Возвращаемся в "Булки" и проверяем
        mainPage.clickBunsSection();
        waitForSectionToLoad("Булки");
        assertEquals("Булки", mainPage.getSelectedSectionText().trim());
    }

    @Test
    @DisplayName("Переход к разделу 'Соусы'")
    @Description("Проверка перехода из раздела 'Булки' в раздел 'Соусы'")
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
    @Description("Проверка перехода из раздела 'Булки' в раздел 'Начинки'")
    public void testNavigateToFillingsSection() {
        mainPage.clickFillingsSection();
        waitForSectionToLoad("Начинки");
        verifySectionText(mainPage, "Начинки");
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