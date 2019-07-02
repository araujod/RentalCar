package ca.douglas.rentalcar.user;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import ca.douglas.rentalcar.entity.User;

public class MainUser {

    private FirebaseFirestore db;
    private final String COLLECTION_NAME = "Users";
    private final String TAG = "SignUp" ;
    private final String []key = {"id","name","email","phone", "type","address","license","password"};
    User myUser;

    public MainUser(){
        initialize();
    }

    //This method returns a hash code for passwords
    public String getMd5(String text) throws NoSuchAlgorithmException {
        try {
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(text.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // ==================
    public void initialize() {

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }

    // =====================
    public void AddData(User myUser) {

        try{

            Map<String, Object> user = new HashMap<>();

            user.put(key[0], myUser.getId());
            user.put(key[1], myUser.getCustomerName());
            user.put(key[2], myUser.getEmail().toLowerCase()); //it saves email as lower case string
            user.put(key[3], myUser.getPhone());
            user.put(key[4], myUser.getType());
            user.put(key[5], myUser.getAddress());
            user.put(key[6], myUser.getDriverLicense());
            user.put(key[7], getMd5(myUser.getPwd()));

            // Add a new document with a generated ID
            db.collection(COLLECTION_NAME)
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG,
                                    "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Log.d(TAG,
                                    "Error adding document " + e);
                        }});
        }  catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public User Search(String email) {

        // Create a reference
        CollectionReference ref = db.collection(COLLECTION_NAME);
        myUser = new User();

        // Create a query against the collection.
        Query query = ref.whereEqualTo(key[2], email.toLowerCase().trim());
        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                myUser.setId(document.getData().get(key[0]).toString());
                                myUser.setCustomerName(document.getData().get(key[1]).toString());
                                myUser.setEmail(document.getData().get(key[2]).toString());
                                myUser.setPhone(document.getData().get(key[3]).toString());
                                myUser.setType(document.getData().get(key[4]).toString());
                                myUser.setAddress(document.getData().get(key[5]).toString());
                                myUser.setDriverLicense(document.getData().get(key[6]).toString());
                                myUser.setPwd(document.getData().get(key[7]).toString());

                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }

                        } else {
                            myUser = null;
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        return myUser;
    }

    public void Update(User myUser) {

        if (myUser != null) {

            Map<String, Object> user = new HashMap<>();

            user.put(key[0], myUser.getId());
            user.put(key[1], myUser.getCustomerName());
            user.put(key[2], myUser.getEmail());
            user.put(key[3], myUser.getPhone());
            user.put(key[4], myUser.getType());
            user.put(key[5], myUser.getAddress());
            user.put(key[6], myUser.getDriverLicense());
            try {
                user.put(key[7], getMd5(myUser.getPwd()));
            }  catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

            db.collection(COLLECTION_NAME).document(myUser.getId()).update(user);

        }
    }
}
