package com.michal_mm.tools.shorts_scraper.output;

import com.michal_mm.tools.shorts_scraper.model.VideoItem;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleTxtListOfShorts implements ListOfShorts {
    @Override
    public void formatResponse(List<VideoItem> shorts, String channelId) {
        AtomicInteger index = new AtomicInteger(1);
        Collections.reverse(shorts);

        IO.println("-------------------------------------------");
        IO.println("YouTube Shorts for channel: " + channelId);
        IO.println("-------------------------------------------");

        for (VideoItem videoShort : shorts) {
            IO.println(index.getAndIncrement() + ". URL[" +
                    videoShort.url() + "] --- " +
                    videoShort.title());
        }

        IO.println("-------------------------------------------");
    }
}
