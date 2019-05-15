package no.smoky.magic.magicserver.controller;

import java.util.List;

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

    @GetMapping("/screens")
    Screen[] send() {
        List<Screen> res = this.screenService.read();
        System.out.println("Result from read: ");
        System.out.println(res.toString());
        return (Screen[]) res.toArray();
    }

    @PostMapping("/screens")
    Screen echo(@RequestBody Screen screen) {
        screen.setKey("my new key");
        return screen;
    }
}