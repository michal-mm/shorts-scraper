package com.michal_mm.tools.shorts_scraper;


import com.michal_mm.tools.shorts_scraper.config.ConfigStore;
import com.michal_mm.tools.shorts_scraper.model.VideoItem;
import com.michal_mm.tools.shorts_scraper.output.OutputFormatterFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.michal_mm.tools.shorts_scraper.json_parser.JsonParser.*;

public class ShortsScraper {

    private static final String YOUTUBE_API_BASE = "https://www.googleapis.com/youtube/v3";
    private static final String GOOGLE_API_KEY = "GOOGLE_API_KEY";
    private static final String OUTPUT_FORMATER_KEY = "output.formatter";
    public static final String PLAYLIST_ITEMS_ENDPOINT = "/playlistItems?part=snippet&maxResult=50";
    public static final String YT_SHORTS_LIST_PREFIX = "UUSH";

    private static String apiKey;
    private static String outputFormatter;

    static void main(String[] args) throws IOException {
        initProperties();

        if (args.length < 1) {
            IO.println("Usage: java ShortsScraper <channel_id>");
            return;
        }

        String channelId = args[0];

        if (channelId.startsWith("@")) {
            channelId = getChannelIdFromHandle(channelId);
        }

        List<VideoItem> shorts = fetchAllShorts(channelId);

        OutputFormatterFactory.outputFormatter(outputFormatter)
                .formatResponse(shorts, channelId);
    }

    private static String getChannelIdFromHandle(String handle) throws IOException {
        // skip the '@' sign at the beginning of a handle
        String username = handle.substring(1);

        String url = YOUTUBE_API_BASE + "/channels?part=id&forUsername=" +
                URLEncoder.encode(username, StandardCharsets.UTF_8) +
                "&key=" + apiKey;

        String jsonResponse = fetchJson(url);

        return parseChannelIdFromJsonResponse(jsonResponse);
    }

    private static List<VideoItem> fetchAllShorts(String channelId) throws IOException {
        List<VideoItem> listOfShorts = new ArrayList<>();
        String pageToken = null;
        // replace first UC from channel Id with "UUSH" to get shorts playlist
        String shortsPlaylistId = YT_SHORTS_LIST_PREFIX + channelId.substring(2);

        do {
            String urlListItems = buildApiUrl(channelId, shortsPlaylistId, pageToken);

            var response = fetchJson(urlListItems);
            pageToken = parsePageTokenFromJsonResponse(response);
            listOfShorts.addAll(buildListOfShorts(response));
        } while (pageToken != null);

        return listOfShorts;
    }

    private static String buildApiUrl(String channelId, String shortsPlaylistId, String pageToken) {
        String urlListItems = YOUTUBE_API_BASE + PLAYLIST_ITEMS_ENDPOINT +
                "&channelId=" + channelId +
                "&playlistId=" + shortsPlaylistId +
                "&order=date&type=video&key=" + apiKey;

        return pageToken != null ? urlListItems + "&pageToken=" + pageToken : urlListItems;
    }

    private static List<VideoItem> buildListOfShorts(String videosJsonStr) {
        Objects.requireNonNull(videosJsonStr, "JSON output from GET call can't be null");
        return parseVideosFromJsonResponse(videosJsonStr);
    }

    private static void initProperties() {
        ConfigStore.loadConfiguration("app.properties");
        apiKey = ConfigStore.stringValue(GOOGLE_API_KEY);
        outputFormatter = ConfigStore.stringValueOrDefault(OUTPUT_FORMATER_KEY, OutputFormatterFactory.SIMPLE);
    }

    private static String fetchJson(String urlString) throws IOException {
        URL url = URI.create(urlString).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            conn.disconnect();
            throw new RuntimeException("HTTP error code: " + conn.getResponseCode());
        }

        String jsonResponse = new InputStreamReader(conn.getInputStream()).readAllAsString();
        conn.disconnect();

        return jsonResponse;
    }
}
