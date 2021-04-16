package com.example.weatherbot.app.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Weather {
    private final Double temp;
    private final Integer pressure;
    private final Integer humidity;
    private final Double speed;
    private Double feelsLike;
    private String condition;
    private Float lat;
    private Float lon;

    public Weather(Double temp, Integer pressure, Integer humidity,Double speed, Double feelsLike, String condition,Float lat,Float lon) {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.speed = speed;
        this.feelsLike = feelsLike;
        this.condition = condition;
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public String toString() {
        return " Temp = " + this.temp + "°C" +
                "\nPressure = " + this.pressure + "mm. of mercury" +
                "\nHumidity = " + this.humidity + "%" +
                "\nWind speed = " + this.speed + " m\\s" +
                "\nFeels like = " + this.feelsLike + "°C" +
                "\nCondition = " + this.condition;
    }
}
