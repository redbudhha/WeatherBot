package com.example.weatherbot.app.config;


import com.example.weatherbot.app.bot.WeatherBot;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;



@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {
    private String webHookPath;
    private String botUserName;
    private String botToken;


    public String getWebHookPath() {
        return webHookPath;
    }

    public void setWebHookPath(String webHookPath) {
        this.webHookPath = webHookPath;
    }

    public String getBotUserName() {
        return botUserName;
    }

    public void setBotUserName(String botUserName) {
        this.botUserName = botUserName;
    }

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }
    @Bean
    public WeatherBot weatherBot(){
        DefaultBotOptions options = ApiContext.getInstance(DefaultBotOptions.class);
        WeatherBot weatherBot = new WeatherBot(options);
        weatherBot.setBotToken(botToken);
        weatherBot.setBotUserName(botUserName);
        weatherBot.setWebHookPath(webHookPath);
        return weatherBot;
    }
}
