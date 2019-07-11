package no.smoky.magic.magicserver.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import no.smoky.magic.magicserver.model.Screen;
import no.smoky.magic.magicserver.model.ScreenGET;
import no.smoky.magic.magicserver.model.ScreenPOST;
import no.smoky.magic.magicserver.service.ScreenService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ScreenController {

    private ScreenService screenService;
    Logger logger = Logger.getLogger(ScreenService.class.getName());
    
    ScreenController() {
        this.screenService = new ScreenService();
    } 

    @GetMapping("/screens")
    List<ScreenGET> send(HttpServletRequest request) {
        List<ScreenGET> screens = this.screenService.read();
        logger.info("Result from read: " + screens.toString());

        return screens;
    }

    @PostMapping("/screens")
    ScreenGET newScreen(@RequestBody ScreenPOST newScreen) {
        logger.info("POST screens: " + newScreen);
        return screenService.create(new Screen(newScreen), newScreen.getSecret());
    }

    
}