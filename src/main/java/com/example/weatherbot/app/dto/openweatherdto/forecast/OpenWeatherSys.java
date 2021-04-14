package com.example.weatherbot.app.dto.openweatherdto.forecast;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OpenWeatherSys {

    @JsonProperty("pod")
    private String pod;


}
