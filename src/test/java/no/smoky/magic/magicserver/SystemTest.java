package no.smoky.magic.magicserver;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

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
}