package api.clients;

import api.BaseTest;
import io.restassured.response.Response;
import models.User;

import static io.restassured.RestAssured.given;

public class UserApiClient {

    public static Response createUser(User user) {
        return given()
                .spec(BaseTest.getRequestSpec())
                .body(user)
                .post("/auth/register");
    }

    public static Response deleteUser(String accessToken) {
        return given()
                .spec(BaseTest.getRequestSpec())
                .header("Authorization", accessToken)
                .delete("/auth/user");
    }
}