package com.example.weatherbot.app.dto.weatherapidto.forecast;

import com.example.weatherbot.app.dto.weatherapidto.current.Condition;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DayWeatherInfo {
    @JsonProperty("maxtemp_c")
    private Double maxTemp;

    @JsonProperty("mintemp_c")
    private Double minTemp;

    @JsonProperty("avgtemp_c")
    private Double avgTemp;

    @JsonProperty("avghumidity")
    private Double avgHumidity;

    @JsonProperty("condition")
    private Condition condition;

}
