// ConstructorTest.java
package tests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;
import io.qameta.allure.Step;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class ConstructorTest extends BaseTestWithoutAuth { // Изменили наследование

    private MainPage mainPage;

    @Before
    @Step("Дополнительная настройка для конструктора")
    public void setUpConstructor() {
        mainPage = new MainPage(driver);

        // Явное ожидание загрузки конструктора
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        mainPage.getSelectedSectionLocator()
                ));
    }

    @Test
    @DisplayName("Проверка перехода к разделу 'Булки'")
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
    public void testNavigateToSaucesSection() {
        // Проверка начального состояния
        assertEquals("Булки", mainPage.getSelectedSectionText());

        // Клик с ожиданием
        new WebDriverWait(driver, Duration.ofSeconds(10));
        mainPage.clickSaucesSection();

        // Проверка с очисткой текста
        String actualText = mainPage.getSelectedSectionText().trim();
        assertEquals("Ожидался раздел 'Соусы'", "Соусы", actualText);
    }

    @Test
    @DisplayName("Переход к разделу 'Начинки'")
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
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.textToBePresentInElementLocated(
                        mainPage.getSelectedSectionLocator(), sectionName));
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