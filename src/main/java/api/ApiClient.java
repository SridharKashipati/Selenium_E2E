package api;

import utils.Config;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiClient {

	private static final Logger log = LoggerFactory.getLogger(ApiClient.class);

	static {
		RestAssured.baseURI = Config.API_URL;
		log.info("API Base URI set to: {}", Config.API_URL);
	}

	public static Response get(String endpoint) {
		log.info("GET request: {}", endpoint);
		Response response = RestAssured.given().header("Content-Type", "application/json").when().get(endpoint);
		log.info("GET response status: {}", response.getStatusCode());
		return response;
	}

	public static Response post(String endpoint, Object body) {
		log.info("POST request: {}", endpoint);
		Response response = RestAssured.given().header("Content-Type", "application/json").body(body).when().post(endpoint);
		log.info("POST response status: {}", response.getStatusCode());
		return response;
	}
}
