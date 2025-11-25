package tests;

import api.ApiClient;
import api.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class SignUpTests extends BaseTest {

    @Test(description = "Sign up endpoint /be/iam/api/signup/")
    public void signUpTest() {
        try{
            String path = "/be/iam/api/signup/";

            String uniqueEmail = "priti" + System.currentTimeMillis() + "@gmail.com";

            Map<String, String> body = Map.of(
                    "name", "Priti",
                    "email", uniqueEmail,
                    "phone_number", "+919526543264",
                    "password", "Test@123"
            );

            Response response = api.post(path,body);
            System.out.println("Response for sign up is: "+response.asString());
            // Validations
            Assert.assertEquals(response.getStatusCode(), 201, "Expected status code 201");
            Assert.assertEquals(response.jsonPath().getString("detail"), "Successful sign up.", "Expected success message");

        }catch (Exception e){
            e.printStackTrace();
            Assert.fail("Exception occurred during sign up test: " + e.getMessage());
        }

    }
}
