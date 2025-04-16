package utils;

import base.RestAssuredCrudRunner;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;
import static base.RestAssuredCrudRunner.requestSpec;

/**
 * Utility class for performing HTTP operations (GET, POST, PUT, PATCH, DELETE)
 * using the Rest Assured framework with centralized request spec and Allure integration.
 */
public class ApiUtils {

    /**
     * Ensures a valid and initialized RequestSpecification is used.
     * Reinitializes if null (usually caused by skipped setup).
     *
     * @return request specification with base URI and headers
     */
    private static RequestSpecification getRequestSpec() {
        if (requestSpec == null) {
            System.err.println("Request specification is null. Reinitializing...");
            new RestAssuredCrudRunner().setup(); // fallback to base setup
        }
        return requestSpec;
    }

    /**
     * Sends a GET request to the given endpoint.
     *
     * @param endpoint relative URI
     * @return API response
     */
    public static Response sendGetRequest(String endpoint) {
        Allure.step("GET Request: " + endpoint);

        Response response = given(getRequestSpec())
                .when()
                .get(endpoint)
                .then()
                .extract().response();

        logResponse("GET", endpoint, response);
        return response;
    }

    /**
     * Sends a POST request with JSON body.
     *
     * @param endpoint    relative URI
     * @param requestBody request payload
     * @return API response
     */
    public static Response sendPostRequest(String endpoint, JSONObject requestBody) {
        Allure.step("POST Request: " + endpoint);

        Response response = given(getRequestSpec())
                .body(requestBody.toString())
                .when()
                .post(endpoint)
                .then()
                .extract().response();

        logResponse("POST", endpoint, response);
        return response;
    }

    /**
     * Sends a PUT request with JSON body.
     *
     * @param endpoint    relative URI
     * @param requestBody request payload
     * @return API response
     */
    public static Response sendPutRequest(String endpoint, JSONObject requestBody) {
        Allure.step("PUT Request: " + endpoint);

        Response response = given(getRequestSpec())
                .body(requestBody.toString())
                .when()
                .put(endpoint)
                .then()
                .extract().response();

        logResponse("PUT", endpoint, response);
        return response;
    }

    /**
     * Sends a PATCH request with JSON body.
     *
     * @param endpoint    relative URI
     * @param requestBody request payload
     * @return API response
     */
    public static Response sendPatchRequest(String endpoint, JSONObject requestBody) {
        Allure.step("PATCH Request: " + endpoint);

        Response response = given(getRequestSpec())
                .body(requestBody.toString())
                .when()
                .patch(endpoint)
                .then()
                .extract().response();

        logResponse("PATCH", endpoint, response);
        return response;
    }

    /**
     * Sends a DELETE request to the given endpoint.
     *
     * @param endpoint relative URI
     * @return API response
     */
    public static Response sendDeleteRequest(String endpoint) {
        Allure.step("DELETE Request: " + endpoint);

        Response response = given(getRequestSpec())
                .when()
                .delete(endpoint)
                .then()
                .extract().response();

        logResponse("DELETE", endpoint, response);
        return response;
    }

    /**
     * Logs and attaches response details to Allure report.
     *
     * @param method   HTTP method (GET, POST, etc.)
     * @param endpoint endpoint path
     * @param response response object
     */
    private static void logResponse(String method, String endpoint, Response response) {
        String status = "Status Code: " + response.getStatusCode();
        System.out.println(method + " " + endpoint + " | " + status);
        Allure.addAttachment(method + " Response", response.prettyPrint());
    }

    /*
    // Optional: You can use this if you want to execute Karate features from Java (not required now)
    public static void runKarateFeature(String featurePath) {
        Allure.step("Executing Karate Feature: " + featurePath);
        Runner.path(featurePath).parallel(1);
        System.out.println("Karate feature executed: " + featurePath);
    }
    */
}
