package com.telegram.example.weatherbot.model;

import com.telegram.example.weatherbot.dto.ForecastDto;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
public class Forecast {
    private final String cityName;
    private final int days;
    private final List<DayForecast> forecasts = new ArrayList<>();

    public Forecast(ForecastDto dto) {

        this.cityName = dto.getCityName();
        this.days = dto.getDays();

        for (int i = 0; i < days; i++) {
            forecasts.add(new DayForecast(
                    String.format("%.0f°C", dto.getDayTemp().get(i)),
                    String.format("%.0f°C", dto.getNightTemp().get(i)),
                    LocalDate.now().plusDays(i)
            ));
        }
    }

    public static class DayForecast {
        private final String dayTemp;
        private final String nightTemp;
        private final LocalDate localDate;

        public DayForecast(String dayTemp,
                           String nightTemp, LocalDate localDate) {
            this.dayTemp = dayTemp;
            this.nightTemp = nightTemp;
            this.localDate = localDate;
        }

        @Override
        public String toString() {
            return  "dayTemp = " + this.dayTemp + '\'' +
                    ", nightTemp = " + this.nightTemp + '\'' +
                    ", localDate = " + this.localDate +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "cityName = " + this.cityName + '\'' +
                ", days = " + this.days +
                ", forecasts = " + this.forecasts +
                '}';
    }
}
