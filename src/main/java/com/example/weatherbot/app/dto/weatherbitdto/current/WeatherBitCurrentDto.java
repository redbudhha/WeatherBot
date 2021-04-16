package com.example.weatherbot.app.dto.weatherbitdto.current;


import com.example.weatherbot.app.dto.weatherbitdto.WeatherBitInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WeatherBitCurrentDto {
    @JsonProperty("data")
    private List<WeatherBitInfo> mainInfo;




}
