package com.michal_mm.tools.shorts_scraper.output;

import com.michal_mm.tools.shorts_scraper.model.VideoItem;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MarkdownListOfShortsTest {

    @Test
    void formatResponse() {
        // dummy test to see if we can get better test coverage with sonar cloud
        var mdListOfShorts = OutputFormatterFactory.outputFormatter(OutputFormatterFactory.MARKDOWN);

        assertThat(mdListOfShorts).isExactlyInstanceOf(MarkdownListOfShorts.class);
        assertDoesNotThrow( () -> mdListOfShorts
                .formatResponse(
                        List.of(new VideoItem("video-id1", "video title")),
                        "Channel ID"));
    }
}
