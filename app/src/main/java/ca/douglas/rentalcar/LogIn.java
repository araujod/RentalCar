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
import ca.douglas.rentalcar.manager.AddNewCar;
import ca.douglas.rentalcar.manager.MainManager;
import ca.douglas.rentalcar.sales.MainSales;
import ca.douglas.rentalcar.user.MainUser;

public class LogIn extends AppCompatActivity {

    private FirebaseFirestore db;
    private final String COLLECTION_NAME = "Users";
    private final String TAG = "LogIn" ;
    private final String []key = {"email","name","id","phone", "type","address","license","password"};
    private String type, UserName;

    private String customerEmail, customerPwd;
    User myUser;
    MainUser myMainUser;
    boolean checkEmail =false;
    boolean checkPwd =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        final EditText cEmail = (EditText) findViewById(R.id.editLogInEmail);
        final EditText cPwd = (EditText) findViewById(R.id.editLogInPassword);



        Button b1 = (Button) findViewById(R.id.btnUpdate);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialize();

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
                    initialize();
                    db.collection(COLLECTION_NAME)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {

                                            if(document.getData().get("email").toString().equals(customerEmail)){
                                                myUser.setEmail(document.getData().get(key[0]).toString());
                                                myUser.setCustomerName(document.getData().get(key[1]).toString());
                                                myUser.setId(document.getData().get(key[2]).toString());
                                                myUser.setPhone(document.getData().get(key[3]).toString());
                                                myUser.setType(document.getData().get(key[4]).toString());
                                                myUser.setAddress(document.getData().get(key[5]).toString());
                                                myUser.setDriverLicense(document.getData().get(key[6]).toString());
                                                myUser.setPwd(document.getData().get(key[7]).toString());

                                                type = myUser.getType();
                                                UserName = myUser.getCustomerName();

                                                checkEmail = true;

                                                if(myUser.getPwd().equals(customerPwd)){
                                                    checkPwd = true;
                                                }

                                            }
                                        }

                                    } else {
                                        Log.w(TAG, "Error getting documents.", task.getException());
                                    }
                                }
                            });




                    if (checkEmail){

                        if (checkPwd){

                            Toast.makeText(LogIn.this, "Welcome to Rental Car " + UserName, Toast.LENGTH_LONG).show();
                            Intent i;
                            switch (type){
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

