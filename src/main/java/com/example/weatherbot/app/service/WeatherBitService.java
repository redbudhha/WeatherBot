package com.example.weatherbot.app.service;

import com.example.weatherbot.app.dto.weatherbitdto.current.WeatherBitCurrentDto;
import com.example.weatherbot.app.dto.weatherbitdto.forecast.WeatherBitForecastDto;
import com.example.weatherbot.app.model.weather_model.WeatherBitModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

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
    public WeatherBitModel getCurrentWeatherFromWBByCity(String cityName) {
        String url = "https://api.weatherbit.io/v2.0/current?city=" + cityName
                + "&key=" + apiTokenWeatherBit;
        return getCurrentWeatherBitModel(url);
    }
    /*
        current weather by location name from service "Weather bit"
         */
    public WeatherBitModel getCurrentWeatherFromWBByLocation(Float lat, float lon) {
        String url = "https://api.weatherbit.io/v2.0/current?lat=" + lat + "&lon=" + lon
                + "&key=" + apiTokenWeatherBit;
        return getCurrentWeatherBitModel(url);
    }

    private WeatherBitModel getCurrentWeatherBitModel(String url) {
        WeatherBitCurrentDto dto = restTemplate.getForObject(url, WeatherBitCurrentDto.class);
        if (Objects.nonNull(dto)) {
            return new WeatherBitModel(dto.getMainInfo().get(0).getCityName(),
                    dto.getMainInfo().get(0).getTemp(),
                    dto.getMainInfo().get(0).getPressure(),
                    dto.getMainInfo().get(0).getHumidity(),
                    dto.getMainInfo().get(0).getFeelsLike(),
                    dto.getMainInfo().get(0).getDesc().getDescription(),
                    dto.getMainInfo().get(0).getLat(),
                    dto.getMainInfo().get(0).getLon(),
                    dto.getMainInfo().get(0).getWindSpeed(),
                    dto.getMainInfo().get(0).getWindDeg(),
                    dto.getMainInfo().get(0).getDateTime());
        } else {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Wrong city name");
        }
    }
    /*
   forecast weather by city name from service "Weather bit"
    */
    public WeatherBitModel getForecastWeatherFromWBByCity(String cityName) {
        String url = "https://api.weatherbit.io/v2.0/forecast/daily?city=" + cityName
                + "&key=" + apiTokenWeatherBit;
        return getWeatherBitModel(url);
    }

    private WeatherBitModel getWeatherBitModel(String url) {
        WeatherBitForecastDto dto = restTemplate.getForObject(url, WeatherBitForecastDto.class);
        if (Objects.nonNull(dto)) {
            return new WeatherBitModel(dto.getCityName(),
                    dto.getMainInfoForecast().get(0).getTemp(),
                    dto.getMainInfoForecast().get(0).getPressure(),
                    dto.getMainInfoForecast().get(0).getHumidity(),
                    dto.getMainInfoForecast().get(0).getFeelsLike(),
                    dto.getMainInfoForecast().get(0).getDesc().getDescription(),
                    dto.getLat(),
                    dto.getLon(),
                    dto.getMainInfoForecast().get(0).getWindSpeed(),
                    dto.getMainInfoForecast().get(0).getWindDeg(),
                    dto.getMainInfoForecast().get(0).getDateTime());
        } else {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Wrong city name");
        }
    }

    /*
    forecast weather by location from service "Weather Bit"
     */
    public WeatherBitModel getForecastWeatherFromWBByLocation(Float lat, float lon) {
        String url = "https://api.weatherbit.io/v2.0/forecast/daily?&lat=" + lat + "&lon=" + lon
                + "&key=" + apiTokenWeatherBit;
        return getWeatherBitModel(url);
    }

}