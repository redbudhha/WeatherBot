package com.example.weatherbot.app.utils;

import com.example.weatherbot.app.bot.WeatherBot;
import com.example.weatherbot.app.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class TelegramUtil {
    public static final String RESOURCE = "https://api.telegram.org/bot";
    private final RestTemplate template;
    private final WeatherBot weatherBot;
    private final UserService userService;

    public TelegramUtil(RestTemplate template, WeatherBot weatherBot,UserService userService) {
    this.template = template;
    this.weatherBot = weatherBot;
    this.userService = userService;
}

    public void handleUpdate(Update update) {
        if (update.getMessage() != null && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("chat_id", update.getMessage().getChatId().toString());
            if (text.equalsIgnoreCase("/start")) {
                requestLocation(params);
            } else if (text.equalsIgnoreCase("/help")) {
                helpResponse(params);
            }
        }
        if (update.getMessage() != null && update.getMessage().hasLocation()) {
            boolean success = userService.createUser(update);
            System.out.println(success);
        }
    }

    public ReplyKeyboardMarkup createButtonForLocation() {
        KeyboardButton location = new KeyboardButton();
        location.setRequestLocation(true);
        location.setText("Send location");
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(location);
        List<KeyboardRow> rows = new ArrayList<>(Collections.singletonList(keyboardRow));
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setKeyboard(rows);
        markup.setOneTimeKeyboard(true);
        markup.setResizeKeyboard(true);
        return markup;
    }

    public void requestLocation(MultiValueMap<String,String> params){
        ObjectMapper mapper = new JsonMapper();
            try {
                String jsonKeyboard = mapper.writeValueAsString(createButtonForLocation());
                params.add("text", "Click to continue");
                params.add("reply_markup", jsonKeyboard);
                template.postForEntity(new URI(TelegramUtil.RESOURCE + weatherBot.getBotToken() + "/sendMessage"), params, String.class);
            } catch (JsonProcessingException | URISyntaxException e) {
                e.printStackTrace();
            }

    }
    public void helpResponse(MultiValueMap<String,String> params) {
        try {
            params.add("text","This service allows you to get weather forecast for today or following days");
            template.postForObject(new URI(TelegramUtil.RESOURCE + weatherBot.getBotToken() + "/sendMessage"),params,String.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
