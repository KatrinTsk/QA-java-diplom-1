package api;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import utils.DataGenerator;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserApiTest extends BaseTest {
    private final String email = DataGenerator.generateEmail();
    private final String password = DataGenerator.generatePassword();
    private final String name = DataGenerator.generateName();

    @Test
    @DisplayName("Создание уникального пользователя")
    public void testCreateUniqueUser() {
        given()
                .spec(requestSpec)
                .body(String.format("{\"email\": \"%s\", \"password\": \"%s\", \"name\": \"%s\"}",
                        email, password, name))
                .when()
                .post("/auth/register")
                .then()
                .statusCode(200)
                .body("success", is(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());
    }

    @Test
    @DisplayName("Создание уже существующего пользователя")
    public void testCreateDuplicateUser() {
        // Сначала создаем пользователя
        given()
                .spec(requestSpec)
                .body(String.format("{\"email\": \"%s\", \"password\": \"%s\", \"name\": \"%s\"}",
                        email, password, name))
                .post("/auth/register");

        // Пытаемся создать такого же
        given()
                .spec(requestSpec)
                .body(String.format("{\"email\": \"%s\", \"password\": \"%s\", \"name\": \"%s\"}",
                        email, password, name))
                .when()
                .post("/auth/register")
                .then()
                .statusCode(403)
                .body("success", is(false))
                .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Создание пользователя без обязательного поля (email)")
    public void testCreateUserWithoutRequiredField() {
        given()
                .spec(requestSpec)
                .body(String.format("{\"password\": \"%s\", \"name\": \"%s\"}", password, name))
                .when()
                .post("/auth/register")
                .then()
                .statusCode(403)
                .body("success", is(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }
}