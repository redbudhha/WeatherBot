package com.example.weatherbot.app.dto.weatherbitdto.forecast;

import com.example.weatherbot.app.dto.weatherbitdto.WeatherDescription;
import com.example.weatherbot.app.utils.UnixTimeStamp;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WeatherBitInfoForecast {
    @JsonProperty("ts")
    @JsonDeserialize(using = UnixTimeStamp.class)
    private LocalDateTime dateTime;

    @JsonProperty("temp")
    private Double temp;

    @JsonProperty("weather")
    private WeatherDescription desc;

    @JsonProperty("app_max_temp")
    private Double feelsLike;

    @JsonProperty("wind_spd")
    private Double windSpeed;

    @JsonProperty("wind_dir")
    private Double windDeg;

    @JsonProperty("pres")
    private Integer pressure;

    @JsonProperty("rh")
    private Integer humidity;


}
