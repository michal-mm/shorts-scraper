package com.michal_mm.tools.shorts_scraper.output;

import com.michal_mm.tools.shorts_scraper.model.VideoItem;

import java.util.List;

public interface ListOfShorts {

    void formatResponse(List<VideoItem> shorts, String channelId);
}
