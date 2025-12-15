package com.michal_mm.tools.shorts_scraper.http;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.michal_mm.tools.shorts_scraper.http.HttpRequestGet.PLAYLIST_ITEMS_ENDPOINT;
import static com.michal_mm.tools.shorts_scraper.http.HttpRequestGet.YOUTUBE_API_BASE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

class HttpRequestGetTest {

    @Test
    void testBuildApiUrlWithoutPageToken() {
        var channelId = "YT_CHANNEL_ID_123";
        var playlistId = "YT_PLAYLIST_ID_456";
        var apiKey = "YT_API_KEY_999";
        String pageToken = null;
        var expected = YOUTUBE_API_BASE + PLAYLIST_ITEMS_ENDPOINT +
                "&channelId=" + channelId +
                "&playlistId=" + playlistId +
                "&order=date&type=video&key=" + apiKey;

        assertThat(HttpRequestGet.buildApiUrl(channelId, playlistId, pageToken, apiKey)).isEqualTo(expected);
    }

    @Test
    void testBuildApiUrlWithPageToken() {
        var channelId = "YT_CHANNEL_ID_123";
        var playlistId = "YT_PLAYLIST_ID_456";
        var apiKey = "YT_API_KEY_999";
        String pageToken = "YT_PAGE_TOKEN_007";
        var expected = YOUTUBE_API_BASE + PLAYLIST_ITEMS_ENDPOINT +
                "&channelId=" + channelId +
                "&playlistId=" + playlistId +
                "&order=date&type=video&key=" + apiKey +
                "&pageToken=" + pageToken;

        assertThat(HttpRequestGet.buildApiUrl(channelId, playlistId, pageToken, apiKey)).isEqualTo(expected);
    }

    @Test
    void testGetChannelIdFromHandle() throws IOException {
        var path = Paths.get("src", "test", "resources", "getUserIdResponse.json");
        assertTrue(Files.exists(path));

        var jsonContent = Files.readString(path);

        var channelId = "@java";
        var channelIdWithoutAtSign = "java";
        var apiKey = "YT_API_KEY_123";
        var expectedIdFromFile = "UCmRtPmgnQ04CMUpSUqPfhxQ";

        try(var mock = Mockito.mockStatic(HttpRequestGet.class, Mockito.CALLS_REAL_METHODS)) {
            mock.when(() -> HttpRequestGet.fetchJson(any()))
                    .thenReturn(jsonContent);

            assertThat(HttpRequestGet.getChannelIdFromHandle(channelId, apiKey)).isEqualTo(expectedIdFromFile);
            assertThat(HttpRequestGet.getChannelIdFromHandle(channelIdWithoutAtSign, apiKey)).isEqualTo(expectedIdFromFile);
        }
    }
}