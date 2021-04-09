package com.telegram.example.weatherbot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastDto {

    private List<String> condition = new ArrayList<>();
    private List<Double> dayTemp = new ArrayList<>();
    private List<Double> nightTemp = new ArrayList<>();

    private String cityName;

    @JsonProperty("cnt")
    private int days;

    @JsonProperty("cod")
    private int status;

    @JsonProperty("city")
    private void unpackCity(Map<String, Object> map) {
        this.cityName = map.get("name").toString();
    }

    @JsonProperty("list")
    private void unpackList(List<Map<String, Object>> list) {
        for (Map<String, Object> map : list) {
            unpackTemp((Map<String, Object>) map.get("temp"));
            unpackWeather((List<Map<String, Object>>) map.get("weather"));
        }
    }

    private void unpackTemp(Map<String, Object> map) {
        this.dayTemp.add(Double.parseDouble(map.get("day").toString()));
        this.nightTemp.add(Double.parseDouble(map.get("night").toString()));
    }

    private void unpackWeather(List<Map<String, Object>> weather) {
        this.condition.add((String) weather.get(0).get("main"));

    }

    @Override
    public String toString() {
        return "condition =" + this.condition +
                ", dayTemp = " + this.dayTemp +
                ", nightTemp =" + this.nightTemp +
                ", cityName = " + this.cityName + '\'' +
                ", days = " + this.days +
                ", status = " + this.status +
                '}';
    }
}