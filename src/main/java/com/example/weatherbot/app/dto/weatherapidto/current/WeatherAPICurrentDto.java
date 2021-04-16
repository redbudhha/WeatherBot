package com.example.weatherbot.app.dto.weatherapidto.current;

import com.example.weatherbot.app.dto.weatherapidto.CurrentWeatherInfo;
import com.example.weatherbot.app.dto.weatherapidto.Location;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class WeatherAPICurrentDto {
    @JsonProperty("location")
    private Location location;

    @JsonProperty("current")
    private CurrentWeatherInfo info;


}