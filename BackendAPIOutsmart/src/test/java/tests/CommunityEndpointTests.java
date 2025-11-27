package tests;

import api.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommunityEndpointTests extends BaseTest {

    private String getAccessToken(ITestContext context) {
        return context.getAttribute("access").toString();
    }

    // -------------------- AUTH --------------------
    @Test(description = "Sign in user and store access/refresh tokens")
    public void signInTest(ITestContext context) {
        Map<String, String> body = Map.of(
                "identifier", "bhavik.mohod@humancloud.co.in",
                "password", "Test@1234"
        );
        Response response = api.post("/be/iam/api/signin/", body);
        response.then().log().all();
        Assert.assertEquals(response.statusCode(), 200, "Sign-in failed");

        context.setAttribute("access", response.jsonPath().getString("access"));
        context.setAttribute("refresh", response.jsonPath().getString("refresh"));
    }

    // -------------------- COMMUNITY --------------------
    @Test( dependsOnMethods = "signInTest", description = "Create a new community")
    public void createCommunityTest(ITestContext context) {
        String accessToken = getAccessToken(context);

        String communityName = "OutSmart_" + System.currentTimeMillis(); // dynamic name

        Map<String, Object> body = new HashMap<>();
        body.put("name", communityName);
        body.put("note", "Community");
        body.put("status", "active");
        body.put("visibility", "private");
        body.put("polygon", List.of(
                List.of(1.0, 1.0),
                List.of(2.0, 2.0),
                List.of(3.0, 3.0)
        ));

        Response response = api.postWithHeader("/be/core/api/v1/community/", accessToken, body);
        response.then().log().all();
        Assert.assertEquals(response.statusCode(), 201, "Community creation failed");

        String communityId = response.jsonPath().getString("id");
        Assert.assertNotNull(communityId, "Community ID should not be null");
        context.setAttribute("communityId", communityId);
    }

    @Test(dependsOnMethods = "signInTest", description = "List communities")
    public void listCommunitiesTest(ITestContext context) {
        Map<String, Integer> queryParams = Map.of("page", 1);
        Response response = api.getWithHeader("/be/core/api/v1/community/", getAccessToken(context), queryParams);
        response.then().log().all();
        Assert.assertEquals(response.statusCode(), 200);
    }


    @Test(priority=2,dependsOnMethods = "createCommunityTest", description = "Retrieve a community")
    public void retrieveCommunityTest(ITestContext context) {
        String id = context.getAttribute("communityId").toString();
        Response response = api.getWithHeader("/be/core/api/v1/community/" + id + "/", getAccessToken(context), null);
        response.then().log().all();
        Assert.assertEquals(response.statusCode(), 200);
    }


    @Test(dependsOnMethods = "createCommunityTest", description = "Update an existing community")
    public void updateCommunityTest(ITestContext context) {
        String communityId = context.getAttribute("communityId").toString();
        String accessToken = getAccessToken(context);

        Map<String, Object> body = new HashMap<>();
        body.put("name", "Updated_Community_" + System.currentTimeMillis());
        body.put("note", "Updated note for community");
        body.put("status", "active");
        body.put("visibility", "public");
        body.put("polygon", List.of(
                List.of(4.0, 4.0),
                List.of(5.0, 5.0),
                List.of(6.0, 6.0)
        ));

        Response response = api.putWithHeader("/be/core/api/v1/community/" + communityId + "/", accessToken, body);
        response.then().log().all();

        Assert.assertEquals(response.statusCode(), 200, "Community update failed");

        // store updated name for future tests
        context.setAttribute("communityName", body.get("name"));
    }


    @Test(dependsOnMethods = "retrieveCommunityTest", description = "Delete a community")
    public void deleteCommunityTest(ITestContext context) {
        String id = context.getAttribute("communityId").toString();
        Response response = api.deleteWithHeader("/be/core/api/v1/community/" + id + "/", getAccessToken(context));
        response.then().log().all();
        Assert.assertEquals(response.statusCode(), 200); // updated to match API
    }

    // -------------------- GEO AREA --------------------
    @Test(dependsOnMethods = "createCommunityTest", description = "Create a geo area within a community")
    public void createGeoAreaTest(ITestContext context) {
        String communityId = context.getAttribute("communityId").toString();
        Map<String, Object> body = new HashMap<>();
        body.put("name", "GeoArea_" + System.currentTimeMillis());
        body.put("note", "Geo note");
        body.put("polygon", List.of(
                List.of(0.0, 0.0),
                List.of(0.0, 1.0),
                List.of(1.0, 1.0)
        ));

        Response response = api.postWithHeader("/be/core/api/v1/community/" + communityId + "/geo-area/", getAccessToken(context), body);
        response.then().log().all();
        Assert.assertEquals(response.statusCode(), 201);
        context.setAttribute("geoAreaId", response.jsonPath().getString("id"));
    }

    // -------------------- NEARBY COMMUNITIES --------------------
    @Test(dependsOnMethods = "signInTest", description = "Get nearby communities based on location")
    public void nearbyCommunitiesTest(ITestContext context) {
        Map<String, Object> body = Map.of(
                "latitude", 0.0,
                "longitude", 0.0,
                "radius_km", 5
        );
        Response response = api.postWithHeader("/be/core/api/v1/community/nearby/?page=1", getAccessToken(context), body);
        response.then().log().all();
        Assert.assertEquals(response.statusCode(), 200);
    }

    // -------------------- GROUP --------------------
    @Test(dependsOnMethods = "signInTest", description = "Create a new group")
    public void createGroupTest(ITestContext context) {
        Map<String, Object> body = new HashMap<>();
        body.put("name", "MyGroup_" + System.currentTimeMillis());
        body.put("note", "Group note");
        body.put("status", "active");
        body.put("visibility", "public");

        Response response = api.postWithHeader("/be/core/api/v1/group/", getAccessToken(context), body);
        response.then().log().all();
        Assert.assertEquals(response.statusCode(), 201);
        context.setAttribute("groupId", response.jsonPath().getString("id"));
    }

    @Test(dependsOnMethods = "createGroupTest", description = "Retrieve a group")
    public void retrieveGroupTest(ITestContext context) {
        String id = context.getAttribute("groupId").toString();
        Response response = api.getWithHeader("/be/core/api/v1/group/" + id + "/", getAccessToken(context), null);
        response.then().log().all();
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(dependsOnMethods = "createGroupTest", description = "Update an existing group")
    public void updateGroupTest(ITestContext context) {
        String groupId = context.getAttribute("groupId").toString();
        String accessToken = getAccessToken(context);

        // Dynamic update values
        Map<String, Object> body = new HashMap<>();
        body.put("name", "Updated_Group_" + System.currentTimeMillis());
        body.put("note", "Updated note for group");
        body.put("status", "active"); // or "inactive" if API supports
        body.put("visibility", "private");

        Response response = api.putWithHeader("/be/core/api/v1/group/" + groupId + "/", accessToken, body);
        response.then().log().all();

        Assert.assertEquals(response.statusCode(), 200, "Group update failed");

        // Store updated name for future tests
        context.setAttribute("groupName", body.get("name"));
    }

    @Test(dependsOnMethods = "updateGroupTest", description = "Delete the updated group")
    public void deleteGroupTest(ITestContext context) {
        String groupId = context.getAttribute("groupId").toString();
        String accessToken = getAccessToken(context);

        Response response = api.deleteWithHeader("/be/core/api/v1/group/" + groupId + "/", accessToken);
        response.then().log().all();

        Assert.assertEquals(response.statusCode(), 200, "Group deletion failed");
    }



    @Test(dependsOnMethods = "createGroupTest", description = "List group users")
    public void listGroupUsersTest(ITestContext context) {
        String id = context.getAttribute("groupId").toString();
        Response response = api.getWithHeader("/be/core/api/v1/group/" + id + "/user/?page=1", getAccessToken(context), null);
        response.then().log().all();
        Assert.assertEquals(response.statusCode(), 200);
    }

    // -------------------- USER --------------------
    @Test(dependsOnMethods = "signInTest", description = "Get logged-in user profile")
    public void getUserProfileTest(ITestContext context) {
        Response response = api.getWithHeader("/be/core/api/v1/user/profile/", getAccessToken(context), null);
        response.then().log().all();
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(dependsOnMethods = "signInTest", description = "List user's communities")
    public void listUserCommunitiesTest(ITestContext context) {
        Response response = api.getWithHeader("/be/core/api/v1/user/community/?page=1", getAccessToken(context), null);
        response.then().log().all();
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(dependsOnMethods = "signInTest", description = "List user's groups")
    public void listUserGroupsTest(ITestContext context) {
        Response response = api.getWithHeader("/be/core/api/v1/user/group/?page=1", getAccessToken(context), null);
        response.then().log().all();
        Assert.assertEquals(response.statusCode(), 200);
    }

    // -------------------- EMERGENCY --------------------
    @Test(dependsOnMethods = "signInTest", description = "Send emergency notification")
    public void emergencyNotifyTest(ITestContext context) {
        String emergencyId = "<valid-emergency-id>"; // replace with actual emergency ID
        Response response = api.getWithHeader("/be/core/api/v1/emergency/" + emergencyId + "/notify/", getAccessToken(context), null);
        response.then().log().all();
        Assert.assertEquals(response.statusCode(), 200);
    }
}
