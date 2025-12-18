package tests;

import api.ApiClient;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

public class ApiTest {

	@Test
	public void testGetAllPosts() {
		Response response = ApiClient.get("/posts");

		Assert.assertEquals(response.getStatusCode(), 200, "Status should be 200");

		List<?> posts = response.jsonPath().getList("$");
		Assert.assertFalse(posts.isEmpty(), "Posts list should not be empty");
		Assert.assertNotNull(response.jsonPath().get("[0].title"), "Post should have title");
	}

	@Test
	public void testNonExistentPost() {
		Response response = ApiClient.get("/posts/99999");

		Assert.assertEquals(response.getStatusCode(), 404, "Status should be 404 for non-existent post");
	}
}
