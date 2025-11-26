package api;

// java
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

public class ApiClient {
    private final String baseUrl;

    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Response get(String path, Map<String, ?> queryParams) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .queryParams(queryParams == null ? Map.of() : queryParams)
                .when()
                .get(baseUrl + path)
                .andReturn();
    }

    public Response getWithHeader(String path,String header, Map<String, ?> queryParams) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + header)
                .queryParams(queryParams == null ? Map.of() : queryParams)
                .when()
                .get(baseUrl + path)
                .andReturn();
    }

    public Response post(String path, Object body) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(baseUrl + path)
                .andReturn();
    }

    public Response postWithHeader(String path, String header,Object body) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + header)
                .body(body)
                .when()
                .post(baseUrl + path)
                .andReturn();
    }
}
