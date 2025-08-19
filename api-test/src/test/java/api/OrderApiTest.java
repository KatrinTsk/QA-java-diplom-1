package api;

import api.clients.OrderApiClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.OrderRequest;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.DataGenerator;

import java.util.Arrays;
import java.util.Collections;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class OrderApiTest extends BaseTest {
    private User testUser;
    private String testAccessToken;
    private final String[] ingredients = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"};
    private final String[] wrongIngredients = {"wrongIngredient1", "wrongIngredient2"};

    @Before
    public void setUpTestUser() {
        testUser = new User(
                DataGenerator.generateEmail(),
                DataGenerator.generatePassword(),
                DataGenerator.generateName()
        );

        // Регистрируем пользователя перед тестами
        api.clients.UserApiClient.createUser(testUser)
                .then()
                .statusCode(SC_OK);

        testAccessToken = getAccessToken(testUser.getEmail(), testUser.getPassword());
    }

    protected String getAccessToken(String email, String password) {
        models.LoginRequest loginRequest = new models.LoginRequest(email, password);

        return api.clients.AuthApiClient.login(loginRequest)
                .then()
                .statusCode(SC_OK)
                .extract()
                .path("accessToken");
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и ингредиентами")
    @Description("Тест проверяет успешное создание заказа с авторизацией и валидными ингредиентами")
    public void testCreateOrderWithAuthAndIngredients() {
        OrderRequest orderRequest = new OrderRequest(Arrays.asList(ingredients));

        OrderApiClient.createOrder(orderRequest, testAccessToken)
                .then()
                .statusCode(SC_OK)
                .body("success", is(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    @Description("Тест проверяет ошибку при создании заказа без авторизации")
    public void testCreateOrderWithoutAuth() {
        OrderRequest orderRequest = new OrderRequest(Arrays.asList(ingredients));

        OrderApiClient.createOrder(orderRequest, null)
                .then()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    @Description("Тест проверяет ошибку при создании заказа без ингредиентов")
    public void testCreateOrderWithoutIngredients() {
        OrderRequest orderRequest = new OrderRequest(Collections.emptyList());

        OrderApiClient.createOrder(orderRequest, testAccessToken)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("success", is(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    @Description("Тест проверяет ошибку при создании заказа с невалидными ингредиентами")
    public void testCreateOrderWithInvalidIngredients() {
        OrderRequest orderRequest = new OrderRequest(Arrays.asList(wrongIngredients));

        OrderApiClient.createOrder(orderRequest, testAccessToken)
                .then()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @After
    public void tearDown() {
        deleteUser(testAccessToken);
    }
}