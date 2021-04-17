package com.example.weatherbot.app.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@Document
public class Weather {
    @Id
    private String id;
    private final String cityName;
    private final Double temp;
    private final Integer pressure;
    private final Integer humidity;
    private final Double speed;
    private Double feelsLike;
    private String condition;
    private Float lat;
    private Float lon;
    private LocalDate date;

    public Weather(String cityName, Double temp, Integer pressure, Integer humidity, Double speed, Double feelsLike, String condition, Float lat, Float lon, LocalDate date) {
        this.cityName = cityName;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.speed = speed;
        this.feelsLike = feelsLike;
        this.condition = condition;
        this.lat = lat;
        this.lon = lon;
        this.date = date;
    }

    @Override
    public String toString() {
        return " City name: " + this.cityName +
                "\n" +
                "\nTemperature = " + this.temp + "°C" +
                "\nPressure = " + this.pressure + " mm. of mercury" +
                "\nHumidity = " + this.humidity + "%" +
                "\nWind speed = " + this.speed + " m\\s" +
                "\nFeels like = " + this.feelsLike + "°C" +
                "\nCondition = " + this.condition;
    }
}
