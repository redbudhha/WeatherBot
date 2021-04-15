package com.example.weatherbot.app.utils;

import com.example.weatherbot.app.model.db_model.User;
import com.example.weatherbot.app.model.Weather;
import com.example.weatherbot.app.service.UserService;
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
    private final WeatherFacade weatherFacade;
    private boolean waitingForCity = false;

    public TelegramFacade(UserService userService, WeatherFacade weatherFacade) {
        this.userService = userService;
        this.weatherFacade = weatherFacade;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        SendMessage messageToUser = new SendMessage();
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                String text = update.getMessage().getText();
                if (text.equalsIgnoreCase("/start")) {
                    User user = userService.findUserByChatId(update.getMessage().getChatId());
                    if (Objects.nonNull(user)) {
                        if (waitingForCity) {
                            user.setCity(update.getMessage().getText());
                            userService.update(user);
                            waitingForCity = false;
                            messageToUser = sendButtonsForChoosingDay(user.getChatId());
                        }
                         else {
                            messageToUser = sendLocationQuestion();
                        }
                    }
                    else {
                        messageToUser = sendKeyBoardWithLocationInputChoice(update.getMessage().getChatId());
                    }
                } else if (text.equalsIgnoreCase("/help")) {
                    messageToUser.setText("This bot allows you to get weather forecast for today or tomorrow.\nTo continue send /start.");
                } else if (waitingForCity) {
                    userService.createUser(update);
                    waitingForCity = false;
                    messageToUser = sendButtonsForChoosingDay(update.getMessage().getChatId());
                }
            }
            if (!update.getMessage().hasText() && update.getMessage().hasLocation()) {
                User user = userService.findUserByChatId(update.getMessage().getChatId());
                if (Objects.nonNull(user)) {
                    if (update.getMessage().hasLocation()) {
                        Location location = update.getMessage().getLocation();
                        user.setLocation(new User.Location(location.getLatitude(),location.getLongitude()));
                        userService.update(user);
                    }
                } else {
                    userService.createUser(update);
                }
                messageToUser = sendButtonsForChoosingDay(update.getMessage().getChatId());
            }
        }
        if (update.hasCallbackQuery()) {
            return processCallBackQuery(update.getCallbackQuery());
        }
        // если ни одно условие не подошло
        if (messageToUser.getText() == null) {
            messageToUser.setText("This command isn't supported.");

        }
        return messageToUser.setChatId(update.getMessage().getChatId());
    }

    public SendMessage sendKeyBoardWithLocationInputChoice(long chatId) {
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
        return new SendMessage().setReplyMarkup(markup).setText("How would you like to send location data?").setChatId(chatId);
    }

    public SendMessage sendButtonsForChoosingDay(long chatId) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton currentWeatherButton = new InlineKeyboardButton("Current weather forecast").setCallbackData("today");
        InlineKeyboardButton tomorrow = new InlineKeyboardButton("Tomorrow weather forecast").setCallbackData("tomorrow");
        List<InlineKeyboardButton> keyboardRow = new ArrayList<>();
        keyboardRow.add(currentWeatherButton);
        keyboardRow.add(tomorrow);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardRow);
        keyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setReplyMarkup(keyboardMarkup).setText("What day would you like to get the weather forecast?").setChatId(chatId);
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
        switch (data) {
            case "location":
                return sendForRequestLocation(chatId);
            case "city":
                waitingForCity = true;
                return messageToUser.setChatId(chatId).setText("Insert city");
            case "previous":
                return sendButtonsForChoosingDay(chatId);
            case "new":
                return sendKeyBoardWithLocationInputChoice(chatId);
            default:
                User user = userService.findUserByChatId(chatId);
                Weather requestToOpenWeather = weatherFacade.createRequestToOpenWeather(user,data);
                setLocationIfNotExist(requestToOpenWeather,user);
                return messageToUser.setText(requestToOpenWeather.toString()).setChatId(user.getChatId());
            }
        }


    public SendMessage sendLocationQuestion() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton previous = new InlineKeyboardButton("Use previous location").setCallbackData("previous");
        InlineKeyboardButton newLocation = new InlineKeyboardButton("Send new location").setCallbackData("new");
        List<InlineKeyboardButton> keyboardRow = new ArrayList<>();
        keyboardRow.add(newLocation);
        keyboardRow.add(previous);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardRow);
        keyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setText("Which location would you like to use?").setReplyMarkup(keyboardMarkup);
    }
    public void setLocationIfNotExist(Weather weather,User user){
        if (user.getLocation() == null) {
            User.Location location = new User.Location(weather.getLat(), weather.getLon());
            user.setLocation(location);
            userService.update(user);
        }

    }

}

