package api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;

import static org.apache.http.HttpStatus.*;

public class BaseTest {
    protected static RequestSpecification requestSpec;

    @BeforeClass
    public static void globalSetUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/api";

        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new AllureRestAssured())
                .build();
    }

    public static RequestSpecification getRequestSpec() {
        return requestSpec;
    }

    protected String getAccessToken(String email, String password) {
        models.LoginRequest loginRequest = new models.LoginRequest(email, password);

        return api.clients.AuthApiClient.login(loginRequest)
                .then()
                .statusCode(SC_OK)
                .extract()
                .path("accessToken");
    }

    protected void deleteUser(String accessToken) {
        if (accessToken != null) {
            api.clients.UserApiClient.deleteUser(accessToken)
                    .then()
                    .statusCode(SC_ACCEPTED);
        }
    }
}