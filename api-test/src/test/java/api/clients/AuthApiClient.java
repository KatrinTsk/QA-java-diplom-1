package api.clients;

import api.BaseTest;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.LoginRequest;

import static io.restassured.RestAssured.given;

public class AuthApiClient {

    @Step("Авторизация пользователя")
    public static Response login(LoginRequest loginRequest) {
        return given()
                .spec(BaseTest.getRequestSpec())
                .body(loginRequest)
                .post("/auth/login");
    }
}