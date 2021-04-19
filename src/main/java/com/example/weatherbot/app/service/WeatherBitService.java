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
    private final String weatherBitCurrentURL = "https://api.weatherbit.io/v2.0/current?";
    private final String weatherBitForecastURL = "https://api.weatherbit.io/v2.0/forecast/daily?";


    @Autowired
    public WeatherBitService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherBitModel getCurrentWeatherFromWBByCity(String cityName) {
        String url = weatherBitCurrentURL + "city=" + cityName + "&key=" + apiTokenWeatherBit;
        return getCurrentWeatherBitModel(url);
    }


    public WeatherBitModel getCurrentWeatherFromWBByLocation(Float lat, float lon) {
        String url = weatherBitCurrentURL + "lat=" + lat + "&lon=" + lon + "&key=" + apiTokenWeatherBit;
        return getCurrentWeatherBitModel(url);
    }


    public WeatherBitModel getForecastWeatherFromWBByLocation(Float lat, float lon) {
        String url = weatherBitForecastURL + "&lat=" + lat + "&lon=" + lon + "&key=" + apiTokenWeatherBit;
        return getWeatherBitModel(url);
    }


    public WeatherBitModel getForecastWeatherFromWBByCity(String cityName) {
        String url = weatherBitForecastURL + "city=" + cityName + "&key=" + apiTokenWeatherBit;
        return getWeatherBitModel(url);
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


    private WeatherBitModel getWeatherBitModel(String url) {
        WeatherBitForecastDto dto = restTemplate.getForObject(url, WeatherBitForecastDto.class);
        if (Objects.nonNull(dto)) {
            return new WeatherBitModel(dto.getCityName(),
                    dto.getMainInfoForecast().get(2).getTemp(),
                    dto.getMainInfoForecast().get(2).getPressure(),
                    dto.getMainInfoForecast().get(2).getHumidity(),
                    dto.getMainInfoForecast().get(2).getFeelsLike(),
                    dto.getMainInfoForecast().get(2).getDesc().getDescription(),
                    dto.getLat(),
                    dto.getLon(),
                    dto.getMainInfoForecast().get(2).getWindSpeed(),
                    dto.getMainInfoForecast().get(2).getWindDeg(),
                    dto.getMainInfoForecast().get(2).getDateTime());
        } else {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Wrong city name");
        }
    }
}