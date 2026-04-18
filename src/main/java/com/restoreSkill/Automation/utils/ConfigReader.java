package com.restoreSkill.Automation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {
    /** UI URLs — edit urls.properties to change environments. */
    private static final String URLS_PATH = "/config/urls.properties";
    private static final String CONFIG_PATH = "/config/config.properties";
    private static final Properties PROPERTIES = loadMergedProperties();

    private static final String ENV_UI_USER = "AUTOMATION_UI_USERNAME";
    private static final String ENV_UI_PASS = "AUTOMATION_UI_PASSWORD";

    private ConfigReader() {
    }

    /**
     * Raw property value (trimmed). Empty string if missing.
     */
    public static String getProperty(String key) {
        String value = PROPERTIES.getProperty(key, "");
        return value == null ? "" : value.trim();
    }

    /**
     * Primary UI login page URL (https added if protocol omitted).
     */
    public static String getUiLoginUrl() {
        String configured = getProperty("ui.login.url");
        if (configured.isEmpty()) {
            configured = getProperty("login.url");
        }
        if (configured.isEmpty()) {
            throw new IllegalStateException("Set 'ui.login.url' or 'login.url' in urls.properties");
        }
        return normalizeUrl(configured);
    }

    /**
     * @deprecated use {@link #getUiLoginUrl()}
     */
    @Deprecated
    public static String getLoginUrl() {
        return getUiLoginUrl();
    }

    public static String getUiBaseUrl() {
        String configured = getProperty("ui.base.url");
        if (configured.isEmpty()) {
            return getUiLoginUrl();
        }
        return normalizeUrl(configured);
    }

    /**
     * Optional UI URL; empty if not set in config.
     */
    public static String getUiDashboardUrl() {
        return optionalUrl("ui.dashboard.url");
    }

    public static String getUiProviderUrl() {
        return optionalUrl("ui.provider.url");
    }

    public static String getUiUsername() {
        return firstNonBlank(System.getenv(ENV_UI_USER), getProperty("ui.username"));
    }

    public static String getUiPassword() {
        return firstNonBlank(System.getenv(ENV_UI_PASS), getProperty("ui.password"));
    }

    private static String firstNonBlank(String envValue, String fileValue) {
        if (envValue != null && !envValue.isBlank()) {
            return envValue.trim();
        }
        return fileValue == null ? "" : fileValue.trim();
    }

    private static String optionalUrl(String key) {
        String configured = getProperty(key);
        if (configured.isEmpty()) {
            return "";
        }
        return normalizeUrl(configured);
    }

    /**
     * Loads {@code urls.properties} first, then {@code config.properties} (credentials override if same key).
     */
    private static Properties loadMergedProperties() {
        Properties merged = new Properties();
        loadInto(merged, URLS_PATH);
        loadInto(merged, CONFIG_PATH);
        return merged;
    }

    private static void loadInto(Properties target, String classpathResource) {
        try (InputStream inputStream = ConfigReader.class.getResourceAsStream(classpathResource)) {
            if (inputStream == null) {
                throw new IllegalStateException("Unable to find config file: " + classpathResource);
            }
            Properties chunk = new Properties();
            chunk.load(inputStream);
            for (String name : chunk.stringPropertyNames()) {
                target.setProperty(name, chunk.getProperty(name));
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load config file: " + classpathResource, e);
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
