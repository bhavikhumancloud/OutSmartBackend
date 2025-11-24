package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class SignUpTests {

    @Test(description = "Verify user signup returns 201 and success message")
    public void signUpTest() {



        // Dynamic email so each test run uses a fresh email
        String uniqueEmail = "priti" + System.currentTimeMillis() + "@gmail.com";

        // Request Body
        Map<String, String> requestBody = Map.of(
                "name", "Priti",
                "email", uniqueEmail,
                "phone_number", "+919526543265",
                "password", "Test@123"
        );

        // API request
        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post("/be/iam/api/signup/");

        // Print Response
        response.prettyPrint();

        // Validations
        Assert.assertEquals(response.getStatusCode(), 201, "Expected status code 201");
        Assert.assertEquals(response.jsonPath().getString("detail"), "Successful sign up.", "Expected success message");
    }
}
