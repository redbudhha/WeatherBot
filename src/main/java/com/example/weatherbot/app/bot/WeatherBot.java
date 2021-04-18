package com.example.weatherbot.app.bot;

import com.example.weatherbot.app.utils.TelegramFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;


public class WeatherBot extends TelegramWebhookBot {
    private String webHookPath;
    private String botUserName;
    private String botToken;


    @Autowired
    private RestTemplate restTemplate;

    public WeatherBot(DefaultBotOptions botOptions) {
        super(botOptions);
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotPath() {
        return webHookPath;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return null;
    }

    public void setWebHookPath(String webHookPath) {
        this.webHookPath = webHookPath;
    }

    public void setBotUserName(String botUserName) {
        this.botUserName = botUserName;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public void executeMethod(BotApiMethod<?> method) {
        try {
            execute(method);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void setNewWebhookForTelegram() {
        try {
            restTemplate.getForObject(new URI(TelegramFacade.RESOURCE + botToken + "/setWebhook?url=" + webHookPath),String.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}

