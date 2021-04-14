package com.example.weatherbot.app.dto.openweatherdto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class WeatherMetrics {
    @JsonProperty("temp")
    private Double temp;

    @JsonProperty("feels_like")
    private Double feelsLike;

    @JsonProperty("temp_min")
    private Double tempMin;

    @JsonProperty("temp_max")
    private Double tempMax;

    @JsonProperty("pressure")
    private Integer pressure;

    @JsonProperty("sea_level")
    private Integer seaLevel;

    @JsonProperty("grnd_level")
    private Integer groundLevel;

    @JsonProperty("humidity")
    private Integer humidity;

    @JsonProperty("temp_kf")
    private Double tempKf;

    public Double getTemp() {
        return temp;
    }

    public Double getFeelsLike() {
        return feelsLike;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public Integer getPressure() {
        return pressure;
    }

    public Integer getSeaLevel() {
        return seaLevel;
    }

    public Integer getGroundLevel() {
        return groundLevel;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public Double getTempKf() {
        return tempKf;
    }
}
