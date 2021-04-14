package com.example.weatherbot.app.model;

import com.example.weatherbot.app.dto.openweatherdto.forecast.OpenWeatherForecastDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class OpenWeatherForecastModel {

    private final String cityName;
    private final int threeHoursCycles;
    private Float lat;
    private Float lon;

//    private final List<ThreeHourForecast> forecasts = new ArrayList<>();

    public OpenWeatherForecastModel(OpenWeatherForecastDto dto) {
        this.cityName = dto.getCity().getName();
        this.threeHoursCycles = dto.getCnt();

        this.lat = dto.getCity().getCoords().getLat();
        this.lon = dto.getCity().getCoords().getLon();
//
//        for (int i = 0; i < threeHoursCycles; i++) {
//            forecasts.add(new ThreeHourForecast(dto.getHourlyArray(),
////                    dto.getDayTemp().get(i)),
////                    dto.getNightTemp().get(i)),
//                    LocalDate.now().plusDays(i);
//        }
//    }

//    public static class ThreeHourForecast {
//        private final Double temp;
//        private final String nightTemp;
//        private final LocalDate localDate;
//
//        public ThreeHourForecast(WeatherCondition condition, Double temp,
//                           String nightTemp, LocalDate localDate) {
////            this.condition = condition;
//            this.temp = temp;
//            this.nightTemp = nightTemp;
//            this.localDate = localDate;
//        }
//    }
    }
}
