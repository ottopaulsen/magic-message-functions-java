package no.smoky.magic.magicserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import org.junit.Assert;
import no.smoky.magic.magicserver.model.Screen;

public class SystemTest {

    @Test
    public void testCreateRead() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/screens";
        Screen screen = new Screen("TestScreenName", "TestScreenKey");
        ResponseEntity<Screen> entity = restTemplate.postForEntity(url, screen, Screen.class);

        Screen[] screens = restTemplate.getForObject(url, Screen[].class);
        Assertions.assertThat(screens).extracting(Screen::getName).containsOnly("TestScreenName");
        
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