package no.smoky.magic.magicserver.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import com.google.cloud.Timestamp;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;

// import com.google.api.Authentication;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import no.smoky.magic.magicserver.model.Message;
import no.smoky.magic.magicserver.model.MessagePOST;
import no.smoky.magic.magicserver.model.Screen;
import no.smoky.magic.magicserver.service.MessageService;
import no.smoky.magic.magicserver.service.ScreenService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MessageController {

    private MessageService messageService;
    private ScreenService screenService;
    
    MessageController() {
        this.messageService = new MessageService();
        this.screenService = new ScreenService();
    } 

    @PostMapping("/screens/{screenKey}/messages")
    Message createMessage(@PathVariable() String screenKey, 
                         @RequestBody MessagePOST messagePost,
                         Principal principal,
                         HttpServletRequest request,
                         @RequestHeader(value = "Authorization", required = true) String idToken
                         ) {
        System.out.println("POST message to " + screenKey + ": " + messagePost);

        Screen screen = screenService.readFromDb(screenKey);
        System.out.println("Posting to screen: " + screen);

        Message message = new Message(
            messagePost.getMessage(),
            screen.getUsers().get(principal.getName().replace('.', '+')), // sentBy
            principal.getName(), // sentByEmail
            Timestamp.now(),
            messagePost.getLifetime());
        System.out.println("Creating message: " + message);
        String messageId =  messageService.create(message, screenKey);

        return messageService.read(screenKey, messageId);
    }

}