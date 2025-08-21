package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class BurgerTest {

    private Burger burger;

    @Mock
    private Bun bun;

    @Mock
    private Ingredient firstIngredient;

    @Mock
    private Ingredient secondIngredient;

    private final String bunName;
    private final float bunPrice;
    private final IngredientType ingredientType;
    private final String ingredientName;
    private final float ingredientPrice;

    public BurgerTest(String bunName, float bunPrice, IngredientType ingredientType, String ingredientName, float ingredientPrice) {
        this.bunName = bunName;
        this.bunPrice = bunPrice;
        this.ingredientType = ingredientType;
        this.ingredientName = ingredientName;
        this.ingredientPrice = ingredientPrice;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Белая булочка", 100.0f, IngredientType.FILLING, "Котлета", 50.0f},
                {"Черная булочка", 150.0f, IngredientType.SAUCE, "Кетчуп", 30.0f}
        });
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        burger = new Burger();

        when(bun.getName()).thenReturn(bunName);
        when(bun.getPrice()).thenReturn(bunPrice);

        when(firstIngredient.getType()).thenReturn(ingredientType);
        when(firstIngredient.getName()).thenReturn(ingredientName);
        when(firstIngredient.getPrice()).thenReturn(ingredientPrice);

        when(secondIngredient.getType()).thenReturn(IngredientType.SAUCE);
        when(secondIngredient.getName()).thenReturn("Майонез");
        when(secondIngredient.getPrice()).thenReturn(20.0f);
    }

    @Test
    public void testSetBuns() {
        burger.setBuns(bun);
        assertEquals("Булочка должна быть установлена", bun, burger.bun);
    }

    @Test
    public void testAddIngredient() {
        burger.addIngredient(firstIngredient);
        assertEquals("Количество ингредиентов должно увеличиться", 1, burger.ingredients.size());
        assertEquals("Добавленный ингредиент должен соответствовать", firstIngredient, burger.ingredients.get(0));
    }

    @Test
    public void testRemoveIngredient() {
        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);

        burger.removeIngredient(0);

        assertEquals("Количество ингредиентов должно уменьшиться", 1, burger.ingredients.size());
        assertEquals("Оставшийся ингредиент должен быть правильным", secondIngredient, burger.ingredients.get(0));
    }

    @Test
    public void testMoveIngredientFromFirstToSecondPosition() {
        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);

        burger.moveIngredient(0, 1);

        assertEquals("Первый ингредиент должен измениться", secondIngredient, burger.ingredients.get(0));
        assertEquals("Второй ингредиент должен измениться", firstIngredient, burger.ingredients.get(1));
    }

    @Test
    public void testGetPriceWithBunAndTwoIngredients() {
        burger.setBuns(bun);
        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);

        float expectedPrice = bunPrice * 2 + ingredientPrice + 20.0f;
        assertEquals("Цена должна быть рассчитана правильно", expectedPrice, burger.getPrice(), 0.0f);
    }

    @Test
    public void testGetReceiptWithBunAndOneIngredient() {
        burger.setBuns(bun);
        burger.addIngredient(firstIngredient);

        String expectedReceipt = String.format(
                "(==== %s ====)%n= %s %s =%n(==== %s ====)%n%nPrice: %f%n",
                bunName,
                ingredientType.toString().toLowerCase(),
                ingredientName,
                bunName,
                bunPrice * 2 + ingredientPrice
        );

        assertEquals("Чек должен быть сформирован правильно", expectedReceipt, burger.getReceipt());
    }
}