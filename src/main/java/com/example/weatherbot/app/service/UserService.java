package com.example.weatherbot.app.service;

import com.example.weatherbot.app.model.db_model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
public class UserService {


    @Autowired
    private MongoTemplate mongoTemplate;


    public void createUser(Update update) {
        User user;
        Long chatId = update.getMessage().getChatId();
        String userName = update.getMessage().getFrom().getUserName();
        if (update.getMessage().hasLocation()) {
            Location location = update.getMessage().getLocation();
            User.Location loc = new User.Location(location.getLatitude(), location.getLongitude());
            user = new User(userName, loc, chatId);
        } else {
            String city = update.getMessage().getText().toLowerCase();
            user = new User(userName, city, chatId);
        }
        mongoTemplate.save(user, "user");
    }


    public List<User> findAll() {
        return mongoTemplate.findAll(User.class, "user");
    }

    public User findUserByChatId(Long chatId) {
        return mongoTemplate.findById(chatId, User.class);
    }

    public void update(User user) {
        mongoTemplate.save(user, "user");
    }


}
