package com.example.weatherbot.app.utils;

import com.example.weatherbot.app.model.Weather;
import com.example.weatherbot.app.model.db_model.User;
import com.example.weatherbot.app.model.weather_model.OpenWeatherModel;
import com.example.weatherbot.app.model.weather_model.WeatherApiModel;
import com.example.weatherbot.app.model.weather_model.WeatherBitModel;
import com.example.weatherbot.app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class WeatherFacade {
    private final OpenWeatherService openWeatherService;
    private final WeatherApiService weatherApiService;
    private final WeatherBitService weatherBitService;
    private final WeatherService weatherService;
    private final UserService userService;


    @Autowired
    public WeatherFacade(OpenWeatherService openWeatherService, WeatherApiService weatherApiService, WeatherBitService weatherBitService, WeatherService weatherService, UserService userService) {
        this.openWeatherService = openWeatherService;
        this.weatherApiService = weatherApiService;
        this.weatherBitService = weatherBitService;
        this.weatherService = weatherService;
        this.userService = userService;
    }

    public Weather createRequestToThreeServices(String data, User user) {
        try {
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
        } catch (HttpClientErrorException e) {
            throw e;
        }
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
        Float lat = Float.parseFloat(String.format("%.2f", weatherAPIModel.getLat()).replace(",", "."));
        Float lon = Float.parseFloat(String.format("%.2f", weatherAPIModel.getLon()).replace(",", "."));
        String condition = openWeatherModel.getCondition();
        LocalDate date = weatherBitModel.getDateTime().toLocalDate();
        Weather weather = new Weather(cityName, temp, pressure, humidity, speed, feelsLike, condition, lat, lon, date);
        if (Objects.equals(date, LocalDate.now())){
            weather.setCurrent(true);
            weatherService.findAndReplace(weather);
        }
        else {
            weatherService.save(weather);
        }
        return weather;
    }

    //@Scheduled(fixedRate = 43200000)
    //Ежедневно в 8 и 20
    private void createDailyRequestToWeatherApi() {
        List<Weather> weather = weatherService.findAll();
        if (!weather.isEmpty()) {
            Set<String> cities = weather.stream().map(Weather::getCityName).collect(Collectors.toSet());
            List<Weather> current = cities.parallelStream()
                    .map(this::createRequestForCurrentWeatherByCity)
                    .peek(weatherService::findAndReplace)
                    .collect(Collectors.toList());
            List<Weather> forecast = cities.parallelStream()
                    .map(this::createForecastRequestByCity)
                    .collect(Collectors.toList());
        }

    }
    //@Scheduled(cron = "0 0 9/24 * *")
    private void compareYesterdayForecastToCurrentWeather(){
        List<Weather> weatherList = weatherService.findAll();
        if (Objects.nonNull(weatherList)){
            Map<String, Weather> yesterday = weatherList.stream()
                    .filter(weather -> !weather.isCurrent() && weather.getDate().equals(LocalDate.now()))
                    .collect(Collectors.toMap(Weather::getCityName, Function.identity(), (existing, replacement) -> replacement));
            Map<String, Weather> current = weatherList.stream()
                    .filter(weather -> weather.isCurrent() && weather.getDate().equals(LocalDate.now()))
                    .collect(Collectors.toMap(Weather::getCityName, Function.identity(), (existing, replacement) -> replacement));
            Map<String,Boolean> matching = new HashMap<>();
            List<Boolean> checking = yesterday.entrySet().stream().map(forecast -> {
                if (current.containsKey(forecast.getKey())) {
                    boolean match = forecast.getValue().equals(current.get(forecast.getKey()));
                    matching.put(forecast.getKey(),match);
                }
                return false;
            }).collect(Collectors.toList());
        }
    }

}
