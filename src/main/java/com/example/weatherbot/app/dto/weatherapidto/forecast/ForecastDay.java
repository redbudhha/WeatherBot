package com.example.weatherbot.app.dto.weatherapidto.forecast;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ForecastDay {
   @JsonProperty("date")
    LocalDateTime dateTime;

   @JsonProperty("day")
    DayWeatherInfo weatherInfo;

   @JsonProperty("hour")
    HourForecast hourForecast;



}
