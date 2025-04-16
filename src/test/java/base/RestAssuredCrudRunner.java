package base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;

/**
 * Base runner class for all REST Assured API test cases.
 * Initializes request specification and configures base URI before each test.
 */
public class RestAssuredCrudRunner {

    // Shared request specification used across all tests
    public static RequestSpecification requestSpec;

    // Constants for headers and fallback URL
    private static final String DEFAULT_BASE_URL = "https://reqres.in";
    private static final String CONTENT_TYPE = "application/json";
    private static final String ACCEPT = "application/json";

    /**
     * Sets up base URI and request specification before each test method.
     */
    @BeforeMethod
    public void setup() {
        System.out.println("[Setup] Initializing API Test Environment...");

        // Load base URL from config or fallback to default
        String baseUrl = getBaseUrlFromConfig();
        RestAssured.baseURI = baseUrl;

        // Build the request specification with common headers
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addHeader("Content-Type", CONTENT_TYPE)
                .addHeader("Accept", ACCEPT)
                .build();

        System.out.println("[Setup] Base URI: " + baseUrl);
    }

    /**
     * Cleans up after each test method.
     */
    @AfterMethod
    public void tearDown() {
        System.out.println("[Teardown] Test Execution Completed.");
    }

    /**
     * Reads base_url from config.properties.
     * Returns default URL if not found or improperly configured.
     *
     * @return valid base URL
     */
    private String getBaseUrlFromConfig() {
        String baseUrl = ConfigReader.getProperty("base_url");

        if (baseUrl == null || baseUrl.trim().isEmpty() || "Property Not Found".equals(baseUrl)) {
            System.out.println("[Warning] base_url not found in config. Using default: " + DEFAULT_BASE_URL);
            return DEFAULT_BASE_URL;
        }

        return baseUrl;
    }
}
