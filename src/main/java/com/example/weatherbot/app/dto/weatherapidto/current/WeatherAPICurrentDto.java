package com.example.weatherbot.app.dto.weatherapidto.current;

import com.example.weatherbot.app.dto.weatherapidto.CurrentWeatherInfo;
import com.example.weatherbot.app.dto.weatherapidto.Location;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class WeatherAPICurrentDto {
    @JsonProperty("location")
    private Location location;

    @JsonProperty("location")
    private CurrentWeatherInfo info;

    @JsonProperty("wind_kph")
    private Double windSpeed;

    @JsonProperty("wind_degree")
    private Double windDeg;

    @JsonProperty("pressure_mb")
    private Integer pressure;

    @JsonProperty("humidity")
    private Integer humidity;

    @JsonProperty("feelslike_c")
    private Double feelsLike;
}