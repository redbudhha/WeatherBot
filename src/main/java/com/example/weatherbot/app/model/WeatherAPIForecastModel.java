package com.example.weatherbot.app.model;

import com.example.weatherbot.app.dto.weatherapidto.forecast.WeatherAPIForecastDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WeatherAPIForecastModel {
    private final String cityName;
    private final LocalDateTime dateTime;
    private final Float lat;
    private final Float lon;

    public WeatherAPIForecastModel(WeatherAPIForecastDto dto) {
        this.cityName = dto.getLocation().getCityName();
        this.dateTime = dto.getLocation().getLocalTime();
        this.lat = dto.getLocation().getLat();
        this.lon = dto.getLocation().getLon();
    }

    @Override
    public String toString() {
        return "WeatherAPIForecastModel{" +
                "cityName='" + this.cityName + '\'' +
                ", dateTime=" + this.dateTime +
                ", lat=" + this.lat +
                ", lon=" + this.lon +
                '}';
    }
}
