package tests;

import api.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class SignIn_SignOutTests extends BaseTest {
    String access;
    String refresh;

    @Test(description = "Sign in using endpoint /be/iam/api/signin")
    public void signInTest(ITestContext context) {
        try {
            String path = "/be/iam/api/signin/";
            Map<String, String> body = Map.of(

                    "identifier", "bhavik.mohod@humancloud.co.in",
                    "password", "Test@123"
            );

            Response response = api.post(path, body);

            Assert.assertEquals(response.statusCode(), 200, "Expected status code 200");

            Assert.assertNotNull(response.jsonPath().getString("access"),
                    "Access token should not be null");

            Assert.assertNotNull(response.jsonPath().getString("refresh"),
                    "Refresh token should not be null");
            System.out.println("sign out endpoint response: "+response.asString());
            context.setAttribute("access",response.getBody().jsonPath().get("access"));
            context.setAttribute("refresh",response.getBody().jsonPath().get("refresh"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test(dependsOnMethods = "signInTest" , description = "sign out using endpoint /be/iam/api/signout/")
    public void signoutest(ITestContext context) {
        try {
            access = context.getAttribute("access").toString();
            refresh = context.getAttribute("refresh").toString();
            Map<String, String> body = new HashMap<>();
            body.put("access", access);
            body.put("refresh", refresh);

            String path = "/be/iam/api/signout/";

            Response response = api.postWithHeader(path,access,body);
            System.out.println("sign out endpoint response: "+response.asString());
            Assert.assertEquals(response.statusCode(), 200);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
