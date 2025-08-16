package api;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import utils.DataGenerator;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class OrderApiTest extends BaseTest {
    private final String email = DataGenerator.generateEmail();
    private final String password = DataGenerator.generatePassword();
    private final String name = DataGenerator.generateName();

    private final String[] ingredients = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"};
    private final String[] wrongIngredients = {"wrongIngredient1", "wrongIngredient2"};

    @Test
    @DisplayName("Создание заказа с авторизацией и ингредиентами")
    public void testCreateOrderWithAuthAndIngredients() {
        // Регистрируем пользователя
        given()
                .spec(requestSpec)
                .body(String.format("{\"email\": \"%s\", \"password\": \"%s\", \"name\": \"%s\"}",
                        email, password, name))
                .post("/auth/register");

        // Получаем токен
        String accessToken = getAccessToken(email, password);

        // Создаем заказ
        given()
                .spec(requestSpec)
                .header("Authorization", accessToken)
                .body(String.format("{\"ingredients\": [\"%s\", \"%s\"]}", ingredients[0], ingredients[1]))
                .when()
                .post("/orders")
                .then()
                .statusCode(200)
                .body("success", is(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void testCreateOrderWithoutAuth() {
        given()
                .spec(requestSpec)
                .body(String.format("{\"ingredients\": [\"%s\", \"%s\"]}", ingredients[0], ingredients[1]))
                .when()
                .post("/orders")
                .then()
                .statusCode(401)
                .body("success", is(false))
                .body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void testCreateOrderWithoutIngredients() {
        given()
                .spec(requestSpec)
                .body("{\"ingredients\": []}")
                .when()
                .post("/orders")
                .then()
                .statusCode(400)
                .body("success", is(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    public void testCreateOrderWithInvalidIngredients() {
        given()
                .spec(requestSpec)
                .body(String.format("{\"ingredients\": [\"%s\", \"%s\"]}",
                        wrongIngredients[0], wrongIngredients[1]))
                .when()
                .post("/orders")
                .then()
                .statusCode(500);
    }
}