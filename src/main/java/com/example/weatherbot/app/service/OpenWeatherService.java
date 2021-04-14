package com.example.weatherbot.app.service;

import com.example.weatherbot.app.dto.openweatherdto.current.OpenWeatherCurrentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class OpenWeatherService {
    private final RestTemplate restTemplate;
    private final String apiTokenOpenWeather = "36daa3f6889a39abb62113bafa51611b";


    @Autowired
    public OpenWeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OpenWeatherCurrentDto getCurrentByCity(String cityName) {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "+&appid=" + apiTokenOpenWeather;
        return restTemplate.getForObject(url, OpenWeatherCurrentDto.class);
    }
}
