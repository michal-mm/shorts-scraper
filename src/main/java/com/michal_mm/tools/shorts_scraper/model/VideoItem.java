package com.michal_mm.tools.shorts_scraper.model;

public record VideoItem(String videoId, String title) {
    public String url() {
        return "https://www.youtube.com/shorts/" + videoId;
    }
}
