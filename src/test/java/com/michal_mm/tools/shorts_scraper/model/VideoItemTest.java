package com.michal_mm.tools.shorts_scraper.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class VideoItemTest {

    @Test
    void titleCsv() {
        var title = "abc,def";
        var expected = "abc;def";

        var vi = new VideoItem("video-id", title);
        assertThat(vi.titleCsv()).isEqualTo(expected);
    }

    @Test
    void titleCsvNoSeparators() {
        var title = "abcdef";
        var expected = "abcdef";

        var vi = new VideoItem("video-id", title);
        assertThat(vi.titleCsv()).isEqualTo(expected);
    }

    @Test
    void titleMd() {
        var title = "abc||def";
        var expected = "abc\\|\\|def";

        var vi = new VideoItem("video-id", title);
        assertThat(vi.titleMd()).isEqualTo(expected);
    }

    @Test
    void titleMdNoCharsToEscape() {
        var title = "abcdef";
        var expected = "abcdef";

        var vi = new VideoItem("video-id", title);
        assertThat(vi.titleMd()).isEqualTo(expected);
    }
}