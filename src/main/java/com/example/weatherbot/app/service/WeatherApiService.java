package com.example.weatherbot.app.service;

import com.example.weatherbot.app.dto.weatherapidto.current.WeatherAPICurrentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
  current weather by city name from service "Weather API"
   */
    public WeatherAPICurrentDto getCurrentWeatherFromWAByLocation(Float lat, float lon) {
        String url = "https://api.weatherapi.com/v1/current.json?key="
                + apiTokenWeatherAPI + "&q=" + lat + "," + lon + "&aqi=no";
        return restTemplate.getForObject(url, WeatherAPICurrentDto.class);
    }

}
