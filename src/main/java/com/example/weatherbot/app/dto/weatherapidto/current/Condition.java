package com.example.weatherbot.app.dto.weatherapidto.current;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Condition {
    @JsonProperty("text")
    private String description;

}
