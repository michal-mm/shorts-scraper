package com.michal_mm.tools.shorts_scraper.json_parser;

import com.michal_mm.tools.shorts_scraper.model.*;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Objects;

public class JsonParser {

    private JsonParser() {
        throw new IllegalStateException("Utility class, don't instantiate");
    }

    public static List<VideoItem> parseVideosFromJsonResponse(String videosJsonStr) {
        Objects.requireNonNull(videosJsonStr, "JSON output string can't be null");

        ObjectMapper objectMapper = new ObjectMapper();
        var parsedResponse = objectMapper.readValue(videosJsonStr, YouTubeResponse.class);

        return parsedResponse.items().stream()
                .map(PlaylistItem::toVideoItem)
                .toList();
    }

    public static String parsePageTokenFromJsonResponse(String videosJsonStr) {
        Objects.requireNonNull(videosJsonStr, "JSON output string can't be null to get pageToken");

        ObjectMapper objectMapper = new ObjectMapper();
        var parsedResponse = objectMapper.readValue(videosJsonStr, PageToken.class);

        return parsedResponse.nextPageToken();
    }

    public static String parseChannelIdFromJsonResponse(String jsonResponse) {
        Objects.requireNonNull(jsonResponse, "JSON output string can't be null to get channelId");

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readTree(jsonResponse)
                .findValuesAsString("id")
                .stream()
                .findFirst()
                .orElseThrow();
    }
}
