package com.example.weatherbot.app.dto.weatherapidto.forecast;

import com.example.weatherbot.app.dto.weatherapidto.Location;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
public class WeatherAPIForecastDto {
    @JsonProperty("location")
    private Location location;


    @JsonProperty("forecast")
    private List <ForecastDay> forecasts;

}
