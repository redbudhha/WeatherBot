package weatherbot.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.weatherbot.app.dto.openweatherdto.CityCoord;
import com.example.weatherbot.app.dto.openweatherdto.OpenWeather;
import com.example.weatherbot.app.dto.openweatherdto.WeatherMetrics;
import com.example.weatherbot.app.dto.openweatherdto.Wind;
import com.example.weatherbot.app.dto.openweatherdto.current.OpenWeatherCurrentDto;
import com.example.weatherbot.app.model.weather_model.OpenWeatherModel;

import java.time.LocalDateTime;

import java.util.ArrayList;

import com.example.weatherbot.app.service.OpenWeatherService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@ContextConfiguration(classes = {RestTemplate.class, OpenWeatherService.class})
@RunWith(SpringRunner.class)
public class OpenWeatherServiceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private OpenWeatherService openWeatherService;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void getCurrentByCityTest() throws RestClientException {
        when(this.restTemplate.getForObject(anyString(), any(), (Object[]) any()))
                .thenThrow(new HttpClientErrorException(HttpStatus.CONTINUE));
        thrown.expect(HttpClientErrorException.class);
        this.openWeatherService.getCurrentByCity("Saint-Petersburg");
        verify(this.restTemplate).getForObject(anyString(), any(), (Object[]) any());
    }


    @Test
    public void getCurrentByCityTestDetailed() throws RestClientException {
        ArrayList<OpenWeather> openWeatherList = new ArrayList<OpenWeather>();
        openWeatherList.add(new OpenWeather());
        OpenWeatherCurrentDto openWeatherCurrentDto = mock(OpenWeatherCurrentDto.class);
        when(openWeatherCurrentDto.getDateTime()).thenReturn(LocalDateTime.of(5, 7,
                12, 6, 1));
        when(openWeatherCurrentDto.getWind()).thenReturn(new Wind());
        when(openWeatherCurrentDto.getCoordinate()).thenReturn(new CityCoord());
        when(openWeatherCurrentDto.getWeather()).thenReturn(openWeatherList);
        when(openWeatherCurrentDto.getMain()).thenReturn(new WeatherMetrics());
        when(openWeatherCurrentDto.getName()).thenReturn("Saint-Petersburg");
        when(this.restTemplate.getForObject(anyString(), any(), (Object[]) any()))
                .thenReturn(openWeatherCurrentDto);
        OpenWeatherModel actualCurrentByCity = this.openWeatherService.getCurrentByCity("Saint-Petersburg");
        assertEquals("Saint-Petersburg", actualCurrentByCity.getCityName());
        assertEquals(
                "OpenWeatherModel(cityName=Saint-Petersburg, temp=null, pressure=null, humidity=null, " +
                        "feelsLike=null, condition=null," + " lat=null, lon=null, " +
                        "windSpeed=null, windDeg=null, dateTime=0005-07-12T06:01)",
                actualCurrentByCity.toString());
        assertNull(actualCurrentByCity.getWindSpeed());
        assertNull(actualCurrentByCity.getWindDeg());
        assertNull(actualCurrentByCity.getTemp());
        assertNull(actualCurrentByCity.getPressure());
        assertNull(actualCurrentByCity.getLon());
        assertNull(actualCurrentByCity.getLat());
        assertNull(actualCurrentByCity.getHumidity());
        assertNull(actualCurrentByCity.getFeelsLike());
        assertNull(actualCurrentByCity.getCondition());
        verify(openWeatherCurrentDto, times(2)).getCoordinate();
        verify(openWeatherCurrentDto).getName();
        verify(openWeatherCurrentDto).getWeather();
        verify(openWeatherCurrentDto).getDateTime();
        verify(openWeatherCurrentDto, times(2)).getWind();
        verify(openWeatherCurrentDto, times(4)).getMain();
        verify(this.restTemplate).getForObject(anyString(), any(), (Object[]) any());
    }

    @Test
    public void getCurrentWeatherFromOWByLocationTest() throws RestClientException {
        when(this.restTemplate.getForObject(anyString(), any(), (Object[]) any()))
                .thenThrow(new HttpClientErrorException(HttpStatus.CONTINUE));
        thrown.expect(HttpClientErrorException.class);
        this.openWeatherService.getCurrentWeatherFromOWByLocation(60.0f, 30.0f);
        verify(this.restTemplate).getForObject(anyString(), any(), (Object[]) any());
    }


    @Test
    public void getCurrentWeatherFromOWByLocationTestDetailed() throws RestClientException {
        ArrayList<OpenWeather> openWeatherList = new ArrayList<OpenWeather>();
        openWeatherList.add(new OpenWeather());
        OpenWeatherCurrentDto openWeatherCurrentDto = mock(OpenWeatherCurrentDto.class);
        when(openWeatherCurrentDto.getDateTime()).thenReturn(LocalDateTime.of(5, 7, 12,
                6, 1));
        when(openWeatherCurrentDto.getWind()).thenReturn(new Wind());
        when(openWeatherCurrentDto.getCoordinate()).thenReturn(new CityCoord());
        when(openWeatherCurrentDto.getWeather()).thenReturn(openWeatherList);
        when(openWeatherCurrentDto.getMain()).thenReturn(new WeatherMetrics());
        when(openWeatherCurrentDto.getName()).thenReturn("Saint-Petersburg");
        when(this.restTemplate.getForObject(anyString(), any(), (Object[]) any()))
                .thenReturn(openWeatherCurrentDto);
        OpenWeatherModel actualCurrentWeatherFromOWByLocation = this.openWeatherService
                .getCurrentWeatherFromOWByLocation(10.0f, 10.0f);
        assertEquals("Saint-Petersburg", actualCurrentWeatherFromOWByLocation.getCityName());
        assertEquals(
                "OpenWeatherModel(cityName=Saint-Petersburg, temp=null, pressure=null, humidity=null," +
                        " feelsLike=null, condition=null," + " lat=null, lon=null, " +
                        "windSpeed=null, windDeg=null, dateTime=0005-07-12T06:01)",
                actualCurrentWeatherFromOWByLocation.toString());
        assertNull(actualCurrentWeatherFromOWByLocation.getWindSpeed());
        assertNull(actualCurrentWeatherFromOWByLocation.getWindDeg());
        assertNull(actualCurrentWeatherFromOWByLocation.getTemp());
        assertNull(actualCurrentWeatherFromOWByLocation.getPressure());
        assertNull(actualCurrentWeatherFromOWByLocation.getLon());
        assertNull(actualCurrentWeatherFromOWByLocation.getLat());
        assertNull(actualCurrentWeatherFromOWByLocation.getHumidity());
        assertNull(actualCurrentWeatherFromOWByLocation.getFeelsLike());
        assertNull(actualCurrentWeatherFromOWByLocation.getCondition());
        verify(openWeatherCurrentDto, times(2)).getCoordinate();
        verify(openWeatherCurrentDto).getName();
        verify(openWeatherCurrentDto).getWeather();
        verify(openWeatherCurrentDto).getDateTime();
        verify(openWeatherCurrentDto, times(2)).getWind();
        verify(openWeatherCurrentDto, times(4)).getMain();
        verify(this.restTemplate).getForObject(anyString(), any(), (Object[]) any());
    }

    @Test
    public void getForecastWeatherFromOWByCityTest() throws RestClientException {
        when(this.restTemplate.getForObject(anyString(), any(), (Object[]) any()))
                .thenThrow(new HttpClientErrorException(HttpStatus.CONTINUE));
        thrown.expect(HttpClientErrorException.class);
        this.openWeatherService.getForecastWeatherFromOWByCity("Saint-Petersburg");
        verify(this.restTemplate).getForObject(anyString(), any(), (Object[]) any());
    }


    @Test
    public void getForecastWeatherFromOWByLocationTest() throws RestClientException {
        when(this.restTemplate.getForObject(anyString(), any(), (Object[]) any()))
                .thenThrow(new HttpClientErrorException(HttpStatus.CONTINUE));
        thrown.expect(HttpClientErrorException.class);
        this.openWeatherService.getForecastWeatherFromOWByLocation(60.0f, 30.0f);
        verify(this.restTemplate).getForObject(anyString(), any(), (Object[]) any());
    }

}