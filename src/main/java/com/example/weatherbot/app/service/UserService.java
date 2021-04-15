package com.example.weatherbot.app.service;

import com.example.weatherbot.app.model.User;
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


    public User createUser(Update update) {
        User user;
        Long chatId = update.getMessage().getChatId();
        String userName = update.getMessage().getFrom().getUserName();
        if (update.getMessage().hasLocation()) {
            Location location = update.getMessage().getLocation();
            User.Location loc = new User.Location((double)location.getLatitude(), (double)location.getLongitude());
            user = new User(userName, loc, chatId);
        } else {
            String city = update.getMessage().getText().toLowerCase();
            user = new User(userName, city, chatId);
        }
        return mongoTemplate.save(user, "user");
    }


    public List<User> findAll() {
        return mongoTemplate.findAll(User.class,"user");
    }
    public User findUserByChatId(Long chatId) {
       return mongoTemplate.findById(chatId,User.class);
    }
    public void update(User user) {
     mongoTemplate.save(user,"user");
    }


}
