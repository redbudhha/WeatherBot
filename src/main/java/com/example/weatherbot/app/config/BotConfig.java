package com.example.weatherbot.app.config;


import com.example.weatherbot.app.bot.WeatherBot;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;


@Configuration
@ConfigurationProperties(prefix = "bot")
//@EnableMongoRepositories
@ComponentScan
public class BotConfig {
    private String webHookPath;
    private String name;
    private String token;


    public String getWebHookPath() {
        return webHookPath;
    }

    public void setWebHookPath(String webHookPath) {
        this.webHookPath = webHookPath;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Bean
    public WeatherBot weatherBot(){
        DefaultBotOptions options = ApiContext.getInstance(DefaultBotOptions.class);
        WeatherBot weatherBot = new WeatherBot(options);
        weatherBot.setBotToken(token);
        weatherBot.setBotUserName(name);
        weatherBot.setWebHookPath(webHookPath);
        return weatherBot;
    }
    /*
    @Bean
    public MongoClient mongoClient() throws UnknownHostException {
        return MongoClients.create();
    }
    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        return new MongoTemplate(mongoClient(), "db_users");
    }

     */


}
