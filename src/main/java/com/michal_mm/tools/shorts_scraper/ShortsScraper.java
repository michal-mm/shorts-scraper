package com.michal_mm.tools.shorts_scraper;


import com.michal_mm.tools.shorts_scraper.config.ConfigStore;
import com.michal_mm.tools.shorts_scraper.model.PlaylistItem;
import com.michal_mm.tools.shorts_scraper.model.VideoItem;
import com.michal_mm.tools.shorts_scraper.model.YouTubeResponse;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ShortsScraper {

    private static final String YOUTUBE_API_BASE = "https://www.googleapis.com/youtube/v3";
    private static final String GOOGLE_API_KEY = "GOOGLE_API_KEY";

    private static String API_KEY;

    static void main(String[] args) throws IOException {
        initProperties();

        if (args.length < 1) {
            IO.println("Usage: java ShortsScraper <channel_id>");
            return;
        }

        String channelId = args[0];

        List<String> shorts = findShorts(channelId);

    }

    private static List<String> findShorts(String channelId) throws IOException {
        //        urlBuilder.append("/search?part=snippet&channelId=").append(channelId)
        String urlBuilder = YOUTUBE_API_BASE + "/playlistItems?part=snippet&channelId=" + channelId +
                "&maxResults=" + 3 +
                // replace first UC from channel Id with "UUSH" to get shorts playlist
                "&playlistId=" + "UUSH" + channelId.substring(2) +
                "&order=date&type=video&key=" + API_KEY;

        makeApiRequest(urlBuilder);

        return new ArrayList<>();
    }

    private static void initProperties() {
        ConfigStore.loadConfiguration("app.properties");
        API_KEY = ConfigStore.stringValue(GOOGLE_API_KEY);
    }

    private static void makeApiRequest(String urlString) throws IOException {
        URL url = URI.create(urlString).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("HTTP error code: " + conn.getResponseCode());
        }



        String videosJsonStr = new InputStreamReader(conn.getInputStream()).readAllAsString();
        IO.println("JSON ---- START");
        IO.println(videosJsonStr);
        IO.println("JSON ---- END");
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

        conn.disconnect();
    }
}
