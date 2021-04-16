package com.example.weatherbot.app.service;

import com.example.weatherbot.app.model.db_model.WeatherModel;
import com.example.weatherbot.app.model.weather_model.OpenWeatherModel;
import com.example.weatherbot.app.model.weather_model.WeatherApiModel;
import com.example.weatherbot.app.model.weather_model.WeatherBitModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WeatherService {
    //здесь или template или repository

    public void save(WeatherBitModel weatherBitModel, OpenWeatherModel openWeatherModel, WeatherApiModel weatherApiModel) {
        WeatherModel weatherModel = new WeatherModel(LocalDateTime.now(), openWeatherModel, weatherApiModel, weatherBitModel);
        //template.save(weatherModel)
    }
}
