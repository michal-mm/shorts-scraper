package com.michal_mm.tools.shorts_scraper.output;

import com.michal_mm.tools.shorts_scraper.model.VideoItem;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class CsvListOfShortsTest {

    @Test
    void formatResponse() {
        // dummy test to see if we can get better test coverage with sonar cloud
        var csvListOfShorts = OutputFormatterFactory.outputFormatter(OutputFormatterFactory.CSV);

        assertThat(csvListOfShorts).isExactlyInstanceOf(CsvListOfShorts.class);
        assertDoesNotThrow( () -> csvListOfShorts
                .formatResponse(
                        List.of(new VideoItem("video-id1", "video title")),
                        "Channel ID"));
    }
}
