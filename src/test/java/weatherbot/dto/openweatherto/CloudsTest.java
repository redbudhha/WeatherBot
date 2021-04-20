package weatherbot.dto.openweatherto;

import com.example.weatherbot.app.dto.openweatherdto.Clouds;
import org.junit.Test;

import static org.junit.Assert.*;

public class CloudsTest {

    @Test
    public void equalsTest() {
        Clouds clouds = new Clouds();
        Clouds clouds1 = new Clouds();
        clouds1.setAll(0);
        assertNotEquals(clouds, clouds1);
    }
       

    @Test
    public void setAllTest() {
        Clouds clouds = new Clouds();
        clouds.setAll(1);
        assertEquals(1, clouds.getAll().intValue());
    }


}
