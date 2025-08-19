package api;

import api.clients.UserApiClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.User;
import org.junit.After;
import org.junit.Test;
import utils.DataGenerator;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class UserApiTest extends BaseTest {
    private User testUser;
    private String testAccessToken;

    protected String getAccessToken(String email, String password) {
        models.LoginRequest loginRequest = new models.LoginRequest(email, password);

        return api.clients.AuthApiClient.login(loginRequest)
                .then()
                .statusCode(SC_OK)
                .extract()
                .path("accessToken");
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    @Description("Тест проверяет успешное создание пользователя с валидными данными")
    public void testCreateUniqueUser() {
        testUser = new User(
                DataGenerator.generateEmail(),
                DataGenerator.generatePassword(),
                DataGenerator.generateName()
        );

        UserApiClient.createUser(testUser)
                .then()
                .statusCode(SC_OK)
                .body("success", is(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());

        testAccessToken = getAccessToken(testUser.getEmail(), testUser.getPassword());
    }

    @Test
    @DisplayName("Создание уже существующего пользователя")
    @Description("Тест проверяет ошибку при попытке создать пользователя с уже существующим email")
    public void testCreateDuplicateUser() {
        testUser = new User(
                DataGenerator.generateEmail(),
                DataGenerator.generatePassword(),
                DataGenerator.generateName()
        );

        // Сначала создаем пользователя
        UserApiClient.createUser(testUser)
                .then()
                .statusCode(SC_OK);

        // Пытаемся создать такого же
        UserApiClient.createUser(testUser)
                .then()
                .statusCode(SC_FORBIDDEN)
                .body("success", is(false))
                .body("message", equalTo("User already exists"));

        testAccessToken = getAccessToken(testUser.getEmail(), testUser.getPassword());
    }

    @Test
    @DisplayName("Создание пользователя без email")
    @Description("Тест проверяет ошибку при создании пользователя без обязательного поля email")
    public void testCreateUserWithoutEmail() {
        testUser = new User(
                null,
                DataGenerator.generatePassword(),
                DataGenerator.generateName()
        );

        UserApiClient.createUser(testUser)
                .then()
                .statusCode(SC_FORBIDDEN)
                .body("success", is(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя без пароля")
    @Description("Тест проверяет ошибку при создании пользователя без обязательного поля password")
    public void testCreateUserWithoutPassword() {
        testUser = new User(
                DataGenerator.generateEmail(),
                null,
                DataGenerator.generateName()
        );

        UserApiClient.createUser(testUser)
                .then()
                .statusCode(SC_FORBIDDEN)
                .body("success", is(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя без имени")
    @Description("Тест проверяет ошибку при создании пользователя без обязательного поле name")
    public void testCreateUserWithoutName() {
        testUser = new User(
                DataGenerator.generateEmail(),
                DataGenerator.generatePassword(),
                null
        );

        UserApiClient.createUser(testUser)
                .then()
                .statusCode(SC_FORBIDDEN)
                .body("success", is(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @After
    public void tearDown() {
        deleteUser(testAccessToken);
    }
}