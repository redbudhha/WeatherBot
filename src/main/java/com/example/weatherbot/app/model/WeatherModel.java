package com.example.weatherbot.app.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Document
@Data
public class WeatherModel {    //класс для бд, объединяющий
    //какое-то поле будет id (есть ли в монго автогенерация?)
    @Id
    private LocalDate date;
    @Field
    private OpenWeatherModel openWeatherModel;
    @Field
    private WeatherApiModel weatherApiModel;
    @Field
    private WeatherGroundModel weatherGroundModel;

    public WeatherModel(LocalDate date, OpenWeatherModel openWeatherModel, WeatherApiModel weatherApiModel, WeatherGroundModel weatherGroundModel) {
        this.date = date;
        this.openWeatherModel = openWeatherModel;
        this.weatherApiModel = weatherApiModel;
        this.weatherGroundModel = weatherGroundModel;
    }
}
