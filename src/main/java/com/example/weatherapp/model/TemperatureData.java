package com.example.weatherapp.model;

import java.util.List;

public class TemperatureData {
 private List<String> labels;
    private List<Double> values;
    private String cityName;

    public TemperatureData(List<String> labels, List<Double> values, String cityName) {
        this.labels = labels;
        this.values = values;
        this.cityName = cityName;
    }

    // Getters
    public List<String> getLabels() {
        return labels;
    }

    public List<Double> getValues() {
        return values;
    }

    public String getCityName() {
        return cityName;
    }
}
