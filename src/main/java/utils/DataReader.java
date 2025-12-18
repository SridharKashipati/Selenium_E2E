package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.List;
import java.util.Map;

public class DataReader {

	private static final ObjectMapper mapper = new ObjectMapper();

	@SuppressWarnings("unchecked")
	public static Map<String, Object> readJson(String filename) {
		try {
			return mapper.readValue(new File("data/" + filename), Map.class);
		} catch (Exception e) {
			throw new RuntimeException("Failed to read: " + filename, e);
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Map<String, String>> getTestData(String filename, String key) {
		Map<String, Object> data = readJson(filename);
		return (List<Map<String, String>>) data.get(key);
	}
}
