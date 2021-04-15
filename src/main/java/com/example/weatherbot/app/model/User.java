package com.example.weatherbot.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
    // @Field
    private String userName;
    // @Field
    private Location location;
    @Id
    private long chatId;
    //@Field
    private String city;

    public static class Location {

        public Location(Double lat, Double lon) {
            this.lat = lat;
            this.lon = lon;
        }

        private Double lat;
        private Double lon;

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLon() {
            return lon;
        }

        public void setLon(Double lon) {
            this.lon = lon;
        }

        @Override
        public String toString() {
            return "Location{" +
                    "lat=" + lat +
                    ", lon=" + lon +
                    '}';
        }
    }
    public User(){}
    public User(String userName, Location location, long chatId) {
        this.userName = userName;
        this.location = location;
        this.chatId = chatId;
    }

    public User(String userName, String city, long chatId) {
        this.userName = userName;
        this.city = city;
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

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", location=" + location +
                ", chatId=" + chatId +
                ", city='" + city + '\'' +
                '}';
    }
}

