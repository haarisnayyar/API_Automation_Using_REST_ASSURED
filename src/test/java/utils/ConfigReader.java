package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class to load and access configuration properties
 * from the config.properties file located in the resources directory.
 */
public class ConfigReader {

    // Holds all the loaded properties
    private static final Properties properties = new Properties();

    // Path to the configuration file
    private static final String CONFIG_FILE_PATH = "src/test/resources/config.properties";

    // Static block executes once to load the properties on class load
    static {
        loadProperties();
    }

    /**
     * Loads the config.properties file into the properties object.
     * If the file is missing or unreadable, an error is printed.
     */
    private static void loadProperties() {
        try (FileInputStream file = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(file);
            System.out.println("[ConfigReader] Loaded config.properties successfully.");
        } catch (IOException e) {
            System.err.println("[ConfigReader] Error loading config file from path: " + CONFIG_FILE_PATH);
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the value for the given property key.
     *
     * @param key the name of the property to retrieve
     * @return the value if found, or "Property Not Found" if missing
     */
    public static String getProperty(String key) {
        return properties.getProperty(key, "Property Not Found");
    }
}
