package com.example.weatherbot.app.model.weather_model;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@AllArgsConstructor
@Data
public class OpenWeatherModel {
    private final String cityName;
    private final Double temp;
    private final Integer pressure;
    private final Integer humidity;
    private Double feelsLike;
    private String condition;
    private Float lat;
    private Float lon;
    private Double windSpeed;
    private Double windDeg;
    private LocalDateTime dateTime;


}
