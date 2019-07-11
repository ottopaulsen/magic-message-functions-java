package no.smoky.magic.magicserver.service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import no.smoky.magic.magicserver.model.Message;

public class MessageService {

    // @Autowired
    // private Environment env;

    // private GoogleCredentials credentials;
    // private FirebaseOptions fbOptions;
    private static Firestore db;

    public MessageService() {
    }


    public static void setFirestore(Firestore fs) {
        db = fs;
    }


    public String create(Message message, String screenKey) {
        CollectionReference ref = db.collection("screens/" + screenKey + "/messages");
        ApiFuture<DocumentReference> future = ref.add(message);
        DocumentReference res;
        try {
            res = future.get();
            System.out.println("create message WriteResult res = " + res);
            return res.getId();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            System.out.println("Hey, Got InterruptedException");
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }


    public boolean delete(String screenKey, String messageId) {
        DocumentReference ref = db.collection("screens/" + screenKey + "/messages").document(messageId);
        ApiFuture<WriteResult> future = ref.delete();
        WriteResult res;
        try {
            res = future.get();
            System.out.println("delete message WriteResult res = " + res);
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

    public Message read(String screenKey, String messageId) {
        DocumentReference ref = db.collection("screens/" + screenKey + "/messages").document(messageId);
        ApiFuture<DocumentSnapshot> future = ref.get();
        DocumentSnapshot doc;
        Message res = null;
        try {
            doc = future.get();
            res = doc.toObject(Message.class);
            res.setId(messageId);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }
}
