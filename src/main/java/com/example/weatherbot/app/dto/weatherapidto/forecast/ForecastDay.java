package com.example.weatherbot.app.dto.weatherapidto.forecast;

import com.example.weatherbot.app.utils.UnixTimeStamp;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ForecastDay {
    @JsonProperty("date_epoch")
    @JsonDeserialize(using = UnixTimeStamp.class)
    private LocalDateTime dateTime;

    @JsonProperty("day")
    DayWeatherInfo weatherInfo;


    @JsonProperty("hour")
    private List<HourForecast> hourForecast;

}
