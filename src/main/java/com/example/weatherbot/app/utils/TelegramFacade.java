package com.example.weatherbot.app.utils;

import com.example.weatherbot.app.dto.ForecastDto;
import com.example.weatherbot.app.dto.WeatherDto;
import com.example.weatherbot.app.model.Forecast;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class TelegramFacade {
    public static final String RESOURCE = "https://api.telegram.org/bot";
    private final UserService userService;
    private final WeatherService weatherService;
    private boolean waitingForCity = false;
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
                    User user = userService.findUserByChatId(update.getMessage().getChatId());
                    if (Objects.nonNull(user)) {
                        messageToUser = sendLocationQuestion();
                    } else {
                        messageToUser = sendKeyBoardWithLocationInputChoice(update.getMessage().getChatId());
                    }
                } else if (text.equalsIgnoreCase("/help")) {
                    messageToUser.setText("This bot allows you to get weather forecast for today or tomorrow.\nTo continue send /start.");
                }
            }
            if (!update.getMessage().hasText() && update.getMessage().hasLocation()) {
                User user = userService.findUserByChatId(update.getMessage().getChatId());
                if (Objects.nonNull(user)) {
                    if (update.getMessage().hasLocation()) {
                        Location location = update.getMessage().getLocation();
                        user.setLocation(new User.Location(location.getLatitude(), location.getLongitude()));

                    }
                    if (waitingForCity) {
                        user.setCity(update.getMessage().getText());
                        waitingForCity = false;
                    }
                    //необходимо занести измения в базу у пользователя
                } else {
                    user = userService.createUser(update);
                }
                messageToUser = sendButtonsForChoosingDay(user.getChatId());
            }
            messageToUser.setChatId(update.getMessage().getChatId());
        }
        if (update.hasCallbackQuery()) {
            return processCallBackQuery(update.getCallbackQuery());
        }
        // если ни одно условие не подошло
        if (messageToUser.getText() == null) {
            messageToUser.setText("This command isn't supported.");
            messageToUser.setChatId(update.getMessage().getChatId());
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
                return createRequestToWeatherApi(data, user);
            } else {
                throw new NullPointerException("User is not found!");
            }
        }
    }

    public SendMessage createRequestToWeatherApi(String data, User user) {
        User.Location location = user.getLocation();
        WeatherDto weatherDto = null;
        if (location == null) {
            weatherDto = weatherService.getCurrentWeatherFromOWByCity(user.getCity());
            Weather weather = new Weather(weatherDto);
            //weatherService.save(weather);
            location = new User.Location(weatherDto.getLat(), weatherDto.getLon());
            user.setLocation(location);
        }
        SendMessage messageToUserWithWeatherForecast = new SendMessage();
        System.out.println(user);
        if (data.equals("today")) {
            if (Objects.isNull(weatherDto)) {
                weatherDto = weatherService.getCurrentWeatherFromOWByLocation(user.getLocation().getLat(), user.getLocation().getLon());
            }
            Weather weather = new Weather(weatherDto);
            //weatherService.save(weather);

            return messageToUserWithWeatherForecast.setText(weather.toString()).setChatId(user.getChatId());
        } else if (data.equals("tomorrow")) {
            ForecastDto forecastDto = weatherService.getForecastWeatherFromOWByLocation(user.getLocation().getLat(), user.getLocation().getLon());
            if (Objects.nonNull(forecastDto)) {
                Forecast forecast = new Forecast(forecastDto);
                return messageToUserWithWeatherForecast.setText(forecast.toString()).setChatId(user.getChatId());
            }

        }
        return messageToUserWithWeatherForecast.setChatId(user.getChatId()).setText("Something goes wrong");
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

}

