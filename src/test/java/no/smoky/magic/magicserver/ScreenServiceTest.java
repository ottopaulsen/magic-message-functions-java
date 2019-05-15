package no.smoky.magic.magicserver;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import no.smoky.magic.magicserver.service.ScreenService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScreenServiceTest {

    ScreenService screenService = new ScreenService() ;

    @Test
    public void connectsFirestore() {
        Assert.assertNotNull(screenService);
    }

    @Test
    public void readReturnsList() {
        Assert.assertTrue(screenService.read().size() > 0);
    }
} 
