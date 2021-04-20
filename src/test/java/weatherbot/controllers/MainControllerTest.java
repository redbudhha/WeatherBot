package weatherbot.controllers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.example.weatherbot.app.bot.WeatherBot;
import com.example.weatherbot.app.controllers.MainController;
import com.example.weatherbot.app.utils.TelegramFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.telegram.telegrambots.meta.api.objects.Update;

@ContextConfiguration(classes = {MainController.class})
@RunWith(SpringRunner.class)
public class MainControllerTest {
    @Autowired
    private MainController mainController;

    @MockBean
    private TelegramFacade telegramFacade;

    @MockBean
    private WeatherBot weatherBot;

    @Test
    public void getUpdatesTest() throws Exception {
        doNothing().when(this.weatherBot).executeMethod((org.telegram.telegrambots.meta.api.methods.BotApiMethod<?>) any());
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new Update()));
        MockMvcBuilders.standaloneSetup(this.mainController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}