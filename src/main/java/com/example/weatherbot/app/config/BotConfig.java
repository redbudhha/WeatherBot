package com.example.weatherbot.app.config;


import com.example.weatherbot.app.bot.WeatherBot;
import com.example.weatherbot.app.utils.TelegramUtil;
import com.mongodb.ClientSessionOptions;
import com.mongodb.client.*;
import com.mongodb.connection.ClusterDescription;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;

import java.net.UnknownHostException;
import java.util.List;


@Configuration
@ConfigurationProperties(prefix = "telegrambot")
@EnableMongoRepositories
@ComponentScan
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
    @Bean
    public MongoClient mongoClient() throws UnknownHostException {
        return MongoClients.create();
    }
    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        return new MongoTemplate(mongoClient(), "db_users");
    }

}
