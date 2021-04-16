package com.example.weatherbot.app.utils;

import com.example.weatherbot.app.dto.openweatherdto.current.OpenWeatherCurrentDto;
import com.example.weatherbot.app.dto.openweatherdto.forecast.OpenWeatherForecastDto;
import com.example.weatherbot.app.dto.openweatherdto.forecast.OpenWeatherThreeHourForecast;
import com.example.weatherbot.app.dto.weatherapidto.current.WeatherAPICurrentDto;
import com.example.weatherbot.app.dto.weatherapidto.forecast.ForecastDay;
import com.example.weatherbot.app.dto.weatherapidto.forecast.WeatherAPIForecastDto;
import com.example.weatherbot.app.dto.weatherbitdto.current.WeatherBitCurrentDto;
import com.example.weatherbot.app.dto.weatherbitdto.forecast.WeatherBitForecastDto;
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
    public WeatherFacade(OpenWeatherService openWeatherService, WeatherApiService weatherApiService, WeatherBitService weatherBitService, WeatherService weatherService) {
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
        OpenWeatherModel openWeatherModel = new OpenWeatherModel(forecastWeatherFromOWByLocation.getCity().getName(),
                forecastWeatherFromOWByLocation.getHourlyArray().get(0).getMainMetrics().getTemp(),
                forecastWeatherFromOWByLocation.getHourlyArray().get(0).getMainMetrics().getPressure(),
                forecastWeatherFromOWByLocation.getHourlyArray().get(0).getMainMetrics().getHumidity(),
                forecastWeatherFromOWByLocation.getHourlyArray().get(0).getMainMetrics().getFeelsLike(),
                forecastWeatherFromOWByLocation.getHourlyArray().get(0).getWeather().get(0).getCondition(),
                forecastWeatherFromOWByLocation.getCity().getCoords().getLat(),
                forecastWeatherFromOWByLocation.getCity().getCoords().getLon(),
                forecastWeatherFromOWByLocation.getHourlyArray().get(0).getWind().getSpeed(),
                forecastWeatherFromOWByLocation.getHourlyArray().get(0).getWind().getDeg(),
                forecastWeatherFromOWByLocation.getHourlyArray().get(0).getDateTime());
        WeatherAPIForecastDto forecastWeatherFromWAByLocation = weatherApiService.getForecastWeatherFromWAByLocation(lat, lon);
//        ForecastDay forecastDay = weatherApiService.searchForTimeStampWA(forecastWeatherFromWAByLocation);
        WeatherApiModel weatherApiModel = new WeatherApiModel(forecastWeatherFromWAByLocation.getLocation().getCityName(),
                forecastWeatherFromWAByLocation.getForecast().getForecasts().get(0).getWeatherInfo().getAvgTemp(),
                forecastWeatherFromWAByLocation.getForecast().getForecasts().get(0).getHourForecast().get(0).getPressure(),
                forecastWeatherFromWAByLocation.getForecast().getForecasts().get(0).getWeatherInfo().getAvgHumidity(),
                forecastWeatherFromWAByLocation.getForecast().getForecasts().get(0).getHourForecast().get(0).getFeelsLike(),
                forecastWeatherFromWAByLocation.getForecast().getForecasts().get(0).getWeatherInfo().getCondition().getDescription(),
                forecastWeatherFromWAByLocation.getLocation().getLat(),
                forecastWeatherFromWAByLocation.getLocation().getLon(),
                forecastWeatherFromWAByLocation.getForecast().getForecasts().get(0).getWeatherInfo().getWindSpeed(),
                forecastWeatherFromWAByLocation.getForecast().getForecasts().get(0).getHourForecast().get(0).getWindDeg(),
                forecastWeatherFromWAByLocation.getLocation().getLocalTime());
        WeatherBitForecastDto fromWBByLocation = weatherBitService.getForecastWeatherFromWBByLocation(lat, lon);
//        WeatherBitInfo weatherBitInfo = weatherBitService.searchForTimeStampWB(fromWBByLocation);
        WeatherBitModel weatherBitModel = new WeatherBitModel(fromWBByLocation.getCityName(),
                fromWBByLocation.getMainInfoForecast().get(0).getTemp(),
                fromWBByLocation.getMainInfoForecast().get(0).getPressure(),
                fromWBByLocation.getMainInfoForecast().get(0).getHumidity(),
                fromWBByLocation.getMainInfoForecast().get(0).getFeelsLike(),
                fromWBByLocation.getMainInfoForecast().get(0).getDesc().getDescription(),
                fromWBByLocation.getLat(),
                fromWBByLocation.getLon(),
                fromWBByLocation.getMainInfoForecast().get(0).getWindSpeed(),
                fromWBByLocation.getMainInfoForecast().get(0).getWindDeg(),
                fromWBByLocation.getMainInfoForecast().get(0).getDateTime());
        return computeAverageData(weatherApiModel, openWeatherModel, weatherBitModel);
//        weatherService.save(null,openWeatherModel,null);
//        return new Weather(openWeatherModel.getTemp(), openWeatherModel.getPressure(), openWeatherModel.getHumidity(),openWeatherModel.getWindSpeed(),
//                openWeatherModel.getFeelsLike(), openWeatherModel.getCondition(), openWeatherModel.getLat(), openWeatherModel.getLon());
    }

    public Weather createRequestForCurrentWeatherByLocation(Float lat, Float lon) {
        OpenWeatherCurrentDto openWeatherCurrentDto = openWeatherService.getCurrentWeatherFromOWByLocation(lat, lon);
        OpenWeatherModel openWeatherModel = new OpenWeatherModel(openWeatherCurrentDto.getName(),
                openWeatherCurrentDto.getMain().getTemp(), openWeatherCurrentDto.getMain().getPressure(),
                openWeatherCurrentDto.getMain().getHumidity(), openWeatherCurrentDto.getMain().getFeelsLike(),
                openWeatherCurrentDto.getWeather().get(0).getCondition(), openWeatherCurrentDto.getCoordinate().getLat(),
                openWeatherCurrentDto.getCoordinate().getLon(), openWeatherCurrentDto.getWind().getSpeed(),
                openWeatherCurrentDto.getWind().getDeg(), openWeatherCurrentDto.getDateTime());
        WeatherAPICurrentDto currentWeatherFromWAByLocation = weatherApiService.getCurrentWeatherFromWAByLocation(lat, lon);
        WeatherApiModel weatherApiModel = new WeatherApiModel(currentWeatherFromWAByLocation.getLocation().getCityName(),
                currentWeatherFromWAByLocation.getInfo().getTemp(),
                currentWeatherFromWAByLocation.getInfo().getPressure(),
                currentWeatherFromWAByLocation.getInfo().getHumidity(), currentWeatherFromWAByLocation.getInfo().getFeelsLike(),
                currentWeatherFromWAByLocation.getInfo().getCondition().getDescription(),
                currentWeatherFromWAByLocation.getLocation().getLat(), currentWeatherFromWAByLocation.getLocation().getLon(),
                currentWeatherFromWAByLocation.getInfo().getWindSpeed(),
                currentWeatherFromWAByLocation.getInfo().getWindDeg(),
                currentWeatherFromWAByLocation.getLocation().getLocalTime());
        WeatherBitCurrentDto weatherBitCurrentDto = weatherBitService.getCurrentWeatherFromWBByLocation(lat, lon);
        WeatherBitModel weatherBitModel = new WeatherBitModel(weatherBitCurrentDto.getMainInfo().get(0).getCityName(),
                weatherBitCurrentDto.getMainInfo().get(0).getTemp(),
                weatherBitCurrentDto.getMainInfo().get(0).getPressure(),
                weatherBitCurrentDto.getMainInfo().get(0).getHumidity(),
                weatherBitCurrentDto.getMainInfo().get(0).getFeelsLike(),
                weatherBitCurrentDto.getMainInfo().get(0).getDesc().getDescription(),
                weatherBitCurrentDto.getMainInfo().get(0).getLat(),
                weatherBitCurrentDto.getMainInfo().get(0).getLon(),
                weatherBitCurrentDto.getMainInfo().get(0).getWindSpeed(),
                weatherBitCurrentDto.getMainInfo().get(0).getWindDeg(),
                weatherBitCurrentDto.getMainInfo().get(0).getDateTime());
        weatherService.save(weatherBitModel, openWeatherModel, weatherApiModel);
        return computeAverageData(weatherApiModel, openWeatherModel, weatherBitModel);

    }

    public Weather createForecastRequestByCity(String city) {
        OpenWeatherForecastDto forecastWeatherFromOWByCity = openWeatherService.getForecastWeatherFromOWByCity(city);
        OpenWeatherThreeHourForecast openWeatherThreeHourForecast = openWeatherService.searchForTimeStamp(forecastWeatherFromOWByCity);
        OpenWeatherModel openWeatherModel = new OpenWeatherModel(forecastWeatherFromOWByCity.getCity().getName(),
                forecastWeatherFromOWByCity.getHourlyArray().get(0).getMainMetrics().getTemp(),
                forecastWeatherFromOWByCity.getHourlyArray().get(0).getMainMetrics().getPressure(),
                forecastWeatherFromOWByCity.getHourlyArray().get(0).getMainMetrics().getHumidity(),
                forecastWeatherFromOWByCity.getHourlyArray().get(0).getMainMetrics().getFeelsLike(),
                forecastWeatherFromOWByCity.getHourlyArray().get(0).getWeather().get(0).getCondition(),
                forecastWeatherFromOWByCity.getCity().getCoords().getLat(),
                forecastWeatherFromOWByCity.getCity().getCoords().getLon(),
                forecastWeatherFromOWByCity.getHourlyArray().get(0).getWind().getSpeed(),
                forecastWeatherFromOWByCity.getHourlyArray().get(0).getWind().getDeg(),
                forecastWeatherFromOWByCity.getHourlyArray().get(0).getDateTime());
        WeatherBitForecastDto fromWBByCity = weatherBitService.getForecastWeatherFromWBByCity(city);
//        WeatherBitInfo weatherBitInfo = weatherBitService.searchForTimeStampWB(fromWBByCity);
        WeatherBitModel weatherBitModel = new WeatherBitModel(fromWBByCity.getCityName(),
                fromWBByCity.getMainInfoForecast().get(0).getTemp(),
                fromWBByCity.getMainInfoForecast().get(0).getPressure(),
                fromWBByCity.getMainInfoForecast().get(0).getHumidity(),
                fromWBByCity.getMainInfoForecast().get(0).getFeelsLike(),
                fromWBByCity.getMainInfoForecast().get(0).getDesc().getDescription(),
                fromWBByCity.getLat(),
                fromWBByCity.getLon(),
                fromWBByCity.getMainInfoForecast().get(0).getWindSpeed(),
                fromWBByCity.getMainInfoForecast().get(0).getWindDeg(),
                fromWBByCity.getMainInfoForecast().get(0).getDateTime());
        WeatherAPIForecastDto forecastWeatherFromWAByCity = weatherApiService.getForecastWeatherFromWAByCity(city);
//        ForecastDay forecastDay = weatherApiService.searchForTimeStampWA(forecastWeatherFromWAByCity);
        WeatherApiModel weatherApiModel = new WeatherApiModel(forecastWeatherFromWAByCity.getLocation().getCityName(),
                forecastWeatherFromWAByCity.getForecast().getForecasts().get(0).getWeatherInfo().getAvgTemp(),
                forecastWeatherFromWAByCity.getForecast().getForecasts().get(0).getHourForecast().get(0).getPressure(),
                forecastWeatherFromWAByCity.getForecast().getForecasts().get(0).getWeatherInfo().getAvgHumidity(),
                forecastWeatherFromWAByCity.getForecast().getForecasts().get(0).getHourForecast().get(0).getFeelsLike(),
                forecastWeatherFromWAByCity.getForecast().getForecasts().get(0).getWeatherInfo().getCondition().getDescription(),
                forecastWeatherFromWAByCity.getLocation().getLat(),
                forecastWeatherFromWAByCity.getLocation().getLon(),
                forecastWeatherFromWAByCity.getForecast().getForecasts().get(0).getWeatherInfo().getWindSpeed(),
                forecastWeatherFromWAByCity.getForecast().getForecasts().get(0).getHourForecast().get(0).getWindDeg(),
                forecastWeatherFromWAByCity.getLocation().getLocalTime());
        //weatherService.save(weatherBitModel,openWeatherModel,weatherApiModel);
        return new Weather(openWeatherModel.getCityName(), openWeatherModel.getTemp(), openWeatherModel.getPressure(), openWeatherModel.getHumidity(), openWeatherModel.getWindSpeed(),
                openWeatherModel.getFeelsLike(), openWeatherModel.getCondition(), openWeatherModel.getLat(), openWeatherModel.getLon());
        //return computeAverageData(weatherApiModel, openWeatherModel, weatherBitModel);
    }


    public Weather createRequestForCurrentWeatherByCity(String city) {
        OpenWeatherCurrentDto openWeatherCurrentDto = openWeatherService.getCurrentByCity(city);
        OpenWeatherModel openWeatherModel = new OpenWeatherModel(openWeatherCurrentDto.getName(),
                openWeatherCurrentDto.getMain().getTemp(), openWeatherCurrentDto.getMain().getPressure(),
                openWeatherCurrentDto.getMain().getHumidity(), openWeatherCurrentDto.getMain().getFeelsLike(),
                openWeatherCurrentDto.getWeather().get(0).getCondition(), openWeatherCurrentDto.getCoordinate().getLat(),
                openWeatherCurrentDto.getCoordinate().getLon(), openWeatherCurrentDto.getWind().getSpeed(),
                openWeatherCurrentDto.getWind().getDeg(), openWeatherCurrentDto.getDateTime());
        WeatherAPICurrentDto currentWeatherFromWAByCity = weatherApiService.getCurrentWeatherFromWAByCity(city);
        WeatherApiModel weatherApiModel = new WeatherApiModel(currentWeatherFromWAByCity.getLocation().getCityName(),
                currentWeatherFromWAByCity.getInfo().getTemp(),
                currentWeatherFromWAByCity.getInfo().getPressure(),
                currentWeatherFromWAByCity.getInfo().getHumidity(), currentWeatherFromWAByCity.getInfo().getFeelsLike(),
                currentWeatherFromWAByCity.getInfo().getCondition().getDescription(),
                currentWeatherFromWAByCity.getLocation().getLat(), currentWeatherFromWAByCity.getLocation().getLon(),
                currentWeatherFromWAByCity.getInfo().getWindSpeed(),
                currentWeatherFromWAByCity.getInfo().getWindDeg(),
                currentWeatherFromWAByCity.getLocation().getLocalTime());
        WeatherBitCurrentDto weatherBitCurrentDto = weatherBitService.getCurrentWeatherFromWBByCity(city);
        WeatherBitModel weatherBitModel = new WeatherBitModel(weatherBitCurrentDto.getMainInfo().get(0).getCityName(),
                weatherBitCurrentDto.getMainInfo().get(0).getTemp(),
                weatherBitCurrentDto.getMainInfo().get(0).getPressure(),
                weatherBitCurrentDto.getMainInfo().get(0).getHumidity(),
                weatherBitCurrentDto.getMainInfo().get(0).getFeelsLike(),
                weatherBitCurrentDto.getMainInfo().get(0).getDesc().getDescription(),
                weatherBitCurrentDto.getMainInfo().get(0).getLat(),
                weatherBitCurrentDto.getMainInfo().get(0).getLon(),
                weatherBitCurrentDto.getMainInfo().get(0).getWindSpeed(),
                weatherBitCurrentDto.getMainInfo().get(0).getWindDeg(),
                weatherBitCurrentDto.getMainInfo().get(0).getDateTime());
        System.out.println(weatherService.save(null, openWeatherModel, null));
//        return new Weather(openWeatherModel.getTemp(), openWeatherModel.getPressure(), openWeatherModel.getHumidity(),openWeatherModel.getWindSpeed(),
//                openWeatherModel.getFeelsLike(), openWeatherModel.getCondition(), openWeatherModel.getLat(), openWeatherModel.getLon());
//        weatherService.save(weatherBitModel,openWeatherModel,weatherApiModel);
        return computeAverageData(weatherApiModel, openWeatherModel, weatherBitModel);
    }

    private Weather computeAverageData(WeatherApiModel weatherAPIModel, OpenWeatherModel openWeatherModel, WeatherBitModel weatherBitModel) {
        String cityName = weatherAPIModel.getCityName();
        Double temp = (weatherAPIModel.getTemp() + openWeatherModel.getTemp() + weatherBitModel.getTemp()) / 3.0;
        Integer pressure = (weatherAPIModel.getPressure() + openWeatherModel.getPressure() + weatherBitModel.getPressure()) / 3;
        Integer humidity = (weatherAPIModel.getHumidity() + openWeatherModel.getHumidity() + weatherBitModel.getHumidity()) / 3;
        Double feelsLike = (weatherAPIModel.getFeelsLike() + openWeatherModel.getFeelsLike() + weatherBitModel.getFeelsLike()) / 3.0;
        Double speed = (weatherAPIModel.getWindSpeed() + openWeatherModel.getWindSpeed() + weatherBitModel.getWindSpeed()) / 3.0;
        Float lat = weatherAPIModel.getLat();
        Float lon = weatherAPIModel.getLon();
        String condition = openWeatherModel.getCondition();
        return new Weather(cityName, temp, pressure, humidity, speed, feelsLike, condition, lat, lon);
    }


}
