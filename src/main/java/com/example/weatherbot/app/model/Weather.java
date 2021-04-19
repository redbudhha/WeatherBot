package com.example.weatherbot.app.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.DecimalFormat;
import java.time.LocalDate;

@Getter
@Setter
@Document
public class Weather {
    @Id
    private String id;
    private String cityName;
    private final Double temp;
    private final Integer pressure;
    private final Integer humidity;
    private final Double speed;
    private final Double feelsLike;
    private final String condition;
    private final Float lat;
    private final Float lon;
    private LocalDate date;
    private boolean current;

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
        return " Date: " + this.date +
                "\nCity name: " + this.cityName +
                "\n" +
                "\nTemperature = " + new DecimalFormat("0").format(this.temp) + "°C" +
                "\nFeels like = " + new DecimalFormat("0").format(this.feelsLike) + "°C" +
                "\nCondition = " + this.condition +
                "\nWind speed = " + new DecimalFormat("0").format(this.speed) + " m\\s" +
                "\nPressure = " + this.pressure + " mm. of mercury" +
                "\nHumidity = " + this.humidity + "%";
    }
}
