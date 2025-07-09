package com.example.weatherapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DailyUnits {
   @JsonProperty("time")
        private String time;
        @JsonProperty("temperature_2m_mean")
        private String temperature2mMean;

        // Getters
        public String getTime() {
            return time;
        }

        public String getTemperature2mMean() {
            return temperature2mMean;
        }
}
