package com.example.weatherbot.app.service;

import com.example.weatherbot.app.dto.weatherbitdto.WeatherBitInfo;
import com.example.weatherbot.app.dto.weatherbitdto.forecast.WeatherBitForecastDto;
import com.example.weatherbot.app.model.weather_model.WeatherBitModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class WeatherBitService {
    private final RestTemplate restTemplate;
    private final String apiTokenWeatherBit = "5bf862115d4d44f6a4112743d9dafa61";


    @Autowired
    public WeatherBitService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    /*
    current weather by city name from service "Weather bit"
     */
    public WeatherBitModel getCurrentWeatherFromWBByCity(String cityName) {
        String url = "https://api.weatherbit.io/v2.0/current?city=" + cityName
                + "&country=RU&key=" + apiTokenWeatherBit;
        WeatherBitInfo dto = restTemplate.getForObject(url, WeatherBitInfo.class);
        if (Objects.nonNull(dto)) {
            return new WeatherBitModel(dto.getCityName(), dto.getTemp(),
                    dto.getPressure(), dto.getHumidity(), dto.getFeelsLike(),
                    dto.getDesc().getDescription(), dto.getLat(), dto.getLon(),
                    dto.getWindSpeed(), dto.getWindDeg(), dto.getDateTime());
        } else throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"WeatherBitDto is null");
    }


    /*
        current weather by location name from service "Weather bit"
         */
    public WeatherBitModel getCurrentWeatherFromWBByLocation(Float lat, float lon) {
        String url = "https://api.weatherbit.io/v2.0/current?lat=" + lat + "&lon=" + lon
                + "&key=" + apiTokenWeatherBit;
        WeatherBitInfo dto = restTemplate.getForObject(url, WeatherBitInfo.class);
        if (Objects.nonNull(dto)) {
            return new WeatherBitModel(dto.getCityName(), dto.getTemp(),
                    dto.getPressure(), dto.getHumidity(), dto.getFeelsLike(),
                    dto.getDesc().getDescription(), dto.getLat(), dto.getLon(),
                    dto.getWindSpeed(), dto.getWindDeg(), dto.getDateTime());
        } else throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"WeatherBitDto is null");
    }


    /*
   forecast weather by city name from service "Weather bit"
    */
    public WeatherBitModel getForecastWeatherFromWBByCity(String cityName) {
        String url = "https://api.weatherbit.io/v2.0/forecast/daily?city=" + cityName
                + "&key=" + apiTokenWeatherBit;
        WeatherBitForecastDto dto = restTemplate.getForObject(url, WeatherBitForecastDto.class);
        if (Objects.nonNull(dto)) {
            WeatherBitInfo weatherBitInfo = searchForTimeStampWB(dto);
            return new WeatherBitModel(dto.getCityName(), weatherBitInfo.getTemp(),
                    weatherBitInfo.getPressure(), weatherBitInfo.getHumidity(), weatherBitInfo.getFeelsLike(),
                    weatherBitInfo.getDesc().getDescription(), dto.getLat(), dto.getLon(),
                    weatherBitInfo.getWindSpeed(), weatherBitInfo.getWindDeg(), weatherBitInfo.getDateTime());
        } else throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"WeatherBitDto is null");
    }

    /*
    forecast weather by location from service "Weather Bit"
     */
    public WeatherBitModel getForecastWeatherFromWBByLocation(Float lat, float lon) {
        String url = "https://api.weatherbit.io/v2.0/forecast/daily?&lat=" + lat + "&lon=" + lon
                + "&key=" + apiTokenWeatherBit;
        WeatherBitForecastDto dto = restTemplate.getForObject(url, WeatherBitForecastDto.class);
        if (Objects.nonNull(dto)) {
            WeatherBitInfo weatherBitInfo = searchForTimeStampWB(dto);
            return new WeatherBitModel(dto.getCityName(), weatherBitInfo.getTemp(),
                    weatherBitInfo.getPressure(), weatherBitInfo.getHumidity(), weatherBitInfo.getFeelsLike(),
                    weatherBitInfo.getDesc().getDescription(), dto.getLat(), dto.getLon(),
                    weatherBitInfo.getWindSpeed(), weatherBitInfo.getWindDeg(), weatherBitInfo.getDateTime());
        } else throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"WeatherBitDto is null");
    }

    public WeatherBitInfo searchForTimeStampWB(WeatherBitForecastDto dto) {
        Optional<WeatherBitInfo> forecast = dto.getMainInfo().stream()
                .filter(weather -> weather.getDateTime().toString().equals(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(15,0)).toString()))
                .findAny();
        return forecast.orElse(null);
    }
}