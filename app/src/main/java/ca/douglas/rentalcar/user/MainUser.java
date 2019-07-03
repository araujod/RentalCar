package ca.douglas.rentalcar.user;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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

import ca.douglas.rentalcar.DB.MyDBConnection;
import ca.douglas.rentalcar.entity.User;

import static java.nio.file.Paths.get;

public class MainUser {

    private FirebaseFirestore db;
    private final String COLLECTION_NAME = "Users";
    private final String TAG = "MainUser" ;
    private final String []key = {"email","name","id","phone", "type","address","license","password"};
    public User myUser;
    private MyDBConnection dbc;


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

            dbc = new MyDBConnection();
            Map<String, Object> user = new HashMap<>();

            user.put(key[0], myUser.getEmail().toLowerCase()); //it saves email as lower case string
            user.put(key[1], myUser.getCustomerName());
            user.put(key[2], myUser.getId());
            user.put(key[3], myUser.getPhone());
            user.put(key[4], myUser.getType());
            user.put(key[5], myUser.getAddress());
            user.put(key[6], myUser.getDriverLicense());
            user.put(key[7], getMd5(myUser.getPwd()));

            CollectionReference users = dbc.getCollectionReference("Users");
            users.document(myUser.getEmail().toLowerCase()).set(user);

        }  catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    //public User Search(String email) {
    public void Search(String email) {

        // Create a reference
        CollectionReference ref = db.collection(COLLECTION_NAME);
        myUser = new User();

        //CollectionReference users = dbc.getCollectionReference("Users");
        //String myEmail = users.document(email).getId();
        //if (myEmail.length() != 0){
        //} else {
        //}

        // Create a query against the collection.
        Query query = ref.whereEqualTo(key[2], email.toLowerCase().trim());

        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                myUser.setEmail(document.getData().get(key[0]).toString());
                                myUser.setCustomerName(document.getData().get(key[1]).toString());
                                myUser.setId(document.getData().get(key[2]).toString());
                                myUser.setPhone(document.getData().get(key[3]).toString());
                                myUser.setType(document.getData().get(key[4]).toString());
                                myUser.setAddress(document.getData().get(key[5]).toString());
                                myUser.setDriverLicense(document.getData().get(key[6]).toString());
                                myUser.setPwd(document.getData().get(key[7]).toString());

                                Log.d(TAG, document.getId() + " => " + document.getData());

                            }

                        } else {

                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        //return myUser;

    }

    public void SearchUser(final String emailInput){
        myUser = new User();
        String email,customerName,ID,phone,type,address,driverLicense,pwd;

        // Display the data from the category selected in the previous activity
        db.collection(COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                if(document.getData().get("email").toString().equals(emailInput)){
                                    myUser.setEmail(document.getData().get(key[0]).toString());
                                    myUser.setCustomerName(document.getData().get(key[1]).toString());
                                    myUser.setId(document.getData().get(key[2]).toString());
                                    myUser.setPhone(document.getData().get(key[3]).toString());
                                    myUser.setType(document.getData().get(key[4]).toString());
                                    myUser.setAddress(document.getData().get(key[5]).toString());
                                    myUser.setDriverLicense(document.getData().get(key[6]).toString());
                                    myUser.setPwd(document.getData().get(key[7]).toString());
                                }
                            }

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                }); // Display the data from the category selected in the previous activity
        db.collection(COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                if(document.getData().get("email").toString().equals(emailInput)){
                                    myUser.setEmail(document.getData().get(key[0]).toString());
                                    myUser.setCustomerName(document.getData().get(key[1]).toString());
                                    myUser.setId(document.getData().get(key[2]).toString());
                                    myUser.setPhone(document.getData().get(key[3]).toString());
                                    myUser.setType(document.getData().get(key[4]).toString());
                                    myUser.setAddress(document.getData().get(key[5]).toString());
                                    myUser.setDriverLicense(document.getData().get(key[6]).toString());
                                    myUser.setPwd(document.getData().get(key[7]).toString());
                                }
                            }

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }



    public void Update(User myUser) {

        if (myUser != null) {

            Map<String, Object> user = new HashMap<>();

            user.put(key[0], myUser.getEmail());
            user.put(key[1], myUser.getCustomerName());
            user.put(key[2], myUser.getId());
            user.put(key[3], myUser.getPhone());
            user.put(key[4], myUser.getType());
            user.put(key[5], myUser.getAddress());
            user.put(key[6], myUser.getDriverLicense());
            try {
                user.put(key[7], getMd5(myUser.getPwd()));
            }  catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

            db.collection(COLLECTION_NAME).document(myUser.getEmail()).update(user);

        }
    }
}
