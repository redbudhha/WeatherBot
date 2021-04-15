package com.example.weatherbot.app.utils;

import com.example.weatherbot.app.dto.openweatherdto.current.OpenWeatherCurrentDto;
import com.example.weatherbot.app.dto.openweatherdto.forecast.OpenWeatherForecastDto;
import com.example.weatherbot.app.dto.openweatherdto.forecast.OpenWeatherThreeHourForecast;
import com.example.weatherbot.app.dto.weatherapidto.current.WeatherAPICurrentDto;
import com.example.weatherbot.app.dto.weatherapidto.forecast.WeatherAPIForecastDto;
import com.example.weatherbot.app.dto.weatherbitdto.WeatherBitInfo;
import com.example.weatherbot.app.dto.weatherbitdto.forecast.WeatherBitForecastDto;
import com.example.weatherbot.app.model.Weather;
import com.example.weatherbot.app.model.db_model.User;
import com.example.weatherbot.app.model.weather_model.OpenWeatherModel;
import com.example.weatherbot.app.model.weather_model.WeatherApiModel;
import com.example.weatherbot.app.model.weather_model.WeatherBitModel;
import com.example.weatherbot.app.service.OpenWeatherService;
import com.example.weatherbot.app.service.WeatherApiService;
import com.example.weatherbot.app.service.WeatherBitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

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

    public Weather createRequestToThreeServices(String data, User user) {
        Weather weather = null;
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
                weather = createForecastRequestByLocation(lat,lon);
            }
        }
        return weather;
    }

    private Weather createForecastRequestByLocation(Float lat, Float lon) {
        OpenWeatherForecastDto forecastWeatherFromOWByLocation = openWeatherService.getForecastWeatherFromOWByLocation(lat,lon);
        OpenWeatherThreeHourForecast openWeatherThreeHourForecast = openWeatherService.searchForTimeStamp(forecastWeatherFromOWByLocation);
        OpenWeatherModel openWeatherModel = new OpenWeatherModel(forecastWeatherFromOWByLocation, openWeatherThreeHourForecast);
        WeatherAPIForecastDto forecastWeatherFromWAByLocation = weatherApiService.getForecastWeatherFromWAByLocation(lat,lon);
        //  WeatherApiModel weatherApiModel = new WeatherApiModel(forecastWeatherFromWAByLocation, );
        WeatherBitForecastDto fromWBByLocation = weatherBitService.getForecastWeatherFromWBByLocation(lat,lon);

        //   WeatherBitModel weatherBitModel = new WeatherBitModel(fromWBByLocation,);
        //return computeAverageData(3 models);
        return null;
    }

    public Weather createRequestForCurrentWeatherByLocation(Float lat,Float lon) {
        OpenWeatherCurrentDto openWeatherCurrentDto = openWeatherService.getCurrentWeatherFromOWByLocation(lat,lon);
        OpenWeatherModel openWeatherModel = new OpenWeatherModel(openWeatherCurrentDto);
        WeatherAPICurrentDto currentWeatherFromWAByCity = weatherApiService.getCurrentWeatherFromWAByLocation(lat,lon);
        WeatherApiModel weatherApiModel = new WeatherApiModel(currentWeatherFromWAByCity);
        WeatherBitInfo weatherBitInfo = weatherBitService.getCurrentWeatherFromWBByLocation(lat,lon);
        WeatherBitModel weatherBitModel = new WeatherBitModel(weatherBitInfo);
        return computeAverageData(weatherApiModel, openWeatherModel, weatherBitModel);

    }

    public Weather createForecastRequestByCity(String city) {
        OpenWeatherForecastDto forecastWeatherFromOWByCity = openWeatherService.getForecastWeatherFromOWByCity(city);
        OpenWeatherThreeHourForecast openWeatherThreeHourForecast = openWeatherService.searchForTimeStamp(forecastWeatherFromOWByCity);
        OpenWeatherModel openWeatherModel = new OpenWeatherModel(forecastWeatherFromOWByCity, openWeatherThreeHourForecast);
        WeatherAPIForecastDto forecastWeatherFromWAByCity = weatherApiService.getForecastWeatherFromWAByCity(city);
      //  WeatherApiModel weatherApiModel = new WeatherApiModel(forecastWeatherFromWAByCity, );
        WeatherBitForecastDto fromWBByCity = weatherBitService.getForecastWeatherFromWBByCity(city);

        //   WeatherBitModel weatherBitModel = new WeatherBitModel(forecastWeatherFromWBByCity,);
        //return computeAverageData(3 models);
        return null;
    }


    public Weather createRequestForCurrentWeatherByCity(String city) {
        OpenWeatherCurrentDto openWeatherCurrentDto = openWeatherService.getCurrentByCity(city);
        OpenWeatherModel openWeatherModel = new OpenWeatherModel(openWeatherCurrentDto);
        WeatherAPICurrentDto currentWeatherFromWAByCity = weatherApiService.getCurrentWeatherFromWAByCity(city);
        WeatherApiModel weatherApiModel = new WeatherApiModel(currentWeatherFromWAByCity);
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
        return new Weather(openWeatherModel.getTemp(), openWeatherModel.getPressure(), openWeatherModel.getHumidity(), openWeatherModel.getWindSpeed(), openWeatherModel.getFeelsLike(),
                openWeatherModel.getCondition(), openWeatherModel.getLat(), openWeatherModel.getLon());
    }


    public Weather computeAverageData(WeatherApiModel weatherAPIModel, OpenWeatherModel openWeatherModel, WeatherBitModel weatherBitModel) {
        Double temp = (weatherAPIModel.getTemp() + openWeatherModel.getTemp() + weatherBitModel.getTemp()) / 3;
        Integer pressure = (weatherAPIModel.getPressure() + openWeatherModel.getPressure() + weatherBitModel.getPressure()) / 3;
        Integer humidity = (weatherAPIModel.getHumidity() + openWeatherModel.getHumidity() + weatherBitModel.getHumidity()) / 3;
        Double feelsLike = (weatherAPIModel.getFeelsLike() + openWeatherModel.getFeelsLike() + weatherBitModel.getFeelsLike()) / 3;
        Double speed = (weatherAPIModel.getWindSpeed() + openWeatherModel.getWindSpeed() + weatherBitModel.getWindSpeed()) / 3;
        Float lat = weatherAPIModel.getLat();
        Float lon = weatherAPIModel.getLon();
        String condition = openWeatherModel.getCondition();
        return new Weather(temp, pressure, humidity, speed, feelsLike, condition, lat, lon);
    }

}
