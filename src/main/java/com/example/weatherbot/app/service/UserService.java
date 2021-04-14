package com.example.weatherbot.app.service;

import com.example.weatherbot.app.model.User;
import com.example.weatherbot.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;


    public User createUser(Update update) {
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
        return repository.save(user);
    }


    public List<User> findAll() {
        return repository.findAll();
    }
    public User findUserByChatId(Long chatId) {
        Optional<User> byId = repository.findById(chatId);
        return byId.orElse(null);
    }


}
