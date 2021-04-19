package com.example.weatherbot.app.model.weather_model;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class WeatherApiModel {
    private final String cityName;
    private final Double temp;
    private final Integer pressure;
    private final Integer humidity;
    private final Double feelsLike;
    private final String condition;
    private final Float lat;
    private final Float lon;
    private final Double windSpeed;
    private final Double windDeg;
    private LocalDateTime dateTime;
}
