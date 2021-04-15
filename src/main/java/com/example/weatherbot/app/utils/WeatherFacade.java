package com.example.weatherbot.app.utils;

import com.example.weatherbot.app.dto.openweatherdto.current.OpenWeatherCurrentDto;
import com.example.weatherbot.app.dto.openweatherdto.forecast.OpenWeatherForecastDto;
import com.example.weatherbot.app.dto.openweatherdto.forecast.OpenWeatherThreeHourForecast;
import com.example.weatherbot.app.dto.weatherapidto.current.WeatherAPICurrentDto;
import com.example.weatherbot.app.model.*;
import com.example.weatherbot.app.service.OpenWeatherService;
import com.example.weatherbot.app.service.WeatherApiService;
import com.example.weatherbot.app.service.WeatherGroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class WeatherFacade {
    private final OpenWeatherService openWeatherService;
    private final WeatherApiService weatherApiService;
    private final WeatherGroundService weatherGroundService;

    @Autowired
    public WeatherFacade(OpenWeatherService openWeatherService, WeatherApiService weatherApiService, WeatherGroundService weatherGroundService) {
        this.openWeatherService = openWeatherService;
        this.weatherApiService = weatherApiService;
        this.weatherGroundService = weatherGroundService;
    }

    public Weather createRequestToThreeServices(String day, User user) {
        Weather weather = null;
        if (Objects.isNull(user.getLocation())) {
            if (day.equals("today")) {
                weather = createRequestForCurrentWeather(user.getCity());
            } else if (day.equals("tomorrow")) {
                weather = createForecastRequest(user.getCity());
            }
        }
        return weather;
    }

    public Weather createForecastRequest(String city) {
        OpenWeatherForecastDto forecastWeatherFromOWByCity = openWeatherService.getForecastWeatherFromOWByCity(city);
        OpenWeatherThreeHourForecast openWeatherThreeHourForecast = searchForTimeStamp(forecastWeatherFromOWByCity);
        OpenWeatherModel openWeatherModel = new OpenWeatherModel(forecastWeatherFromOWByCity,openWeatherThreeHourForecast);

        WeatherGroundModel weatherGroundModel = new WeatherGroundModel();
         //return computeAverageData(3 models)
        return null;
    }


   public Weather createRequestForCurrentWeather(String city) {
            OpenWeatherCurrentDto openWeatherCurrentDto = openWeatherService.getCurrentByCity(city);
            OpenWeatherModel openWeatherModel = new OpenWeatherModel(openWeatherCurrentDto);
       WeatherAPICurrentDto currentWeatherFromWAByCity = weatherApiService.getCurrentWeatherFromWAByCity(city);
       WeatherApiModel weatherApiModel = new WeatherApiModel(currentWeatherFromWAByCity);
       // для этого сервиса нет текущей погоды
       WeatherGroundModel weatherGroundModel = new WeatherGroundModel();
        return computeAverageData(weatherApiModel,openWeatherModel);
    }


    //третий сервис надо добавить
    public Weather computeAverageData(WeatherApiModel weatherAPIModel, OpenWeatherModel openWeatherModel) {
       Double temp = (weatherAPIModel.getTemp() + openWeatherModel.getTemp()) / 2;
       Double pressure = (weatherAPIModel.getPressure() + openWeatherModel.getPressure()) / 2;
       Integer humidity = (weatherAPIModel.getHumidity() + openWeatherModel.getHumidity()) / 2;
       Double feelsLike = (weatherAPIModel.getFeelsLike() + openWeatherModel.getFeelsLike()) / 2;
       String condition = openWeatherModel.getCondition();
       Double speed = openWeatherModel.getSpeed();
       return new Weather(temp, pressure, humidity,feelsLike,condition,speed);
    }

    // Этот метод ищет одну временную точку в завтрашнем дне и по ней и строит прогноз на завтра, в моем методе это полдень
    public OpenWeatherThreeHourForecast searchForTimeStamp(OpenWeatherForecastDto dto) {
        Optional<OpenWeatherThreeHourForecast> forecast = dto.getHourlyArray().stream()
                .filter(weather -> weather.getDateTime().toString().equals(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.NOON).toString()))
                .findAny();
        return forecast.orElse(null);
    }
}
