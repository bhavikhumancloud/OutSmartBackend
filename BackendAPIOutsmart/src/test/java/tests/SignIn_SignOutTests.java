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

    @Test(description = "Sign in using POST endpoint /be/iam/api/signin")
    public void signInTest(ITestContext context) {
        try {
            String path = "/be/iam/api/signin/";
            Map<String, String> body = Map.of(

                    "identifier", "bhavik.mohod@humancloud.co.in",
                    "password", "Test@1234"
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
            Assert.fail("Exception occurred during sign in test: " + e.getMessage());
        }

    }

    @Test(dependsOnMethods = "passwordChangedTest" , description = "sign out using POST endpoint /be/iam/api/signout/")
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


    @Test(dependsOnMethods = "signInTest", description = "Update the password with POST endpoint /be/iam/api/password-change/")
    public void passwordChangedTest(ITestContext context){
        try{
        String path = "/be/iam/api/password-change/";
        String access = context.getAttribute("access").toString();
            Map<String, String> body = new HashMap<>();
            body.put("current_password", "Test@123");
            body.put("new_password", "Test@1234");
        Response response = api.postWithHeader(path,access,body);

        System.out.println("JSON Response for password changes is: "+response.asString());
        Assert.assertEquals(response.statusCode(),200);

        }catch (Exception e){
            e.printStackTrace();
            Assert.fail("Exception occurred during sign up test: " + e.getMessage());
        }
    }

    @Test(description = "Forgot password using POST Endpoint /be/iam/api/password-reset/")
    public void forgotPasswordTest(){
        try{

            String path = "/be/iam/api/password-reset/";
            Map<String, String> body = new HashMap<>();
            body.put("identifier","bhavik.mohod@humancloud.co.in");

            Response response  = api.post(path,body);
            System.out.println("Forgot password response is: "+response.asString());
            Assert.assertEquals(response.statusCode(),200);
            Assert.assertEquals(response.jsonPath().getString("detail"),
                    "If record exists, a reset link has been sent to the email",
                    "Response message mismatch!");

        }catch (Exception e){
            e.printStackTrace();
            Assert.fail("Exception occurred during forgot password test: " + e.getMessage());
        }
    }
}
