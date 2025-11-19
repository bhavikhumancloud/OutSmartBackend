package tests;

import api.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class ExampleTest extends BaseTest {

    @Test(description = "GET /posts returns 200 and list")
    public void getPostsShouldReturnList() {
        Response res = api.get("/posts", Map.of("userId", 1));
        Assert.assertEquals(res.getStatusCode(), 200, "Expected 200 OK");
        Assert.assertTrue(res.jsonPath().getList("$").size() > 0, "Expected non-empty list");
    }

    @Test(description = "POST /posts creates post")
    public void createPostShouldReturn201() {
        var body = Map.of("title", "foo", "body", "bar", "userId", 1);
        Response res = api.post("/posts", body);
        Assert.assertTrue(res.getStatusCode() == 201 || res.getStatusCode() == 200, "Expected 201/200");
        Assert.assertEquals(res.jsonPath().getString("title"), "foo");
    }
}
