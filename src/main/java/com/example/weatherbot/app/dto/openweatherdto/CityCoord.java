package com.example.weatherbot.app.dto.openweatherdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CityCoord {
    @JsonProperty("lat")
    private Double lat;

    @JsonProperty("lon")
    private Double lon;


    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }
}
