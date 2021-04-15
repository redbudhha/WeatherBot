package com.example.weatherbot.app.service;

import com.example.weatherbot.app.dto.ForecastDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherGroundService {
    private final RestTemplate restTemplate;
    private final String apiTokenWeatherAPI = "c34f9733545b4663ae9181840210604";


    @Autowired
    public WeatherGroundService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    /*
         forecast weather by city name from service "Weather Ground"
          */
    public ForecastDto getForecastWeatherFromWSByCity(String cityName, int days) {
        String url = "http://api.weatherstack.com/forecast ? access_key =" + apiTokenWeatherAPI
                + "& query =" + cityName + "& forecast_days =" + days;
        return restTemplate.getForObject(url, ForecastDto.class);
    }

    /*
    forecast weather by location from service "Weather Ground"
     */
    public ForecastDto getForecastWeatherFromWSByLocation(Float lan, float lon, int days) {
        String url = "http://api.weatherstack.com/forecast ? access_key =" + apiTokenWeatherAPI
                + "& query =" + lan + ", " + lon + "& forecast_days =" + days;
        return restTemplate.getForObject(url, ForecastDto.class);
    }
}
