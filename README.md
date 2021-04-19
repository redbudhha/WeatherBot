#Weatherbot
Telegram weather bot. Provides information about the current weather and forecast for tomorrow.

**Java 8 + Spring Boot + JUnit4 + Lombok + Gradle**

Uses Telegram Bot Api and services Open Weather, Weather Api and Weather Bit.

Bot features:
Since the weather data may differ from service to service, our service calculates the average of three popular APIs for each metric. Thus, the user gets the forecast as close as possible to the truth;
Our bot stores geolocation data. Thanks to this, the user does not need to enter his city every time;
Subscription implementation. Thanks to our bot, the user can automatically receive a forecast for tomorrow.

For start:
Register a bot with BotFather: https://core.telegram.org/bots

Write the received token and username as telegram.bot.token and telegram.bot.username in the main / resources / application.properties file or set as application launch parameters

Register for services Open Weather, Weather Api and Weather Bit and get keys.

Run WeatherbotApplication.java

