package reqres.tests;

import base.RestAssuredCrudRunner;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ApiUtils;

import static org.hamcrest.Matchers.*;

/**
 * This class contains end-to-end API test cases for CRUD operations on user records
 * using the Reqres API (https://reqres.in/).
 * It covers GET, POST, PUT, PATCH, and DELETE methods.
 */
public class UserCrudTests extends RestAssuredCrudRunner {

    // Shared employeeId for use across tests
    private static String employeeId;

    /**
     * GET API - Fetch user list from Page 2 and validate response data.
     */
    @Test(description = "GET API - Fetching User List from Page 2", priority = 1)
    public void fetchUserList() {
        Allure.step("Fetching user list from page 2");

        Response response = ApiUtils.sendGetRequest("/api/users?page=2");

        // Response validations
        response.then()
                .statusCode(200)
                .body("page", equalTo(2))
                .body("total_pages", greaterThan(1))
                .body("total", greaterThan(10))
                .body("data", not(empty()));

        // Attach response to Allure report
        Allure.addAttachment("Fetch User List Response", response.prettyPrint());
    }

    /**
     * POST API - Create a new employee and store the generated ID.
     */
    @Test(description = "POST API - Create a New Employee", priority = 2)
    public void createNewEmployee() {
        // Create request payload
        JSONObject requestBody = new JSONObject()
                .put("name", "M.Haaris")
                .put("job", "SQA Lead");

        // Send POST request
        Response response = ApiUtils.sendPostRequest("/api/users", requestBody);

        // Validate that ID is returned
        Assert.assertTrue(response.getBody().asString().contains("id"), "Response does not contain 'id'");

        // Save ID for subsequent operations
        employeeId = response.jsonPath().getString("id");
        Allure.step("Stored new employee ID: " + employeeId);
    }

    /**
     * PUT API - Update all information of the created employee.
     */
    @Test(description = "PUT API - Update Employee Information", dependsOnMethods = "createNewEmployee", priority = 3)
    public void updateEmployee() {
        Assert.assertNotNull(employeeId, "Employee ID is null");

        JSONObject requestBody = new JSONObject()
                .put("name", "Muhammad Haaris Nayyar")
                .put("job", "Lead Automation Engineer");

        Response response = ApiUtils.sendPutRequest("/api/users/" + employeeId, requestBody);

        // Validate response contents
        response.then()
                .statusCode(200)
                .body("name", equalTo("Muhammad Haaris Nayyar"))
                .body("job", equalTo("Lead Automation Engineer"))
                .body("updatedAt", notNullValue());

        Allure.addAttachment("Update Employee Response", response.prettyPrint());
    }

    /**
     * PATCH API - Partially update job title of the created employee.
     */
    @Test(description = "PATCH API - Partially Update Employee Information", dependsOnMethods = "updateEmployee", priority = 4)
    public void patchEmployee() {
        Assert.assertNotNull(employeeId, "Employee ID is null");

        JSONObject requestBody = new JSONObject()
                .put("job", "Software Developer Engineer in Test");

        Response response = ApiUtils.sendPatchRequest("/api/users/" + employeeId, requestBody);

        // Validate updated job title
        response.then()
                .statusCode(200)
                .body("job", equalTo("Software Developer Engineer in Test"))
                .body("updatedAt", notNullValue());

        Allure.addAttachment("Patch Employee Response", response.prettyPrint());
    }

    /**
     * DELETE API - Delete the created employee and verify the deletion.
     */
    @Test(description = "DELETE API - Delete Employee", dependsOnMethods = "patchEmployee", priority = 5)
    public void deleteEmployee() {
        Assert.assertNotNull(employeeId, "Employee ID is null");

        Response response = ApiUtils.sendDeleteRequest("/api/users/" + employeeId);

        // 204 No Content expected for successful deletion
        response.then().statusCode(204);

        Allure.step("Deleted employee with ID: " + employeeId);
    }
}
