package com.example.weatherbot.app.utils;

import com.example.weatherbot.app.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class TelegramFacade {
    public static final String RESOURCE = "https://api.telegram.org/bot";
    private final UserService userService;

    public TelegramFacade(UserService userService) {
        this.userService = userService;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        SendMessage messageToUser = new SendMessage();
        messageToUser.setChatId(update.getMessage().getChatId());
        if (Objects.nonNull(update.getMessage())) {
            if (update.getMessage().hasText()) {
                String text = update.getMessage().getText();
                if (text.equalsIgnoreCase("/start")) {
                    messageToUser.setText("Click to continue");
                    messageToUser.setReplyMarkup(createButtonForLocation());
                } else if (text.equalsIgnoreCase("/help")) {
                    messageToUser.setText("This bot allows you to get weather forecast for today or following days.\nTo continue send /start.");
                }
                else messageToUser.setText("This command isn't supported. To continue send /start");
            }
            if (update.getMessage().hasLocation()) {
                boolean success = userService.createUser(update);
                System.out.println(success);
                messageToUser.setText("What day would you like to get the weather?");
            }
        }
        return messageToUser;
    }

    public ReplyKeyboardMarkup createButtonForLocation() {
        KeyboardButton location = new KeyboardButton();
        location.setRequestLocation(true);
        location.setText("Send location");
        KeyboardButton city = new KeyboardButton();
        city.setText("Insert city name");
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(location);
        keyboardRow.add(city);
        List<KeyboardRow> rows = new ArrayList<>(Collections.singletonList(keyboardRow));
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setKeyboard(rows);
        markup.setOneTimeKeyboard(true);
        markup.setResizeKeyboard(true);
        return markup;
    }


}
