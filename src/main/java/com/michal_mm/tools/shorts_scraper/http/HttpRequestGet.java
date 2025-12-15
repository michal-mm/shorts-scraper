package com.michal_mm.tools.shorts_scraper.http;

import com.michal_mm.tools.shorts_scraper.model.VideoItem;

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

public class HttpRequestGet {

    public static final String YOUTUBE_API_BASE = "https://www.googleapis.com/youtube/v3";
    public static final String PLAYLIST_ITEMS_ENDPOINT = "/playlistItems?part=snippet&maxResult=50";

    public static final String YT_SHORTS_LIST_PREFIX = "UUSH";

    public static String fetchJson(String urlString) throws IOException {
        URL url = URI.create(urlString).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            conn.disconnect();
            throw new IOException("HTTP error code: " + conn.getResponseCode());
        }

        String jsonResponse = new InputStreamReader(conn.getInputStream()).readAllAsString();
        conn.disconnect();

        return jsonResponse;
    }

    public static List<VideoItem> fetchAllShorts(String channelId, String apiKey) throws IOException {
        List<VideoItem> listOfShorts = new ArrayList<>();
        String pageToken = null;
        // replace first UC from channel Id with "UUSH" to get shorts playlist
        String shortsPlaylistId = YT_SHORTS_LIST_PREFIX + channelId.substring(2);

        do {
            String urlListItems = buildApiUrl(channelId, shortsPlaylistId, pageToken, apiKey);

            var response = fetchJson(urlListItems);
            pageToken = parsePageTokenFromJsonResponse(response);
            listOfShorts.addAll(buildListOfShorts(response));
        } while (pageToken != null);

        return listOfShorts;
    }

    private static List<VideoItem> buildListOfShorts(String videosJsonStr) {
        Objects.requireNonNull(videosJsonStr, "JSON output from GET call can't be null");
        return parseVideosFromJsonResponse(videosJsonStr);
    }

    public static String buildApiUrl(String channelId, String shortsPlaylistId, String pageToken, String apiKey) {
        String urlListItems = YOUTUBE_API_BASE + PLAYLIST_ITEMS_ENDPOINT +
                "&channelId=" + channelId +
                "&playlistId=" + shortsPlaylistId +
                "&order=date&type=video&key=" + apiKey;

        return pageToken != null ? urlListItems + "&pageToken=" + pageToken : urlListItems;
    }

    public static String getChannelIdFromHandle(String handle, String apiKey) throws IOException {
        // skip the '@' sign at the beginning of a handle
        String username = handle.startsWith("@") ? handle.substring(1) : handle;

        String url = YOUTUBE_API_BASE + "/channels?part=id&forUsername=" +
                URLEncoder.encode(username, StandardCharsets.UTF_8) +
                "&key=" + apiKey;

        String jsonResponse = fetchJson(url);

        return parseChannelIdFromJsonResponse(jsonResponse);
    }
}
