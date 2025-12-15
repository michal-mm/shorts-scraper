package com.michal_mm.tools.shorts_scraper;


import com.michal_mm.tools.shorts_scraper.config.ConfigStore;
import com.michal_mm.tools.shorts_scraper.model.VideoItem;
import com.michal_mm.tools.shorts_scraper.output.OutputFormatterFactory;

import java.io.IOException;
import java.util.List;

import static com.michal_mm.tools.shorts_scraper.http.HttpRequestGet.fetchAllShorts;
import static com.michal_mm.tools.shorts_scraper.http.HttpRequestGet.getChannelIdFromHandle;

public class ShortsScraper {

    private static final String GOOGLE_API_KEY = "GOOGLE_API_KEY";
    private static final String OUTPUT_FORMATER_KEY = "output.formatter";


    private static String apiKey;
    private static String outputFormatter;

    private ShortsScraper() {
        throw new IllegalStateException("Util class, don't instantiate");
    }

    static void main(String[] args) throws IOException {
        initProperties();

        if (args.length < 1) {
            throw new IllegalStateException("Usage: java ShortsScraper <channel_id>");
        }

        String channelId = getChannelId(args[0]);

        List<VideoItem> shorts = fetchAllShorts(channelId, apiKey);

        OutputFormatterFactory.outputFormatter(outputFormatter)
                .formatResponse(shorts, channelId);
    }

    private static void initProperties() {
        ConfigStore.loadConfiguration("app.properties");
        apiKey = ConfigStore.stringValue(GOOGLE_API_KEY);
        outputFormatter = ConfigStore.stringValueOrDefault(OUTPUT_FORMATER_KEY, OutputFormatterFactory.SIMPLE);
    }

    private static String getChannelId(String channelId) throws IOException {
        if (channelId.startsWith("@")) {
            channelId = getChannelIdFromHandle(channelId, apiKey);
        }
        return channelId;
    }
}
