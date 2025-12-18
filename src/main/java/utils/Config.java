package utils;

public class Config {

	public static final String BASE_URL = "https://the-internet.herokuapp.com";
	public static final String API_URL = "https://jsonplaceholder.typicode.com";

	public static final String USERNAME = getEnv("TEST_USERNAME", "tomsmith");
	public static final String PASSWORD = getEnv("TEST_PASSWORD", "SuperSecretPassword!");

	public static final int TIMEOUT = 10;

	private static String getEnv(String key, String defaultValue) {
		String value = System.getenv(key);
		return (value != null) ? value : defaultValue;
	}
}
