package com.example.weatherbot.app.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastDto {

    private List<String> condition = new ArrayList<>();
    private List<Double> dayTemp = new ArrayList<>();
    private List<Double> nightTemp = new ArrayList<>();
    private String cityName;
    private String countryName;

    @JsonProperty("cnt")
    private int days;

    @JsonProperty("cod")
    private int status;

    @JsonProperty("city")
    private void unpackCity(Map<String, Object> map) {
        this.cityName = map.get("name").toString();
        this.countryName = map.get("country").toString();
    }

    @JsonProperty("list")
    @SuppressWarnings("unchecked")
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

    public List<String> getCondition() {
        return condition;
    }

    public void setCondition(List<String> condition) {
        this.condition = condition;
    }

    public List<Double> getDayTemp() {
        return dayTemp;
    }

    public void setDayTemp(List<Double> dayTemp) {
        this.dayTemp = dayTemp;
    }

    public List<Double> getNightTemp() {
        return nightTemp;
    }

    public void setNightTemp(List<Double> nightTemp) {
        this.nightTemp = nightTemp;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "condition = " + this.condition +
                ", day temp = " + this.dayTemp +
                ", night temp = " + this.nightTemp +
                ", City name = " + this.cityName + '\'' +
                ", Country name ='" + this.countryName + '\'' +
                ", days = " + this.days +
                ", status = " + this.status;
    }
}