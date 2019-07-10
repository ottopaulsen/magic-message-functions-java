package no.smoky.magic.magicserver;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import no.smoky.magic.magicserver.model.Screen;
import no.smoky.magic.magicserver.model.ScreenGET;
import no.smoky.magic.magicserver.service.ScreenService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScreenServiceTest {

    ScreenService screenService = new ScreenService();

    @Test
    public void connectsFirestore() {
        Assert.assertNotNull(screenService);
    }

    @Test
    public void readReturnsList() {
        Assert.assertTrue(screenService.read().size() > 0);
    }

    @Test
    public void firstScreenHasName() {
        List<ScreenGET> screens = screenService.read();
        Assert.assertNotNull(screens.get(0).getName());
    }

    @Test
    public void firstScreenHasKey() {
        List<ScreenGET> screens = screenService.read();
        Assert.assertNotNull(screens.get(0).getKey());
    }

    @Test
    public void hasMultipleScreens() {
        List<ScreenGET> screens = screenService.read();
        Assert.assertTrue(screens.size() > 3);
    }

    @Test
    public void canCreateReadDeleteScreen() {
        String key = "Test screen key";
        Screen toCreate = new Screen("Test screen name");
        ScreenGET created = screenService.create(toCreate, key);
        Assert.assertEquals("Created object equals input", created, toCreate);
        ScreenGET read = screenService.read(key);
        Assert.assertEquals("Read object equals created", read, created);
        boolean deleted = screenService.delete(read.getKey());
        Assert.assertTrue("Delete object returns true", deleted);
    }
} 
