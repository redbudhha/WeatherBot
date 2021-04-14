package com.example.weatherbot.app.dto.weatherapidto;

import com.example.weatherbot.app.dto.weatherapidto.current.Condition;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CurrentWeatherInfo {
    @JsonProperty("temp_c")
    private Double temp;

    @JsonProperty("condition")
    private Condition condition;

    @JsonProperty("wind_kph")
    private Double windSpeed;

    @JsonProperty("wind_degree")
    private Double windDeg;

    @JsonProperty("pressure_mb")
    private Double pressure;

    @JsonProperty("humidity")
    private Integer humidity;

    @JsonProperty("feelslike_c")
    private Double feelsLike;

}
