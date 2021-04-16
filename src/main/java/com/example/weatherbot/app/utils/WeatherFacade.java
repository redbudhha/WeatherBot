package com.example.weatherbot.app.utils;

import com.example.weatherbot.app.dto.openweatherdto.current.OpenWeatherCurrentDto;
import com.example.weatherbot.app.dto.openweatherdto.forecast.OpenWeatherForecastDto;
import com.example.weatherbot.app.dto.openweatherdto.forecast.OpenWeatherThreeHourForecast;
import com.example.weatherbot.app.dto.weatherapidto.current.WeatherAPICurrentDto;
import com.example.weatherbot.app.dto.weatherapidto.forecast.ForecastDay;
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
        ForecastDay forecastDay = weatherApiService.searchForTimeStampWA(forecastWeatherFromWAByLocation);
        WeatherApiModel weatherApiModel = new WeatherApiModel(forecastWeatherFromWAByLocation.getLocation().getCityName(),
                forecastDay.getWeatherInfo().getAvgTemp(), forecastDay.getHourForecast().getPressure(),
                forecastDay.getWeatherInfo().getAvgHumidity(),
                forecastDay.getHourForecast().getFeelsLike(),
                forecastDay.getWeatherInfo().getCondition().getDescription(),
                forecastWeatherFromWAByLocation.getLocation().getLat(),
                forecastWeatherFromWAByLocation.getLocation().getLon(), forecastDay.getWeatherInfo().getWindSpeed(),
                forecastDay.getHourForecast().getWindDeg(),
                forecastWeatherFromWAByLocation.getLocation().getLocalTime());
        WeatherBitForecastDto fromWBByLocation = weatherBitService.getForecastWeatherFromWBByLocation(lat, lon);
        WeatherBitInfo weatherBitInfo = weatherBitService.searchForTimeStampWB(fromWBByLocation);
        WeatherBitModel weatherBitModel = new WeatherBitModel(fromWBByLocation.getCityName(), weatherBitInfo.getTemp(),
                weatherBitInfo.getPressure(), weatherBitInfo.getHumidity(), weatherBitInfo.getFeelsLike(),
                weatherBitInfo.getDesc().getDescription(), fromWBByLocation.getLat(), fromWBByLocation.getLon(),
                weatherBitInfo.getWindSpeed(), weatherBitInfo.getWindDeg(), weatherBitInfo.getDateTime());
        weatherService.save(weatherBitModel, openWeatherModel, weatherApiModel);
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
        WeatherAPICurrentDto currentWeatherFromWAByCity = weatherApiService.getCurrentWeatherFromWAByLocation(lat, lon);
        WeatherApiModel weatherApiModel = new WeatherApiModel(currentWeatherFromWAByCity.getLocation().getCityName(),
                currentWeatherFromWAByCity.getInfo().getTemp(), currentWeatherFromWAByCity.getPressure(),
                currentWeatherFromWAByCity.getHumidity(), currentWeatherFromWAByCity.getFeelsLike(),
                currentWeatherFromWAByCity.getInfo().getCondition().getDescription(),
                currentWeatherFromWAByCity.getLocation().getLat(), currentWeatherFromWAByCity.getLocation().getLon(),
                currentWeatherFromWAByCity.getWindSpeed(),
                currentWeatherFromWAByCity.getWindDeg(),
                currentWeatherFromWAByCity.getLocation().getLocalTime());
        WeatherBitInfo weatherBitInfo = weatherBitService.getCurrentWeatherFromWBByLocation(lat, lon);
        WeatherBitModel weatherBitModel = new WeatherBitModel(weatherBitInfo.getCityName(), weatherBitInfo.getTemp(),
                weatherBitInfo.getPressure(), weatherBitInfo.getHumidity(), weatherBitInfo.getFeelsLike(),
                weatherBitInfo.getDesc().getDescription(), weatherBitInfo.getLat(), weatherBitInfo.getLon(),
                weatherBitInfo.getWindSpeed(), weatherBitInfo.getWindDeg(), weatherBitInfo.getDateTime());
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
        WeatherBitInfo weatherBitInfo = weatherBitService.searchForTimeStampWB(fromWBByCity);
        WeatherBitModel weatherBitModel = new WeatherBitModel(fromWBByCity.getCityName(), weatherBitInfo.getTemp(),
                weatherBitInfo.getPressure(), weatherBitInfo.getHumidity(), weatherBitInfo.getFeelsLike(),
                weatherBitInfo.getDesc().getDescription(), fromWBByCity.getLat(), fromWBByCity.getLon(),
                weatherBitInfo.getWindSpeed(), weatherBitInfo.getWindDeg(), weatherBitInfo.getDateTime());
        WeatherAPIForecastDto forecastWeatherFromWAByCity = weatherApiService.getForecastWeatherFromWAByCity(city);
        ForecastDay forecastDay = weatherApiService.searchForTimeStampWA(forecastWeatherFromWAByCity);
        WeatherApiModel weatherApiModel = new WeatherApiModel(forecastWeatherFromWAByCity.getLocation().getCityName(),
                forecastDay.getWeatherInfo().getAvgTemp(), forecastDay.getHourForecast().getPressure(),
                forecastDay.getWeatherInfo().getAvgHumidity(),
                forecastDay.getHourForecast().getFeelsLike(),
                forecastDay.getWeatherInfo().getCondition().getDescription(),
                forecastWeatherFromWAByCity.getLocation().getLat(),
                forecastWeatherFromWAByCity.getLocation().getLon(), forecastDay.getWeatherInfo().getWindSpeed(),
                forecastDay.getHourForecast().getWindDeg(),
                forecastWeatherFromWAByCity.getLocation().getLocalTime());
        //weatherService.save(weatherBitModel,openWeatherModel,weatherApiModel);
        return new Weather(openWeatherModel.getTemp(), openWeatherModel.getPressure(), openWeatherModel.getHumidity(), openWeatherModel.getWindSpeed(),
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
                currentWeatherFromWAByCity.getInfo().getTemp(), currentWeatherFromWAByCity.getPressure(),
                currentWeatherFromWAByCity.getHumidity(), currentWeatherFromWAByCity.getFeelsLike(),
                currentWeatherFromWAByCity.getInfo().getCondition().getDescription(),
                currentWeatherFromWAByCity.getLocation().getLat(), currentWeatherFromWAByCity.getLocation().getLon(),
                currentWeatherFromWAByCity.getWindSpeed(),
                currentWeatherFromWAByCity.getWindDeg(),
                currentWeatherFromWAByCity.getLocation().getLocalTime());
        WeatherBitInfo weatherBitInfo = weatherBitService.getCurrentWeatherFromWBByCity(city);
        WeatherBitModel weatherBitModel = new WeatherBitModel(weatherBitInfo.getCityName(), weatherBitInfo.getTemp(),
                weatherBitInfo.getPressure(), weatherBitInfo.getHumidity(), weatherBitInfo.getFeelsLike(),
                weatherBitInfo.getDesc().getDescription(), weatherBitInfo.getLat(), weatherBitInfo.getLon(),
                weatherBitInfo.getWindSpeed(), weatherBitInfo.getWindDeg(), weatherBitInfo.getDateTime());
        System.out.println(weatherService.save(null, openWeatherModel, null));
//        return new Weather(openWeatherModel.getTemp(), openWeatherModel.getPressure(), openWeatherModel.getHumidity(),openWeatherModel.getWindSpeed(),
//                openWeatherModel.getFeelsLike(), openWeatherModel.getCondition(), openWeatherModel.getLat(), openWeatherModel.getLon());
//        weatherService.save(weatherBitModel,openWeatherModel,weatherApiModel);
        return computeAverageData(weatherApiModel, openWeatherModel, weatherBitModel);
    }

    private Weather computeAverageData(WeatherApiModel weatherAPIModel, OpenWeatherModel openWeatherModel, WeatherBitModel weatherBitModel) {
        Double temp = (weatherAPIModel.getTemp() + openWeatherModel.getTemp() + weatherBitModel.getTemp()) / 3.0;
        Integer pressure = (weatherAPIModel.getPressure() + openWeatherModel.getPressure() + weatherBitModel.getPressure()) / 3;
        Integer humidity = (weatherAPIModel.getHumidity() + openWeatherModel.getHumidity() + weatherBitModel.getHumidity()) / 3;
        Double feelsLike = (weatherAPIModel.getFeelsLike() + openWeatherModel.getFeelsLike() + weatherBitModel.getFeelsLike()) / 3.0;
        Double speed = (weatherAPIModel.getWindSpeed() + openWeatherModel.getWindSpeed() + weatherBitModel.getWindSpeed()) / 3.0;
        Float lat = weatherAPIModel.getLat();
        Float lon = weatherAPIModel.getLon();
        String condition = openWeatherModel.getCondition();
        return new Weather(temp, pressure, humidity, speed, feelsLike, condition, lat, lon);
    }


}
