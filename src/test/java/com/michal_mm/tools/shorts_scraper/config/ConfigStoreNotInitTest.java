package com.michal_mm.tools.shorts_scraper.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermissions;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

class ConfigStoreNotInitTest {

    public static final String TEST_SHORTS_SCRAPER = "Test-shorts-scraper";

    /**
     * Using java reflection mechanism to set cache to null
     * since it's a static field and once it is initialized
     * in other unit tests, it stays available until the end.
     */
    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        var cachefield = ConfigStore.class.getDeclaredField("cache");
        cachefield.setAccessible(true);
        cachefield.set(null, null);
    }

    @Test
    void test_configurationNotLoadedFirst_exceptionsThrown() {
        assertThatThrownBy(() -> ConfigStore.stringValue("some.value"))
                .isExactlyInstanceOf(IllegalStateException.class);

        assertThatThrownBy(() -> ConfigStore.stringValueOrDefault("some.value", "default value"))
                .isExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    void testAppPropertiesFileNotAvailable(@TempDir Path path) {
        var dir = path.resolve(TEST_SHORTS_SCRAPER);
        var notExistingFile = dir.resolve("not-existing-file");
        Assertions.assertThat(Files.notExists(notExistingFile)).isTrue();

        assertDoesNotThrow(() -> ConfigStore.loadConfiguration(String.valueOf(notExistingFile.toAbsolutePath())));
    }

    @Test
    void testIOException_whenReadingFileUsingBufferedReader(@TempDir Path path) {
        var propFilePath = path.resolve(TEST_SHORTS_SCRAPER);
        FileAttribute<?>[] attributes = {
                PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rw-r--r--"))
        };

        try (MockedStatic<Files> filesMock = mockStatic(Files.class)) {
            var pr = Files.createDirectories(propFilePath);
            var propFile = Files.createTempFile(pr, "tmpPrefix", "tmpSuffix", attributes);

            filesMock.when(() -> Files.exists(any(Path.class))).thenReturn(true);
            filesMock.when(() -> Files.newBufferedReader(any(Path.class)))
                            .thenThrow(IOException.class);

            var propFileStr =String.valueOf(propFile);
            assertThatThrownBy(() -> ConfigStore.loadConfiguration(propFileStr))
                    .isExactlyInstanceOf(IllegalStateException.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testPrivateConstructorThrowsIllegalStateException() throws NoSuchMethodException {
        var constructor = ConfigStore.class.getDeclaredConstructor();

        constructor.setAccessible(true);

        assertThatThrownBy(constructor::newInstance)
                .rootCause()
                .isExactlyInstanceOf(IllegalStateException.class);
    }
}
