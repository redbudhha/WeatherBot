package com.example.weatherbot.app.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Weather {
    private final Double temp;
    private final Double pressure;
    private final Integer humidity;
    private final Double speed;
    private Double feelsLike;
    private String condition;

    public Weather(Double temp, Double pressure, Integer humidity, Double feelsLike, String condition,Double speed) {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.feelsLike = feelsLike;
        this.condition = condition;
        this.speed = speed;
    }
}
