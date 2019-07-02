package ca.douglas.rentalcar.manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
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
    private final String []key = {"id","categ_ID","model","maker", "year","seats","millage","color","datePurchase","status","licensePlate"};
    private String CarID, carModel, carMaker, carYear, carSeats,carMillage,carColor,carDatePurchase,carStatus,carLicensePlate;
    private EditText cModel, cMaker, cYear, cSeats,cMillage,cColor,cDatePurchase,cLicensePlate;

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
                myCar.setCateg_ID("MISSING");
                myCar.setModel(carModel);
                myCar.setMaker(carMaker);
                myCar.setYear(carYear);
                myCar.setSeats(carSeats);
                myCar.setMillage(carMillage);
                myCar.setColor(carColor);
                myCar.setDatePurchase(carDatePurchase);
                myCar.setStatus("MISSING");
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
            car.put(key[1], myCar.getCateg_ID());
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
