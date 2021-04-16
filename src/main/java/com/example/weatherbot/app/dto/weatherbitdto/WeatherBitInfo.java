package com.example.weatherbot.app.dto.weatherbitdto;

import com.example.weatherbot.app.utils.UnixTimeStamp;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WeatherBitInfo {
    @JsonProperty("lat")
    private Float lat;

    @JsonProperty("lon")
    private Float lon;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("city_name")
    private String cityName;

    @JsonProperty("wind_spd")
    private Double windSpeed;

    @JsonProperty("wind_dir")
    private Double windDeg;

    @JsonProperty("weather")
    private WeatherDescription desc;


    @JsonProperty("ts")
    @JsonDeserialize(using = UnixTimeStamp.class)
    private LocalDateTime dateTime;


    @JsonProperty("temp")
    private Double temp;

    @JsonProperty("pres")
    private Integer pressure;

    @JsonProperty("rh")
    private Integer humidity;

    @JsonProperty("app_temp")
    private Double feelsLike;
}
