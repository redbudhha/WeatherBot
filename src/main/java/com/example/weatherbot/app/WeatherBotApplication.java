package com.example.weatherbot.app;

import com.example.weatherbot.app.model.db_model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.ApiContextInitializer;

@EnableMongoRepositories

@SpringBootApplication
@EnableScheduling
public class WeatherBotApplication implements CommandLineRunner {
    @Autowired
    MongoTemplate template;

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(WeatherBotApplication.class, args);
    }

    @Override
    public void run(String... args)  {
        template.findAllAndRemove(Query.query(Criteria.where("userName").is("Yana")), User.class);
    }
}
