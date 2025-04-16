package utils;

import com.jayway.jsonpath.JsonPath;

/**
 * Utility class to extract values from JSON responses using JSONPath.
 * Currently used to extract authentication token from response bodies.
 */
public class JsonUtils {

    /**
     * Extracts the token value from a JSON response string.
     *
     * @param response full JSON response string
     * @return the value of "token" if found; otherwise, null
     */
    public static String extractToken(String response) {
        try {
            return JsonPath.read(response, "$.token");
        } catch (Exception e) {
            System.err.println("[JsonUtils] Error: Unable to extract 'token' from JSON response.");
            return null;
        }
    }
}
