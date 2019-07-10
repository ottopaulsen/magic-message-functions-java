package no.smoky.magic.magicserver.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import javax.annotation.PreDestroy;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import no.smoky.magic.magicserver.model.Message;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.core.env.Environment;

import no.smoky.magic.magicserver.model.Screen;
import no.smoky.magic.magicserver.model.ScreenGET;

public class ScreenService {

    // @Autowired
    // private Environment env;

    // private GoogleCredentials credentials;
    // private FirebaseOptions fbOptions;
    private static Firestore db;

    Logger logger = Logger.getLogger(ScreenService.class.getName());


    public ScreenService() {
    }

    public static void setFirestore(Firestore fs) {
        db = fs;
    }

    public ScreenGET create(Screen newScreen, String key) {

        DocumentReference ref = db.collection("screens").document(key);
        ApiFuture<WriteResult> future = ref.set(newScreen);
        WriteResult res;
        try {
            res = future.get();
            System.out.println("create WriteResult res = " + res);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return read(key);
    }

    public boolean delete(String key) {
        DocumentReference ref = db.collection("screens").document(key);
        ApiFuture<WriteResult> future = ref.delete();
        WriteResult res;
        try {
            res = future.get();
            System.out.println("delete WriteResult res = " + res);
            return true;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public ScreenGET read(String key) {
        DocumentReference ref = db.collection("screens").document(key);
        ApiFuture<DocumentSnapshot> future = ref.get();
        DocumentSnapshot doc;
        ScreenGET res = null;
        try {
            doc = future.get();
            res = new ScreenGET(doc.get("name").toString(), doc.getId());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

    public Screen readFromDb(String key) {
        DocumentReference ref = db.collection("screens").document(key);
        ApiFuture<DocumentSnapshot> future = ref.get();
        DocumentSnapshot doc;
        Screen res = null;
        try {
            doc = future.get();
            // res = new Screen(doc.get("name").toString(), doc.get("refreshTime"), doc.get("users"));
            res = doc.toObject(Screen.class);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

    public List<ScreenGET> read() {
        CollectionReference screensRef = db.collection("screens");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        logger.info("ScreenService read found userName: " + userName);
        // ApiFuture<QuerySnapshot> screenListFuture = screensRef.get();
        // String userKey = "users." + userName.replaceAll("/\./g", "+");
        String userKey = "users." + userName.replace(".", "+");
        logger.info("ScreenService read userKey: " + userKey);
        ApiFuture<QuerySnapshot> screenListFuture = screensRef.whereGreaterThan(userKey, "").get();
        List<ScreenGET> screens = new ArrayList<>();
        try {
            QuerySnapshot screenList = screenListFuture.get();
            screenList.forEach(doc -> {
                String key = doc.getId();
                String name = doc.get("name").toString();
                ScreenGET screen = new ScreenGET(name, key);
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

    public boolean create(Message message, String screenKey) {
        CollectionReference ref = db.collection("screens/" + screenKey + "/messages");
        ApiFuture<DocumentReference> future = ref.add(message);
        DocumentReference res;
        try {
            res = future.get();
            System.out.println("create message WriteResult res = " + res);
            return true;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    @PreDestroy
    public void onExit() {
        System.out.println("Otto: This is onExit");
        logger.info("###STOPing###");
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            logger.info("###CATCH ing###");
            logger.info(e.toString());
            ;
        }
        logger.info("###STOP FROM THE LIFECYCLE###");
    }
}
