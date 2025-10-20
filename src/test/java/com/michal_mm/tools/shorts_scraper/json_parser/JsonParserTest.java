package com.michal_mm.tools.shorts_scraper.json_parser;

import com.michal_mm.tools.shorts_scraper.model.VideoItem;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {

    @Test
    void parseVideosFromJsonResponse() throws IOException {
        var path = Paths.get("src", "test", "resources", "get3ShortItemsForChannel.json");
        assertTrue(Files.exists(path));

        var jsonContent = Files.readString(path);

        List<VideoItem> expectedList = List.of(
                new VideoItem("SLCp6PPSbUs", "What is a distinct stream? - Cracking the Java Coding Interview#javalanguage #javacoding #javatips"),
                new VideoItem("gGumOIMCPsg", "\"Special Episode 300 - Cracking the Java Coding Interview #javalanguage #javacoding #javatips\""),
                new VideoItem("x3eqcdmxEzw", "What is a cache miss?  - Cracking the Java Coding Interview #javalanguage #javacoding #javatips")
        );

        List<VideoItem> result = JsonParser.parseVideosFromJsonResponse(jsonContent);

        assertThat(result.size()).isEqualTo(expectedList.size());
        expectedList.forEach(video -> assertThat(result.contains(video)));
        result.forEach(video -> assertThat(expectedList.contains(video)));
    }

    @Test
    void parsePageTokenFromJsonResponse() throws IOException {
        var path = Paths.get("src", "test", "resources", "get3ShortItemsForChannel.json");
        assertTrue(Files.exists(path));

        var jsonContent = Files.readString(path);

        String expectedChannelId = "EAAaHlBUOkNBVWlFRE01T0RGR01qSXpOREpHT1VVd1JVRQ";

        assertThat(JsonParser.parsePageTokenFromJsonResponse(jsonContent)).isEqualTo(expectedChannelId);
    }

    @Test
    void parseChannelIdFromJsonResponse() throws IOException {
        var path = Paths.get("src", "test", "resources", "getUserIdResponse.json");
        assertTrue(Files.exists(path));

        var jsonContent = Files.readString(path);

        String expectedId = "UCmRtPmgnQ04CMUpSUqPfhxQ";

        assertThat(JsonParser.parseChannelIdFromJsonResponse(jsonContent)).isEqualTo(expectedId);
    }
}