package com.example.weatherbot.app.model;

import com.example.weatherbot.app.dto.weatherapidto.current.WeatherAPICurrentDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WeatherApiModel {

    private final String cityName;
    private final Double temp;
    private final Double pressure;
    private final Integer humidity;
    private final Double feelsLike;
    private String condition;
    private LocalDateTime dateTime;
    private float lat;
    private float lon;



    public WeatherApiModel(WeatherAPICurrentDto dto) {
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

    /*  Здесь нужен тот же подход, что и OpenWeatherModel
    public WeatherApiModel(WeatherAPIForecastDto dto) {
        this.cityName = dto.getLocation().getCityName();
        this.dateTime = dto.getLocation().getLocalTime();
        this.lat = dto.getLocation().getLat();
        this.lon = dto.getLocation().getLon();
    }

    */
}
