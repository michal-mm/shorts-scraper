package com.michal_mm.tools.shorts_scraper.json_parser;

import com.michal_mm.tools.shorts_scraper.model.PlaylistItem;
import com.michal_mm.tools.shorts_scraper.model.VideoItem;
import com.michal_mm.tools.shorts_scraper.model.YouTubeResponse;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonParser {

    public static void parseJsonResponse(String videosJsonStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        var v = objectMapper.readValue(videosJsonStr, YouTubeResponse.class);
        IO.println("YU response: " + v);
        List<VideoItem> shortsDetails = v
                .items().stream()
                .map(PlaylistItem::toVideoItem)
                .toList();
        IO.println("----");
        for (VideoItem sd : shortsDetails) {
            IO.println("~~ " + sd);
        }
    }
}
