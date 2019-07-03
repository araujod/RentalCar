package ca.douglas.rentalcar.sales;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

import ca.douglas.rentalcar.LogIn;
import ca.douglas.rentalcar.R;
import ca.douglas.rentalcar.manager.CarOption;

public class SelectCar extends AppCompatActivity {
    private TextView getStartDate;
    private TextView getStartTime;
    private TextView getEndDate;
    private TextView getEndTime;
    private ListView listCars;
    private String getLicensePlate;
    private final String TAG = "SelectCars" ;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_car);

        Intent intent = getIntent();

        getStartDate = findViewById(R.id.txtStartDate2);
        getEndDate = findViewById(R.id.txtEndDate2);
        getStartTime = findViewById(R.id.txtStartTime2);
        getEndTime = findViewById(R.id.txtEndTime2);

        getStartDate.setText(intent.getStringExtra("StartDate"));
        getStartTime.setText(intent.getStringExtra("StartTime"));
        getEndDate.setText(intent.getStringExtra("EndDate"));
        getEndTime.setText(intent.getStringExtra("EndTime"));


        listCars = findViewById(R.id.listViewSelectCar);

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);



        // Display the cars in the Inventory added to the DB into the ListView
        db.collection("Cars")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    final List<String> nameList = new ArrayList<String>();

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getData().get("status").toString().equals("Available")) {
                                    String carName = document.getData().get("maker").toString() + " " + document.getData().get("model").toString() + " Category: " +
                                            document.getData().get("categ_Name").toString() + " \n " + "License Plate:" + document.getData().get("licensePlate").toString() +
                                            " Status: " + document.getData().get("status").toString();
                                    nameList.add(carName);
                                }


                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SelectCar.this, android.R.layout.simple_expandable_list_item_1, nameList);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            listCars.setAdapter(arrayAdapter);

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        listCars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                for (int j = 0; j < adapterView.getChildCount(); j++)
                    adapterView.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);

                // change the background color of the selected element
                view.setBackgroundColor(Color.LTGRAY);

                String selectedString = (listCars.getItemAtPosition(i)).toString();

                int endIndex = selectedString.indexOf(" Status");
                int startIndex = selectedString.indexOf("Plate:")+6;
                getLicensePlate = selectedString.substring(startIndex, endIndex);

                Toast.makeText(SelectCar.this, getLicensePlate, Toast.LENGTH_SHORT).show();


            }
        });







        Button btnContinue = findViewById(R.id.btnContinue2);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(SelectCar.this, DriverInfo.class);
                i.putExtra("StartDate", getStartDate.getText().toString());
                i.putExtra("StartTime", getStartTime.getText().toString());
                i.putExtra("EndDate", getEndDate.getText().toString());
                i.putExtra("EndTime", getEndTime.getText().toString());
                i.putExtra("carSelected", getLicensePlate);
                startActivity(i);
            }
        });

    }
}
