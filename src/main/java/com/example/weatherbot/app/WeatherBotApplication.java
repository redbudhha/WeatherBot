package com.example.weatherbot.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.telegram.telegrambots.ApiContextInitializer;

@EnableMongoRepositories
@SpringBootApplication
public class WeatherBotApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(WeatherBotApplication.class, args);
    }
}
