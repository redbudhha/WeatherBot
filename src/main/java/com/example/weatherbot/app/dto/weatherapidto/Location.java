package com.example.weatherbot.app.dto.weatherapidto;

import com.example.weatherbot.app.utils.UnixTimeStamp;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Location {

    @JsonProperty("name")
    private String cityName;

    @JsonProperty("region")
    private String region;

    @JsonProperty("country")
    private String country;

    @JsonProperty("lat")
    private Float lat;

    @JsonProperty("lon")
    private Float lon;


    @JsonProperty("localtime_epoch")
    @JsonDeserialize(using = UnixTimeStamp.class)
    LocalDateTime localTime;

}
