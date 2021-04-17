package com.example.weatherbot.app.model.db_model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;

@Document
public class User {
    @Field
    private String userName;
    @Field
    private Location location;
    @Id
    private long chatId;
    @Field
    private String city;

    public static class Location {

        public Location(Float lat, Float lon) {
            this.lat = lat;
            this.lon = lon;
        }

        private Float lat;
        private Float lon;

        public Float getLat() {
            return lat;
        }

        public void setLat(Float lat) {
            this.lat = lat;
        }

        public Float getLon() {
            return lon;
        }

        public void setLon(Float lon) {
            this.lon = lon;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Location)) return false;
            Location location = (Location) o;
            return lat.equals(location.lat) && lon.equals(location.lon);
        }

        @Override
        public int hashCode() {
            return Objects.hash(lat, lon);
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

