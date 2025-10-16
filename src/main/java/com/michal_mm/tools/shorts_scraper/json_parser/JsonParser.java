package com.michal_mm.tools.shorts_scraper.json_parser;

import com.michal_mm.tools.shorts_scraper.model.PageToken;
import com.michal_mm.tools.shorts_scraper.model.PlaylistItem;
import com.michal_mm.tools.shorts_scraper.model.VideoItem;
import com.michal_mm.tools.shorts_scraper.model.YouTubeResponse;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Objects;

public class JsonParser {

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
}
