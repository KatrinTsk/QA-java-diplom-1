package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import constants.Endpoints;

public class UserApiClient {

    @Step("Создать пользователя через API: {user.name}")
    public static Response createUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user) // Правильная сериализация через объект
                .post(Endpoints.API_REGISTER);
    }

    @Step("Создать пользователя через API: {name}")
    public static Response createUser(String email, String password, String name) {
        User user = new User(email, password, name);
        return createUser(user); // Делегируем основному методу
    }

    @Step("Удалить пользователя через API")
    public static Response deleteUser(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .delete(Endpoints.API_USER);
    }
}