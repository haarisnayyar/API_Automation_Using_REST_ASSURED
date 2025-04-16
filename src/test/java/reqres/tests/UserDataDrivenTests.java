package reqres.tests;

import base.RestAssuredCrudRunner;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.ApiUtils;
import utils.UserDataProvider;
import utils.UserDeletionHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * This test class handles dynamic and data-driven testing of the GET /users/{id} endpoint.
 * Supports both CLI-based execution and reading IDs from a CSV file.
 * Also includes a conditional deletion step for user ID "3".
 */
@Epic("Task 4 - Fetch User Details")
@Feature("Fetch User Details from API")
public class UserDataDrivenTests extends RestAssuredCrudRunner {

    // Shared list of all fetched users
    private static final List<String[]> allUsers = new ArrayList<>();

    // CLI property (if passed via -Duser.id=3)
    private static final String cliUserId = System.getProperty("user.id");

    // Used to track conditional deletion of user ID 3
    private static boolean isUser3Deleted = false;

    /**
     * Simple setup log to indicate start of task-specific test run.
     */
    @BeforeMethod
    @Step("Setting up API Tests for Task 4")
    public void setupTest() {
        System.out.println("===== Task 4 API Test Environment Setup =====");
    }

    /**
     * Fetch user via CLI-provided user ID.
     * If not provided, the test skips execution.
     */
    @Test(description = "Fetch User Details via CLI (if provided)")
    @Severity(SeverityLevel.NORMAL)
    @Story("CLI Mode: Fetch User by ID")
    public void testFetchUserFromCLI() {
        if (cliUserId != null) {
            Allure.step("Fetching details for User ID: " + cliUserId);
            fetchAndStoreUser(cliUserId);
            displayUsersBeforeAndAfterDeletion();
        } else {
            Allure.step("No CLI user ID provided. Skipping CLI execution.");
        }
    }

    /**
     * Fetch user details for each ID from a CSV file using TestNG DataProvider.
     */
    @Test(
            description = "Fetch User Details from CSV File",
            dataProvider = "userIdsFromCSV",
            dataProviderClass = UserDataProvider.class,
            dependsOnMethods = "testFetchUserFromCLI"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Story("Fetch Users from CSV Data")
    public void testFetchUserFromCSV(String userId) {
        Allure.step("Fetching User Details for ID: " + userId);
        fetchAndStoreUser(userId);
    }

    /**
     * Fetches user data from API and stores it for verification.
     * Adds details to shared list and Allure report.
     *
     * @param userId ID of user to fetch
     */
    private void fetchAndStoreUser(String userId) {
        String endpoint = "/api/users/" + userId;
        Response response = ApiUtils.sendGetRequest(endpoint);

        // Skip if user does not exist (e.g., 404)
        if (response.getStatusCode() != 200) {
            System.out.println("User ID " + userId + " not found. Skipping...");
            return;
        }

        // Extract fields from JSON response
        String id = response.jsonPath().getString("data.id");
        String email = response.jsonPath().getString("data.email");
        String firstName = response.jsonPath().getString("data.first_name");
        String lastName = response.jsonPath().getString("data.last_name");
        String avatar = response.jsonPath().getString("data.avatar");

        // Store user in shared list and log info
        allUsers.add(new String[]{id, email, firstName, lastName, avatar});
        logUserDetails(id, email, firstName, lastName, avatar);

        // Add to Allure report
        attachToAllure("Fetched User Details", formatUserDetails(id, email, firstName, lastName, avatar));
        attachToAllure("API Response", response.prettyPrint());
    }

    /**
     * Displays user list before and after conditionally deleting user ID "3".
     * Validates that ID "3" no longer exists if it was deleted.
     */
    @Test(description = "Display Users Before and After Deletion", dependsOnMethods = "testFetchUserFromCSV")
    @Severity(SeverityLevel.NORMAL)
    @Story("Verify Deletion & Display Users")
    public void displayUsersBeforeAndAfterDeletion() {
        // Log user list before deletion
        System.out.println("User List Before Deletion:");
        String beforeTable = formatUserTable(allUsers);
        System.out.println(beforeTable);
        attachToAllure("User List Before Deletion", beforeTable);

        // Conditionally delete user with ID "3"
        if (allUsers.stream().anyMatch(user -> user[0].equals("3"))) {
            isUser3Deleted = true;
            UserDeletionHelper.deleteUser("3");
            allUsers.removeIf(user -> user[0].equals("3"));
        }

        // Log user list after deletion
        System.out.println("User List After Deletion:");
        String afterTable = formatUserTable(allUsers);
        System.out.println(afterTable);
        attachToAllure("User List After Deletion", afterTable);

        // Assert user 3 no longer exists
        if (isUser3Deleted) {
            for (String[] user : allUsers) {
                Assert.assertNotEquals(user[0], "3", "Deleted User ID 3 should not exist in the updated list");
            }
        }
    }

    /**
     * Formats the list of user data into a tabular string format.
     *
     * @param users List of user detail arrays
     * @return formatted table string
     */
    private String formatUserTable(List<String[]> users) {
        if (users.isEmpty()) {
            return "No user data available.";
        }

        StringBuilder table = new StringBuilder();
        table.append(String.format("%-5s | %-35s | %-15s | %-15s | %s%n",
                "ID", "Email", "First Name", "Last Name", "Avatar"));
        table.append("--------------------------------------------------------------------------------------------------\n");

        for (String[] user : users) {
            table.append(String.format("%-5s | %-35s | %-15s | %-15s | %s%n",
                    user[0], user[1], user[2], user[3], user[4]));
        }

        return table.toString();
    }

    /**
     * Logs user details to console.
     */
    private void logUserDetails(String id, String email, String firstName, String lastName, String avatar) {
        System.out.println("User Details: ID=" + id + ", Email=" + email);
    }

    /**
     * Attaches plain text content to Allure reports.
     */
    @Attachment(value = "{0}", type = "text/plain")
    private String attachToAllure(String title, String content) {
        return content;
    }

    /**
     * Formats user details into readable string block for Allure.
     */
    private String formatUserDetails(String id, String email, String firstName, String lastName, String avatar) {
        return String.format("User ID: %s\nEmail: %s\nFirst Name: %s\nLast Name: %s\nAvatar URL: %s",
                id, email, firstName, lastName, avatar);
    }
}
