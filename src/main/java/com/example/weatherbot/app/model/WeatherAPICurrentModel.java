package com.example.weatherbot.app.model;

import com.example.weatherbot.app.dto.weatherapidto.current.WeatherAPICurrentDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WeatherAPICurrentModel {

    private final String cityName;
    private final Double temp;
    private final Double pressure;
    private final Integer humidity;
    private final Double feelsLike;
    private String condition;
    private LocalDateTime dateTime;
    private float lat;
    private float lon;


    public WeatherAPICurrentModel(WeatherAPICurrentDto dto) {
        this.cityName = dto.getLocation().getCityName();
        this.temp = dto.getInfo().getTemp();
        this.pressure = dto.getInfo().getPressure();
        this.humidity = dto.getInfo().getHumidity();
        this.feelsLike = dto.getInfo().getFeelsLike();
        this.condition = dto.getInfo().getCondition().getDescription();
        this.dateTime = dto.getLocation().getLocalTime();
        this.lat = dto.getLocation().getLat();
        this.lon = dto.getLocation().getLon();
    }

    @Override
    public String toString() {
        return "WeatherAPICurrentModel{" +
                "cityName='" + cityName + '\'' +
                ", temp=" + temp +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", feelsLike=" + feelsLike +
                ", condition='" + condition + '\'' +
                ", dateTime=" + dateTime +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
