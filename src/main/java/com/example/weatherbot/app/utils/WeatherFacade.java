package com.example.weatherbot.app.utils;

import com.example.weatherbot.app.model.Weather;
import com.example.weatherbot.app.model.db_model.User;
import com.example.weatherbot.app.model.weather_model.OpenWeatherModel;
import com.example.weatherbot.app.model.weather_model.WeatherApiModel;
import com.example.weatherbot.app.model.weather_model.WeatherBitModel;
import com.example.weatherbot.app.service.OpenWeatherService;
import com.example.weatherbot.app.service.WeatherApiService;
import com.example.weatherbot.app.service.WeatherBitService;
import com.example.weatherbot.app.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class WeatherFacade {
    private final OpenWeatherService openWeatherService;
    private final WeatherApiService weatherApiService;
    private final WeatherBitService weatherBitService;
    private final WeatherService weatherService;

    @Autowired
    public WeatherFacade(OpenWeatherService openWeatherService, WeatherApiService weatherApiService, WeatherBitService weatherBitService, WeatherService weatherService) {
        this.openWeatherService = openWeatherService;
        this.weatherApiService = weatherApiService;
        this.weatherBitService = weatherBitService;
        this.weatherService = weatherService;
    }

    public Weather createRequestToThreeServices(String data, User user) {
        Weather weather = null;
        LocalDate date = null;
        if (data.equals("today")) {
            date = LocalDate.now();
        } else {
            date = LocalDate.now().plusDays(1);
        }
         weather = weatherService.checkIfWeatherAlreadyExists(date, user);
        if (Objects.isNull(weather)) {
            if (Objects.isNull(user.getLocation())) {
                if (data.equals("today")) {
                    weather = createRequestForCurrentWeatherByCity(user.getCity());
                } else if (data.equals("tomorrow")) {
                    weather = createForecastRequestByCity(user.getCity());
                }
            } else {
                Float lat = user.getLocation().getLat();
                Float lon = user.getLocation().getLon();
                if (data.equals("today")) {
                    weather = createRequestForCurrentWeatherByLocation(lat, lon);
                } else if (data.equals("tomorrow")) {
                    weather = createForecastRequestByLocation(lat, lon);
                }
            }
        }
        return weather;
    }

    private Weather createForecastRequestByLocation(Float lat, Float lon) {
        OpenWeatherModel openWeatherModel = openWeatherService.getForecastWeatherFromOWByLocation(lat, lon);
        WeatherApiModel weatherApiModel = weatherApiService.getForecastWeatherFromWAByLocation(lat, lon);
        WeatherBitModel weatherBitModel = weatherBitService.getForecastWeatherFromWBByLocation(lat, lon);
        return computeAndSaveAverageData(weatherApiModel, openWeatherModel, weatherBitModel);
    }

    public Weather createRequestForCurrentWeatherByLocation(Float lat, Float lon) {
        OpenWeatherModel openWeatherModel = openWeatherService.getCurrentWeatherFromOWByLocation(lat, lon);
        WeatherApiModel weatherApiModel = weatherApiService.getCurrentWeatherFromWAByLocation(lat, lon);
        WeatherBitModel weatherBitModel = weatherBitService.getCurrentWeatherFromWBByLocation(lat, lon);
        return computeAndSaveAverageData(weatherApiModel, openWeatherModel, weatherBitModel);
    }

    public Weather createForecastRequestByCity(String city) {
        OpenWeatherModel openWeatherModel = openWeatherService.getForecastWeatherFromOWByCity(city);
        WeatherBitModel weatherBitModel = weatherBitService.getForecastWeatherFromWBByCity(city);
        WeatherApiModel weatherApiModel = weatherApiService.getForecastWeatherFromWAByCity(city);
        return computeAndSaveAverageData(weatherApiModel, openWeatherModel, weatherBitModel);
    }


    public Weather createRequestForCurrentWeatherByCity(String city) {
        OpenWeatherModel openWeatherModel = openWeatherService.getCurrentByCity(city);
        WeatherApiModel weatherApiModel = weatherApiService.getForecastWeatherFromWAByCity(city);
        WeatherBitModel weatherBitModel = weatherBitService.getCurrentWeatherFromWBByCity(city);
        return computeAndSaveAverageData(weatherApiModel, openWeatherModel, weatherBitModel);
    }

    private Weather computeAndSaveAverageData(WeatherApiModel weatherAPIModel, OpenWeatherModel openWeatherModel, WeatherBitModel weatherBitModel) {
        String cityName = weatherAPIModel.getCityName();
        Double temp = (weatherAPIModel.getTemp() + openWeatherModel.getTemp() + weatherBitModel.getTemp()) / 3.0;
        Integer pressure = (weatherAPIModel.getPressure() + openWeatherModel.getPressure() + weatherBitModel.getPressure()) / 3;
        Integer humidity = (weatherAPIModel.getHumidity() + openWeatherModel.getHumidity() + weatherBitModel.getHumidity()) / 3;
        Double feelsLike = (weatherAPIModel.getFeelsLike() + openWeatherModel.getFeelsLike() + weatherBitModel.getFeelsLike()) / 3.0;
        Double speed = (weatherAPIModel.getWindSpeed() + openWeatherModel.getWindSpeed() + weatherBitModel.getWindSpeed()) / 3.0;
        Float lat = Float.parseFloat(String.format("%.2f",weatherAPIModel.getLat()).replace(",","."));
        Float lon = Float.parseFloat(String.format("%.2f",weatherAPIModel.getLon()).replace(",","."));
        String condition = openWeatherModel.getCondition();
        LocalDate date = openWeatherModel.getDateTime().toLocalDate();
        Weather weather = new Weather(cityName, temp, pressure, humidity, speed, feelsLike, condition, lat, lon, date);
        weatherService.save(weather);
        return weather;
    }


}
