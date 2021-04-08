package model;

import org.telegram.telegrambots.meta.api.objects.Location;

public class User {
    private String userName;
    private Location location;
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

    public long getChatID() {
        return chatId;
    }

    public void setChatId(long chat_id) {
        this.chatId = chat_id;
    }
}
