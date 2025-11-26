package tests;

import api.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class CommunityTest extends BaseTest {

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

    @Test(dependsOnMethods = "signInTest", description = "Fetching the list of Community record from Endpoint GET /communities")
    public void testFetchCommunityRecords(ITestContext context) {
        try{
            String path = "/be/core/api/v1/community/";
            String access = context.getAttribute("access").toString();
            Map<String, Integer> body = new HashMap<>();
            body.put("page",1);

            Response response = api.getWithHeader(path, access,body);
            System.out.println("JSON Response is: "+response.asString());
            Assert.assertEquals(response.statusCode(),200);

        }catch (Exception e){
            e.printStackTrace();
            Assert.fail("Exception occurred while fetching community records: " + e.getMessage());
        }
    }

}
