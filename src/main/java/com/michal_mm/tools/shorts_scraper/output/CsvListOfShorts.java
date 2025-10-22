package com.michal_mm.tools.shorts_scraper.output;

import com.michal_mm.tools.shorts_scraper.model.VideoItem;

import java.util.List;

public class CsvListOfShorts implements ListOfShorts{
    @Override
    public void formatResponse(List<VideoItem> shorts, String channelId) {
        IO.println("URL,Title");
        for (VideoItem videoItem : shorts) {
            IO.println(String.join(",",
                    videoItem.url(),
                    // since we need CSV file, we will replace all commas in the title (igf they exist)
                    videoItem.title().replace(",", ";")));
        }
    }
}
