package no.smoky.magic.magicserver;

import java.util.Date;
import java.util.List;

import com.google.cloud.Timestamp;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import no.smoky.magic.magicserver.model.Message;
import no.smoky.magic.magicserver.model.Screen;
import no.smoky.magic.magicserver.service.MessageService;
import no.smoky.magic.magicserver.service.ScreenService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageServiceTest {

    MessageService messageService = new MessageService();
    ScreenService screenService = new ScreenService();

    @Test
    public void connectsFirestore() {
        Assert.assertNotNull(messageService);
    }

    @Test
    public void canCreateReadDeleteMessage() {
        String screenKey = "Test screen key";
        Screen toCreate = new Screen("Test screen name");
        screenService.create(toCreate, screenKey);

        Timestamp testTime = Timestamp.now();
        Message message = new Message("Test message", "Otto", "ottpau@gmail.com", testTime, 10);
        String messageId = messageService.create(message, screenKey);

        Assert.assertNotNull("MessageId not null", messageId);

        message = messageService.read(screenKey, messageId);
        Assert.assertEquals("Test message", message.getMessage());
        Assert.assertEquals("Otto", message.getSentBy());
        Assert.assertEquals("ottpau@gmail.com", message.getSentByEmail());
        Assert.assertEquals(testTime, message.getSentTime());
        Assert.assertEquals(10, message.getValidMinutes());
        Assert.assertEquals(message.getId(), messageId);
                
        boolean messageDeleted = messageService.delete(screenKey, messageId);
        Assert.assertTrue("Delete message returns true", messageDeleted);

        boolean screenDeleted = screenService.delete(screenKey);
        Assert.assertTrue("Delete screen returns true", screenDeleted);
    }
} 
