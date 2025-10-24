package com.michal_mm.tools.shorts_scraper.output;

import com.michal_mm.tools.shorts_scraper.model.VideoItem;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


class SimpleTxtListOfShortsTest {

    @Test
    void formatResponse() {
        // dummy test to see if we can get better test coverage with sonar cloud
        var simpleTxtListOfShorts = OutputFormatterFactory.outputFormatter(OutputFormatterFactory.SIMPLE);

        assertThat(simpleTxtListOfShorts).isExactlyInstanceOf(SimpleTxtListOfShorts.class);
        assertDoesNotThrow( () -> simpleTxtListOfShorts
                .formatResponse(
                        List.of(new VideoItem("video-id1", "video title")),
                        "Channel ID"));
    }
}