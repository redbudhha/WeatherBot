package weatherbot.service;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.weatherbot.app.model.Weather;
import com.example.weatherbot.app.model.db_model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.weatherbot.app.service.WeatherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@ContextConfiguration(classes = {MongoTemplate.class, WeatherService.class})
@RunWith(SpringRunner.class)
public class WeatherServiceTest {
    @MockBean
    private MongoTemplate mongoTemplate;

    @Autowired
    private WeatherService weatherService;

    @Test
    public void findAllTest() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        when(this.mongoTemplate.findAll(any(), anyString())).thenReturn(objectList);
        List<Weather> actualFindAllResult = this.weatherService.findAll();
        assertSame(objectList, actualFindAllResult);
        assertTrue(actualFindAllResult.isEmpty());
        verify(this.mongoTemplate).findAll(any(), anyString());
    }

    @Test
    public void checkIfWeatherAlreadyExistsTest() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        when(this.mongoTemplate.findAll(any(), anyString())).thenReturn(objectList);
        LocalDate date = LocalDate.ofEpochDay(1L);
        assertNull(this.weatherService.checkIfWeatherAlreadyExists(date, new User("Egor",
                "Saint-Petersburg", 123L)));
        verify(this.mongoTemplate).findAll(any(), anyString());
        assertSame(objectList, this.weatherService.findAll());
    }
}