package com.example.urlshortener.urlShortenerModule.shared.enums;

public enum TimeUnit {
    SECOND("seconds"),
    MINUTE("minutes"),
    HOUR("hours"),
    DAY("days"),
    MONTH("months"),
    YEAR("years");

    private final String unit;

    TimeUnit(final String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return unit;
    }
}
