package com.example.weatherbot.app.dto.openweatherdto.forecast;

import com.example.weatherbot.app.dto.openweatherdto.CityCoord;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class City {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("coord")
    private CityCoord coords;

    @JsonProperty("country")
    private String country;

    @JsonProperty("population")
    private Integer population;

    @JsonProperty("timezone")
    private Integer zone;

    @JsonProperty("sunrise")
    private Integer sunrise;

    @JsonProperty("sunset")
    private Integer sunset;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CityCoord getCoords() {
        return coords;
    }

    public String getCountry() {
        return country;
    }

    public Integer getPopulation() {
        return population;
    }

    public Integer getZone() {
        return zone;
    }

    public Integer getSunrise() {
        return sunrise;
    }

    public Integer getSunset() {
        return sunset;
    }
}
