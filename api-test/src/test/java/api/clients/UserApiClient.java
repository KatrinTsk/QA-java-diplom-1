package api.clients;

import api.BaseTest;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.User;

import static io.restassured.RestAssured.given;

public class UserApiClient {

    @Step("Создание пользователя")
    public static Response createUser(User user) {
        return given()
                .spec(BaseTest.getRequestSpec())
                .body(user)
                .post("/auth/register");
    }

    @Step("Удаление пользователя")
    public static Response deleteUser(String accessToken) {
        return given()
                .spec(BaseTest.getRequestSpec())
                .header("Authorization", accessToken)
                .delete("/auth/user");
    }
}