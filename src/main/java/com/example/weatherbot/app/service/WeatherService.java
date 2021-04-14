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
    private final String apiTokenWeatherAPI = "c34f9733545b4663ae9181840210604";

    private String token = "267f70c609cff8699fdc74f50434b9c4";

    @Autowired
    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /*
    current weather by city name from service "Open Weather"
     */
    public OpenWeatherCurrentDto getCurrentWeatherFromOWByCity(String cityName) {
        String url = "http://api.openweathermap.org/data/2.5/weather?&units=metric&q=" + cityName + "&appid=" + apiTokenOpenWeather;
        return restTemplate.getForObject(url, OpenWeatherCurrentDto.class);
    }

    /*
    current weather by location name from service "Open Weather"
     */
    public OpenWeatherCurrentDto getCurrentWeatherFromOWByLocation(Float lat, Float lon) {
        String url = "http://api.openweathermap.org/data/2.5/weather?&units=metric&lat=" + lat + "&lon=" + lon
                + "&appid=" + apiTokenOpenWeather;
        return restTemplate.getForObject(url, OpenWeatherCurrentDto.class);
    }

    /*
    forecast weather for the next day from by city name service "Open Weather"
     */
    public ForecastDto getForecastWeatherFromOWByCity(String cityName, int threeHoursCycles) {
        String url = "http://api.openweathermap.org/data/2.5/forecast?q=" + cityName + "&units=metric&cnt="
                + threeHoursCycles + "&appid=" + apiTokenOpenWeather;
        return restTemplate.getForObject(url, ForecastDto.class);

    }

    /*
    forecast weather for the next day from by location service "Open Weather"
     */
    public OpenWeatherForecastDto getForecastWeatherFromOWByLocation(Float lat, Float lon, int threeHoursCycles) {
        String url = "http://api.openweathermap.org/data/2.5/forecast?&units=metric&lat=" + lat + "&lon=" + "&cnt="
                + threeHoursCycles + "&appid=" + apiTokenOpenWeather;
        return restTemplate.getForObject(url, OpenWeatherForecastDto.class);

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


//    /*
//      forecast weather by city name from service "Weather Ground"
//       */
//    public ForecastDto getForecastWeatherFromWSByCity(String cityName, int days) {
//        String url = "http://api.weatherstack.com/forecast ? access_key =" + apiTokenWeatherAPI
//                + "& query =" + cityName + "& forecast_days =" + days;
//        return restTemplate.getForObject(url, ForecastDto.class);
//    }
//
//    /*
//    forecast weather by location from service "Weather Ground"
//     */
//    public ForecastDto getForecastWeatherFromWSByLocation(Float lan, float lon, int days) {
//        String url = "http://api.weatherstack.com/forecast ? access_key =" + apiTokenWeatherAPI
//                + "& query =" + lan + ", " + lon + "& forecast_days =" + days;
//        return restTemplate.getForObject(url, ForecastDto.class);
//    }

}


