package api;

import api.clients.AuthApiClient;
import api.clients.UserApiClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.LoginRequest;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.DataGenerator;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class AuthApiTest extends BaseTest {
    private User testUser;
    private String testAccessToken;

    @Before
    public void setUp() {
        testUser = new User(
                DataGenerator.generateEmail(),
                DataGenerator.generatePassword(),
                DataGenerator.generateName()
        );

        UserApiClient.createUser(testUser).then().statusCode(SC_OK);
        testAccessToken = getAccessToken(testUser.getEmail(), testUser.getPassword());
    }

    @Test
    @DisplayName("Успешный вход в систему")
    @Description("Тест проверяет успешный вход с валидными учетными данными")
    public void testSuccessfulLogin() {
        LoginRequest loginRequest = new LoginRequest(testUser.getEmail(), testUser.getPassword());

        AuthApiClient.login(loginRequest)
                .then()
                .statusCode(SC_OK)
                .body("success", is(true))
                .body("accessToken", startsWith("Bearer"))
                .body("refreshToken", notNullValue());
    }

    @Test
    @DisplayName("Вход с неверным email")
    @Description("Тест проверяет ошибку при входе с неверным email")
    public void testLoginWithInvalidEmail() {
        LoginRequest loginRequest = new LoginRequest("wrong_" + testUser.getEmail(), testUser.getPassword());

        AuthApiClient.login(loginRequest)
                .then()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Вход с неверным паролем")
    @Description("Тест проверяет ошибку при входе с неверным паролем")
    public void testLoginWithInvalidPassword() {
        LoginRequest loginRequest = new LoginRequest(testUser.getEmail(), "wrong_" + testUser.getPassword());

        AuthApiClient.login(loginRequest)
                .then()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", equalTo("email or password are incorrect"));
    }

    @After
    public void tearDown() {
        deleteUser(testAccessToken);
    }
}