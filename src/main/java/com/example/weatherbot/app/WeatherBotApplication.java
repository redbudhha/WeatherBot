package com.example.weatherbot.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

import java.time.Instant;
import java.time.LocalDate;


@SpringBootApplication
public class WeatherBotApplication {

    public static void main(String[] args) {
//        System.out.println(Instant.ofEpochSecond(1618313401L));
        ApiContextInitializer.init();
        SpringApplication.run(WeatherBotApplication.class, args);
    }
}
