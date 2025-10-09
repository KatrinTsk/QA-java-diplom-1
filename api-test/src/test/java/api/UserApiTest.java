package api;

import api.clients.UserApiClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.User;
import org.junit.After;
import org.junit.Test;
import utils.DataGenerator;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;

public class UserApiTest extends BaseTest {
    private User testUser;

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

        UserApiClient.createUser(testUser).then().statusCode(SC_OK);
        UserApiClient.createUser(testUser)
                .then()
                .statusCode(SC_FORBIDDEN)
                .body("success", is(false))
                .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Создание пользователя без email")
    @Description("Тест проверяет ошибку при создании пользователя без обязательного поля email")
    public void testCreateUserWithoutEmail() {
        testUser = new User(null, DataGenerator.generatePassword(), DataGenerator.generateName());

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
        testUser = new User(DataGenerator.generateEmail(), null, DataGenerator.generateName());

        UserApiClient.createUser(testUser)
                .then()
                .statusCode(SC_FORBIDDEN)
                .body("success", is(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя без имени")
    @Description("Тест проверяет ошибку при создании пользователя без обязательного поля name")
    public void testCreateUserWithoutName() {
        testUser = new User(DataGenerator.generateEmail(), DataGenerator.generatePassword(), null);

        UserApiClient.createUser(testUser)
                .then()
                .statusCode(SC_FORBIDDEN)
                .body("success", is(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @After
    public void tearDown() {
        if (testUser != null && testUser.getEmail() != null && testUser.getPassword() != null) {
            try {
                String token = getAccessToken(testUser.getEmail(), testUser.getPassword());
                deleteUser(token);
            } catch (AssertionError e) {
                // Ожидаемо для тестов с валидацией
            }
        }
    }
}