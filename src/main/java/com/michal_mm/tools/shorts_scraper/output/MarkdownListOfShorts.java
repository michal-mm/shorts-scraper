package com.michal_mm.tools.shorts_scraper.output;

import com.michal_mm.tools.shorts_scraper.model.VideoItem;

import java.util.Collections;
import java.util.List;

public class MarkdownListOfShorts implements ListOfShorts{
    @Override
    public void formatResponse(List<VideoItem> shorts, String channelId) {
        Collections.reverse(shorts);

        IO.println("### YouTube Shorts for channel: ==" + channelId + "==");

        IO.println("| - | # | TITLE | URL |");
        IO.println("|---|-----|-------------|----|");

        for(VideoItem video : shorts) {
            IO.println("| [ ] | 1. | " +
                    video.title() + " | " +
                    "[" + video.url() + "](" + video.url() + ")|");
        }
    }
}
