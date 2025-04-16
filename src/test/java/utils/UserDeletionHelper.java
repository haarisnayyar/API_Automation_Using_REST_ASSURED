package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Utility class to help delete a user from the Reqres API using a given user ID.
 * Validates expected responses such as 204 (success) and 404 (not found).
 */
public class UserDeletionHelper {

    /**
     * Sends a DELETE request to remove a user with the given ID.
     * Handles and logs expected status codes (204, 404) with appropriate messages.
     *
     * @param userId ID of the user to be deleted
     */
    public static void deleteUser(String userId) {
        // Fetch base URL from config, fallback if not found
        String baseUrl = ConfigReader.getProperty("base_url");
        if (baseUrl == null || baseUrl.isEmpty() || "Property Not Found".equals(baseUrl)) {
            baseUrl = "https://reqres.in"; // Default fallback
        }

        RestAssured.baseURI = baseUrl;

        // Send DELETE request
        Response response = given()
                .when()
                .delete("/api/users/" + userId)
                .then()
                .extract().response();

        int statusCode = response.getStatusCode();

        // Handle and log result
        if (statusCode == 204) {
            System.out.println("[UserDeletionHelper] User with ID " + userId + " was deleted successfully (204).");
        } else if (statusCode == 404) {
            System.out.println("[UserDeletionHelper] User with ID " + userId + " not found (404) — likely already deleted.");
        } else {
            throw new RuntimeException("[UserDeletionHelper] Unexpected status code received: " + statusCode);
        }
    }
}
