package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ForgotPasswordTests {

    @Test
    public void forgotPasswordTest() {

        // Request Body
        String forgotPasswordBody = "{\"identifier\": \"woxotof586@bipochub.com\"}";

        // API Call
        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(forgotPasswordBody)
                .when()
                .post("/be/iam/api/password-reset/")
                .then()
                .extract().response();

        // Print response
        System.out.println(response.asPrettyString());

        // Assertions
        Assert.assertEquals(response.statusCode(), 200, "Status code mismatch!");
        Assert.assertEquals(response.jsonPath().getString("detail"),
                "If record exists, a reset link has been sent to the email",
                "Response message mismatch!");
    }
}

