package weatherbot.service;

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


}
