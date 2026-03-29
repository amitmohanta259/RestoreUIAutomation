package com.restoreSkill.Automation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {
    private static final String CONFIG_PATH = "/config/config.properties";
    private static final Properties PROPERTIES = loadProperties();

    private ConfigReader() {
    }

    public static String getLoginUrl() {
        String configured = PROPERTIES.getProperty("login.url", "").trim();
        if (configured.isEmpty()) {
            throw new IllegalStateException("Missing 'login.url' in config.properties");
        }
        return normalizeUrl(configured);
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = ConfigReader.class.getResourceAsStream(CONFIG_PATH)) {
            if (inputStream == null) {
                throw new IllegalStateException("Unable to find config file: " + CONFIG_PATH);
            }
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load config file: " + CONFIG_PATH, e);
        }
    }

    private static String normalizeUrl(String url) {
        String value = url.trim();
        if (value.startsWith("http://") || value.startsWith("https://")) {
            return value;
        }
        return "https://" + value;
    }
}
