package com.example.weatherbot.app.service;

import com.example.weatherbot.app.model.Weather;
import com.example.weatherbot.app.model.db_model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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
            if (Objects.nonNull(user.getLocation())) {
                return weatherList.stream()
                        .filter(weather -> weather.getLat().equals(user.getLocation().getLat())
                                && weather.getLon().equals(user.getLocation().getLon())
                                && weather.getDate().equals(date))
                        .findAny().orElse(null);

            } else {
                return weatherList.stream()
                        .filter(weather -> weather.getCityName().equals(user.getCity()) && weather.getDate().equals(date))
                        .findAny().orElse(null);
            }

        } else {
            return null;
        }

    }
}
