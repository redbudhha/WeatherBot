package com.example.weatherbot.app.dto.openweatherdto.current;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OpenWeatherSysCurrent {
    @JsonProperty("type")
    private Integer type;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("country")
    private String country;

    @JsonProperty("sunrise")
    private Long sunrise;

    @JsonProperty("sunset")
    private Long sunset;


}
