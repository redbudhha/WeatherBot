package com.example.weatherbot.app.dto.weatherbitdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeatherDescription {
    @JsonProperty("code")
    private Integer code;

    @JsonProperty("description")
    private String description;
}
