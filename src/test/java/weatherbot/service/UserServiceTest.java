package weatherbot.service;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.example.weatherbot.app.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@ContextConfiguration(classes = {MongoTemplate.class, UserService.class})
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @MockBean
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserService userService;

    @Test
    public void createUserTest() {
        Message message = mock(Message.class);
        when(message.getLocation()).thenReturn(new Location());
        when(message.hasLocation()).thenReturn(true);
        when(message.getFrom()).thenReturn(new User());
        when(message.getChatId()).thenReturn(1L);
        Update update = mock(Update.class);
        when(update.getMessage()).thenReturn(message);
        this.userService.createUser(update);
        verify(message).hasLocation();
        verify(message).getFrom();
        verify(message).getLocation();
        verify(message).getChatId();
        verify(update, times(4)).getMessage();
    }

    @Test
    public void findAllTest() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        when(this.mongoTemplate.findAll(any(), anyString())).thenReturn(objectList);
        List<com.example.weatherbot.app.model.db_model.User> actualFindAllResult = this.userService.findAll();
        assertSame(objectList, actualFindAllResult);
        assertTrue(actualFindAllResult.isEmpty());
        verify(this.mongoTemplate).findAll(any(), anyString());
    }

    @Test
    public void findUserByChatIdTest() {
        com.example.weatherbot.app.model.db_model.User user = new com.example.weatherbot.app.model.db_model.User();
        user.setLocation(new com.example.weatherbot.app.model.db_model.User.Location(60.0f, 30.0f));
        user.setUserName("Egor");
        user.setCity("Saint-Petersburg");
        user.setChatIid(123L);
        when(this.mongoTemplate.findById(any(), any())).thenReturn(user);
        assertSame(user, this.userService.findUserByChatId(123L));
        verify(this.mongoTemplate).findById(any(), any());
    }

    @Test
    public void updateTest() {
        when(this.mongoTemplate.save(any(), anyString())).thenReturn("42");
        this.userService.update(new com.example.weatherbot.app.model.db_model.User("Egor",
                "Saint-Petersburg", 123L));
        verify(this.mongoTemplate).save(any(), anyString());
    }
}