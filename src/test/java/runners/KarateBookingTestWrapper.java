package runners;

import com.intuit.karate.Runner;
import com.intuit.karate.Results;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * This TestNG wrapper class is used to execute Karate feature files individually.
 * Each method triggers a separate Karate scenario for the Restful-Booker API (Task 4).
 * Useful for CI integration, parallel test management, and controlled execution.
 */
public class KarateBookingTestWrapper {

    /**
     * Executes the Authenticate.feature file to test token generation.
     */
    @Test(description = "Run Authenticate.feature")
    public void runAuthenticateFeature() {
        runFeature("classpath:features/Authenticate.feature");
    }

    /**
     * Executes the FetchBookingIds.feature file to fetch all booking IDs with filters.
     */
    @Test(description = "Run FetchBookingIds.feature")
    public void runFetchBookingIdsFeature() {
        runFeature("classpath:features/FetchBookingIds.feature");
    }

    /**
     * Executes the FetchBookingDetail.feature file to get details for each booking ID.
     */
    @Test(description = "Run FetchBookingDetail.feature")
    public void runFetchBookingDetailFeature() {
        runFeature("classpath:features/FetchBookingDetail.feature");
    }

    /**
     * Executes the CreateBooking.feature file to create a new booking.
     */
    @Test(description = "Run CreateBooking.feature")
    public void runCreateBookingFeature() {
        runFeature("classpath:features/CreateBooking.feature");
    }

    /**
     * Executes the UpdateBooking.feature file to fully update an existing booking.
     */
    @Test(description = "Run UpdateBooking.feature")
    public void runUpdateBookingFeature() {
        runFeature("classpath:features/UpdateBooking.feature");
    }

    /**
     * Executes the PartialUpdateBooking.feature file to partially update a booking.
     */
    @Test(description = "Run PartialUpdateBooking.feature")
    public void runPartialUpdateBookingFeature() {
        runFeature("classpath:features/PartialUpdateBooking.feature");
    }

    /**
     * Executes the DeleteBooking.feature file to delete a booking and validate the response.
     */
    @Test(description = "Run DeleteBooking.feature")
    public void runDeleteBookingFeature() {
        runFeature("classpath:features/DeleteBooking.feature");
    }

    /**
     * Shared method to execute a given Karate feature file path.
     * Verifies that there are no failures after execution.
     *
     * @param featurePath path to the Karate feature file
     */
    private void runFeature(String featurePath) {
        Results results = Runner.path(featurePath).parallel(1);
        Assert.assertEquals(
                results.getFailCount(),
                0,
                "Karate test failed for: " + featurePath + "\n" + results.getErrorMessages()
        );
    }
}
