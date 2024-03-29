package ca.douglas.rentalcar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import ca.douglas.rentalcar.entity.User;
import ca.douglas.rentalcar.sales.MainSales;
import ca.douglas.rentalcar.user.MainUser;

public class UpdateUser extends AppCompatActivity {

    private String UID, customerName, customerEmail, customerPhone, getCustomerAccess = "client";
    private String customerAddress, customerLicense, customerPwd, customerConfPwd;
    private User myUser;
    private MainUser myMainUser;

    private FirebaseFirestore db;
    private final String COLLECTION_NAME = "Users";
    private final String TAG = "UpdateUser" ;
    private final String []key = {"email","name","id","phone", "type","address","license","password"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        final EditText cName = (EditText) findViewById(R.id.editUpdName);
        final EditText cEmail = (EditText) findViewById(R.id.editUpdEmail);
        final EditText cPhone = (EditText) findViewById(R.id.editUpdPhone);
        final EditText cAddress = (EditText) findViewById(R.id.editUpdAddress);
        final EditText cLicense = (EditText) findViewById(R.id.editUpdLicense);
        final EditText cPwd = (EditText) findViewById(R.id.editUpdPassword);
        final EditText cConfPwd = (EditText) findViewById(R.id.editUpdConfPassword);

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

        Button b1 = (Button) findViewById(R.id.btnUpd);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //validate data
                customerName = cName.getText().toString();
                customerEmail = cEmail.getText().toString();
                customerPhone = cPhone.getText().toString();
                customerAddress = cAddress.getText().toString();
                customerLicense = cLicense.getText().toString();
                customerPwd = cPwd.getText().toString();
                customerConfPwd = cConfPwd.getText().toString();

                if (customerName.length() == 0)
                    Toast.makeText(UpdateUser.this, "Please, enter your name", Toast.LENGTH_SHORT).show();
                else if (customerEmail.length() == 0)
                    Toast.makeText(UpdateUser.this, "Please, enter your email", Toast.LENGTH_SHORT).show();
                else if (customerPhone.length() == 0)
                    Toast.makeText(UpdateUser.this, "Please, enter your phone number", Toast.LENGTH_SHORT).show();
                else if (customerAddress.length() == 0)
                    Toast.makeText(UpdateUser.this, "Please, enter your address", Toast.LENGTH_SHORT).show();
                else if (customerLicense.length() == 0)
                    Toast.makeText(UpdateUser.this, "Please, enter your driver license", Toast.LENGTH_SHORT).show();
                else if (customerPwd.length() == 0)
                    Toast.makeText(UpdateUser.this, "Please, enter your password", Toast.LENGTH_SHORT).show();
                else if (customerConfPwd.length() == 0)
                    Toast.makeText(UpdateUser.this, "Please, confirm your password", Toast.LENGTH_SHORT).show();
                else if (customerPwd.toString().compareTo(customerConfPwd.toString())!=0)
                    Toast.makeText(UpdateUser.this, "Please, password does not match confirmation" +
                            customerPwd.toString() + " " + customerConfPwd.toString(), Toast.LENGTH_SHORT).show();
                else {

                    myUser = new User();
                    myMainUser = new MainUser();

                    //create new user
                    UID = "";
                    for (int i=0 ; i < (int)(Math.random()*5) + 5; i++)
                        UID += (char)((Math.random()*26) + 'A');

                    myUser.setId(UID);
                    myUser.setCustomerName(customerName);
                    myUser.setEmail(customerEmail);
                    myUser.setPhone(customerPhone);
                    myUser.setAddress(customerAddress);
                    myUser.setDriverLicense(customerLicense);
                    myUser.setPwd(customerPwd);
                    myUser.setType(getCustomerAccess);

                    //update usee to database
                    myMainUser.Update(myUser);

                    Toast.makeText(UpdateUser.this, "Data Updated. Welcome to Rental Car, " + myUser.getCustomerName() +
                            "!", Toast.LENGTH_LONG).show();

                    //open Customer Activity
                    Intent i;
                    i = new Intent(UpdateUser.this, MainSales.class);
                    startActivity(i);

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
