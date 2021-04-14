package com.example.weatherbot.app.dto.openweatherdto.forecast;

import com.example.weatherbot.app.dto.openweatherdto.Clouds;
import com.example.weatherbot.app.dto.openweatherdto.OpenWeather;
import com.example.weatherbot.app.dto.openweatherdto.WeatherMetrics;
import com.example.weatherbot.app.dto.openweatherdto.Wind;
import com.example.weatherbot.app.utils.UnixTimeStamp;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OpenWeatherThreeHourForecast {
    @JsonProperty("dt")
    @JsonDeserialize(using = UnixTimeStamp.class)
    private LocalDateTime dateTime;

    @JsonProperty("main")
    private WeatherMetrics mainMetrics;

    @JsonProperty("weather")
    private List<OpenWeather> weather;

    @JsonProperty("clouds")
    private Clouds clouds;

    @JsonProperty("wind")
    private Wind wind;

    @JsonProperty("visibility")
    private Integer visibility;

    @JsonProperty("pop")
    private Integer pop;

    @JsonProperty("sys")
    private OpenWeatherSys sys;

    @JsonProperty("dt_txt")
    String localDateTime;

}
