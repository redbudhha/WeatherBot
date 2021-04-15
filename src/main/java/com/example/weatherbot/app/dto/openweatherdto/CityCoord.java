package com.example.weatherbot.app.dto.openweatherdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CityCoord {
    @JsonProperty("lat")
    private Float lat;

    @JsonProperty("lon")
    private Float lon;


    public Float getLat() {
        return lat;
    }

    public Float getLon() {
        return lon;
    }
}
