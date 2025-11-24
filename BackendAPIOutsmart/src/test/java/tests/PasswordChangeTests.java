package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class PasswordChangeTests {

    @Test
    public void passwordChangeTest() {

        // LOGIN to get token
        Map<String, String> loginBody = new HashMap<>();
        loginBody.put("identifier", "woxotof586@bipochub.com");
        loginBody.put("password", "Test@1234");  // Used updated password

        String accessToken =
                RestAssured
                        .given()
                        .contentType(ContentType.JSON)
                        .body(loginBody)
                        .when()
                        .post("/be/iam/api/signin/")
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .extract()
                        .jsonPath()
                        .getString("access");

        System.out.println("Token: " + accessToken);

        // STEP 2: PASSWORD CHANGE
        Map<String, String> passwordBody = new HashMap<>();
        passwordBody.put("current_password", "Test@1234"); // Old password
        passwordBody.put("new_password", "Test@12345");    // New password

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .body(passwordBody)
                .when()
                .post("/be/iam/api/password-change/")
                .then()
                .assertThat()
                .statusCode(200);

        System.out.println("Password changed successfully!");
    }
}