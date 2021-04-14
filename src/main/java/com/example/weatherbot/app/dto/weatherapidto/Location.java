package com.example.weatherbot.app.dto.weatherapidto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Location {

    @JsonProperty("location")
    private String cityName;

    @JsonProperty("region")
    private String region;


    @JsonProperty("country")
    private String country;

    @JsonProperty("lat")
    private Float lat;

    @JsonProperty("lon")
    private Float lon;

    @JsonProperty("localtime")
    LocalDateTime localTime;

}
