package com.example.weatherbot.app.dto.openweatherdto.current;

import com.example.weatherbot.app.dto.openweatherdto.*;
import com.example.weatherbot.app.utils.UnixTimeStamp;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class OpenWeatherCurrentDto {

    @JsonProperty("cod")
    private Integer statusCode;

    @JsonProperty("coord")
    private CityCoord coordinate;

    @JsonProperty("weather")
    private List<OpenWeather> weather;

    @JsonProperty("base")
    private String base;

    @JsonProperty("main")
    private WeatherMetrics main;

    @JsonProperty("visibility")
    private Integer visibility;

    @JsonProperty("wind")
    private Wind wind;

    @JsonProperty("clouds")
    private Clouds clouds;

    @JsonProperty("dt")
    @JsonDeserialize(using = UnixTimeStamp.class)
    private LocalDateTime dateTime;

    @JsonProperty("sys")
    private OpenWeatherSysCurrent currentSys;


    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("timezone")
    private Integer zone;

    public Integer getStatusCode() {
        return statusCode;
    }

    public CityCoord getCoordinate() {
        return coordinate;
    }

    public List<OpenWeather> getWeather() {
        return weather;
    }

    public String getBase() {
        return base;
    }

    public WeatherMetrics getMain() {
        return main;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public OpenWeatherSysCurrent getCurrentSys() {
        return currentSys;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getZone() {
        return zone;
    }
}
