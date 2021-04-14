package com.example.weatherbot.app.dto.openweatherdto.forecast;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
public class OpenWeatherForecastDto {
    @JsonProperty("cod")
    private Integer statusCode;

    @JsonProperty("message")
    private Integer message;

    @JsonProperty("cnt")
    private Integer cnt;

    @JsonProperty("list")
    private List<OpenWeatherThreeHourForecast> hourlyArray;

    @JsonProperty("city")
    private City city;

    public Integer getStatusCode() {
        return statusCode;
    }

    public Integer getMessage() {
        return message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public List<OpenWeatherThreeHourForecast> getHourlyArray() {
        return hourlyArray;
    }

    public City getCity() {
        return city;
    }
}
