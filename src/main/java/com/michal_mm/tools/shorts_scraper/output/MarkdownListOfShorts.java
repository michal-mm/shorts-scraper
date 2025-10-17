package com.michal_mm.tools.shorts_scraper.output;

import com.michal_mm.tools.shorts_scraper.model.VideoItem;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MarkdownListOfShorts implements ListOfShorts{
    @Override
    public void formatResponse(List<VideoItem> shorts, String channelId) {
        Collections.reverse(shorts);
        AtomicInteger index = new AtomicInteger(1);

        IO.println("### YouTube Shorts for channel: ==" + channelId + "==");

        IO.println("| # | TITLE | URL |");
        IO.println("|-----|-----------------|------------------|");

        for(VideoItem video : shorts) {
            IO.println("| " + index.getAndIncrement() + ". | " +
                    video.title() + " | " +
                    "[" + video.url() + "](" + video.url() + ")|");
        }
    }
}
