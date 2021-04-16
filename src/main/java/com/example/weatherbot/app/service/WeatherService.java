package com.example.weatherbot.app.service;

import com.example.weatherbot.app.model.db_model.WeatherModel;
import com.example.weatherbot.app.model.weather_model.OpenWeatherModel;
import com.example.weatherbot.app.model.weather_model.WeatherApiModel;
import com.example.weatherbot.app.model.weather_model.WeatherBitModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public WeatherModel save(WeatherBitModel weatherBitModel, OpenWeatherModel openWeatherModel, WeatherApiModel weatherApiModel) {
        WeatherModel weatherModel = new WeatherModel(openWeatherModel, weatherApiModel, weatherBitModel);
        return mongoTemplate.save(weatherModel);
    }
}
