package com.example.weatherbot.app.model;

import com.example.weatherbot.app.dto.openweatherdto.current.OpenWeatherCurrentDto;
import com.example.weatherbot.app.dto.openweatherdto.forecast.OpenWeatherForecastDto;
import com.example.weatherbot.app.dto.openweatherdto.forecast.OpenWeatherThreeHourForecast;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OpenWeatherModel {
    private final String cityName;
    private final Double temp;
    private final Integer pressure;
    private final Integer humidity;
    private Double feelsLike;
    private String condition;
    private Double lat;
    private Double lon;
    private Double windSpeed;
    private Double windDeg;
    private LocalDateTime dateTime;

    public OpenWeatherModel(OpenWeatherCurrentDto dto) {
        this.cityName = dto.getName();
        this.temp = dto.getMain().getTemp();
        this.pressure = dto.getMain().getPressure();
        this.humidity = dto.getMain().getHumidity();
        this.feelsLike = dto.getMain().getFeelsLike();
        this.lat = dto.getCoordinate().getLat();
        this.lon = dto.getCoordinate().getLon();
        this.windSpeed = dto.getWind().getSpeed();
        this.windDeg = dto.getWind().getDeg();
        this.dateTime = dto.getDateTime();
    }

    public OpenWeatherModel(OpenWeatherForecastDto forecastDto, OpenWeatherThreeHourForecast tomorrowForecast) {
        this.cityName = forecastDto.getCity().toString();
        this.temp = tomorrowForecast.getMainMetrics().getTemp();
        this.pressure = tomorrowForecast.getMainMetrics().getPressure();
        this.humidity = tomorrowForecast.getMainMetrics().getHumidity();
        this.feelsLike = tomorrowForecast.getMainMetrics().getFeelsLike();
        this.lat = forecastDto.getCity().getCoords().getLat();
        this.lon = forecastDto.getCity().getCoords().getLon();
        this.windSpeed = tomorrowForecast.getWind().getSpeed();
        this.windDeg = tomorrowForecast.getWind().getDeg();
        this.dateTime = tomorrowForecast.getDateTime();
    }

}
