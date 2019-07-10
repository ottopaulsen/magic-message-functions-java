package no.smoky.magic.magicserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import no.smoky.magic.magicserver.service.ScreenService;

import org.springframework.boot.Banner;

@SpringBootApplication
public class MagicServer {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(ScreenService.class);
        context.registerShutdownHook();        
        // SpringApplication.run(MagicServer.class, args);
        SpringApplication app = new SpringApplication(MagicServer.class);
        // app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
