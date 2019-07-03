package ca.douglas.rentalcar.manager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import ca.douglas.rentalcar.DB.MyDBConnection;
import ca.douglas.rentalcar.R;
import ca.douglas.rentalcar.entity.Car;

public class AddNewCar extends AppCompatActivity {

    private FirebaseFirestore db;
    private final String COLLECTION_NAME = "Cars";
    private final String TAG = "Category" ;
    private final String []key = {"id","categ_Name","model","maker", "year","seats","millage","color","datePurchase","status","licensePlate"};
    private String CarID, Categ_Name, carModel, carMaker, carYear, carSeats,carMillage,carColor,carDatePurchase,carStatus,carLicensePlate;
    private EditText cModel, cMaker, cYear, cSeats,cMillage,cColor,cDatePurchase,cLicensePlate;
    private String selectedSpinnerStatus;
    private String selectedSpinnerCategory;
    Button btnAdd;
    Car myCar;
    private MyDBConnection dbc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_car);

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);


        cModel = findViewById(R.id.addModel);
        cMaker = findViewById(R.id.addMaker);
        cYear = findViewById(R.id.addYear);
        cSeats = findViewById(R.id.addSeats);
        cMillage = findViewById(R.id.addMillage);
        cColor = findViewById(R.id.addColor);
        cDatePurchase = findViewById(R.id.addPurchaseDate);
        cLicensePlate = findViewById(R.id.addLicensePlate);
        btnAdd = findViewById(R.id.btnAddCar);
        final Spinner spinnerStatus = findViewById(R.id.spinnerStatus);
        final Spinner spinnerCategory = findViewById(R.id.spinnerCategory);




        // Display the categories added to the DB into the Spinner
        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    final List<String> nameList = new ArrayList<String>();

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, document.getId() + " => " + document.getData().get("name").toString());
                                String catName = document.getData().get("name").toString();
                                nameList.add(catName);
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddNewCar.this, android.R.layout.simple_spinner_item, nameList);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerCategory.setAdapter(arrayAdapter);

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });



        //Get Car Category Selected from Spinner
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {

                selectedSpinnerCategory = spinnerCategory.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });





        //Get Car Status Selected from Spinner
        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {

                selectedSpinnerStatus = spinnerStatus.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carModel = cModel.getText().toString();
                carMaker = cMaker.getText().toString();
                carYear = cYear.getText().toString();
                carSeats = cSeats.getText().toString();
                carMillage = cMillage.getText().toString();
                carColor = cColor.getText().toString();
                carDatePurchase = cDatePurchase.getText().toString();
                carLicensePlate = cLicensePlate.getText().toString();





                myCar = new Car();
                //create new car
                CarID = "";
                for (int i=0 ; i < (int)(Math.random()*3) + 3; i++)
                    CarID += (char)((Math.random()*26) + 'A');

                //MISSING INPUT VALIDATION!

                myCar.setId(CarID);
                myCar.setCateg_Name(selectedSpinnerCategory);
                myCar.setModel(carModel);
                myCar.setMaker(carMaker);
                myCar.setYear(carYear);
                myCar.setSeats(carSeats);
                myCar.setMillage(carMillage);
                myCar.setColor(carColor);
                myCar.setDatePurchase(carDatePurchase);
                myCar.setStatus(selectedSpinnerStatus);
                myCar.setLicensePlate(carLicensePlate);

                //add user to database
                AddCar(myCar);


                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent i;
                        i = new Intent(AddNewCar.this, MainManager.class);
                        startActivity(i);
                    }
                }, 3000);

            }
        });

    }


    // =====================
    public void AddCar(Car myCar) {

        try{
            dbc = new MyDBConnection();

            Map<String, Object> car = new HashMap<>();

            car.put(key[0], myCar.getId());
            car.put(key[1], myCar.getCateg_Name());
            car.put(key[2], myCar.getModel());
            car.put(key[3], myCar.getMaker());
            car.put(key[4], myCar.getYear());
            car.put(key[5], myCar.getSeats());
            car.put(key[6], myCar.getMillage());
            car.put(key[7], myCar.getColor());
            car.put(key[8], myCar.getDatePurchase());
            car.put(key[9], myCar.getStatus());
            car.put(key[10], myCar.getLicensePlate());

            CollectionReference cars = dbc.getCollectionReference("Cars");
            cars.document(myCar.getId()).set(car);

            Toast.makeText(AddNewCar.this,
                    "Car Add Successfully", Toast.LENGTH_LONG).show();
        }

        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
