package com.example.weatherbot.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.List;
import java.util.Map;



@JsonIgnoreProperties(ignoreUnknown = true)
public final class WeatherDto {

    private String condition;
    private double temp;
    private double feelsLike;
    private long pressure;
    private int humidity;
    private String countryName;
    private int sunriseTime;
    private int sunsetTime;
    private float lat;
    private float lon;


    private int windSpeed;
    private double windDeg;

    @JsonProperty("cod")
    private int status;

    @JsonProperty("name")
    private String cityName;

    @JsonProperty("coord")
    private void unpackCoord(Map<String,Object> coord) {
        this.lat = Float.parseFloat(coord.get("lat").toString());
        this.lon = Float.parseFloat(coord.get("lon").toString());
    }


    @JsonProperty("weather")
    private void unpackWeather(List<Map<String, Object>> weather) {
        this.condition = (String) weather.get(0).get("main");
    }

    @JsonProperty("main")
    private void unpackMain(Map<String, Object> main) {
        this.temp = Double.parseDouble(main.get("temp").toString());
        this.pressure = (Integer) main.get("pressure");
        this.humidity = (Integer) main.get("humidity");
        this.feelsLike = Double.parseDouble(main.get("feels_like").toString());
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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public long getPressure() {
        return pressure;
    }

    public void setPressure(long pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getSunriseTime() {
        return sunriseTime;
    }

    public void setSunriseTime(int sunriseTime) {
        this.sunriseTime = sunriseTime;
    }

    public int getSunsetTime() {
        return sunsetTime;
    }

    public void setSunsetTime(int sunsetTime) {
        this.sunsetTime = sunsetTime;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(double windDeg) {
        this.windDeg = windDeg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }


    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }
}
