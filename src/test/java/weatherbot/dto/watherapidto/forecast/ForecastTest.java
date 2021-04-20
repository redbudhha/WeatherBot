package weatherbot.dto.watherapidto.forecast;

import java.util.ArrayList;

import com.example.weatherbot.app.dto.weatherapidto.forecast.Forecast;
import com.example.weatherbot.app.dto.weatherapidto.forecast.ForecastDay;
import org.junit.Test;

import static org.junit.Assert.*;

public class ForecastTest {

    @Test
    public void equalsTest() {
        Forecast forecast = new Forecast();
        forecast.setForecasts(new ArrayList<ForecastDay>());
        Forecast forecast1 = new Forecast();
        forecast1.setForecasts(new ArrayList<ForecastDay>());
        assertEquals(forecast, forecast1);
    }


    @Test
    public void setForecastsTest() {
        Forecast forecast = new Forecast();
        forecast.setForecasts(new ArrayList<ForecastDay>());
        assertEquals("Forecast(forecasts=[])", forecast.toString());
    }

}
