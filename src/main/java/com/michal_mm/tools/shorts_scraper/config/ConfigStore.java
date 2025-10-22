package com.michal_mm.tools.shorts_scraper.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;

public class ConfigStore {

    private static Properties cache;

    private ConfigStore() {
        throw new IllegalStateException("Utility class, don't instantiate");
    }

    private static void initCache() {
        cache = new Properties();
    }

    public static void loadConfiguration(String fileName) {
        initCache();

        Properties properties = new Properties();

        Objects.requireNonNull(fileName, "Provide valid properties file name, example: app.properties");

        Path configFile = Path.of(fileName);
        if (Files.exists(configFile)) {
            loadFromFile(configFile, properties);
        }

        cache.putAll(properties);
    }

    public static String stringValue(String key) {
        if (cache == null) {
            throw new IllegalStateException("Call ConfigStore.loadConfiguration(aFileName) first");
        } else {
            return cache.getProperty(key);
        }
    }

    public static String stringValueOrDefault(String key, String defValue) {
        if (cache == null) {
            throw new IllegalStateException("Call ConfigStore.loadConfiguration(aFileName) first");
        } else {
            return cache.getProperty(key, defValue);
        }
    }

    private static void loadFromFile(Path file, Properties properties) {
        try (BufferedReader is = Files.newBufferedReader(file)) {
            properties.load(is);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot load properties from: " + file, e);
        }
    }
}
