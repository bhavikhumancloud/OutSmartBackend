package api;

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



        // GET with auth + query params
        public Response getWithHeader(String path, String token, Map<String, ?> queryParams) {
            return RestAssured.given()
                    .contentType(ContentType.JSON)
                    .header("Authorization", "Bearer " + token)
                    .queryParams(queryParams == null ? Map.of() : queryParams)
                    .get(baseUrl + path)
                    .andReturn();
        }


        public Response put(String path, Object body) {
            return RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(body)
                    .put(baseUrl + path)
                    .andReturn();
        }

        public Response putWithHeader(String path, String token, Object body) {
            return RestAssured.given()
                    .contentType(ContentType.JSON)
                    .header("Authorization", "Bearer " + token)
                    .body(body)
                    .put(baseUrl + path)
                    .andReturn();
        }


        public Response patch(String path, Object body) {
            return RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(body)
                    .patch(baseUrl + path)
                    .andReturn();
        }


        public Response patchWithHeader(String path, String token, Object body) {
            return RestAssured.given()
                    .contentType(ContentType.JSON)
                    .header("Authorization", "Bearer " + token)
                    .body(body)
                    .patch(baseUrl + path)
                    .andReturn();
        }


        public Response deleteWithHeader(String path, String token) {
            return RestAssured.given()
                    .contentType(ContentType.JSON)
                    .header("Authorization", "Bearer " + token)
                    .delete(baseUrl + path)
                    .andReturn();
        }


        public Response deleteWithHeader(String path, String token, Object body) {
            return RestAssured.given()
                    .contentType(ContentType.JSON)
                    .header("Authorization", "Bearer " + token)
                    .body(body)
                    .delete(baseUrl + path)
                    .andReturn();
        }
    }
