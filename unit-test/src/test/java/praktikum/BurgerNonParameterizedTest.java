package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class BurgerNonParameterizedTest {

    private Burger burger;

    @Mock
    private Bun bun;

    @Mock
    private Ingredient ingredient;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        burger = new Burger();

        when(bun.getName()).thenReturn("Белая булочка");
        when(bun.getPrice()).thenReturn(100.0f);

        when(ingredient.getType()).thenReturn(IngredientType.FILLING);
        when(ingredient.getName()).thenReturn("Котлета");
        when(ingredient.getPrice()).thenReturn(50.0f);
    }

    @Test
    public void testGetPriceWithOnlyBun() {
        burger.setBuns(bun);
        float expectedPrice = 200.0f;
        assertEquals("Цена только с булочками должна быть правильной", expectedPrice, burger.getPrice(), 0.0f);
    }

    @Test
    public void testGetReceiptWithOnlyBun() {
        burger.setBuns(bun);
        String expectedReceipt = String.format(
                "(==== %s ====)%n(==== %s ====)%n%nPrice: %f%n",
                "Белая булочка",
                "Белая булочка",
                200.0f
        );
        assertEquals("Чек только с булочками должен быть правильным", expectedReceipt, burger.getReceipt());
    }

    @Test
    public void testMoveIngredientToSamePositionSizeRemains() {
        burger.addIngredient(ingredient);
        int originalSize = burger.ingredients.size();
        burger.moveIngredient(0, 0);
        assertEquals("Количество ингредиентов не должно измениться при перемещении на ту же позицию",
                originalSize, burger.ingredients.size());
    }

    @Test
    public void testMoveIngredientToSamePositionIngredientRemains() {
        burger.addIngredient(ingredient);
        burger.moveIngredient(0, 0);
        assertEquals("Ингредиент должен остаться на том же месте",
                ingredient, burger.ingredients.get(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveIngredientWithInvalidIndex() {
        burger.addIngredient(ingredient);
        burger.removeIngredient(5);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testMoveIngredientWithInvalidIndex() {
        burger.addIngredient(ingredient);
        burger.moveIngredient(0, 5);
    }
}