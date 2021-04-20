package weatherbot.config;

import static org.junit.Assert.assertTrue;

import java.net.UnknownHostException;

import com.example.weatherbot.app.config.BotConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@ContextConfiguration(classes = {BotConfig.class})
@RunWith(SpringRunner.class)
public class BotConfigTest {
    @Autowired
    private BotConfig botConfig;

    @Test
    public void mongoClientTest() throws UnknownHostException {
        assertTrue(this.botConfig.mongoClient() instanceof com.mongodb.client.internal.MongoClientImpl);
    }
}
