package no.smoky.magic.magicserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import no.smoky.magic.magicserver.service.ScreenService;


@SpringBootApplication
public class MagicServer {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(ScreenService.class);
        context.registerShutdownHook();        
        SpringApplication app = new SpringApplication(MagicServer.class);
        app.run(args);
    }
}
