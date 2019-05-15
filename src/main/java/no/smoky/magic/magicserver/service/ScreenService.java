package no.smoky.magic.magicserver.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import no.smoky.magic.magicserver.model.Screen;

public class ScreenService {

    @Autowired
    private Environment env;

    private GoogleCredentials credentials;
    private FirebaseOptions options;

    private Firestore db;

    public ScreenService() {
        // String projectId = env.getProperty("app.firebase.projectId");
        String projectId = "magic-acf51";
        try {
            // Use the application default credentials
            credentials = GoogleCredentials.getApplicationDefault();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        options = new FirebaseOptions.Builder().setCredentials(credentials).setProjectId(projectId).build();
        try {
            FirebaseApp.initializeApp(options);
        } catch (IllegalStateException e) {
            
        }
        db = FirestoreClient.getFirestore();
    }

    public void create(Screen newScreen) {

    }


    public List<Screen> read() {
        CollectionReference screensRef = db.collection("screens");
        ApiFuture<QuerySnapshot> screenListFuture = screensRef.get();
        List<Screen> screens = new ArrayList<>();
        try {
            QuerySnapshot screenList = screenListFuture.get();
            screenList.forEach(screen -> {
                System.out.println(screen.toString());
                screens.add(screen.toObject(Screen.class));
            });
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return screens;
    }
}
