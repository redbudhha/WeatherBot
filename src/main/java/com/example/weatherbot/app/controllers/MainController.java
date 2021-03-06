package com.example.weatherbot.app.controllers;

import com.example.weatherbot.app.bot.WeatherBot;
import com.example.weatherbot.app.utils.TelegramFacade;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class MainController {

    private final WeatherBot weatherBot;
    private final TelegramFacade facade;

    public MainController(WeatherBot weatherBot, TelegramFacade facade) {
        this.weatherBot = weatherBot;
        this.facade = facade;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void getUpdates(@RequestBody Update update) {
            weatherBot.executeMethod(facade.handleUpdate(update));
    }
}

