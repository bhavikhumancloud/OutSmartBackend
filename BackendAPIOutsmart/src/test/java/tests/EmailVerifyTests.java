package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class EmailVerifyTests {

    @Test
    public void emailVerifyTest() {

        Map<String, String> emailBody = new HashMap<>();
        emailBody.put("otp", "BmfB6a");
        emailBody.put("email", "woxotof586@bipochub.com");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(emailBody)
                .post("/be/iam/email-verify/");

        response.prettyPrint();

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("detail"),
                "Email verified successfully.");

        Assert.assertEquals(response.statusCode(), 400);
        Assert.assertEquals(response.jsonPath().getString("detail"),
                "Email verification failed");

    }
}
