package utils;

import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data provider utility class to supply user IDs from a CSV file
 * for use in TestNG data-driven tests.
 */
public class UserDataProvider {

    /**
     * Reads user IDs from a CSV file located in /resources/data/UserIds.csv
     * and returns them as a two-dimensional Object array for TestNG.
     *
     * @return Object[][] containing user IDs
     * @throws IOException if the file cannot be read
     */
    @DataProvider(name = "userIdsFromCSV")
    public static Object[][] getUserIdsFromCSV() throws IOException {
        List<String[]> records = new ArrayList<>();

        // Construct absolute path to CSV file
        String filePath = System.getProperty("user.dir") + "/src/test/resources/data/UserIds.csv";

        // Read CSV content line-by-line
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean skipHeader = true;

            while ((line = br.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false; // Skip header line
                    continue;
                }
                // Wrap each line into a single-element String[] to match TestNG DataProvider format
                records.add(new String[]{line});
            }
        }

        // Convert list to two-dimensional Object array
        return records.toArray(new Object[0][]);
    }
}
