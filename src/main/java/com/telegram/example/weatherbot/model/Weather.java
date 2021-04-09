package com.telegram.example.weatherbot.model;

import com.telegram.example.weatherbot.dto.WeatherDto;
import lombok.Getter;

@Getter
public final class Weather {
    private final String cityName;
    private final String temp;
    private final String pressure;
    private final String humidity;


    public Weather(WeatherDto dto) {
        this.cityName = dto.getCityName();
        this.temp = String.format("%.0f°C", dto.getTemp());
        this.pressure = String.format("%.0f мм рт.ст.", dto.getPressure() * 3 / 4.0);
        this.humidity = dto.getHumidity() + "%";
    }

    @Override
    public String toString() {
        return  "cityName = " + this.cityName + '\'' +
                ", temp = " + this.temp + '\'' +
                ", pressure = " + this.pressure + '\'' +
                ", humidity = " + this.humidity + '\'' +
                '}';
    }
}
