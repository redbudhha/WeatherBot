package com.example.weatherbot.app.dto.openweatherdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OpenWeather {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("main")
    private String condition;

    @JsonProperty("description")
    private String description;

    @JsonProperty("icon")
    private String icon;




}
