package api;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import utils.DataGenerator;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthApiTest extends BaseTest {
    private final String email = DataGenerator.generateEmail();
    private final String password = DataGenerator.generatePassword();
    private final String name = DataGenerator.generateName();
    private final String wrongEmail = "wrong" + email;
    private final String wrongPassword = "wrong" + password;

    @Test
    @DisplayName("Успешный вход в систему")
    public void testSuccessfulLogin() {
        // Сначала регистрируем пользователя
        given()
                .spec(requestSpec)
                .body(String.format("{\"email\": \"%s\", \"password\": \"%s\", \"name\": \"%s\"}",
                        email, password, name))
                .post("/auth/register");

        // Пытаемся войти
        given()
                .spec(requestSpec)
                .body(String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password))
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("success", is(true))
                .body("accessToken", startsWith("Bearer"))
                .body("refreshToken", notNullValue());
    }

    @Test
    @DisplayName("Вход с неверными учетными данными")
    public void testLoginWithInvalidCredentials() {
        given()
                .spec(requestSpec)
                .body(String.format("{\"email\": \"%s\", \"password\": \"%s\"}", wrongEmail, wrongPassword))
                .when()
                .post("/auth/login")
                .then()
                .statusCode(401)
                .body("success", is(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}