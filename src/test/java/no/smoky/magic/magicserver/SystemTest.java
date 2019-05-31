package no.smoky.magic.magicserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import org.junit.Assert;
import no.smoky.magic.magicserver.model.ScreenGET;
import no.smoky.magic.magicserver.model.ScreenPOST;

public class SystemTest {

    @Test
    public void testCreateRead() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/screens";
        String name = "TestScreenName";
        String key = "TestScreenKey";
        Map<String, String> users = new HashMap<String, String>();
        ScreenPOST screen = new ScreenPOST(name, key, users);
        restTemplate.postForEntity(url + "/" + key, screen, ScreenGET.class);

        ScreenGET savedScreen = restTemplate.getForObject(url + "/" + key, ScreenGET.class);
        Assert.assertEquals("Read screen equals saved screen", savedScreen.getName(), screen.getName());
        
    }
    @Test
    public void testMultipleScreensInDb() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/screens";

        List<Map<String, Object>> screens = restTemplate.getForObject(url, List.class);
        // Assertions.assertThat(screens.size() > 20);
        Assert.assertTrue(screens.size() > 2);
        
    }

}