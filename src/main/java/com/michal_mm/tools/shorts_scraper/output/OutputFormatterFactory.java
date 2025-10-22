package com.michal_mm.tools.shorts_scraper.output;

public class OutputFormatterFactory {

    public static final String SIMPLE = "SIMPLE";
    public static final String MARKDOWN = "MARKDOWN";
    public static final String CSV = "CSV";

    private OutputFormatterFactory() {
        throw new IllegalStateException("Util class, don't instantiate");
    }

    public static ListOfShorts outputFormatter (String preferredFormat) {
        return switch (preferredFormat) {
            case null -> new SimpleTxtListOfShorts();
            case SIMPLE -> new SimpleTxtListOfShorts();
            case MARKDOWN -> new MarkdownListOfShorts();
            case CSV -> new CsvListOfShorts();
            default -> {
                IO.println("WARN: using default SIMPLE output formatter");
                yield new SimpleTxtListOfShorts();
            }
        };
    }
}
