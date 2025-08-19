package api.clients;

import api.BaseTest;
import io.restassured.response.Response;
import models.LoginRequest;

import static io.restassured.RestAssured.given;

public class AuthApiClient {

    public static Response login(LoginRequest loginRequest) {
        return given()
                .spec(BaseTest.getRequestSpec())
                .body(loginRequest)
                .post("/auth/login");
    }
}