package ca.douglas.rentalcar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import ca.douglas.rentalcar.entity.User;
import ca.douglas.rentalcar.manager.MainManager;
import ca.douglas.rentalcar.sales.MainSales;
import ca.douglas.rentalcar.user.MainUser;

public class LogIn extends AppCompatActivity {

    private FirebaseFirestore db;
    private final String COLLECTION_NAME = "Users";
    private final String TAG = "LogIn" ;
    private final String []key = {"email","name","id","phone", "type","address","license","password"};

    private String customerEmail, customerPwd;
    private User myUser;
    private MainUser myMainUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        final EditText cEmail = (EditText) findViewById(R.id.editLogInEmail);
        final EditText cPwd = (EditText) findViewById(R.id.editLogInPassword);

        initialize();

        // Listening to changes
        final CollectionReference docRef = db.collection(COLLECTION_NAME);
        docRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && !snapshot.isEmpty()) {
                    List<DocumentChange> data = snapshot.getDocumentChanges();
                    Map<String, Object> user;
                    for (DocumentChange dc : data) {
                        user =dc.getDocument().getData();
                        Log.d(TAG, "Current data: ");
                        Log.d(TAG,user.get(key[0]) + ", " + user.get(key[1]) + ", " + user.get(key[2]));
                    }

                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });

<<<<<<< HEAD
        Button b1 = (Button) findViewById(R.id.btnUpdate);
=======



        Button b1 = (Button) findViewById(R.id.btnLogIn);
>>>>>>> master
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get user data
                customerEmail = cEmail.getText().toString();
                customerPwd = cPwd.getText().toString();

                if (customerEmail.length() == 0)
                    Toast.makeText(LogIn.this, "Please, enter your email", Toast.LENGTH_SHORT).show();
                else if (customerPwd.length() == 0)
                    Toast.makeText(LogIn.this, "Please, enter your password", Toast.LENGTH_SHORT).show();
                else {
                    Intent i;
                    i = new Intent(LogIn.this, UpdateUser.class);
                    startActivity(i);
                }
            }
        });

        Button b2 = (Button) findViewById(R.id.btnLogIn);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get user data
                customerEmail = cEmail.getText().toString();
                customerPwd = cPwd.getText().toString();

                if (customerEmail.length() == 0)
                    Toast.makeText(LogIn.this, "Please, enter your email", Toast.LENGTH_SHORT).show();
                else if (customerPwd.length() == 0)
                    Toast.makeText(LogIn.this, "Please, enter your password", Toast.LENGTH_SHORT).show();
                else {

                    myUser = new User();
                    myMainUser = new MainUser();
                    try{
                        customerPwd = myMainUser.getMd5(customerPwd);
                    }  catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }

                    //Verify if email already exists
<<<<<<< HEAD
                    myMainUser.Search(customerEmail);

                    myUser = myMainUser.myUser;
=======

                    //myUser = myMainUser.Search(customerEmail);


>>>>>>> master

                    if (myUser.getCustomerName() != null){

                        if (myUser.getPwd().compareTo(customerPwd) == 0){

                            Toast.makeText(LogIn.this, "Welcome to Rental Car " + myUser.getCustomerName(), Toast.LENGTH_LONG).show();
                            Intent i;
                            switch (myUser.getType()){
                                case "client":
                                    //open Customer Activity
                                    i = new Intent(LogIn.this, MainSales.class);
                                    startActivity(i);
                                    break;
                                case "manager":
                                    //open Manager Activity
                                    i = new Intent(LogIn.this, MainManager.class);
                                    startActivity(i);
                                    break;
                                case "sales":
                                    //open Sales Activity - NEED TO CONFIRM!
                                    i = new Intent(LogIn.this, MainSales.class);
                                    startActivity(i);
                                    break;
                            }
                        } else {
                            Toast.makeText(LogIn.this, "Wrong password. Please try again!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(LogIn.this, "Customer not found. Try Register!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    // ==================
    public void initialize() {

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

