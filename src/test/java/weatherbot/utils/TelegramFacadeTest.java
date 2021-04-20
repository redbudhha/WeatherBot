package weatherbot.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.example.weatherbot.app.bot.WeatherBot;
import com.example.weatherbot.app.model.Weather;
import com.example.weatherbot.app.model.db_model.User;
import com.example.weatherbot.app.service.OpenWeatherService;
import com.example.weatherbot.app.service.UserService;
import com.example.weatherbot.app.service.WeatherApiService;
import com.example.weatherbot.app.service.WeatherBitService;
import com.example.weatherbot.app.service.WeatherService;

import java.time.LocalDate;

import java.util.List;

import com.example.weatherbot.app.utils.TelegramFacade;
import com.example.weatherbot.app.utils.WeatherFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

@ContextConfiguration(classes = {WeatherFacade.class, WeatherBot.class, TelegramFacade.class, UserService.class})
@RunWith(SpringRunner.class)
public class TelegramFacadeTest {
    @Autowired
    private TelegramFacade telegramFacade;

    @MockBean
    private UserService userService;

    @MockBean
    private WeatherBot weatherBot;

    @MockBean
    private WeatherFacade weatherFacade;

    @Test
    public void sendKeyBoardWithLocationInputChoiceTest() {
        SendMessage actualSendKeyBoardWithLocationInputChoiceResult = this.telegramFacade
                .sendKeyBoardWithLocationInputChoice(123L);
        assertEquals("123", actualSendKeyBoardWithLocationInputChoiceResult.getChatId());
        assertEquals("SendMessage{chatId='123', text='How would you like to send location data?', parseMode='null',"
                + " disableNotification='null', disableWebPagePreview=null, replyToMessageId=null, replyMarkup"
                + "=InlineKeyboardMarkup{inline_keyboard=[[InlineKeyboardButton{text='Send geolocation', url='null',"
                + " callbackData='location', callbackGame=null, switchInlineQuery='null', switchInlineQueryCurrentChat='null',"
                + " pay=null, loginUrl=null}, InlineKeyboardButton{text='Send city name', url='null', callbackData='city',"
                + " callbackGame=null, switchInlineQuery='null', switchInlineQueryCurrentChat='null', pay=null,"
                + " loginUrl=null}]]}}", actualSendKeyBoardWithLocationInputChoiceResult.toString());
        assertEquals("How would you like to send location data?",
                actualSendKeyBoardWithLocationInputChoiceResult.getText());
        ReplyKeyboard replyMarkup = actualSendKeyBoardWithLocationInputChoiceResult.getReplyMarkup();
        assertEquals("InlineKeyboardMarkup{inline_keyboard=[[InlineKeyboardButton{text='Send geolocation', url='null',"
                + " callbackData='location', callbackGame=null, switchInlineQuery='null', switchInlineQueryCurrentChat='null',"
                + " pay=null, loginUrl=null}, InlineKeyboardButton{text='Send city name', url='null', callbackData='city',"
                + " callbackGame=null, switchInlineQuery='null', switchInlineQueryCurrentChat='null', pay=null,"
                + " loginUrl=null}]]}", replyMarkup.toString());
        List<List<InlineKeyboardButton>> keyboard = ((InlineKeyboardMarkup) replyMarkup).getKeyboard();
        assertEquals(1, keyboard.size());
        assertEquals(2, keyboard.get(0).size());
    }

    @Test
    public void sendButtonsForChoosingDayTest() {
        SendMessage actualSendButtonsForChoosingDayResult = this.telegramFacade.sendButtonsForChoosingDay(123L);
        assertEquals("123", actualSendButtonsForChoosingDayResult.getChatId());
        assertEquals(
                "SendMessage{chatId='123', text='What day would you like to get the weather forecast?', parseMode='null',"
                        + " disableNotification='null', disableWebPagePreview=null, replyToMessageId=null, replyMarkup"
                        + "=InlineKeyboardMarkup{inline_keyboard=[[InlineKeyboardButton{text='Current weather', url='null',"
                        + " callbackData='today', callbackGame=null, switchInlineQuery='null', switchInlineQueryCurrentChat='null',"
                        + " pay=null, loginUrl=null}, InlineKeyboardButton{text='Tomorrow weather', url='null', callbackData='tomorrow',"
                        + " callbackGame=null, switchInlineQuery='null', switchInlineQueryCurrentChat='null', pay=null,"
                        + " loginUrl=null}]]}}",
                actualSendButtonsForChoosingDayResult.toString());
        assertEquals("What day would you like to get the weather forecast?",
                actualSendButtonsForChoosingDayResult.getText());
        ReplyKeyboard replyMarkup = actualSendButtonsForChoosingDayResult.getReplyMarkup();
        assertEquals("InlineKeyboardMarkup{inline_keyboard=[[InlineKeyboardButton{text='Current weather', url='null',"
                + " callbackData='today', callbackGame=null, switchInlineQuery='null', switchInlineQueryCurrentChat='null',"
                + " pay=null, loginUrl=null}, InlineKeyboardButton{text='Tomorrow weather', url='null', callbackData='tomorrow',"
                + " callbackGame=null, switchInlineQuery='null', switchInlineQueryCurrentChat='null', pay=null,"
                + " loginUrl=null}]]}", replyMarkup.toString());
        List<List<InlineKeyboardButton>> keyboard = ((InlineKeyboardMarkup) replyMarkup).getKeyboard();
        assertEquals(1, keyboard.size());
        assertEquals(2, keyboard.get(0).size());
    }

    @Test
    public void sendForRequestLocationTest() {
        SendMessage actualSendForRequestLocationResult = this.telegramFacade.sendForRequestLocation(123L);
        assertEquals("123", actualSendForRequestLocationResult.getChatId());
        assertEquals("SendMessage{chatId='123', text='For sending location click 'Send geolocation'', parseMode='null',"
                        + " disableNotification='null', disableWebPagePreview=null, replyToMessageId=null, replyMarkup"
                        + "=ReplyKeyboardMarkup{keyboard=[[KeyboardButton{text=Send geolocation, requestContact=null, requestLocation"
                        + "=true}]], resizeKeyboard=true, oneTimeKeyboard=true, selective=null}}",
                actualSendForRequestLocationResult.toString());
        assertEquals("For sending location click 'Send geolocation'", actualSendForRequestLocationResult.getText());
        ReplyKeyboard replyMarkup = actualSendForRequestLocationResult.getReplyMarkup();
        assertTrue(((ReplyKeyboardMarkup) replyMarkup).getResizeKeyboard());
        assertTrue(((ReplyKeyboardMarkup) replyMarkup).getOneTimeKeyboard());
        assertEquals(
                "ReplyKeyboardMarkup{keyboard=[[KeyboardButton{text=Send geolocation, requestContact=null, requestLocation"
                        + "=true}]], resizeKeyboard=true, oneTimeKeyboard=true, selective=null}",
                replyMarkup.toString());
        KeyboardButton getResult = ((ReplyKeyboardMarkup) replyMarkup).getKeyboard().get(0).get(0);
        assertEquals("Send geolocation", getResult.getText());
        assertTrue(getResult.getRequestLocation());
    }

    @Test
    public void sendLocationQuestionTest() {
        SendMessage actualSendLocationQuestionResult = this.telegramFacade.sendLocationQuestion();
        assertEquals("SendMessage{chatId='null', text='Which location would you like to use?', parseMode='null', disableNo"
                + "tification='null', disableWebPagePreview=null, replyToMessageId=null, replyMarkup=InlineKeyboardMarkup"
                + "{inline_keyboard=[[InlineKeyboardButton{text='Send new geolocation', url='null', callbackData='new',"
                + " callbackGame=null, switchInlineQuery='null', switchInlineQueryCurrentChat='null', pay=null, loginUrl=null},"
                + " InlineKeyboardButton{text='Use previous geolocation', url='null', callbackData='previous',"
                + " callbackGame=null, switchInlineQuery='null', switchInlineQueryCurrentChat='null', pay=null,"
                + " loginUrl=null}]]}}", actualSendLocationQuestionResult.toString());
        assertEquals("Which location would you like to use?", actualSendLocationQuestionResult.getText());
        ReplyKeyboard replyMarkup = actualSendLocationQuestionResult.getReplyMarkup();
        assertEquals("InlineKeyboardMarkup{inline_keyboard=[[InlineKeyboardButton{text='Send new geolocation', url='null',"
                + " callbackData='new', callbackGame=null, switchInlineQuery='null', switchInlineQueryCurrentChat='null',"
                + " pay=null, loginUrl=null}, InlineKeyboardButton{text='Use previous geolocation', url='null',"
                + " callbackData='previous', callbackGame=null, switchInlineQuery='null', switchInlineQueryCurrentChat='null',"
                + " pay=null, loginUrl=null}]]}", replyMarkup.toString());
        List<List<InlineKeyboardButton>> keyboard = ((InlineKeyboardMarkup) replyMarkup).getKeyboard();
        assertEquals(1, keyboard.size());
        assertEquals(2, keyboard.get(0).size());
    }

    @Test
    public void setLocationOrCityIfNotExistsTest() {
        UserService userService = mock(UserService.class);
        doNothing().when(userService).update((User) any());
        OpenWeatherService openWeatherService = new OpenWeatherService(new RestTemplate());
        WeatherApiService weatherApiService = new WeatherApiService(new RestTemplate());
        WeatherBitService weatherBitService = new WeatherBitService(new RestTemplate());
        WeatherService weatherService = new WeatherService();
        WeatherFacade weatherFacade = new WeatherFacade(openWeatherService, weatherApiService, weatherBitService,
                weatherService, new UserService());
        TelegramFacade telegramFacade = new TelegramFacade(userService, weatherFacade,
                new WeatherBot(new DefaultBotOptions()));
        Weather weather = new Weather("Saint-Petersburg", 10.0, 1, 1, 10.0, 10.0, "Condition", 10.0f, 10.0f,
                LocalDate.ofEpochDay(1L));
        User user = new User("Egor Matveev", "Saint-Petersburg", 123L);
        telegramFacade.setLocationOrCityIfNotExists(weather, user);
        verify(userService).update((User) any());
        assertEquals("User{userName='Egor Matveev', location=Location{lat=10.0, lon=10.0}, chatId=123, city='Saint-Petersburg'}",
                user.toString());
        assertEquals("Saint-Petersburg", user.getCity());
        User.Location location = user.getLocation();
        assertEquals(10.0f, location.getLon(), 0.0f);
        assertEquals(10.0f, location.getLat(), 0.0f);
    }

    @Test
    public void offerSubscriptionTest() {
        InlineKeyboardMarkup actualOfferSubscriptionResult = this.telegramFacade.offerSubscription();
        assertEquals(
                "InlineKeyboardMarkup{inline_keyboard=[[InlineKeyboardButton{text='Yes', url='null', callbackData='Yes',"
                        + " callbackGame=null, switchInlineQuery='null', switchInlineQueryCurrentChat='null', pay=null,"
                        + " loginUrl=null}, InlineKeyboardButton{text=' I haven't decided yet', url='null', callbackData='No',"
                        + " callbackGame=null, switchInlineQuery='null', switchInlineQueryCurrentChat='null', pay=null,"
                        + " loginUrl=null}]]}",
                actualOfferSubscriptionResult.toString());
        List<List<InlineKeyboardButton>> keyboard = actualOfferSubscriptionResult.getKeyboard();
        assertEquals(1, keyboard.size());
        assertEquals(2, keyboard.get(0).size());
    }
}

