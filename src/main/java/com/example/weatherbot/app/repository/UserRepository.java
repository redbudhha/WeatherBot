package com.example.weatherbot.app.repository;


import com.example.weatherbot.app.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {

}
