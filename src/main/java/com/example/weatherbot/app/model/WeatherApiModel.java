package com.example.weatherbot.app.model;

import com.example.weatherbot.app.dto.weatherapidto.current.WeatherAPICurrentDto;
import com.example.weatherbot.app.dto.weatherapidto.forecast.ForecastDay;
import com.example.weatherbot.app.dto.weatherapidto.forecast.WeatherAPIForecastDto;
import lombok.Data;

import java.time.LocalDateTime;

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
        this.windSpeed = dto.getInfo().getWindSpeed();
        this.windDeg = dto.getInfo().getWindDeg();
        this.dateTime = dto.getLocation().getLocalTime();
    }


    public WeatherApiModel(WeatherAPIForecastDto dto, ForecastDay forecastDay) {
        this.cityName = dto.getLocation().getCityName();
        this.temp = forecastDay.getWeatherInfo().getAvgTemp();
        this.pressure = forecastDay.getHourForecast().getPressure();
        this.humidity = forecastDay.getWeatherInfo().getAvgHumidity();
        this.feelsLike = forecastDay.getHourForecast().getFeelsLike();
        this.condition = forecastDay.getWeatherInfo().getCondition().getDescription();
        this.dateTime = dto.getLocation().getLocalTime();
        this.lat = dto.getLocation().getLat();
        this.lon = dto.getLocation().getLon();
        this.windSpeed = forecastDay.getWeatherInfo().getWindSpeed();
        this.windDeg = forecastDay.getHourForecast().getWindDeg();


    }


}
