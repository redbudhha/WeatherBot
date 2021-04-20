package weatherbot.dto.openweatherto.current;

import com.example.weatherbot.app.dto.openweatherdto.current.OpenWeatherSysCurrent;
import org.junit.Test;

import static org.junit.Assert.*;

public class OpenWeatherSysCurrentTest {


    @Test
    public void equalsTest() {
        OpenWeatherSysCurrent openWeatherSysCurrent = new OpenWeatherSysCurrent();
        openWeatherSysCurrent.setId(1);
        OpenWeatherSysCurrent openWeatherSysCurrent1 = new OpenWeatherSysCurrent();
        openWeatherSysCurrent1.setId(1);
        assertEquals(openWeatherSysCurrent, openWeatherSysCurrent1);
    }


    @Test
    public void setIdTest() {
        OpenWeatherSysCurrent openWeatherSysCurrent = new OpenWeatherSysCurrent();
        openWeatherSysCurrent.setId(1);
        assertEquals(1, openWeatherSysCurrent.getId().intValue());
    }

    @Test
    public void setSunriseTest() {
        OpenWeatherSysCurrent openWeatherSysCurrent = new OpenWeatherSysCurrent();
        openWeatherSysCurrent.setSunrise(1L);
        assertEquals(1L, openWeatherSysCurrent.getSunrise().longValue());
    }

    @Test
    public void setSunsetTest() {
        OpenWeatherSysCurrent openWeatherSysCurrent = new OpenWeatherSysCurrent();
        openWeatherSysCurrent.setSunset(1L);
        assertEquals(1L, openWeatherSysCurrent.getSunset().longValue());
    }

    @Test
    public void setTypeTest() {
        OpenWeatherSysCurrent openWeatherSysCurrent = new OpenWeatherSysCurrent();
        openWeatherSysCurrent.setType(1);
        assertEquals(1, openWeatherSysCurrent.getType().intValue());
    }

}