package com.example.weatherbot.app.model.db_model;

import com.example.weatherbot.app.model.weather_model.OpenWeatherModel;
import com.example.weatherbot.app.model.weather_model.WeatherApiModel;
import com.example.weatherbot.app.model.weather_model.WeatherBitModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
public class WeatherModel {
    @Id
    private String id;
    private OpenWeatherModel openWeatherModel;
    private WeatherApiModel weatherApiModel;
    private WeatherBitModel weatherBitModel;
    private LocalDateTime localDateTime;

    public WeatherModel(OpenWeatherModel openWeatherModel, WeatherApiModel weatherApiModel, WeatherBitModel weatherBitModel) {
        this.openWeatherModel = openWeatherModel;
        this.weatherApiModel = weatherApiModel;
        this.weatherBitModel = weatherBitModel;
        this.localDateTime = LocalDateTime.now();
    }


}
