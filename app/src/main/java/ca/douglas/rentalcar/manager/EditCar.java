package ca.douglas.rentalcar.manager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import ca.douglas.rentalcar.entity.Category;

public class EditCar extends AppCompatActivity {
    private FirebaseFirestore db;
    private final String COLLECTION_NAME = "Cars";
    private final String TAG = "Car" ;
    private final String []key = {"id","categ_Name","model","maker", "year","seats","millage","color","datePurchase","status","licensePlate"};
    private String CarID, Categ_Name, carModel, carMaker, carYear, carSeats,carMillage,carColor,carDatePurchase,carStatus,carLicensePlate;
    private EditText cModel, cMaker, cYear, cSeats,cMillage,cColor,cDatePurchase,cLicensePlate;
    private String selectedSpinnerStatus;
    private String selectedSpinnerCategory;
    Button btnEdit;
    Button btnDelete;
    Car myCar;
    private MyDBConnection dbc;
    //String to get intent
    private String getCarLicensePlate;
    private String Car_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car);

        Intent intent = getIntent();
        getCarLicensePlate = intent.getStringExtra("CarLicense");

        cModel = findViewById(R.id.editModel);
        cMaker = findViewById(R.id.editMaker);
        cYear = findViewById(R.id.editYear);
        cSeats = findViewById(R.id.editSeats);
        cMillage = findViewById(R.id.editMillage);
        cColor = findViewById(R.id.editColor);
        cDatePurchase = findViewById(R.id.editPurchaseDate);
        cLicensePlate = findViewById(R.id.editLicensePlate);
        btnEdit = findViewById(R.id.btnEditCar);
        btnDelete = findViewById(R.id.btnDeleteCar);
        final Spinner spinnerEditStatus = findViewById(R.id.spinnerEditStatus);
        final Spinner spinnerEditCategory = findViewById(R.id.spinnerEditCategory);


        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);


        // Display the data from the car selected in the previous activity
        db.collection(COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                if(document.getData().get("licensePlate").toString().equals(getCarLicensePlate)){
                                    Car_ID = document.getData().get("id").toString();
                                    cModel.setText(document.getData().get("model").toString());
                                    cMaker.setText(document.getData().get("maker").toString());
                                    cYear.setText(document.getData().get("year").toString());
                                    cSeats.setText(document.getData().get("seats").toString());
                                    cMillage.setText(document.getData().get("millage").toString());
                                    cColor.setText(document.getData().get("color").toString());
                                    cDatePurchase.setText(document.getData().get("datePurchase").toString());
                                    cLicensePlate.setText(document.getData().get("licensePlate").toString());
                                    Categ_Name = document.getData().get("categ_Name").toString();
                                    carStatus = document.getData().get("status").toString();
                                    //Get the previous Status of the Car
                                    selectSpinnerValue(spinnerEditStatus,carStatus);
                                }
                            }

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


        //Inflate the spinner Category
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
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(EditCar.this, android.R.layout.simple_spinner_item, nameList);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerEditCategory.setAdapter(arrayAdapter);
                            selectSpinnerValue(spinnerEditCategory,Categ_Name);

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        //Get Car Category Selected from Spinner
        spinnerEditCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {

                selectedSpinnerCategory = spinnerEditCategory.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });





        //Get Car Status Selected from Spinner
        spinnerEditStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {

                selectedSpinnerStatus = spinnerEditStatus.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        btnEdit.setOnClickListener(new View.OnClickListener() {
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

                //create new car
                myCar = new Car();

                myCar.setId(Car_ID);
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


                UpdateCar(myCar);

            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteCar(Car_ID);

            }
        });


    }


    public void UpdateCar(Car myCar) {

        try{

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

            db.collection(COLLECTION_NAME).document(Car_ID).update(car);

            Toast.makeText(EditCar.this,
                    "Category Edited Successfully", Toast.LENGTH_LONG).show();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent i;
                    i = new Intent(EditCar.this, MainManager.class);
                    startActivity(i);
                }
            }, 2000);


        }

        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void DeleteCar(String CarID_) {

        try{

            db.collection(COLLECTION_NAME).document(CarID_).delete();

            Toast.makeText(EditCar.this,
                    "Car Delete Successfully", Toast.LENGTH_LONG).show();

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent i;
                    i = new Intent(EditCar.this, MainManager.class);
                    startActivity(i);
                }
            }, 2000);

        }

        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void selectSpinnerValue(Spinner spinner, String myString)
    {
        int index = 0;
        for(int i = 0; i < spinner.getCount(); i++){
            if(spinner.getItemAtPosition(i).toString().equals(myString)){
                spinner.setSelection(i);
                break;
            }
        }
    }
}
