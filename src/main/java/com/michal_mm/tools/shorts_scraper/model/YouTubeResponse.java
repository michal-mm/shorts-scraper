package com.michal_mm.tools.shorts_scraper.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.michal_mm.tools.shorts_scraper.ShortsScraper;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record YouTubeResponse(@JsonProperty("items") List<PlaylistItem> items) {}
