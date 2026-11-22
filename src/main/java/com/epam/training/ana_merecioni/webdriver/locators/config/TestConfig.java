package com.epam.training.ana_merecioni.webdriver.locators.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public final class TestConfig {

    private static final String FILE = "test.properties";
    private static final Properties PROPERTIES = load();

    private TestConfig() {
    }

    private static Properties load() {
        Properties properties = new Properties();
        try (InputStream in = TestConfig.class.getClassLoader().getResourceAsStream(FILE)) {
            if (in != null) {
                properties.load(in);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot read " + FILE, e);
        }
        return properties;
    }

    public static String get(String key, String defaultValue) {
        String fromSystem = System.getProperty(key);
        if (fromSystem != null && !fromSystem.isBlank()) {
            return fromSystem.trim();
        }
        return PROPERTIES.getProperty(key, defaultValue).trim();
    }

    public static String baseUrl() {
        return get("base.url", "https://www.epam.com/");
    }

    public static String browser() {
        return get("browser", "chrome");
    }

    public static boolean headless() {
        return Boolean.parseBoolean(get("headless", "false"));
    }

    public static Duration timeout() {
        return Duration.ofSeconds(Long.parseLong(get("timeout.seconds", "20")));
    }

    public static String location() {
        return get("search.location", "All Locations");
    }

    public static List<String> keywords() {
        return Arrays.stream(get("search.keywords", "Java").split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }
}
