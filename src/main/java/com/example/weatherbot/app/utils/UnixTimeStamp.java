package com.example.weatherbot.app.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

public class UnixTimeStamp extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Long timestamp = null;
        try {
            timestamp = Long.valueOf(p.getText().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Data Format" + timestamp + e.getMessage());
        }
        if (Objects.nonNull(timestamp)) {
            return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneOffset.UTC);
        }
        return null;

    }
}
