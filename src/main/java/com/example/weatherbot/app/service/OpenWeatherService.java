package com.example.weatherbot.app.service;

import com.example.weatherbot.app.dto.openweatherdto.current.OpenWeatherCurrentDto;
import com.example.weatherbot.app.dto.openweatherdto.forecast.OpenWeatherForecastDto;
import com.example.weatherbot.app.dto.openweatherdto.forecast.OpenWeatherThreeHourForecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class OpenWeatherService {
    private final RestTemplate restTemplate;
    private final String apiTokenOpenWeather = "36daa3f6889a39abb62113bafa51611b";
    //это мой токен
    private final String token = "267f70c609cff8699fdc74f50434b9c4";


    @Autowired
    public OpenWeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OpenWeatherCurrentDto getCurrentByCity(String cityName) {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "+&appid=" + token;
        return restTemplate.getForObject(url, OpenWeatherCurrentDto.class);

    }
    /*
    current weather by location name from service "Open Weather"
     */
    public OpenWeatherCurrentDto getCurrentWeatherFromOWByLocation(Double lat, Double lon) {
        String url = "http://api.openweathermap.org/data/2.5/weather?&units=metric&lat=" + lat + "&lon=" + lon
                + "&appid=" + apiTokenOpenWeather;
        return restTemplate.getForObject(url, OpenWeatherCurrentDto.class);
    }

    /*
    forecast weather for the next day from by city name service "Open Weather"
     */
    public OpenWeatherForecastDto getForecastWeatherFromOWByCity(String cityName) {
        String url = "http://api.openweathermap.org/data/2.5/forecast?q=" + cityName + "&units=metric&cnt=16"
                 + "&appid=" + apiTokenOpenWeather;
        return restTemplate.getForObject(url, OpenWeatherForecastDto.class);

    }

    /*
    forecast weather for the next day from by location service "Open Weather"
     */
    public OpenWeatherForecastDto getForecastWeatherFromOWByLocation(Float lat, Float lon) {
        String url = "http://api.openweathermap.org/data/2.5/forecast?&units=metric&lat=" + lat + "&lon=" + "&cnt=16"
                + "&appid=" + apiTokenOpenWeather;
        return restTemplate.getForObject(url, OpenWeatherForecastDto.class);

    }
    public OpenWeatherThreeHourForecast searchForTimeStamp(OpenWeatherForecastDto dto) {
        Optional<OpenWeatherThreeHourForecast> forecast = dto.getHourlyArray().stream()
                .filter(weather -> weather.getDateTime().toString().equals(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(15,0)).toString()))
                .findAny();
        return forecast.orElse(null);
    }

}
