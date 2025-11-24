package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class SignInTests {

    @Test
    public void signInTest() {


        Map<String, String> body = Map.of(
                "identifier", "woxotof586@bipochub.com",
                "password", "Test@123"
        );

        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(body)
                .post("/be/iam/api/signin/");

        response.prettyPrint();

        // Validations
        Assert.assertEquals(response.statusCode(), 200, "Expected status code 200");

        Assert.assertNotNull(response.jsonPath().getString("access"),
                "Access token should not be null");

        Assert.assertNotNull(response.jsonPath().getString("refresh"),
                "Refresh token should not be null");
    }
}
