package com.michal_mm.tools.shorts_scraper.config;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConfigStoreTest {

    public static final String TEST_SHORTS_SCRAPER = "Test-shorts-scraper";

    @BeforeAll
    static void setUpAndCreatePropertiesFileInTmp(@TempDir Path tempDir) throws IOException {
        IO.println("will use " + tempDir.toAbsolutePath());
        var props = """
                GOOGLE_API_KEY=google.api.key.value
                output.formatter=CSV
                """;

        var dir = tempDir.resolve(TEST_SHORTS_SCRAPER);
        Files.createDirectories(dir);
        var appPropertiesPath = Files.writeString(dir.resolve("app.properties"), props);

        // load configuration initially
        ConfigStore.loadConfiguration(appPropertiesPath.toString());
        assertNotNull(ConfigStore.stringValueOrDefault("jest.testing", "DEFAULT"));
    }

    @AfterAll
    static void tearDownAndCleanUp(@TempDir Path tempDir) throws IOException {
        var dir = tempDir.resolve(TEST_SHORTS_SCRAPER);
        Files.deleteIfExists(dir);
    }


    @Test
    void stringValue() {
        assertThat(ConfigStore.stringValue("GOOGLE_API_KEY")).isEqualTo("google.api.key.value");
        assertThat(ConfigStore.stringValue("output.formatter")).isEqualTo("CSV");
        assertThat(ConfigStore.stringValue("output.formatter").length()).isEqualTo("CSV".length());
        assertThat(ConfigStore.stringValue("not.existing.value")).isNull();
    }

    @Test
    void stringValueOrDefault() {
        assertThat(ConfigStore.stringValueOrDefault("GOOGLE_API_KEY", "default google key"))
                .isEqualTo("google.api.key.value");
        assertThat(ConfigStore.stringValueOrDefault("not.existing.value", "default.value"))
                .isEqualTo("default.value");
    }
}