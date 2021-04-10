package com.example.weatherbot.app.utils;

import com.example.weatherbot.app.dto.WeatherDto;
import com.example.weatherbot.app.model.User;
import com.example.weatherbot.app.model.Weather;
import com.example.weatherbot.app.service.UserService;
import com.example.weatherbot.app.service.WeatherService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.*;

@Component
public class TelegramFacade {
    public static final String RESOURCE = "https://api.telegram.org/bot";
    private final UserService userService;
    private final WeatherService weatherService;
    Map<Long, User> users = new HashMap<>();  // база не подключена, пока использую это

    public TelegramFacade(UserService userService, WeatherService service) {
        this.userService = userService;
        this.weatherService = service;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        SendMessage messageToUser = new SendMessage();
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                String text = update.getMessage().getText();
                if (text.equalsIgnoreCase("/start")) {
                    messageToUser = sendKeyBoardWithLocationInputChoice();
                } else if (text.equalsIgnoreCase("/help")) {
                    messageToUser.setText("This bot allows you to get weather forecast for today or following days.\nTo continue send /start.");
                }
            }
            if (update.getMessage().hasLocation()) {
                User user = userService.createUser(update);
                users.put(user.getChatId(), user);
                messageToUser = sendButtonsForChoosingDay();
            }
            messageToUser.setChatId(update.getMessage().getChatId());
        }
        if (update.hasCallbackQuery()) {
            messageToUser = processCallBackQuery(update.getCallbackQuery());
        }
        // если ни одно условие не подошло 
        if (messageToUser.getText() == null) {
            messageToUser.setText("This command isn't supported.");
        }
        return messageToUser;
    }

    public SendMessage sendKeyBoardWithLocationInputChoice() {
        InlineKeyboardButton location = new InlineKeyboardButton()
                .setText("Send location")
                .setCallbackData("location");
        InlineKeyboardButton city = new InlineKeyboardButton()
                .setText("Send city name")
                .setCallbackData("city");
        List<InlineKeyboardButton> keyboardRow = new ArrayList<>();
        keyboardRow.add(location);
        keyboardRow.add(city);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardRow);
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rowList);
        return new SendMessage().setReplyMarkup(markup).setText("How would you like to send location data?");
    }

    public SendMessage sendButtonsForChoosingDay() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton currentWeatherButton = new InlineKeyboardButton("Current weather forecast").setCallbackData("today");
        InlineKeyboardButton forThreeDays = new InlineKeyboardButton("For 3 days").setCallbackData("3");
        InlineKeyboardButton forFiveDays = new InlineKeyboardButton("For 5 days").setCallbackData("5");
        List<InlineKeyboardButton> keyboardRow = new ArrayList<>();
        List<InlineKeyboardButton> keyboardRow1 = new ArrayList<>();
        keyboardRow1.add(currentWeatherButton);
        keyboardRow.add(forThreeDays);
        keyboardRow.add(forFiveDays);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardRow1);
        rowList.add(keyboardRow);
        keyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setReplyMarkup(keyboardMarkup).setText("What day would you like to get the weather forecast?");
    }

    public SendMessage sendForRequestLocation(long chatId) {
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(new KeyboardButton()
                .setRequestLocation(true)
                .setText("Send location"));
        List<KeyboardRow> rows = new ArrayList<>(Collections.singletonList(keyboardRow));
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup().setKeyboard(rows).setOneTimeKeyboard(true).setResizeKeyboard(true);
        return new SendMessage().setChatId(chatId).setReplyMarkup(markup).setText("For sending location click 'Send location'");
    }

    public SendMessage processCallBackQuery(CallbackQuery query) {
        SendMessage messageToUser = new SendMessage();
        String data = query.getData();
        Long chatId = query.getMessage().getChatId();
        messageToUser.setChatId(chatId);
        if (data.equals("location")) {
            return sendForRequestLocation(chatId);
        } else if (data.equals("city")) {
            return messageToUser.setText("Insert city");
        } else {
            User user = users.get(chatId);
            Location location = user.getLocation();
            WeatherDto weatherDto = null;
            switch (data) {
                case "today":
                    weatherDto = weatherService.getCurrentWeatherFromOWByLocation(location.getLatitude(), location.getLongitude());
                    break;
                case "3":
                    weatherDto = weatherService.getForecastWeatherFromOWByLocation(location.getLatitude(), location.getLongitude(), 3);
                    break;
                case "5":
                    weatherDto = weatherService.getForecastWeatherFromOWByLocation(location.getLatitude(), location.getLongitude(), 5);
                    break;
            }
            Weather weather = new Weather(weatherDto);
            if (Objects.nonNull(weather)) {
                messageToUser.setText(weather.toString());

            }
        }
        return messageToUser;

    }
}

