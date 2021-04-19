package com.example.weatherbot.app.service;

import com.example.weatherbot.app.dto.openweatherdto.current.OpenWeatherCurrentDto;
import com.example.weatherbot.app.dto.openweatherdto.forecast.OpenWeatherForecastDto;
import com.example.weatherbot.app.model.weather_model.OpenWeatherModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class OpenWeatherService {
    private final String apiTokenOpenWeather = "36daa3f6889a39abb62113bafa51611b";
    private final String openWeatherCurrentURL = "http://api.openweathermap.org/data/2.5/weather?&units=metric&";
    private final String openWeatherForecastURL = "http://api.openweathermap.org/data/2.5/forecast?&units=metric&";
    private final String unitsAndCnt = "&units=metric&cnt=16";
    private final RestTemplate restTemplate;


    @Autowired
    public OpenWeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OpenWeatherModel getCurrentByCity(String cityName) {
        String url = openWeatherCurrentURL + "q=" + cityName + "&appid=" + apiTokenOpenWeather;
        System.out.println(cityName);
        return getCurrentOpenWeatherModel(url);
    }


    public OpenWeatherModel getCurrentWeatherFromOWByLocation(Float lat, Float lon) {
        String url = openWeatherCurrentURL + "lat=" + lat + "&lon=" + lon + "&appid=" + apiTokenOpenWeather;
        return getCurrentOpenWeatherModel(url);

    }


    public OpenWeatherModel getForecastWeatherFromOWByCity(String cityName) {
        String url = openWeatherForecastURL + "q=" + cityName + unitsAndCnt + "&appid=" + apiTokenOpenWeather;
        return getOpenWeatherModel(url);
    }


    public OpenWeatherModel getForecastWeatherFromOWByLocation(Float lat, Float lon) {
        String url = openWeatherForecastURL + "lat=" + lat + "&lon=" + lon + "&cnt=16" + "&appid=" + apiTokenOpenWeather;
        return getOpenWeatherModel(url);
    }

    private OpenWeatherModel getOpenWeatherModel(String url) {
        OpenWeatherForecastDto dto = restTemplate.getForObject(url, OpenWeatherForecastDto.class);
        if (Objects.nonNull(dto)) {
            return new OpenWeatherModel(dto.getCity().getName(),
                    dto.getHourlyArray().get(2).getMainMetrics().getTemp(),
                    dto.getHourlyArray().get(2).getMainMetrics().getPressure(),
                    dto.getHourlyArray().get(2).getMainMetrics().getHumidity(),
                    dto.getHourlyArray().get(2).getMainMetrics().getFeelsLike(),
                    dto.getHourlyArray().get(2).getWeather().get(0).getCondition(),
                    dto.getCity().getCoords().getLat(),
                    dto.getCity().getCoords().getLon(),
                    dto.getHourlyArray().get(2).getWind().getSpeed(),
                    dto.getHourlyArray().get(2).getWind().getDeg(),
                    dto.getHourlyArray().get(2).getDateTime());
        } else {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Wrong city name");
        }
    }

    private OpenWeatherModel getCurrentOpenWeatherModel(String url) {
        OpenWeatherCurrentDto dto = restTemplate.getForObject(url, OpenWeatherCurrentDto.class);
        if (Objects.nonNull(dto)) {
            return new OpenWeatherModel(dto.getName(),
                    dto.getMain().getTemp(), dto.getMain().getPressure(),
                    dto.getMain().getHumidity(), dto.getMain().getFeelsLike(),
                    dto.getWeather().get(0).getCondition(), dto.getCoordinate().getLat(),
                    dto.getCoordinate().getLon(), dto.getWind().getSpeed(),
                    dto.getWind().getDeg(), dto.getDateTime());
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Wrong city name");
        }
    }


}