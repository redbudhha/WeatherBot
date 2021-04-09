package com.example.weatherbot.app.controllers;

import com.example.weatherbot.app.bot.WeatherBot;
import com.example.weatherbot.app.utils.TelegramFacade;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@RestController
public class MainController {

    private final TelegramUtil util;
    public MainController(TelegramUtil util) {
        this.util = util;
    }


    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void getUpdates(@RequestBody Update update) {
        util.handleUpdate(update);
    }
}

