package com.telegram.example.weatherbot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public final class WeatherDto {

    private String condition;
    private double temp;
    private long pressure;
    private int humidity;
    private String countryName;
    private int sunriseTime;
    private int sunsetTime;

    private int windSpeed;
    private double windDeg;

    @JsonProperty("cod")
    private int status;

    @JsonProperty("name")
    private String cityName;


    @JsonProperty("weather")
    private void unpackWeather(List<Map<String, Object>> weather) {
        this.condition = (String) weather.get(0).get("main");
    }

    @JsonProperty("main")
    private void unpackMain(Map<String, Object> main) {
        this.temp = Double.parseDouble(main.get("temp").toString());
        this.pressure = (Integer) main.get("pressure");
        this.humidity = (Integer) main.get("humidity");
    }

    @JsonProperty("sys")
    private void unpackSys(Map<String, Object> sys) {
        this.sunriseTime = (Integer) sys.get("sunrise");
        this.sunsetTime = (Integer) sys.get("sunset");
    }

    @JsonProperty("wind")
    private void unpackWind(Map<String, Object> wind) {
        this.windSpeed = (int) Double.parseDouble(wind.get("speed").toString());
        this.windDeg = Double.parseDouble(wind.get("deg").toString());
    }

    @Override
    public String toString() {
        return "condition = " + this.condition + '\'' +
                ", temp = " + this.temp +
                ", pressure = " + this.pressure +
                ", humidity = " + this.humidity +
                ", sunriseTime = " + this.sunriseTime +
                ", sunsetTime = " + this.sunsetTime +
                ", windSpeed = " + this.windSpeed +
                ", windDeg = " + this.windDeg +
                ", status = " + this.status +
                ", cityName = " + this.cityName + '\'' +
                '}';
    }
}
