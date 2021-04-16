package com.example.weatherbot.app.service;

import com.example.weatherbot.app.dto.weatherapidto.current.WeatherAPICurrentDto;
import com.example.weatherbot.app.dto.weatherapidto.forecast.ForecastDay;
import com.example.weatherbot.app.dto.weatherapidto.forecast.WeatherAPIForecastDto;
import com.example.weatherbot.app.model.weather_model.WeatherApiModel;
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
        WeatherAPICurrentDto dto = restTemplate.getForObject(url, WeatherAPICurrentDto.class);
        if (Objects.nonNull(dto)) {
            return new WeatherApiModel(dto.getLocation().getCityName(),
                    dto.getInfo().getTemp(), dto.getPressure(),
                    dto.getHumidity(), dto.getFeelsLike(),
                    dto.getInfo().getCondition().getDescription(),
                    dto.getLocation().getLat(), dto.getLocation().getLon(),
                    dto.getWindSpeed(),
                    dto.getWindDeg(),
                    dto.getLocation().getLocalTime());
        } else {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"WeatherApiDto is null");
        }
    }

    /*
  current weather by location name from service "Weather API"
   */
    public WeatherApiModel getCurrentWeatherFromWAByLocation(Float lat, float lon) {
        String url = "https://api.weatherapi.com/v1/current.json?key="
                + apiTokenWeatherAPI + "&q=" + lat + "," + lon + "&aqi=no";
        WeatherAPICurrentDto dto = restTemplate.getForObject(url, WeatherAPICurrentDto.class);
        if (Objects.nonNull(dto)) {
            return new WeatherApiModel(dto.getLocation().getCityName(),
                    dto.getInfo().getTemp(), dto.getPressure(),
                    dto.getHumidity(), dto.getFeelsLike(),
                    dto.getInfo().getCondition().getDescription(),
                    dto.getLocation().getLat(), dto.getLocation().getLon(),
                    dto.getWindSpeed(),
                    dto.getWindDeg(),
                    dto.getLocation().getLocalTime());
        } else {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"WeatherApiDto is null");
        }
    }

    /*
  forecast weather by city name from service "Weather API"
  */
    public WeatherApiModel getForecastWeatherFromWAByCity(String cityName) {
        String url = "https://api.weatherapi.com/v1/forecast.json?key=" + apiTokenWeatherAPI
                + "&q=" + cityName + "&days=2&aqi=no&alerts=no";
        WeatherAPIForecastDto dto = restTemplate.getForObject(url, WeatherAPIForecastDto.class);
        if (Objects.nonNull(dto)) {
            ForecastDay forecastDay = searchForTimeStampWA(dto);
            return new WeatherApiModel(dto.getLocation().getCityName(),
                    forecastDay.getWeatherInfo().getAvgTemp(), forecastDay.getHourForecast().getPressure(),
                    forecastDay.getWeatherInfo().getAvgHumidity(),
                    forecastDay.getHourForecast().getFeelsLike(),
                    forecastDay.getWeatherInfo().getCondition().getDescription(),
                    dto.getLocation().getLat(),
                    dto.getLocation().getLon(), forecastDay.getWeatherInfo().getWindSpeed(),
                    forecastDay.getHourForecast().getWindDeg(),
                    dto.getLocation().getLocalTime()
            );
        } else {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"WeatherApiDto is null");
        }
    }

    /*
 forecast weather by location from service "Weather API"
  */
    public WeatherApiModel getForecastWeatherFromWAByLocation(Float lat, float lon) {
        String url = "https://api.weatherapi.com/v1/forecast.json?key=" + apiTokenWeatherAPI
                + "&q=" + lat + "," + lon + "&days=2&aqi=no&alerts=no";
        WeatherAPIForecastDto dto = restTemplate.getForObject(url, WeatherAPIForecastDto.class);
        if (Objects.nonNull(dto)) {
            ForecastDay forecastDay = searchForTimeStampWA(dto);
            return new WeatherApiModel(dto.getLocation().getCityName(),
                    forecastDay.getWeatherInfo().getAvgTemp(), forecastDay.getHourForecast().getPressure(),
                    forecastDay.getWeatherInfo().getAvgHumidity(),
                    forecastDay.getHourForecast().getFeelsLike(),
                    forecastDay.getWeatherInfo().getCondition().getDescription(),
                    dto.getLocation().getLat(),
                    dto.getLocation().getLon(), forecastDay.getWeatherInfo().getWindSpeed(),
                    forecastDay.getHourForecast().getWindDeg(),
                    dto.getLocation().getLocalTime());
        } else {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"WeatherApiDto is null");
        }

    }
    public ForecastDay searchForTimeStampWA(WeatherAPIForecastDto dto) {
        Optional<ForecastDay> forecast = dto.getForecasts().stream()
                .filter(weather -> weather.getDateTime().toString().equals(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(15,0)).toString()))
                .findAny();
        return forecast.orElse(null);
    }
}
