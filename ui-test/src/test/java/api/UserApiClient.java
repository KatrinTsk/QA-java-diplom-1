package api;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import constants.Endpoints;

public class UserApiClient {

    public static Response createUser(String email, String password, String name) {
        return given()
                .header("Content-type", "application/json")
                .body(String.format("{\"email\": \"%s\", \"password\": \"%s\", \"name\": \"%s\"}",
                        email, password, name))
                .post(Endpoints.API_REGISTER);
    }

    public static Response deleteUser(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .delete(Endpoints.API_USER);
    }
}