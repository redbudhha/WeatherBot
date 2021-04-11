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
        User user;
        Long chatId = update.getMessage().getChatId();
        String userName = update.getMessage().getFrom().getUserName();
        if (update.getMessage().hasLocation()) {
            Location location = update.getMessage().getLocation();
            User.Location loc = new User.Location(location.getLatitude(),location.getLongitude());
            user = new User(userName,loc,chatId);
        } else {
            String city = update.getMessage().getText().toLowerCase();
            user = new User(userName,city,chatId);
        }
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
