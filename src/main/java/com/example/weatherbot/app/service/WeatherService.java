package com.example.weatherbot.app.service;

import com.example.weatherbot.app.dto.ForecastDto;
import com.example.weatherbot.app.dto.WeatherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;


@PropertySource("classpath:application.properties")
@Configuration
public class WeatherService {
    private final RestTemplate restTemplate;
    private final String apiTokenOpenWeather = "36daa3f6889a39abb62113bafa51611b";
    private final String apiTokenYandexWeather = "fffff866-6141-46fb-aa27-9cc943366a62";
    private final String apiTokenWeatherStack = "549c72be601e1286a925e1fdce16bf7b";


    @Autowired
    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /*
    current weather by city name from service "Open Weather"
     */
    public WeatherDto getCurrentWeatherFromOWByCity(String cityName) {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "+&appid=" + apiTokenOpenWeather;
        return restTemplate.getForObject(url, WeatherDto.class);
    }

    /*
    current weather by location name from service "Open Weather"
     */
    public WeatherDto getCurrentWeatherFromOWByLocation(Float lat, Float lon) {
        String url = "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon
                + "&appid=" + apiTokenOpenWeather;
        return restTemplate.getForObject(url, WeatherDto.class);
    }

    /*
    forecast weather for the next day from by city name service "Open Weather"
     */
    public ForecastDto getForecastWeatherFromOWByCity(String cityName, int days) {
        String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q=" + cityName + "&cnt="
                + days + "&appid=" + apiTokenOpenWeather;
        return restTemplate.getForObject(url, ForecastDto.class);

    }

    /*
    forecast weather for the next day from by location service "Open Weather"
     */
    public ForecastDto getForecastWeatherFromOWByLocation(Float lat, Float lon, int days) {
        String url = "http://api.openweathermap.org/data/2.5/forecast/?lat=" + lat + "&lon=" + lon
                + days + "&cnt=8&units=metric&appid=" + apiTokenOpenWeather;
        return restTemplate.getForObject(url, ForecastDto.class);

    }

    /*
  current weather by location from service "Яндекс погода"
   */
    public WeatherDto getCurrentWeatherFromYNByLocation(Float lat, Float lon) {
        String url = "https://api.weather.yandex.ru/v2/fact?lat=" + lat + "&lon=" + lon + "&extra=true " +
                "X-Yandex-API-Key:" + apiTokenYandexWeather;
        return restTemplate.getForObject(url, WeatherDto.class);
    }

    /*
  forecast weather by location from service "Яндекс погода"
  */
    public ForecastDto getForecastWeatherFromYNByLocation(Float lat, Float lon, int days) {
        String url = "https://api.weather.yandex.ru/v2/forecast?lat=" + lat + "&lon=" + lon + "&extra=true " +
                days + "X-Yandex-API-Key:" + apiTokenYandexWeather;
        return restTemplate.getForObject(url, ForecastDto.class);
    }

    /*
      current weather by city name from service "Weather Stack"
       */
    public WeatherDto getCurrentWeatherFromWSByCity(String cityName) {
        String url = "http://api.weatherstack.com/current ? access_key =" + apiTokenWeatherStack + "& query =" + cityName;
        return restTemplate.getForObject(url, WeatherDto.class);
    }

    /*
  current weather by city name from service "Weather Stack"
   */
    public WeatherDto getCurrentWeatherFromWSByLocation(Float lan, float lon) {
        String url = "http://api.weatherstack.com/current ? access_key =" + apiTokenWeatherStack
                + "& query =" + lan + ", " + lon;
        return restTemplate.getForObject(url, WeatherDto.class);
    }


    /*
      forecast weather by city name from service "Weather Stack"
       */
    public ForecastDto getForecastWeatherFromWSByCity(String cityName, int days) {
        String url = "http://api.weatherstack.com/forecast ? access_key =" + apiTokenWeatherStack
                + "& query =" + cityName + "& forecast_days =" + days;
        return restTemplate.getForObject(url, ForecastDto.class);
    }

    /*
    forecast weather by location from service "Weather Stack"
     */
    public ForecastDto getForecastWeatherFromWSByLocation(Float lan, float lon, int days) {
        String url = "http://api.weatherstack.com/forecast ? access_key =" + apiTokenWeatherStack
                + "& query =" + lan + ", " + lon + "& forecast_days =" + days;
        return restTemplate.getForObject(url, ForecastDto.class);
    }

}


