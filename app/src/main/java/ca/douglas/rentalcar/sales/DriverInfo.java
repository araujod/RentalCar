package ca.douglas.rentalcar.sales;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ca.douglas.rentalcar.R;
import ca.douglas.rentalcar.SignUp;

public class DriverInfo extends AppCompatActivity {
    private final String TAG = "DriverInfo" ;
    private FirebaseFirestore db;
    private EditText searchDriverLicense;
    private TextView driverName,driverEmail,driverPhone,driverAddress, driverLicense;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_info);

        Button btnContinue = findViewById(R.id.btnContinue3);
        Button btnSearchDriverLice = findViewById(R.id.btnSerachDriveLicense);
        Button btnCreateUser = findViewById(R.id.btnCreateNewUser);

        final Intent intent = getIntent();

        final String getStartDate = intent.getStringExtra("StartDate");
        final String getStartTime = intent.getStringExtra("StartTime");
        final String getEndDate=intent.getStringExtra("EndDate");
        final String getEndTime = intent.getStringExtra("EndTime");
        searchDriverLicense = findViewById(R.id.eTDriverLicense);
        driverName= findViewById(R.id.txtName);
        driverEmail= findViewById(R.id.txtEmail);
        driverPhone= findViewById(R.id.txtPhone);
        driverAddress= findViewById(R.id.txtAddress);
        driverLicense= findViewById(R.id.txtDriverLicense);


        btnSearchDriverLice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                driverName.setText("");
                driverEmail.setText("");
                driverPhone.setText("");
                driverAddress.setText("");
                driverLicense.setText("");


                final String inputDriverLic = searchDriverLicense.getText().toString();


                db = FirebaseFirestore.getInstance();
                FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                        .setTimestampsInSnapshotsEnabled(true)
                        .build();
                db.setFirestoreSettings(settings);


                // Display the cars in the Inventory added to the DB into the ListView
                db.collection("Users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if(document.getData().get("license").toString().equals(inputDriverLic)) {
                                            driverName.setText(document.getData().get("name").toString());
                                            driverEmail.setText(document.getData().get("email").toString());
                                            driverPhone.setText(document.getData().get("phone").toString());
                                            driverAddress.setText(document.getData().get("address").toString());
                                            driverLicense.setText(document.getData().get("license").toString());
                                        }

                                    }

                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                }
                            }
                        });

                if(driverName.getText().toString().equals("")){
                   // Toast.makeText(DriverInfo.this, "Customer Not Found!", Toast.LENGTH_SHORT).show();

                }
            }
        });




        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(DriverInfo.this, SignUp.class);
                startActivity(i);
            }
        });


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(DriverInfo.this, SummaryRent.class);
                i.putExtra("StartDate", getStartDate);
                i.putExtra("StartTime", getStartTime);
                i.putExtra("EndDate", getEndDate);
                i.putExtra("EndTime", getEndTime);
                i.putExtra("carSelected", intent.getStringExtra("carSelected"));
                i.putExtra("driveLicense", driverLicense.getText().toString());
                i.putExtra("driverName", driverName.getText().toString());

                startActivity(i);
            }
        });



    }
}
