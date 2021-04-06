package model;

import org.telegram.telegrambots.meta.api.objects.Location;

public class User {
    private String userName;
    private Location location;
    private long chat_id;

    public User(String userName, Location location, long chat_id) {
        this.userName = userName;
        this.location = location;
        this.chat_id = chat_id;
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

    public long getChat_id() {
        return chat_id;
    }

    public void setChat_id(long chat_id) {
        this.chat_id = chat_id;
    }
}
