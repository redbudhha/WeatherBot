package weatherbot.dto.openweatherto.current;

import com.example.weatherbot.app.dto.openweatherdto.forecast.OpenWeatherSys;
import org.junit.Test;

import static org.junit.Assert.*;

public class OpenWeatherSysTest {


    @Test
    public void equalsTest() {
        OpenWeatherSys openWeatherSys = new OpenWeatherSys();

        OpenWeatherSys openWeatherSys1 = new OpenWeatherSys();
        openWeatherSys1.setPod("Pod");
        assertNotEquals(openWeatherSys, openWeatherSys1);
    }


    @Test
    public void setPodTest() {
        OpenWeatherSys openWeatherSys = new OpenWeatherSys();
        openWeatherSys.setPod("Pod");
        assertEquals("Pod", openWeatherSys.getPod());
    }

}

