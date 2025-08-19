package api.clients;

import api.BaseTest;
import io.restassured.response.Response;
import models.OrderRequest;

import static io.restassured.RestAssured.given;

public class OrderApiClient {

    public static Response createOrder(OrderRequest orderRequest, String accessToken) {
        if (accessToken != null) {
            return given()
                    .spec(BaseTest.getRequestSpec())
                    .header("Authorization", accessToken)
                    .body(orderRequest)
                    .post("/orders");
        } else {
            return given()
                    .spec(BaseTest.getRequestSpec())
                    .body(orderRequest)
                    .post("/orders");
        }
    }
}