package com.example.weatherbot.app.dto.openweatherdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Wind {
    @JsonProperty("speed")
    private Double speed;

    @JsonProperty("deg")
    private Integer deg;

    public Double getSpeed() {
        return speed;
    }

    public Integer getDeg() {
        return deg;
    }
}
