package weatherbot.bot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


import com.example.weatherbot.app.bot.WeatherBot;
import org.junit.Test;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.objects.Update;

public class WeatherBotTest {
    @Test
    public void constructorTest() {
        assertEquals("https://api.telegram.org/botnull/", (new WeatherBot(new DefaultBotOptions())).getBaseUrl());
    }


    @Test
    public void onWebhookUpdateReceivedTest() {
        WeatherBot weatherBot = new WeatherBot(new DefaultBotOptions());
        assertNull(weatherBot.onWebhookUpdateReceived(new Update()));
    }

    @Test
    public void setWebHookPathTest() {
        WeatherBot weatherBot = new WeatherBot(new DefaultBotOptions());
        weatherBot.setWebHookPath("Web Hook Path");
        assertEquals("Web Hook Path", weatherBot.getBotPath());
    }

    @Test
    public void setBotUserNameTest() {
        WeatherBot weatherBot = new WeatherBot(new DefaultBotOptions());
        weatherBot.setBotUserName("janedoe");
        assertEquals("janedoe", weatherBot.getBotUsername());
    }

    @Test
    public void setBotTokenTest() {
        WeatherBot weatherBot = new WeatherBot(new DefaultBotOptions());
        weatherBot.setBotToken("ABC123");
        assertEquals("ABC123", weatherBot.getBotToken());
    }


}