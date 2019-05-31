package no.smoky.magic.magicserver.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import com.google.cloud.Timestamp;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

// import com.google.api.Authentication;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import no.smoky.magic.magicserver.model.Message;
import no.smoky.magic.magicserver.model.MessagePOST;
import no.smoky.magic.magicserver.service.MessageService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MessageController {

    private MessageService messageService;
    
    MessageController() {
        this.messageService = new MessageService();
    } 

    @PostMapping("/screens/{screenKey}/messages")
    String createMessage(@PathVariable() String screenKey, 
                         @RequestBody MessagePOST messagePost,
                         Principal principal,
                         HttpServletRequest request
                         ) {
        System.out.println("POST message to " + screenKey + ": " + messagePost);
        Message message = new Message(
            messagePost.getMessage(),
            principal.getName(), // sentBy
            principal.getName(), // sentByEmail
            Timestamp.now(),
            messagePost.getValidMinutes());
        System.out.println("Creating message: " + message);
        return messageService.create(message, screenKey);
    }

}