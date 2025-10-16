package com.michal_mm.tools.shorts_scraper.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Snippet(@JsonProperty("title") String title,
               @JsonProperty("resourceId") ResourceId resourceId){}