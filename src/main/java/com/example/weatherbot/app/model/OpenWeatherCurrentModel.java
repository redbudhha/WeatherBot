package com.example.weatherbot.app.model;


import com.example.weatherbot.app.dto.openweatherdto.current.OpenWeatherCurrentDto;
import lombok.Data;

@Data
public final class OpenWeatherCurrentModel {
    private final String cityName;
    private final Double temp;
    private final Integer pressure;
    private final Integer humidity;
    private Double feelsLike;
    private String condition;
    private Float lat;
    private Float lon;
    private Double speed;
    private Integer deg;
    private Long dateTime;



    public OpenWeatherCurrentModel(OpenWeatherCurrentDto dto) {
        this.cityName = dto.getName();
        this.temp = dto.getMain().getTemp();
        this.pressure = dto.getMain().getPressure();
        this.humidity = dto.getMain().getHumidity();
        this.feelsLike = dto.getMain().getFeelsLike();
        this.lat = dto.getCoordinate().getLat();
        this.lon = dto.getCoordinate().getLon();
        this.speed = dto.getWind().getSpeed();
        this.deg = dto.getWind().getDeg();
        this.dateTime = dto.getDateTime();
    }

    @Override
    public String toString() {
        return "OpenWeatherCurrentModel{" +
                "cityName='" + cityName + '\'' +
                ", temp=" + temp +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", feelsLike=" + feelsLike +
                ", condition='" + condition + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", speed=" + speed +
                ", deg=" + deg +
                ", dateTime=" + dateTime +
                '}';
    }

    public String getCityName() {
        return cityName;
    }

    public Double getTemp() {
        return temp;
    }

    public Integer getPressure() {
        return pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public Double getFeelsLike() {
        return feelsLike;
    }

    public String getCondition() {
        return condition;
    }

    public Float getLat() {
        return lat;
    }

    public Float getLon() {
        return lon;
    }

    public Double getSpeed() {
        return speed;
    }

    public Integer getDeg() {
        return deg;
    }

    public Long getDateTime() {
        return dateTime;
    }
}
