package com.example.weatherapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenMeteoResponse {
@JsonProperty("latitude")
    private double latitude;
    @JsonProperty("longitude")
    private double longitude;
    @JsonProperty("generationtime_ms")
    private double generationtimeMs;
    @JsonProperty("utc_offset_seconds")
    private int utcOffsetSeconds;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("timezone_abbreviation")
    private String timezoneAbbreviation;
    @JsonProperty("elevation")
    private double elevation;
    @JsonProperty("daily_units")
    private DailyUnits dailyUnits;
    @JsonProperty("daily")
    private DailyData daily;

    // Getters
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getGenerationtimeMs() {
        return generationtimeMs;
    }

    public int getUtcOffsetSeconds() {
        return utcOffsetSeconds;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getTimezoneAbbreviation() {
        return timezoneAbbreviation;
    }

    public double getElevation() {
        return elevation;
    }

    public DailyUnits getDailyUnits() {
        return dailyUnits;
    }

    public DailyData getDaily() {
        return daily;
    }
}
