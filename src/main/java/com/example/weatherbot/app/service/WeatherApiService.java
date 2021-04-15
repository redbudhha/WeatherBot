package com.example.weatherbot.app.service;

import com.example.weatherbot.app.dto.openweatherdto.forecast.OpenWeatherForecastDto;
import com.example.weatherbot.app.dto.openweatherdto.forecast.OpenWeatherThreeHourForecast;
import com.example.weatherbot.app.dto.weatherapidto.current.WeatherAPICurrentDto;
import com.example.weatherbot.app.dto.weatherapidto.forecast.ForecastDay;
import com.example.weatherbot.app.dto.weatherapidto.forecast.WeatherAPIForecastDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class WeatherApiService {
    private final String apiTokenWeatherAPI = "c34f9733545b4663ae9181840210604";
    private final RestTemplate restTemplate;

    @Autowired
    public WeatherApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /*
    current weather by city name from service "Weather API"
     */
    public WeatherAPICurrentDto getCurrentWeatherFromWAByCity(String cityName) {
        String url = "https://api.weatherapi.com/v1/current.json?key="
                + apiTokenWeatherAPI + "&q=" + cityName + "&aqi=no";
        return restTemplate.getForObject(url, WeatherAPICurrentDto.class);
    }

    /*
  current weather by location name from service "Weather API"
   */
    public WeatherAPICurrentDto getCurrentWeatherFromWAByLocation(Float lat, float lon) {
        String url = "https://api.weatherapi.com/v1/current.json?key="
                + apiTokenWeatherAPI + "&q=" + lat + "," + lon + "&aqi=no";
        return restTemplate.getForObject(url, WeatherAPICurrentDto.class);
    }

    /*
  forecast weather by city name from service "Weather API"
  */
    public WeatherAPIForecastDto getForecastWeatherFromWAByLocation(String cityName) {
        String url = "https://api.weatherapi.com/v1/forecast.json?key=" + apiTokenWeatherAPI
                + "&q=" + cityName + "&days=2&aqi=no&alerts=no";
        return restTemplate.getForObject(url, WeatherAPIForecastDto.class);
    }

    /*
 forecast weather by location from service "Weather API"
  */
    public WeatherAPIForecastDto getForecastWeatherFromWAByLocation(Float lat, float lon, int days) {
        String url = "https://api.weatherapi.com/v1/forecast.json?key=" + apiTokenWeatherAPI
                + "&q=" + lat + "," + lon + "&days=2&aqi=no&alerts=no";
        return restTemplate.getForObject(url, WeatherAPIForecastDto.class);

    }
    public ForecastDay searchForTimeStampWA(WeatherAPIForecastDto dto) {
        Optional<ForecastDay> forecast = dto.getForecasts().stream()
                .filter(weather -> weather.getDateTime().toString().equals(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.NOON).toString()))
                .findAny();
        return forecast.orElse(null);
    }
    //возможно, в plusDays нужно передавать 2, т.к. под 1 может храниться прогноз на сегодня
}
