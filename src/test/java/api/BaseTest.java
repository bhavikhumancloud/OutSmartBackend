package api;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public abstract class BaseTest {
    protected ApiClient api;

    @BeforeClass
    public void setup() {
        String base = Config.get("base.url");
        // optional RestAssured global config
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        api = new ApiClient(base);
    }
}

