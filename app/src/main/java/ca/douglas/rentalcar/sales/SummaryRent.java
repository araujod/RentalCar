package ca.douglas.rentalcar.sales;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import ca.douglas.rentalcar.R;
import ca.douglas.rentalcar.manager.AddCategory;
import ca.douglas.rentalcar.manager.MainManager;

public class SummaryRent extends AppCompatActivity {
    private final String TAG = "SummaryInfo" ;
    private FirebaseFirestore db;
    private TextView getStartDate;
    private TextView getStartTime;
    private TextView getEndDate;
    private TextView getEndTime;
    private  TextView driverName,driverLicense,carCat,carMake,carModel,carSeats,txtTotalDays;
    private  TextView deposit, price, txtTotal;
    private String getCat,pricePerDay;
    private String d1,d2;
    private long days;
    private double totalPrice;

    private String getCarSelected, getDriveLicense,getDriverName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_rent);
        Intent intent = getIntent();

        getStartDate = findViewById(R.id.txtStartDate3);
        getEndDate = findViewById(R.id.txtEndDate3);
        getStartTime = findViewById(R.id.txtStartTime3);
        getEndTime = findViewById(R.id.txtEndTime3);

        getStartDate.setText(intent.getStringExtra("StartDate"));
        getStartTime.setText(intent.getStringExtra("StartTime"));
        getEndDate.setText(intent.getStringExtra("EndDate"));
        getEndTime.setText(intent.getStringExtra("EndTime"));
        getCarSelected = intent.getStringExtra("carSelected");
        getDriveLicense = intent.getStringExtra("driveLicense");
        getDriverName = intent.getStringExtra("driverName");


        try {
            d1 = intent.getStringExtra("StartDate");
            d2 = intent.getStringExtra("EndDate");
            String myFormatString = "MM/dd/yyyy"; // for example
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);
            Date date1 = df.parse(d1);
            Date date2 = df.parse(d2);
            days = getDifferenceDays(date1, date2);

        }
        catch (Exception e)
        {

        }




        Toast.makeText(SummaryRent.this,
                String.valueOf(days), Toast.LENGTH_LONG).show();

        //TotalDays
        txtTotalDays = findViewById(R.id.txtTotalDays);
        txtTotalDays.setText(String.valueOf(days));


        //driver info
        driverName = findViewById(R.id.txtDriverName);
        driverLicense = findViewById(R.id.txtDriverLicense);
        driverName.setText(getDriverName);
        driverLicense.setText(getDriveLicense);

        //Car Info
         carCat = findViewById(R.id.txtCarCategory);
         carMake = findViewById(R.id.txtCarBrand);
         carModel = findViewById(R.id.txtCarModel);
         carSeats = findViewById(R.id.txtCarSeats);

         //Price
        deposit = findViewById(R.id.txtDeposit2);
        price = findViewById(R.id.txtPrice);
        txtTotal = findViewById(R.id.txtTotalS1);





        //Get car  INFO

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);


        // Display the cars in the Inventory added to the DB into the ListView
        db.collection("Cars")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getData().get("licensePlate").toString().equals(getCarSelected)) {
                                    carCat.setText(document.getData().get("categ_Name").toString());
                                    carMake.setText(document.getData().get("maker").toString());
                                    carModel.setText(document.getData().get("model").toString());
                                    carSeats.setText(document.getData().get("seats").toString());
                                    getCat = document.getData().get("categ_Name").toString();
                                }

                            }

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });



        deposit.setText("$300.00");

        // Display the cars in the Inventory added to the DB into the ListView
        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getData().get("name").toString().equals(getCat)) {
                                    price.setText(document.getData().get("priceDay").toString());
                                    pricePerDay = document.getData().get("priceDay").toString();
                                }

                            }

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


                price.setText(pricePerDay);

                txtTotal.setText("XXXXXXX");





    }

    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
}
