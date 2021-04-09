package com.example.weatherbot.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.ApiContextInitializer;


@SpringBootApplication
public class WeatherBotApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(WeatherBotApplication.class, args);
    }
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
