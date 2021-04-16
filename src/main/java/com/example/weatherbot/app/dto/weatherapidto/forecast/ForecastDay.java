package com.example.weatherbot.app.dto.weatherapidto.forecast;

import com.example.weatherbot.app.utils.UnixTimeStamp;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ForecastDay {
    @JsonProperty("date_epoch")
    @JsonDeserialize(using = UnixTimeStamp.class)
    private LocalDateTime dateTime;

    @JsonProperty("day")
    DayWeatherInfo weatherInfo;

    @JsonProperty("hour")
    HourForecast hourForecast;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public DayWeatherInfo getWeatherInfo() {
        return weatherInfo;
    }

    public HourForecast getHourForecast() {
        return hourForecast;
    }
}
