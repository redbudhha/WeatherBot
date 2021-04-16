package com.example.weatherbot.app.model.db_model;

import com.example.weatherbot.app.model.weather_model.OpenWeatherModel;
import com.example.weatherbot.app.model.weather_model.WeatherApiModel;
import com.example.weatherbot.app.model.weather_model.WeatherBitModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document
@Data
public class WeatherModel {    //класс для бд, объединяющий
    //какое-то поле будет id (есть ли в монго автогенерация?)
    @Id
    private LocalDateTime date;
    @Field
    private OpenWeatherModel openWeatherModel;
    @Field
    private WeatherApiModel weatherApiModel;
    @Field
    private WeatherBitModel weatherBitModel;

    public WeatherModel(LocalDateTime date, OpenWeatherModel openWeatherModel, WeatherApiModel weatherApiModel, WeatherBitModel weatherBitModel) {
        this.date = date;
        this.openWeatherModel = openWeatherModel;
        this.weatherApiModel = weatherApiModel;
        this.weatherBitModel = weatherBitModel;
    }


}
