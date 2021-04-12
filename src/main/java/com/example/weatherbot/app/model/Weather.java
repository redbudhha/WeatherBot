package com.example.weatherbot.app.model;

import com.example.weatherbot.app.dto.WeatherDto;


public final class Weather {
    private final String cityName;
    private final String temp;
    private final String pressure;
    private final String humidity;
    private double feelsLike;
    private String condition;
    private float lat;
    private float lon;


    public Weather(WeatherDto dto) {
        this.cityName = dto.getCityName();
        this.temp = String.format("%.0f°C", dto.getTemp());
        this.pressure = String.format("%.0f мм рт.ст.", dto.getPressure() * 3 / 4.0);
        this.humidity = dto.getHumidity() + "%";
        this.feelsLike = dto.getFeelsLike();
        this.condition = dto.getCondition();
        this.lat = dto.getLat();
        this.lon = dto.getLon();
    }

    public String getCityName() {
        return cityName;
    }

    public String getTemp() {
        return temp;
    }

    public String getPressure() {
        return pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
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

    @Override
    public String toString() {
        return "Температура: " + this.temp + "\n"
                + "ощущается как " + this.feelsLike + "\n"
                + "давление: " + this.pressure + "\n"
                + "влажность: " + this.humidity + "\n"
                + this.condition;
    }
}
