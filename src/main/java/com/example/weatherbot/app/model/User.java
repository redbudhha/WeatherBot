package com.example.weatherbot.app.model;

import org.telegram.telegrambots.meta.api.objects.Location;

//@Document
public class User {
   // @Field
    private String userName;
   // @Field
    private Location location;
   // @Id
    private long chatId;

    public User(String userName, Location location, long chatId) {
        this.userName = userName;
        this.location = location;
        this.chatId = chatId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatIid(long chatId) {
        this.chatId = chatId;
    }
}
