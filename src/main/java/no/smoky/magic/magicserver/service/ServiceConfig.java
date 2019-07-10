package no.smoky.magic.magicserver.service;

import java.io.IOException;
import java.util.logging.Logger;

import javax.annotation.PreDestroy;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    static Logger logger = Logger.getLogger(ServiceConfig.class.getName());
    static Firestore db;
    FirebaseOptions fbOptions;

    // String projectId = env.getProperty("app.firebase.projectId");

    ServiceConfig() {
        String projectId = "magic-acf51";
        GoogleCredentials credentials;

        logger.info("ServiceConfig constructor");
        
        try {
            // Use the application default credentials
            credentials = GoogleCredentials.getApplicationDefault();
            fbOptions = new FirebaseOptions.Builder().setCredentials(credentials).setProjectId(projectId).build();
            FirestoreOptions fsOptions = FirestoreOptions.newBuilder().setTimestampsInSnapshotsEnabled(true)
            .setCredentials(credentials).setProjectId(projectId).build();
            try {
                // if(FirebaseApp.getInstance() == null){
                if(FirebaseApp.getApps().size() == 0){
                    FirebaseApp.initializeApp(fbOptions);
                }
                db = fsOptions.getService();
                ScreenService.setFirestore(db);
                MessageService.setFirestore(db);
                logger.info("Services db set");
            } catch (IllegalStateException e) {
                logger.severe("ServiceConfig could not set ScreenService database. Error: " + e);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void onExit() {
        System.out.println("Otto: This is onExit in ServiceConfig");
        logger.info("###STOPing### ServiceConfig");
        if(db != null) {
            try {
                System.out.println("Otto: Closing  db");
                db.close();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                System.out.println("Otto: Exception closing db");
                e1.printStackTrace();
            }
        }
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            logger.info("###CATCH ing### ServiceConfig ");
            logger.info(e.toString());
            ;
        }
        logger.info("###STOP FROM THE LIFECYCLE### ServiceConfig");
    }

}
