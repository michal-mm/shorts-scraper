package com.michal_mm.tools.shorts_scraper.output;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class OutputFormatterFactoryTest {

    @Test
    void testCsvFormatter() {
        assertThat(OutputFormatterFactory.outputFormatter(OutputFormatterFactory.CSV))
                .isExactlyInstanceOf(CsvListOfShorts.class);
    }

    @Test
    void testMarkdownFormatter() {
        assertThat(OutputFormatterFactory.outputFormatter(OutputFormatterFactory.MARKDOWN))
                .isExactlyInstanceOf(MarkdownListOfShorts.class);
    }

    @Test
    void testSimpleFormatter() {
        assertThat(OutputFormatterFactory.outputFormatter(OutputFormatterFactory.SIMPLE))
                .isExactlyInstanceOf(SimpleTxtListOfShorts.class);
    }

    @Test
    void testDefaultFormatter_andGetSimpleFormatter() {
        assertThat(OutputFormatterFactory.outputFormatter("not existing formatter"))
                .isExactlyInstanceOf(SimpleTxtListOfShorts.class);
    }

    @Test
    void testProvidedNullFormatter_andGetSimpleFormatter() {
        assertThat(OutputFormatterFactory.outputFormatter(null))
                .isExactlyInstanceOf(SimpleTxtListOfShorts.class);
    }
}