package no.smoky.magic.magicserver.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import no.smoky.magic.magicserver.model.Screen;
import no.smoky.magic.magicserver.model.ScreenGET;
import no.smoky.magic.magicserver.model.ScreenPOST;
import no.smoky.magic.magicserver.service.ScreenService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ScreenController {

    private ScreenService screenService;
    
    ScreenController() {
        this.screenService = new ScreenService();
    } 

    @GetMapping("/screens")
    List<ScreenGET> send() {
        List<ScreenGET> screens = this.screenService.read();
        System.out.println("Result from read: ");
        System.out.println(screens.toString());
        // ArrayList<ScreenGET> res = new ArrayList<ScreenGET>();
        // screens.forEach((Screen r) -> {
        //     res.add(new ScreenGET(r), );
        // });
        return screens;
    }

    @PostMapping("/screens")
    ScreenGET newScreen(@RequestBody ScreenPOST newScreen) {
        System.out.println("POST screens: " + newScreen);
        return screenService.create(new Screen(newScreen), newScreen.getSecret());
    }
}