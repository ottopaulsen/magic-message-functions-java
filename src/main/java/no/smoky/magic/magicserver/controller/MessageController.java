package no.smoky.magic.magicserver.controller;

import java.security.Principal;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.google.cloud.Timestamp;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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

// @CrossOrigin(origins = "http://localhost:4200")
@RestController
@CrossOrigin
public class MessageController {

    private MessageService messageService;
    private ScreenService screenService;

    Logger logger = Logger.getLogger(MessageController.class.getName());


    MessageController() {
        this.messageService = new MessageService();
        this.screenService = new ScreenService();
    }

    @PostMapping("/screens/{screenKey}/messages")
    Message createMessage(@PathVariable()
                          String screenKey,
                          @RequestBody MessagePOST messagePost,
                          Principal principal,
                          HttpServletRequest request,
                          @RequestHeader(value = "Authorization", required = true) String idToken) {

        logger.info("POST message to " + screenKey + ": " + messagePost);

        Screen screen = screenService.readFromDb(screenKey);

        Message message = new Message(messagePost.getMessage(),
                screen.getUsers().get(principal.getName().replace('.', '+')), // sentBy
                principal.getName(), // sentByEmail
                Timestamp.now(), messagePost.getLifetime());
                String messageId = messageService.create(message, screenKey);
                message.setId(messageId);
                logger.info("Created message: " + message);

        return messageService.read(screenKey, messageId);
    }

    @DeleteMapping("/screens/{screenKey}/messages/{messageId}")
    void deleteMessage(@PathVariable() String screenKey,
                         @PathVariable() String messageId,
                         Principal principal, 
                         HttpServletRequest request,
                         @RequestHeader(value = "Authorization", required = true) String idToken) {

        logger.info("DELETE message " + screenKey + "/messages/" + messageId);

        Message message = messageService.read(screenKey, messageId);

        String sentByEmail = message.getSentByEmail();
        String userEmail = principal.getName();

        if(sentByEmail.equals(userEmail)) {
            if(messageService.delete(screenKey, messageId)) {
                logger.info("\"Message\": \"Message deleted\"");
            } else {
                logger.info("\"Error\": \"Error deleting message\"");
            }
        } else {
            logger.info("{\"Error\": \"Not allowed to delete message\"}");
        }
    }

}