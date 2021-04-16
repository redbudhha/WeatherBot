package com.example.weatherbot.app.service;

import com.example.weatherbot.app.dto.openweatherdto.current.OpenWeatherCurrentDto;
import com.example.weatherbot.app.dto.openweatherdto.forecast.OpenWeatherForecastDto;
import com.example.weatherbot.app.dto.openweatherdto.forecast.OpenWeatherThreeHourForecast;
import com.example.weatherbot.app.model.weather_model.OpenWeatherModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class OpenWeatherService {
    private final RestTemplate restTemplate;
    private final String apiTokenOpenWeather = "36daa3f6889a39abb62113bafa51611b";


    @Autowired
    public OpenWeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OpenWeatherModel getCurrentByCity(String cityName) {
        String url = "http://api.openweathermap.org/data/2.5/weather?&units=metric&q=" + cityName + "+&appid=" + apiTokenOpenWeather;
        OpenWeatherCurrentDto dto = restTemplate.getForObject(url, OpenWeatherCurrentDto.class);
        if (Objects.nonNull(dto)) {
            return new OpenWeatherModel(dto.getName(),
                    dto.getMain().getTemp(), dto.getMain().getPressure(),
                    dto.getMain().getHumidity(), dto.getMain().getFeelsLike(),
                    dto.getWeather().get(0).getCondition(), dto.getCoordinate().getLat(),
                    dto.getCoordinate().getLon(), dto.getWind().getSpeed(),
                    dto.getWind().getDeg(), dto.getDateTime());
        } else throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "OpenWeatherDto is null");
    }

    /*
    current weather by location name from service "Open Weather"
     */
    public OpenWeatherModel getCurrentWeatherFromOWByLocation(Float lat, Float lon) {
        String url = "http://api.openweathermap.org/data/2.5/weather?&units=metric&lat=" + lat + "&lon=" + lon
                + "&appid=" + apiTokenOpenWeather;
        OpenWeatherCurrentDto dto = restTemplate.getForObject(url, OpenWeatherCurrentDto.class);
        if (Objects.nonNull(dto)) {
            return new OpenWeatherModel(dto.getName(),
                    dto.getMain().getTemp(), dto.getMain().getPressure(),
                    dto.getMain().getHumidity(), dto.getMain().getFeelsLike(),
                    dto.getWeather().get(0).getCondition(), dto.getCoordinate().getLat(),
                    dto.getCoordinate().getLon(), dto.getWind().getSpeed(),
                    dto.getWind().getDeg(), dto.getDateTime());
        } else throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "OpenWeatherDto is null");
    }

    /*
    forecast weather for the next day from by city name service "Open Weather"
     */
    public OpenWeatherModel getForecastWeatherFromOWByCity(String cityName) {
        String url = "http://api.openweathermap.org/data/2.5/forecast?&units=metric&q=" + cityName + "&units=metric&cnt=16"
                + "&appid=" + apiTokenOpenWeather;
        OpenWeatherForecastDto dto = restTemplate.getForObject(url, OpenWeatherForecastDto.class);
        if (Objects.nonNull(dto)) {
            return new OpenWeatherModel(dto.getCity().getName(),
                    dto.getHourlyArray().get(0).getMainMetrics().getTemp(),
                    dto.getHourlyArray().get(0).getMainMetrics().getPressure(),
                    dto.getHourlyArray().get(0).getMainMetrics().getHumidity(),
                    dto.getHourlyArray().get(0).getMainMetrics().getFeelsLike(),
                    dto.getHourlyArray().get(0).getWeather().get(0).getCondition(),
                    dto.getCity().getCoords().getLat(),
                    dto.getCity().getCoords().getLon(),
                    dto.getHourlyArray().get(0).getWind().getSpeed(),
                    dto.getHourlyArray().get(0).getWind().getDeg(),
                    dto.getHourlyArray().get(0).getDateTime());
        } else throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "OpenWeatherDto is null");
    }

    /*
    forecast weather for the next day from by location service "Open Weather"
     */
    public OpenWeatherModel getForecastWeatherFromOWByLocation(Float lat, Float lon) {
        String url = "http://api.openweathermap.org/data/2.5/forecast?&units=metric&lat=" + lat + "&lon=" + lon + "&cnt=16"
                + "&appid=" + apiTokenOpenWeather;
        OpenWeatherForecastDto dto = restTemplate.getForObject(url, OpenWeatherForecastDto.class);
        if (Objects.nonNull(dto)) {
            return new OpenWeatherModel(dto.getCity().getName(),
                    dto.getHourlyArray().get(0).getMainMetrics().getTemp(),
                    dto.getHourlyArray().get(0).getMainMetrics().getPressure(),
                    dto.getHourlyArray().get(0).getMainMetrics().getHumidity(),
                    dto.getHourlyArray().get(0).getMainMetrics().getFeelsLike(),
                    dto.getHourlyArray().get(0).getWeather().get(0).getCondition(),
                    dto.getCity().getCoords().getLat(),
                    dto.getCity().getCoords().getLon(),
                    dto.getHourlyArray().get(0).getWind().getSpeed(),
                    dto.getHourlyArray().get(0).getWind().getDeg(),
                    dto.getHourlyArray().get(0).getDateTime());
        } else throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "OpenWeatherDto is null");
    }

    public OpenWeatherThreeHourForecast searchForTimeStamp(OpenWeatherForecastDto dto) {
        Optional<OpenWeatherThreeHourForecast> forecast = dto.getHourlyArray().stream()
                .filter(weather -> weather.getDateTime().toString().equals(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(15, 0)).toString()))
                .findAny();
        return forecast.orElse(null);
    }
}