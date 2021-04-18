package weatherbot.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.weatherbot.app.dto.weatherbitdto.WeatherBitInfo;
import com.example.weatherbot.app.dto.weatherbitdto.WeatherDescription;
import com.example.weatherbot.app.dto.weatherbitdto.current.WeatherBitCurrentDto;
import com.example.weatherbot.app.dto.weatherbitdto.forecast.WeatherBitForecastDto;
import com.example.weatherbot.app.dto.weatherbitdto.forecast.WeatherBitInfoForecast;
import com.example.weatherbot.app.model.weather_model.WeatherBitModel;

import java.util.ArrayList;

import com.example.weatherbot.app.service.WeatherBitService;
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

@ContextConfiguration(classes = {WeatherBitService.class, RestTemplate.class})
@RunWith(SpringRunner.class)
public class WeatherBitServiceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private WeatherBitService weatherBitService;

    @Test
    public void getCurrentWeatherFromWBByLocationTest() throws RestClientException {
        when(this.restTemplate.getForObject(anyString(), any(), (Object[]) any()))
                .thenThrow(new HttpClientErrorException(HttpStatus.CONTINUE));
        thrown.expect(HttpClientErrorException.class);
        this.weatherBitService.getCurrentWeatherFromWBByLocation(60.0f, 25.0f);
        verify(this.restTemplate).getForObject(anyString(), any(), (Object[]) any());
    }


    @Test
    public void getCurrentWeatherFromWBByLocationTestDetailed() throws RestClientException {
        WeatherBitInfo weatherBitInfo = new WeatherBitInfo();
        weatherBitInfo.setDesc(new WeatherDescription());

        ArrayList<WeatherBitInfo> weatherBitInfoList = new ArrayList<WeatherBitInfo>();
        weatherBitInfoList.add(weatherBitInfo);
        WeatherBitCurrentDto weatherBitCurrentDto = mock(WeatherBitCurrentDto.class);
        when(weatherBitCurrentDto.getMainInfo()).thenReturn(weatherBitInfoList);
        when(this.restTemplate.getForObject(anyString(), any(), (Object[]) any()))
                .thenReturn(weatherBitCurrentDto);
        WeatherBitModel actualCurrentWeatherFromWBByLocation = this.weatherBitService
                .getCurrentWeatherFromWBByLocation(45.0f, 17.0f);
        assertNull(actualCurrentWeatherFromWBByLocation.getCityName());
        assertNull(actualCurrentWeatherFromWBByLocation.getWindSpeed());
        assertNull(actualCurrentWeatherFromWBByLocation.getWindDeg());
        assertNull(actualCurrentWeatherFromWBByLocation.getTemp());
        assertNull(actualCurrentWeatherFromWBByLocation.getPressure());
        assertNull(actualCurrentWeatherFromWBByLocation.getLon());
        assertNull(actualCurrentWeatherFromWBByLocation.getLat());
        assertNull(actualCurrentWeatherFromWBByLocation.getHumidity());
        assertNull(actualCurrentWeatherFromWBByLocation.getFeelsLike());
        assertNull(actualCurrentWeatherFromWBByLocation.getDateTime());
        assertNull(actualCurrentWeatherFromWBByLocation.getCondition());
        verify(this.restTemplate).getForObject(anyString(), any(), (Object[]) any());
        verify(weatherBitCurrentDto, times(11)).getMainInfo();
    }

    @Test
    public void getForecastWeatherFromWBByLocationTest() throws RestClientException {
        when(this.restTemplate.getForObject(anyString(), any(), (Object[]) any()))
                .thenThrow(new HttpClientErrorException(HttpStatus.CONTINUE));
        thrown.expect(HttpClientErrorException.class);
        this.weatherBitService.getForecastWeatherFromWBByLocation(75.0f, 28.0f);
        verify(this.restTemplate).getForObject(anyString(), any(), (Object[]) any());
    }


    @Test
    public void getForecastWeatherFromWBByLocationTestDetailed() throws RestClientException {
        WeatherBitInfoForecast weatherBitInfoForecast = new WeatherBitInfoForecast();
        weatherBitInfoForecast.setDesc(new WeatherDescription());

        ArrayList<WeatherBitInfoForecast> weatherBitInfoForecastList = new ArrayList<WeatherBitInfoForecast>();
        weatherBitInfoForecastList.add(weatherBitInfoForecast);
        WeatherBitForecastDto weatherBitForecastDto = mock(WeatherBitForecastDto.class);
        when(weatherBitForecastDto.getLon()).thenReturn(10.0f);
        when(weatherBitForecastDto.getLat()).thenReturn(10.0f);
        when(weatherBitForecastDto.getMainInfoForecast()).thenReturn(weatherBitInfoForecastList);
        when(weatherBitForecastDto.getCityName()).thenReturn("Saint-Petersburg");
        when(this.restTemplate.getForObject(anyString(), any(), (Object[]) any()))
                .thenReturn(weatherBitForecastDto);
        WeatherBitModel actualForecastWeatherFromWBByLocation = this.weatherBitService
                .getForecastWeatherFromWBByLocation(10.0f, 10.0f);
        assertEquals("Saint-Petersburg", actualForecastWeatherFromWBByLocation.getCityName());
        assertNull(actualForecastWeatherFromWBByLocation.getWindSpeed());
        assertNull(actualForecastWeatherFromWBByLocation.getWindDeg());
        assertNull(actualForecastWeatherFromWBByLocation.getTemp());
        assertNull(actualForecastWeatherFromWBByLocation.getPressure());
        assertEquals(10.0f, actualForecastWeatherFromWBByLocation.getLon(), 0.0f);
        assertEquals(10.0f, actualForecastWeatherFromWBByLocation.getLat(), 0.0f);
        assertNull(actualForecastWeatherFromWBByLocation.getHumidity());
        assertNull(actualForecastWeatherFromWBByLocation.getFeelsLike());
        assertNull(actualForecastWeatherFromWBByLocation.getDateTime());
        assertNull(actualForecastWeatherFromWBByLocation.getCondition());
        verify(this.restTemplate).getForObject(anyString(), any(), (Object[]) any());
        verify(weatherBitForecastDto, times(8)).getMainInfoForecast();
        verify(weatherBitForecastDto).getLon();
        verify(weatherBitForecastDto).getCityName();
        verify(weatherBitForecastDto).getLat();
    }
}
