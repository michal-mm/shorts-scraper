package com.michal_mm.tools.shorts_scraper;


import com.michal_mm.tools.shorts_scraper.config.ConfigStore;
import com.michal_mm.tools.shorts_scraper.model.VideoItem;
import com.michal_mm.tools.shorts_scraper.output.OutputFormatterFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.michal_mm.tools.shorts_scraper.json_parser.JsonParser.parsePageTokenFromJsonResponse;
import static com.michal_mm.tools.shorts_scraper.json_parser.JsonParser.parseVideosFromJsonResponse;

public class ShortsScraper {

    private static final String YOUTUBE_API_BASE = "https://www.googleapis.com/youtube/v3";
    private static final String GOOGLE_API_KEY = "GOOGLE_API_KEY";
    private static final String OUTPUT_FORMATER_KEY = "output.formatter";

    private static String API_KEY;
    private static String OUTPUT_FORMATTER;

    static void main(String[] args) throws IOException {
        initProperties();

        if (args.length < 1) {
            IO.println("Usage: java ShortsScraper <channel_id>");
            return;
        }

        String channelId = args[0];

        List<VideoItem> shorts = findShorts(channelId);
        formatResponse(shorts, channelId);
    }

    private static void formatResponse(List<VideoItem> shorts, String channelId) {
        OutputFormatterFactory.outputFormatter(OUTPUT_FORMATTER)
                .formatResponse(shorts, channelId);
    }

    private static List<VideoItem> findShorts(String channelId) throws IOException {
        List<VideoItem> listOfShorts = new ArrayList<>();
        String pageToken = null;

        do {
            String urlListItems = YOUTUBE_API_BASE + "/playlistItems?part=snippet&channelId=" + channelId +
                    "&maxResults=" + 50 +
                    // replace first UC from channel Id with "UUSH" to get shorts playlist
                    "&playlistId=" + "UUSH" + channelId.substring(2) +
                    "&order=date&type=video&key=" + API_KEY;

            if (pageToken != null) {
                urlListItems += "&pageToken=" + pageToken;
            }

            var response = makeApiRequestAndReturnJsonString(urlListItems);
            pageToken = parsePageTokenFromJsonResponse(response);
            listOfShorts.addAll(buildListOfShorts(response));
        } while (pageToken != null);

        return listOfShorts;
    }

    private static List<VideoItem> buildListOfShorts(String videosJsonStr) {
        Objects.requireNonNull(videosJsonStr, "JSON output from GET call can't be null");
        return parseVideosFromJsonResponse(videosJsonStr);
    }

    private static void initProperties() {
        ConfigStore.loadConfiguration("app.properties");
        API_KEY = ConfigStore.stringValue(GOOGLE_API_KEY);
        OUTPUT_FORMATTER = ConfigStore.stringValueOrDefault(OUTPUT_FORMATER_KEY, OutputFormatterFactory.SIMPLE);
    }

    private static String makeApiRequestAndReturnJsonString(String urlString) throws IOException {
        URL url = URI.create(urlString).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("HTTP error code: " + conn.getResponseCode());
        }

        String videosJsonStr = new InputStreamReader(conn.getInputStream()).readAllAsString();

        conn.disconnect();

        return videosJsonStr;
    }
}
