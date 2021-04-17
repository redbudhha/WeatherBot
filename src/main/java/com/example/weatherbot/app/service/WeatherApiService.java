package com.example.weatherbot.app.service;

import com.example.weatherbot.app.dto.weatherapidto.current.WeatherAPICurrentDto;
import com.example.weatherbot.app.dto.weatherapidto.forecast.WeatherAPIForecastDto;
import com.example.weatherbot.app.model.weather_model.WeatherApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

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
    public WeatherApiModel getCurrentWeatherFromWAByCity(String cityName) {
        String url = "https://api.weatherapi.com/v1/current.json?key="
                + apiTokenWeatherAPI + "&q=" + cityName + "&aqi=no";
        return getCurrentWeatherApiModel(url);
    }
    /*
  current weather by location name from service "Weather API"
   */
    public WeatherApiModel getCurrentWeatherFromWAByLocation(Float lat, float lon) {
        String url = "https://api.weatherapi.com/v1/current.json?key="
                + apiTokenWeatherAPI + "&q=" + lat + "," + lon + "&aqi=no";
        return getCurrentWeatherApiModel(url);
    }


    /*
  forecast weather by city name from service "Weather API"
  */
    public WeatherApiModel getForecastWeatherFromWAByCity(String cityName) {
        String url = "https://api.weatherapi.com/v1/forecast.json?key=" + apiTokenWeatherAPI
                + "&q=" + cityName + "&days=2&aqi=no&alerts=no";
        return getWeatherApiModel(url);
    }

    /*
 forecast weather by location from service "Weather API"
  */
    public WeatherApiModel getForecastWeatherFromWAByLocation(Float lat, float lon) {
        String url = "https://api.weatherapi.com/v1/forecast.json?key=" + apiTokenWeatherAPI
                + "&q=" + lat + "," + lon + "&days=2&aqi=no&alerts=no";
        return getWeatherApiModel(url);
    }

    private WeatherApiModel getWeatherApiModel(String url) {
        WeatherAPIForecastDto dto = restTemplate.getForObject(url, WeatherAPIForecastDto.class);
        if (Objects.nonNull(dto)) {
            return new WeatherApiModel(dto.getLocation().getCityName(),
                    dto.getForecast().getForecasts().get(0).getWeatherInfo().getAvgTemp(),
                    dto.getForecast().getForecasts().get(0).getHourForecast().get(0).getPressure(),
                    dto.getForecast().getForecasts().get(0).getWeatherInfo().getAvgHumidity(),
                    dto.getForecast().getForecasts().get(0).getHourForecast().get(0).getFeelsLike(),
                    dto.getForecast().getForecasts().get(0).getWeatherInfo().getCondition().getDescription(),
                    dto.getLocation().getLat(),
                    dto.getLocation().getLon(),
                    dto.getForecast().getForecasts().get(0).getWeatherInfo().getWindSpeed(),
                    dto.getForecast().getForecasts().get(0).getHourForecast().get(0).getWindDeg(),
                    dto.getLocation().getLocalTime());
        } else {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Wrong city name");
        }
    }
    private WeatherApiModel getCurrentWeatherApiModel(String url) {
        WeatherAPICurrentDto dto = restTemplate.getForObject(url, WeatherAPICurrentDto.class);
        if (Objects.nonNull(dto)) {
            return new WeatherApiModel(dto.getLocation().getCityName(),
                    dto.getInfo().getTemp(),
                    dto.getInfo().getPressure(),
                    dto.getInfo().getHumidity(), dto.getInfo().getFeelsLike(),
                    dto.getInfo().getCondition().getDescription(),
                    dto.getLocation().getLat(), dto.getLocation().getLon(),
                    dto.getInfo().getWindSpeed(),
                    dto.getInfo().getWindDeg(),
                    dto.getLocation().getLocalTime());
        } else {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Wrong city name");
        }
    }


}
