package com.michal_mm.tools.shorts_scraper;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ShortsScraperTest {

    @Test
    void testMain() {
        String[] input = new String[0];
        assertThrows(IllegalStateException.class, () -> ShortsScraper.main(input));
    }

    @Test
    void testSimpleWithHttp400() {
        assertThrows(IOException.class, () -> ShortsScraper.main(new String[] {"@java"}));
        assertThrows(IOException.class, () -> ShortsScraper.main(new String[] {"UCksTNgiRyQGwi2ODBie8HdA"}));
    }
}
