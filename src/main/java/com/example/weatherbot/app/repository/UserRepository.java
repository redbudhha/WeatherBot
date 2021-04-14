package com.example.weatherbot.app.repository;


import com.example.weatherbot.app.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Long> {

    Optional<User> findById(Long chatId);
}
