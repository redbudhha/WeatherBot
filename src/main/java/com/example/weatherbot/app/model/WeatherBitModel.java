package com.example.weatherbot.app.model;

import com.example.weatherbot.app.dto.weatherbitdto.WeatherBitInfo;
import com.example.weatherbot.app.dto.weatherbitdto.forecast.WeatherBitForecastDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WeatherBitModel {
    private String cityName;
    private Double temp;
    private Integer pressure;
    private Integer humidity;
    private Double feelsLike;
    private String condition;
    private Float lat;
    private Float lon;
    private Double windSpeed;
    private Double windDeg;
    private LocalDateTime dateTimeForForecast;
    private LocalDateTime dateTimeForCurrent;


    public WeatherBitModel(WeatherBitInfo currentInfo) {
        this.cityName = currentInfo.getCityName();
        this.temp = currentInfo.getTemp();
        this.pressure = currentInfo.getPressure();
        this.humidity = currentInfo.getHumidity();
        this.feelsLike = currentInfo.getFeelsLike();
        this.condition = currentInfo.getDesc().getDescription();
        this.lat = currentInfo.getLat();
        this.lon = currentInfo.getLon();
        this.windSpeed = currentInfo.getWindSpeed();
        this.windDeg = currentInfo.getWindDeg();
        this.dateTimeForCurrent = currentInfo.getDateTimeForCurrent();

    }

    public WeatherBitModel(WeatherBitForecastDto forecastDto, WeatherBitInfo forecastInfo) {
        this.cityName = forecastDto.getCityName();
        this.temp = forecastInfo.getTemp();
        this.pressure = forecastInfo.getPressure();
        this.humidity = forecastInfo.getHumidity();
        this.feelsLike = forecastInfo.getFeelsLike();
        this.condition = forecastInfo.getDesc().getDescription();
        this.lat = forecastDto.getLat();
        this.lon = forecastDto.getLon();
        this.windSpeed = forecastInfo.getWindSpeed();
        this.windDeg = forecastInfo.getWindDeg();
        this.dateTimeForForecast = forecastInfo.getDateTimeForForecast();

    }

}
