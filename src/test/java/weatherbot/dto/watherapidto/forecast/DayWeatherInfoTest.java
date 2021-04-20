package weatherbot.dto.watherapidto.forecast;

import com.example.weatherbot.app.dto.weatherapidto.current.Condition;
import com.example.weatherbot.app.dto.weatherapidto.forecast.DayWeatherInfo;
import org.junit.Test;

import static org.junit.Assert.*;

public class DayWeatherInfoTest {

      @Test
    public void equalsTest() {
        DayWeatherInfo dayWeatherInfo = new DayWeatherInfo();
        DayWeatherInfo dayWeatherInfo1 = new DayWeatherInfo();
        dayWeatherInfo1.setAvgTemp(250.0);
          assertNotEquals(dayWeatherInfo, dayWeatherInfo1);
    }
    
    @Test
    public void setAvgHumidityTest() {
        DayWeatherInfo dayWeatherInfo = new DayWeatherInfo();
        dayWeatherInfo.setAvgHumidity(1);
        assertEquals(1, dayWeatherInfo.getAvgHumidity().intValue());
    }

    @Test
    public void setAvgTempTest() {
        DayWeatherInfo dayWeatherInfo = new DayWeatherInfo();
        dayWeatherInfo.setAvgTemp(50.0);
        assertEquals(50.0, dayWeatherInfo.getAvgTemp(), 0.0);
    }

    @Test
    public void setConditionTest() {
        DayWeatherInfo dayWeatherInfo = new DayWeatherInfo();
        dayWeatherInfo.setCondition(new Condition());
        assertEquals(
                "DayWeatherInfo(maxTemp=null, minTemp=null, avgTemp=null, avgHumidity=null, condition=Condition(description"
                        + "=null), windSpeed=null)",
                dayWeatherInfo.toString());
    }

    @Test
    public void setMaxTempTest() {
        DayWeatherInfo dayWeatherInfo = new DayWeatherInfo();
        dayWeatherInfo.setMaxTemp(40.0);
        assertEquals(40.0, dayWeatherInfo.getMaxTemp(), 0.0);
    }

    @Test
    public void setMinTempTest() {
        DayWeatherInfo dayWeatherInfo = new DayWeatherInfo();
        dayWeatherInfo.setMinTemp(30.0);
        assertEquals(30.0, dayWeatherInfo.getMinTemp(), 0.0);
    }

    @Test
    public void setWindSpeedTest() {
        DayWeatherInfo dayWeatherInfo = new DayWeatherInfo();
        dayWeatherInfo.setWindSpeed(10.0);
        assertEquals(10.0, dayWeatherInfo.getWindSpeed(), 0.0);
    }

}
