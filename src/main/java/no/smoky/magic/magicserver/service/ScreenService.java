package no.smoky.magic.magicserver.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
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
    private FirebaseOptions fbOptions;
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
        fbOptions = new FirebaseOptions.Builder().setCredentials(credentials).setProjectId(projectId).build();

        FirestoreOptions fsOptions = FirestoreOptions.newBuilder().setTimestampsInSnapshotsEnabled(true).setCredentials(credentials).setProjectId(projectId).build();

        try {
            FirebaseApp.initializeApp(fbOptions);
        } catch (IllegalStateException e) {
        }

        db = fsOptions.getService();;
    }

    public void create(Screen newScreen) {
        // const screen = {
        //     name: "" + req.body.name,
        //     users: req.body.users,
        //     refreshTime: new Date(),
        //     }
        
        //     const secret = req.body.secret;
        //     const ref = db().collection('screens').doc(secret);
        //     ref.set(screen).then(() => {
        //     null
        //     res.redirect(303, screen.toString());
        //     }).catch((error) => {
        //     console.log('Screen save failed: ', error);
        //     })

        



    }


    public List<Screen> read() {
        CollectionReference screensRef = db.collection("screens");
        ApiFuture<QuerySnapshot> screenListFuture = screensRef.get();
        List<Screen> screens = new ArrayList<>();
        try {
            QuerySnapshot screenList = screenListFuture.get();
            screenList.forEach(doc -> {
                String key = doc.getId();
                String name = doc.get("name").toString();
                Screen screen = new Screen(name, key);
                screens.add(screen);
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
