package com.example.weatherbot.app.model;

import com.example.weatherbot.app.dto.ForecastDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Forecast {
    private final List<String> condition;
    private final String cityName;
    private final int days;
    private final List<DayForecast> forecasts = new ArrayList<>();

    public Forecast(ForecastDto dto) {
        this.condition = dto.getCondition();
        this.cityName = dto.getCityName();
        this.days = dto.getDays();

        for (int i = 0; i < days; i++) {
            forecasts.add(new DayForecast(dto.getCondition().get(i),
                    String.format("%.0f°C", dto.getDayTemp().get(i)),
                    String.format("%.0f°C", dto.getNightTemp().get(i)),
                    LocalDate.now().plusDays(i)
            ));
        }
    }

    public String getCityName() {
        return cityName;
    }

    public int getDays() {
        return days;
    }

    public List<DayForecast> getForecasts() {
        return forecasts;
    }

    public static class DayForecast {
        private final String condition;
        private final String dayTemp;
        private final String nightTemp;
        private final LocalDate localDate;

        public DayForecast(String condition, String dayTemp,
                           String nightTemp, LocalDate localDate) {
            this.condition = condition;
            this.dayTemp = dayTemp;
            this.nightTemp = nightTemp;
            this.localDate = localDate;
        }

        @Override
        public String toString() {
            return "condition = " + this.condition + '\'' +
                    ", day temp = " + this.dayTemp + '\'' +
                    ", night temp = " + this.nightTemp + '\'' +
                    ", local date = " + this.localDate;
        }
    }

    @Override
    public String toString() {
        return "condition = " + this.condition +
                ", City name = " + this.cityName + '\'' +
                ", days = " + this.days +
                ", forecasts = " + this.forecasts;
    }
}
