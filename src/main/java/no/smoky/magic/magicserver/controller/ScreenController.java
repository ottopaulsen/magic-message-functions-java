package no.smoky.magic.magicserver.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import no.smoky.magic.magicserver.model.Screen;
import no.smoky.magic.magicserver.service.ScreenService;

@RestController
public class ScreenController {

    private ScreenService screenService;
    
    ScreenController() {
        this.screenService = new ScreenService();
    } 

    // @GetMapping("/screens")
    // Screen[] send() {
    //     List<Screen> res = this.screenService.read();
    //     System.out.println("Result from read: ");
    //     System.out.println(res.toString());
    //     return (Screen[]) res.toArray(new Screen[0]);
    // }

    @GetMapping("/screens")
    List<Map<String, Object>> send() {
        List<Screen> screens = this.screenService.read();
        System.out.println("Result from read: ");
        System.out.println(screens.toString());
        List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
        screens.forEach((Screen r) -> {
            Map<String, Object> s = new HashMap<String, Object>();
            s.put("name", r.getName());
            s.put("key", r.getKey());
            res.add(s);
        });
        return res;
    }

    @PostMapping("/screens")
    Screen echo(@RequestBody Screen screen) {
        screen.setKey("my new key");
        return screen;
    }
}