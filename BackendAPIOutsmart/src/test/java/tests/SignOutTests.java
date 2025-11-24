package tests;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class SignOutTests {

    @Test
    public void signInAndSignOutFlow() {

        RestAssured.baseURI = "https://outsmart-dev.outsmart-tech.com";

        // Log request / response
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        //  Sign In
        Map<String, String> signInBody = new HashMap<>();
        signInBody.put("identifier", "woxotof586@bipochub.com");
        signInBody.put("password", "Test@123");

        Response signInResponse = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(signInBody)
                .post("/be/iam/api/signin/");

        String access = signInResponse.jsonPath().getString("access");
        String refresh = signInResponse.jsonPath().getString("refresh");

        System.out.println("ACCESS TOKEN  = " + access);
        System.out.println("REFRESH TOKEN = " + refresh);

        // Validate login
        Assert.assertNotNull(access, "Access token is NULL");
        Assert.assertNotNull(refresh, "Refresh token is NULL");
        Assert.assertEquals(signInResponse.statusCode(), 200);

        // Sign Out
        Map<String, String> logoutBody = new HashMap<>();
        logoutBody.put("access", access);
        logoutBody.put("refresh", refresh);

        Response signOutResponse = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + access)
                .body(logoutBody)
                .post("/be/iam/api/signout/");

        Assert.assertEquals(signOutResponse.statusCode(), 200);
    }

}