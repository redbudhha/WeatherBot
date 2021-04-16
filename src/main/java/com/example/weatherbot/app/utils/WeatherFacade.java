package com.example.weatherbot.app.utils;

import com.example.weatherbot.app.dto.openweatherdto.current.OpenWeatherCurrentDto;
import com.example.weatherbot.app.dto.openweatherdto.forecast.OpenWeatherForecastDto;
import com.example.weatherbot.app.dto.openweatherdto.forecast.OpenWeatherThreeHourForecast;
import com.example.weatherbot.app.dto.weatherapidto.current.WeatherAPICurrentDto;
import com.example.weatherbot.app.dto.weatherbitdto.WeatherBitInfo;
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

import java.util.Objects;

@Service
public class WeatherFacade {
    private final OpenWeatherService openWeatherService;
    private final WeatherApiService weatherApiService;
    private final WeatherBitService weatherBitService;
    private final WeatherService weatherService;

    @Autowired
    public WeatherFacade(OpenWeatherService openWeatherService, WeatherApiService weatherApiService, WeatherBitService weatherBitService,WeatherService weatherService) {
        this.openWeatherService = openWeatherService;
        this.weatherApiService = weatherApiService;
        this.weatherBitService = weatherBitService;
        this.weatherService = weatherService;
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
                weather = createForecastRequestByLocation(lat, lon);
            }
        }
        return weather;
    }

    private Weather createForecastRequestByLocation(Float lat, Float lon) {
        OpenWeatherForecastDto forecastWeatherFromOWByLocation = openWeatherService.getForecastWeatherFromOWByLocation(lat, lon);
        OpenWeatherThreeHourForecast openWeatherThreeHourForecast = openWeatherService.searchForTimeStamp(forecastWeatherFromOWByLocation);
        OpenWeatherModel openWeatherModel = new OpenWeatherModel(forecastWeatherFromOWByLocation, openWeatherThreeHourForecast);
        /*WeatherAPIForecastDto forecastWeatherFromWAByLocation = weatherApiService.getForecastWeatherFromWAByLocation(lat, lon);
        ForecastDay forecastDay = weatherApiService.searchForTimeStampWA(forecastWeatherFromWAByLocation);
        WeatherApiModel weatherApiModel = new WeatherApiModel(forecastWeatherFromWAByLocation, forecastDay);
        WeatherBitForecastDto fromWBByLocation = weatherBitService.getForecastWeatherFromWBByLocation(lat, lon);
        WeatherBitInfo weatherBitInfo = weatherBitService.searchForTimeStampWB(fromWBByLocation);
        WeatherBitModel weatherBitModel = new WeatherBitModel(fromWBByLocation, weatherBitInfo);
        weatherService.save(weatherBitModel,openWeatherModel,weatherApiModel);
        return computeAverageData(weatherApiModel, openWeatherModel, weatherBitModel);*/
        weatherService.save(null,openWeatherModel,null);
        return new Weather(openWeatherModel.getTemp(), openWeatherModel.getPressure(), openWeatherModel.getHumidity(),openWeatherModel.getWindSpeed(),
                openWeatherModel.getFeelsLike(), openWeatherModel.getCondition(), openWeatherModel.getLat(), openWeatherModel.getLon());
    }

    public Weather createRequestForCurrentWeatherByLocation(Float lat, Float lon) {
        OpenWeatherCurrentDto openWeatherCurrentDto = openWeatherService.getCurrentWeatherFromOWByLocation(lat, lon);
        OpenWeatherModel openWeatherModel = new OpenWeatherModel(openWeatherCurrentDto);
        WeatherAPICurrentDto currentWeatherFromWAByCity = weatherApiService.getCurrentWeatherFromWAByLocation(lat, lon);
        WeatherApiModel weatherApiModel = new WeatherApiModel(currentWeatherFromWAByCity);
        WeatherBitInfo weatherBitInfo = weatherBitService.getCurrentWeatherFromWBByLocation(lat, lon);
        WeatherBitModel weatherBitModel = new WeatherBitModel(weatherBitInfo);
        weatherService.save(weatherBitModel,openWeatherModel,weatherApiModel);
        return computeAverageData(weatherApiModel, openWeatherModel, weatherBitModel);

    }

    public Weather createForecastRequestByCity(String city) {
        OpenWeatherForecastDto forecastWeatherFromOWByCity = openWeatherService.getForecastWeatherFromOWByCity(city);
        OpenWeatherThreeHourForecast openWeatherThreeHourForecast = openWeatherService.searchForTimeStamp(forecastWeatherFromOWByCity);
        OpenWeatherModel openWeatherModel = new OpenWeatherModel(forecastWeatherFromOWByCity, openWeatherThreeHourForecast);
      /*  WeatherBitForecastDto fromWBByCity = weatherBitService.getForecastWeatherFromWBByCity(city);
        WeatherBitInfo weatherBitInfo = weatherBitService.searchForTimeStampWB(fromWBByCity);
        WeatherBitModel weatherBitModel = new WeatherBitModel(fromWBByCity, weatherBitInfo);
        WeatherAPIForecastDto forecastWeatherFromWAByCity = weatherApiService.getForecastWeatherFromWAByCity(city);
        ForecastDay forecastDay = weatherApiService.searchForTimeStampWA(forecastWeatherFromWAByCity);
        WeatherApiModel weatherApiModel = new WeatherApiModel(forecastWeatherFromWAByCity, forecastDay);
       */ //weatherService.save(weatherBitModel,openWeatherModel,weatherApiModel);
        return new Weather(openWeatherModel.getTemp(), openWeatherModel.getPressure(), openWeatherModel.getHumidity(),openWeatherModel.getWindSpeed(),
                openWeatherModel.getFeelsLike(), openWeatherModel.getCondition(), openWeatherModel.getLat(), openWeatherModel.getLon());
        //return computeAverageData(weatherApiModel, openWeatherModel, weatherBitModel);
    }


    public Weather createRequestForCurrentWeatherByCity(String city) {
        OpenWeatherCurrentDto openWeatherCurrentDto = openWeatherService.getCurrentByCity(city);
        OpenWeatherModel openWeatherModel = new OpenWeatherModel(openWeatherCurrentDto);
       // WeatherAPICurrentDto currentWeatherFromWAByCity = weatherApiService.getCurrentWeatherFromWAByCity(city);
      //  WeatherApiModel weatherApiModel = new WeatherApiModel(currentWeatherFromWAByCity);
        //WeatherBitInfo weatherBitInfo = weatherBitService.getCurrentWeatherFromWBByCity(city);
      //  WeatherBitModel weatherBitModel = new WeatherBitModel(weatherBitInfo);
        System.out.println(weatherService.save(null, openWeatherModel, null));
        return new Weather(openWeatherModel.getTemp(), openWeatherModel.getPressure(), openWeatherModel.getHumidity(),openWeatherModel.getWindSpeed(),
                openWeatherModel.getFeelsLike(), openWeatherModel.getCondition(), openWeatherModel.getLat(), openWeatherModel.getLon());
        //weatherService.save(weatherBitModel,openWeatherModel,weatherApiModel);
       // return computeAverageData(weatherApiModel, openWeatherModel, weatherBitModel);
    }

    private Weather computeAverageData(WeatherApiModel weatherAPIModel, OpenWeatherModel openWeatherModel, WeatherBitModel weatherBitModel) {
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
