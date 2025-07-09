package com.example.weatherapp.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DailyData {
     @JsonProperty("time")
    private List<String> time;
    @JsonProperty("temperature_2m_mean")
    private List<Double> temperature2mMean;

    // Getters
    public List<String> getTime() {
        return time;
    }

    public List<Double> getTemperature2mMean() {
        return temperature2mMean;
    }
}
