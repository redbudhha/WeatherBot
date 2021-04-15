package com.example.weatherbot.app.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Weather {
    private final Double temp;
    private final Double pressure;
    private final Integer humidity;
    private final Double speed;
    private Double feelsLike;
    private String condition;
    private LocalDateTime localDateTime;
    private Double lat;
    private Double lon;

    public Weather(Double temp, Double pressure, Integer humidity, Double feelsLike, String condition, Double speed, LocalDateTime localDateTime,Double lat,Double lon) {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.feelsLike = feelsLike;
        this.condition = condition;
        this.speed = speed;
        this.localDateTime = localDateTime;
        this.lat = lat;
        this.lon = lon;
    }
}
