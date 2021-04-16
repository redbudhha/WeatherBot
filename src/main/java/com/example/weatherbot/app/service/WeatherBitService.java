package com.example.weatherbot.app.service;

import com.example.weatherbot.app.dto.weatherbitdto.WeatherBitInfo;
import com.example.weatherbot.app.dto.weatherbitdto.forecast.WeatherBitForecastDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class WeatherBitService {
    private final RestTemplate restTemplate;
    private final String apiTokenWeatherBit = "5bf862115d4d44f6a4112743d9dafa61";


    @Autowired
    public WeatherBitService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    /*
    current weather by city name from service "Weather bit"
     */
    public WeatherBitInfo getCurrentWeatherFromWBByCity(String cityName) {
        String url = "https://api.weatherbit.io/v2.0/current?city=" + cityName
                + "&country=RU&key=" + apiTokenWeatherBit;
        return restTemplate.getForObject(url, WeatherBitInfo.class);
    }


    /*
        current weather by location name from service "Weather bit"
         */
    public WeatherBitInfo getCurrentWeatherFromWBByLocation(Float lat, float lon) {
        String url = "https://api.weatherbit.io/v2.0/current?lat=" + lat + "&lon=" + lon
                + "&key=" + apiTokenWeatherBit;
        return restTemplate.getForObject(url, WeatherBitInfo.class);
    }


    /*
   forecast weather by city name from service "Weather bit"
    */
    public WeatherBitForecastDto getForecastWeatherFromWBByCity(String cityName) {
        String url = "https://api.weatherbit.io/v2.0/forecast/daily?city=" + cityName
                + "&key=" + apiTokenWeatherBit;
        return restTemplate.getForObject(url, WeatherBitForecastDto.class);
    }

    /*
    forecast weather by location from service "Weather Bit"
     */
    public WeatherBitForecastDto getForecastWeatherFromWBByLocation(Float lat, float lon) {
        String url = "https://api.weatherbit.io/v2.0/forecast/daily?&lat=" + lat + "&lon=" + lon
                + "&key=" + apiTokenWeatherBit;
        return restTemplate.getForObject(url, WeatherBitForecastDto.class);
    }

    public WeatherBitInfo searchForTimeStampWB(WeatherBitForecastDto dto) {
        Optional<WeatherBitInfo> forecast = dto.getMainInfo().stream()
                .filter(weather -> weather.getDateTime().toString().equals(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(15,0)).toString()))
                .findAny();
        return forecast.orElse(null);
    }
}