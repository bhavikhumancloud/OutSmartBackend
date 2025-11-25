package tests;

import api.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class EmailVerifyTests extends BaseTest {

    @Test(description = "verify the email otp with expired using POST Endpoint /be/iam/email-verify/")
    public void emailVerificationWithExpiredOTPTest() {

        try {

            Map<String, String> body = new HashMap<>();
            body.put("otp", "BmfB6a");
            body.put("email", "woxotof586@bipochub.com");

            String path = "/be/iam/email-verify/";

            Response response = api.post(path,body);

            System.out.println("JSON Response is: "+response.asString());
            Assert.assertEquals(response.statusCode(), 404);
            Assert.assertEquals(response.jsonPath().getString("detail"),
                    "OTP is expired or invalid");

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception occurred during email verify test: " + e.getMessage());
        }
    }
}
