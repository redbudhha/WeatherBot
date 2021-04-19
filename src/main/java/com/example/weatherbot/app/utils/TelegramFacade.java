package com.example.weatherbot.app.utils;

import com.example.weatherbot.app.model.Weather;
import com.example.weatherbot.app.model.db_model.User;
import com.example.weatherbot.app.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
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
    private final WeatherBot weatherBot;


    public TelegramFacade(UserService userService, WeatherFacade weatherFacade, WeatherBot weatherBot) {
        this.userService = userService;
        this.weatherFacade = weatherFacade;
        this.weatherBot = weatherBot;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        SendMessage messageToUser = new SendMessage();
        if (update.hasMessage()) {
            Long chatId = update.getMessage().getChatId();
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
                        } else {
                            messageToUser = sendLocationQuestion();
                        }
                    } else {
                        messageToUser = sendKeyBoardWithLocationInputChoice(update.getMessage().getChatId());
                    }
                } else if (waitingForCity) {
                    userService.createUser(update);
                    waitingForCity = false;
                    messageToUser = sendButtonsForChoosingDay(update.getMessage().getChatId());
                } else if (text.equalsIgnoreCase("/help")) {
                    messageToUser.setText("This bot allows you to get weather forecast for today or tomorrow.\nTo continue send /start.");
                } else if (text.equalsIgnoreCase("/cancel")) {
                    boolean result = userService.findSubscriberAndDeleteByChatId(update.getMessage().getChatId());
                    if (result) {
                        return new SendMessage().setText("Your subscription were canceled.\nTo continue send /start").setChatId(chatId);
                    } else {
                        return new SendMessage().setText("To continue send /start").setChatId(chatId);
                    }
                }
            }
            if (!update.getMessage().hasText() && update.getMessage().hasLocation()) {
                User user = userService.findUserByChatId(update.getMessage().getChatId());
                if (Objects.nonNull(user)) {
                    if (update.getMessage().hasLocation()) {
                        Location location = update.getMessage().getLocation();
                        user.setLocation(new User.Location(location.getLatitude(), location.getLongitude()));
                        userService.update(user);
                    }
                } else {
                    userService.createUser(update);
                }
                messageToUser = sendButtonsForChoosingDay(chatId);
            }
        }
        if (update.hasCallbackQuery()) {
            return processCallBackQuery(update.getCallbackQuery());
        }
        // если ни одно условие не подошло
        if (messageToUser.getText() == null) {
            messageToUser.setText("This command isn't supported. " +
                    "If you want to find out the current weather or forecast, please, type the command /start");

        }
        return messageToUser.setChatId(update.getMessage().getChatId());
    }

    public SendMessage sendKeyBoardWithLocationInputChoice(long chatId) {
        InlineKeyboardButton location = new InlineKeyboardButton()
                .setText("Send geolocation")
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
        InlineKeyboardButton currentWeatherButton = new InlineKeyboardButton("Current weather").setCallbackData("today");
        InlineKeyboardButton tomorrow = new InlineKeyboardButton("Tomorrow weather").setCallbackData("tomorrow");
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
                .setText("Send geolocation"));
        List<KeyboardRow> rows = new ArrayList<>(Collections.singletonList(keyboardRow));
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup().setKeyboard(rows).setOneTimeKeyboard(true).setResizeKeyboard(true);
        return new SendMessage().setChatId(chatId).setReplyMarkup(markup).setText("For sending location click 'Send geolocation'");
    }

    public SendMessage processCallBackQuery(CallbackQuery query) {
        String data = query.getData();
        Long chatId = query.getMessage().getChatId();
        switch (data) {
            case "location": {
                return sendForRequestLocation(chatId);
            }
            case "city": {
                waitingForCity = true;
                return new SendMessage().setChatId(chatId).setText("Insert city");
            }
            case "previous": {
                return sendButtonsForChoosingDay(chatId);
            }
            case "new": {
                return sendKeyBoardWithLocationInputChoice(chatId);
            }
            case "Yes": {
                User userByChatId = userService.findUserByChatId(chatId);
                userService.saveSubscriber(userByChatId);
                return new SendMessage().setChatId(chatId).setText("Thanks for subscription! " +
                        "You will get weather forecast every morning at 8:05." +
                        "\nTo refuse send /cancel" +
                        "\nFor getting weather send /start");
            }
            case "no": {
                return new SendMessage().setChatId(chatId).setText("For getting weather send /start");
            }

            default:
                User user = userService.findUserByChatId(chatId);
                try {
                    Weather requestToOpenWeather = weatherFacade.createRequestToThreeServices(data, user);
                    setLocationOrCityIfNotExists(requestToOpenWeather, user);
                    if (Objects.nonNull(userService.findSubscriberById(user.getChatId()))){
                        return new SendMessage().setText(requestToOpenWeather.toString() + "\nTo continue send /start").setChatId(user.getChatId());
                    } else {
                        return new SendMessage().setText(requestToOpenWeather.toString() + "\n\nWould you like to get weather forecast every morning by subscription?")
                                .setChatId(user.getChatId()).setReplyMarkup(offerSubscription());
                    }
                } catch (HttpClientErrorException e) {
                    waitingForCity = true;
                    return new SendMessage().setText("Incorrect city name. Please try again").setChatId(chatId);
                }

        }
    }

    public SendMessage sendLocationQuestion() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton previous = new InlineKeyboardButton("Use previous geolocation").setCallbackData("previous");
        InlineKeyboardButton newLocation = new InlineKeyboardButton("Send new geolocation").setCallbackData("new");
        List<InlineKeyboardButton> keyboardRow = new ArrayList<>();
        keyboardRow.add(newLocation);
        keyboardRow.add(previous);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardRow);
        keyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setText("Which location would you like to use?").setReplyMarkup(keyboardMarkup);
    }

    public void setLocationOrCityIfNotExists(Weather weather, User user) {
        if (user.getLocation() == null) {
            User.Location location = new User.Location(weather.getLat(), weather.getLon());
            user.setLocation(location);
        }
        user.setCity(weather.getCityName());
        userService.update(user);

    }

    //@Scheduled(cron = "0 5 8/24 * *")
    // @Scheduled(fixedRate = 300000)
    //Ежедневно в 8 утра
    private void sendCurrentWeatherToSubscribers() {
        List<User> subscribers = userService.findSubscribers();
        Map<String, Weather> weather = new HashMap<>();
        SendMessage weatherForUser = new SendMessage();
        if (Objects.nonNull(subscribers)) {
            for (User user : subscribers) {
                try {
                    if (weather.containsKey(user.getCity())) {
                        weatherBot.executeMethod(weatherForUser.setChatId(user.getChatId()).setText(weather.get(user.getCity()).toString()));
                    } else {
                        Weather todayWeather = weatherFacade.createRequestToThreeServices("today", user);
                        weather.put(todayWeather.getCityName(), todayWeather);
                        weatherBot.executeMethod(weatherForUser.setChatId(user.getChatId()).setText(todayWeather.toString()));
                    }
                } catch (HttpClientErrorException e) {
                    weatherBot.executeMethod(new SendMessage().setChatId(user.getChatId()).setText("Incorrect city name"));
                }
            }
        }
    }

    public InlineKeyboardMarkup offerSubscription() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton yes = new InlineKeyboardButton("Yes").setCallbackData("Yes");
        InlineKeyboardButton no = new InlineKeyboardButton(" I haven't decided yet").setCallbackData("No");
        List<InlineKeyboardButton> keyboardRow = new ArrayList<>();
        keyboardRow.add(yes);
        keyboardRow.add(no);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardRow);
        keyboardMarkup.setKeyboard(rowList);
        return keyboardMarkup;
    }

}

