package com.example.weatherbot.app.dto.weatherapidto.forecast;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HourForecast {
    @JsonProperty("pressure_mb")
    private Integer pressure;

    @JsonProperty("feelslike_c")
    private Double feelsLike;

    @JsonProperty("wind_degree")
    private Double windDeg;


}
