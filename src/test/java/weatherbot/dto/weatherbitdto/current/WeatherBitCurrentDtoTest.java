package weatherbot.dto.weatherbitdto.current;

import static org.junit.Assert.assertEquals;
import com.example.weatherbot.app.dto.weatherbitdto.WeatherBitInfo;

import java.util.ArrayList;

import com.example.weatherbot.app.dto.weatherbitdto.current.WeatherBitCurrentDto;
import org.junit.Test;

public class WeatherBitCurrentDtoTest {

    @Test
    public void equalsTest() {
        WeatherBitCurrentDto weatherBitCurrentDto = new WeatherBitCurrentDto();
        weatherBitCurrentDto.setMainInfo(new ArrayList<WeatherBitInfo>());
        WeatherBitCurrentDto weatherBitCurrentDto1 = new WeatherBitCurrentDto();
        weatherBitCurrentDto1.setMainInfo(new ArrayList<WeatherBitInfo>());
        assertEquals(weatherBitCurrentDto, weatherBitCurrentDto1);
    }


    @Test
    public void setMainInfoTest() {
        WeatherBitCurrentDto weatherBitCurrentDto = new WeatherBitCurrentDto();
        weatherBitCurrentDto.setMainInfo(new ArrayList<WeatherBitInfo>());
        assertEquals("WeatherBitCurrentDto(mainInfo=[])", weatherBitCurrentDto.toString());
    }

}
