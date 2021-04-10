package com.example.weatherbot.app.service;

import com.example.weatherbot.app.model.User;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class UserService {
    /*
        @Autowired
        private UserRepository repository;

    */
    public User createUser(Update update) {
        Location location = update.getMessage().getLocation();
        Long chatId = update.getMessage().getChatId();
        String userName = update.getMessage().getFrom().getUserName();
        User user = new User(userName, location, chatId);
        return user;
    }

   /* public void save(User user) {
        if (user != null) repository.save(user);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    */
}
