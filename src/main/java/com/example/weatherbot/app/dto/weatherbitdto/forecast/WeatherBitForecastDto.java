package com.example.weatherbot.app.dto.weatherbitdto.forecast;

import com.example.weatherbot.app.dto.weatherbitdto.WeatherBitInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WeatherBitForecastDto {
    @JsonProperty("data")
    private List<WeatherBitInfo> mainInfo;

    @JsonProperty("city_name")
    private String cityName;

    @JsonProperty("lat")
    private Float lat;

    @JsonProperty("lon")
    private Float lon;

    @JsonProperty("country_code")
    private String countryCode;
}
