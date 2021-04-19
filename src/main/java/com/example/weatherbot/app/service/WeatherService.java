package com.example.weatherbot.app.service;

import com.example.weatherbot.app.model.Weather;
import com.example.weatherbot.app.model.db_model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Weather save(Weather weather) {
        return mongoTemplate.save(weather, "weather");
    }

    public List<Weather> findAll() {
        return mongoTemplate.findAll(Weather.class, "weather");
    }

    public Weather checkIfWeatherAlreadyExists(LocalDate date, User user) {
        List<Weather> weatherList = findAll();
        if (!weatherList.isEmpty()) {
            List<Weather> weatherForecast;
            if (Objects.nonNull(user.getLocation())) {
                weatherForecast = weatherList.stream()
                        .filter(weather -> weather.getLat().equals(user.getLocation().getLat())
                                && weather.getLon().equals(user.getLocation().getLon())
                                && weather.getDate().equals(date))
                        .collect(Collectors.toList());
            } else {
                weatherForecast = weatherList.stream()
                        .filter(weather -> weather.getCityName().equals(user.getCity()) && weather.getDate().equals(date))
                        .collect(Collectors.toList());
            }
            if (date.equals(LocalDate.now())) {
                return weatherForecast.stream().filter(Weather::isCurrent).findAny().orElse(null);
            } else  {
                return weatherForecast.stream().filter(weather -> !weather.isCurrent()).findAny().orElse(null);
            }
        } else {
            return null;
        }

    }
    public Weather findAndReplace(Weather weather){
        Query current = Query.query(Criteria.where("current").is(true).and("date").is(LocalDate.now()));
        Weather replacedWeather = mongoTemplate.findAndReplace(current, weather);
        if (Objects.nonNull(replacedWeather)) {
            mongoTemplate.save(weather);
        }
        return replacedWeather;
    }
}
