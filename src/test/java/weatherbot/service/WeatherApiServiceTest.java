package weatherbot.service;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.weatherbot.app.dto.weatherapidto.CurrentWeatherInfo;
import com.example.weatherbot.app.dto.weatherapidto.Location;
import com.example.weatherbot.app.dto.weatherapidto.current.Condition;
import com.example.weatherbot.app.dto.weatherapidto.current.WeatherAPICurrentDto;
import com.example.weatherbot.app.model.weather_model.WeatherApiModel;
import com.example.weatherbot.app.service.WeatherApiService;
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

@ContextConfiguration(classes = {WeatherApiService.class, RestTemplate.class})
@RunWith(SpringRunner.class)
public class WeatherApiServiceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private WeatherApiService weatherApiService;

    @Test
    public void getCurrentWeatherFromWAByCityTest() throws RestClientException {
        WeatherAPICurrentDto weatherAPICurrentDto = mock(WeatherAPICurrentDto.class);
        when(weatherAPICurrentDto.getInfo()).thenThrow(new HttpClientErrorException(HttpStatus.CONTINUE));
        when(weatherAPICurrentDto.getLocation()).thenReturn(new Location());
        when(this.restTemplate.getForObject(anyString(), any(), (Object[]) any()))
                .thenReturn(weatherAPICurrentDto);
        thrown.expect(HttpClientErrorException.class);
        this.weatherApiService.getCurrentWeatherFromWAByCity("Saint-Petersburg");
        verify(this.restTemplate).getForObject(anyString(), any(), (Object[]) any());
        verify(weatherAPICurrentDto).getLocation();
        verify(weatherAPICurrentDto).getInfo();
    }

    @Test
    public void getCurrentWeatherFromWAByCityTestDetailed() throws RestClientException {
        CurrentWeatherInfo currentWeatherInfo = new CurrentWeatherInfo();
        currentWeatherInfo.setCondition(new Condition());
        WeatherAPICurrentDto weatherAPICurrentDto = mock(WeatherAPICurrentDto.class);
        when(weatherAPICurrentDto.getInfo()).thenReturn(currentWeatherInfo);
        when(weatherAPICurrentDto.getLocation()).thenReturn(new Location());
        when(this.restTemplate.getForObject(anyString(), any(), (Object[]) any()))
                .thenReturn(weatherAPICurrentDto);
        WeatherApiModel actualCurrentWeatherFromWAByCity = this.weatherApiService.getCurrentWeatherFromWAByCity(
                "Saint-Petersburg");
        assertNull(actualCurrentWeatherFromWAByCity.getCityName());
        assertNull(actualCurrentWeatherFromWAByCity.getWindSpeed());
        assertNull(actualCurrentWeatherFromWAByCity.getWindDeg());
        assertNull(actualCurrentWeatherFromWAByCity.getTemp());
        assertNull(actualCurrentWeatherFromWAByCity.getPressure());
        assertNull(actualCurrentWeatherFromWAByCity.getLon());
        assertNull(actualCurrentWeatherFromWAByCity.getLat());
        assertNull(actualCurrentWeatherFromWAByCity.getHumidity());
        assertNull(actualCurrentWeatherFromWAByCity.getFeelsLike());
        assertNull(actualCurrentWeatherFromWAByCity.getDateTime());
        assertNull(actualCurrentWeatherFromWAByCity.getCondition());
        verify(this.restTemplate).getForObject(anyString(), any(), (Object[]) any());
        verify(weatherAPICurrentDto, times(4)).getLocation();
        verify(weatherAPICurrentDto, times(7)).getInfo();
    }


    @Test
    public void getCurrentWeatherFromWAByLocationTestDetailed() throws RestClientException {
        CurrentWeatherInfo currentWeatherInfo = new CurrentWeatherInfo();
        currentWeatherInfo.setCondition(new Condition());
        WeatherAPICurrentDto weatherAPICurrentDto = mock(WeatherAPICurrentDto.class);
        when(weatherAPICurrentDto.getInfo()).thenReturn(currentWeatherInfo);
        when(weatherAPICurrentDto.getLocation()).thenReturn(new Location());
        when(this.restTemplate.getForObject(anyString(), any(), (Object[]) any()))
                .thenReturn(weatherAPICurrentDto);
        WeatherApiModel actualCurrentWeatherFromWAByLocation = this.weatherApiService
                .getCurrentWeatherFromWAByLocation(65.0f, 30.0f);
        assertNull(actualCurrentWeatherFromWAByLocation.getCityName());
        assertNull(actualCurrentWeatherFromWAByLocation.getWindSpeed());
        assertNull(actualCurrentWeatherFromWAByLocation.getWindDeg());
        assertNull(actualCurrentWeatherFromWAByLocation.getTemp());
        assertNull(actualCurrentWeatherFromWAByLocation.getPressure());
        assertNull(actualCurrentWeatherFromWAByLocation.getLon());
        assertNull(actualCurrentWeatherFromWAByLocation.getLat());
        assertNull(actualCurrentWeatherFromWAByLocation.getHumidity());
        assertNull(actualCurrentWeatherFromWAByLocation.getFeelsLike());
        assertNull(actualCurrentWeatherFromWAByLocation.getDateTime());
        assertNull(actualCurrentWeatherFromWAByLocation.getCondition());
        verify(this.restTemplate).getForObject(anyString(), any(), (Object[]) any());
        verify(weatherAPICurrentDto, times(4)).getLocation();
        verify(weatherAPICurrentDto, times(7)).getInfo();
    }
}
