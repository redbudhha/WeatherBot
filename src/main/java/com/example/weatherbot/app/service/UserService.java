package com.example.weatherbot.app.service;

import model.User;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class UserService {

    //private UserDao userDao;


    public boolean createUser(Update update){
        Location location = update.getMessage().getLocation();
        Long chatId = update.getMessage().getChatId();
        String userName = update.getMessage().getFrom().getUserName();
        User user = new User(userName,location,chatId);
        //userDao.save(user);
        return true;
    }
}
