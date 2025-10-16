package com.michal_mm.tools.shorts_scraper.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PlaylistItem(@JsonProperty("snippet") Snippet snippet) {
    public VideoItem toVideoItem(){
        return new VideoItem(snippet.resourceId().videoId(), snippet.title());
    }
}
