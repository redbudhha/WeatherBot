package com.example.weatherbot.app.utils;

import com.example.weatherbot.app.dto.openweatherdto.current.OpenWeatherCurrentDto;
import com.example.weatherbot.app.dto.openweatherdto.forecast.OpenWeatherForecastDto;
import com.example.weatherbot.app.dto.openweatherdto.forecast.OpenWeatherThreeHourForecast;
import com.example.weatherbot.app.dto.weatherapidto.current.WeatherAPICurrentDto;
import com.example.weatherbot.app.dto.weatherbitdto.WeatherBitInfo;
import com.example.weatherbot.app.dto.weatherbitdto.current.WeatherBitCurrentDto;
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
    private final WeatherBitService weatherBitService;

    @Autowired
    public WeatherFacade(OpenWeatherService openWeatherService, WeatherApiService weatherApiService, WeatherBitService weatherBitService) {
        this.openWeatherService = openWeatherService;
        this.weatherApiService = weatherApiService;
        this.weatherBitService = weatherBitService;
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
        OpenWeatherThreeHourForecast openWeatherThreeHourForecast = openWeatherService.searchForTimeStamp(forecastWeatherFromOWByCity);
        OpenWeatherModel openWeatherModel = new OpenWeatherModel(forecastWeatherFromOWByCity, openWeatherThreeHourForecast);
//       WeatherBitModel weatherBitModel = new WeatherBitModel();
        //return computeAverageData(3 models)
        return null;
    }


    public Weather createRequestForCurrentWeather(String city) {
        OpenWeatherCurrentDto openWeatherCurrentDto = openWeatherService.getCurrentByCity(city);
        OpenWeatherModel openWeatherModel = new OpenWeatherModel(openWeatherCurrentDto);
        WeatherAPICurrentDto currentWeatherFromWAByCity = weatherApiService.getCurrentWeatherFromWAByCity(city);
        WeatherApiModel weatherApiModel = new WeatherApiModel(currentWeatherFromWAByCity);
        // для этого сервиса нет текущей погоды
        WeatherBitInfo weatherBitInfo = weatherBitService.getCurrentWeatherFromWBByCity(city);
        WeatherBitModel weatherBitModel = new WeatherBitModel(weatherBitInfo);
        return computeAverageData(weatherApiModel, openWeatherModel, weatherBitModel);

    }

    public Weather createRequestToOpenWeather(User user, String data) {
        OpenWeatherModel openWeatherModel = null;
        if (data.equals("today")) {
            OpenWeatherCurrentDto openWeatherCurrentDto = openWeatherService.getCurrentByCity(user.getCity());
            openWeatherModel = new OpenWeatherModel(openWeatherCurrentDto);
        } else {
            OpenWeatherForecastDto forecastWeatherFromOWByCity = openWeatherService.getForecastWeatherFromOWByCity(user.getCity());
            OpenWeatherThreeHourForecast openWeatherThreeHourForecast = openWeatherService.searchForTimeStamp(forecastWeatherFromOWByCity);
            openWeatherModel = new OpenWeatherModel(forecastWeatherFromOWByCity, openWeatherThreeHourForecast);
        }
        return new Weather(openWeatherModel.getTemp(), (double) openWeatherModel.getPressure()
                , openWeatherModel.getHumidity(), openWeatherModel.getFeelsLike(), openWeatherModel.getCondition()
                , openWeatherModel.getSpeed(), openWeatherModel.getDateTime(), openWeatherModel.getLat(), openWeatherModel.getLon());
    }


    public Weather computeAverageData(WeatherApiModel weatherAPIModel, OpenWeatherModel openWeatherModel, WeatherBitModel weatherBitModel) {
        Double temp = (weatherAPIModel.getTemp() + openWeatherModel.getTemp() + weatherBitModel.getTemp()) / 3;
        Double pressure = (weatherAPIModel.getPressure() + openWeatherModel.getPressure() + weatherBitModel.getPressure()) / 3.0;
        Integer humidity = (weatherAPIModel.getHumidity() + openWeatherModel.getHumidity() + weatherBitModel.getHumidity()) / 3;
        Double feelsLike = (weatherAPIModel.getFeelsLike() + openWeatherModel.getFeelsLike() + weatherBitModel.getFeelsLike()) / 3;
        Double speed = (weatherAPIModel.getWindSpeed() + openWeatherModel.getWindSpeed() + weatherBitModel.getWindSpeed()) / 3;
        Float lat = (weatherAPIModel.getLat());
        Float lon = (weatherAPIModel.getLon());
        String condition = openWeatherModel.getCondition();
        return new Weather(temp, pressure, humidity, feelsLike, speed, condition, lat, lon);
    }

    // Этот метод ищет одну временную точку в завтрашнем дне и по ней и строит прогноз на завтра, в моем методе это полдень
    public OpenWeatherThreeHourForecast searchForTimeStamp(OpenWeatherForecastDto dto) {
        Optional<OpenWeatherThreeHourForecast> forecast = dto.getHourlyArray().stream()
                .filter(weather -> weather.getDateTime().toString().equals(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.NOON).toString()))
                .findAny();
        return forecast.orElse(null);
    }
}
