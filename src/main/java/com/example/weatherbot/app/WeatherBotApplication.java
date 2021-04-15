package com.example.weatherbot.app;

import com.example.weatherbot.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.telegram.telegrambots.ApiContextInitializer;

import java.util.List;

@EnableMongoRepositories
@SpringBootApplication
public class WeatherBotApplication implements CommandLineRunner {
    @Autowired
    MongoTemplate template;

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(WeatherBotApplication.class, args);
    }

    @Override
    public void run(String... args)  {
        template.save(new User("Yana","Saint-Petersburg",124444),"user");
        template.save(new User("Alina","Saint-Petersburg",123333213),"user");
        List<User> user = template.findAll(User.class, "user");
        user.forEach(System.out::println);
    }
}
