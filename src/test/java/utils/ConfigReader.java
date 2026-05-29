package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream inputStream = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (inputStream == null) {
                throw new RuntimeException("config.properties file was not found under src/test/resources");
            }

            PROPERTIES.load(inputStream);

        } catch (IOException e) {
            throw new RuntimeException("Could not load config.properties file", e);
        }
    }

    private ConfigReader() {
    }

    public static String get(String key) {
        String value = PROPERTIES.getProperty(key);

        if (value == null || value.isBlank()) {
            throw new RuntimeException("Missing property in config.properties: " + key);
        }

        return value.trim();
    }

    public static String getOrDefault(String key, String defaultValue) {
        String value = PROPERTIES.getProperty(key);

        if (value == null || value.isBlank()) {
            return defaultValue;
        }

        return value.trim();
    }
}