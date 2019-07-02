package ca.douglas.rentalcar.manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import ca.douglas.rentalcar.R;
import ca.douglas.rentalcar.entity.Category;
import ca.douglas.rentalcar.entity.User;
import ca.douglas.rentalcar.sales.SelectTime;

public class AddCategory extends AppCompatActivity {

    private FirebaseFirestore db;
    private final String COLLECTION_NAME = "Category";
    private final String TAG = "Category" ;
    private final String []key = {"id","name","priceHr","priceDay", "feature"};
    private String CID, cName, cPriceHr, cPriceDay, cFeature;
    private EditText name;
    private EditText priceHr;
    private EditText priceDay;
    private EditText feature;
    Button btnAdd;
    Category myCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        name = findViewById(R.id.addCatName);
        priceHr = findViewById(R.id.addPriceHr);
        priceDay = findViewById(R.id.addPriceDay);
        feature = findViewById(R.id.addFeatures);
        btnAdd = findViewById(R.id.btnAddCat);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            cName = name.getText().toString();
            cPriceHr = priceHr.getText().toString();
            cPriceDay = priceDay.getText().toString();
            cFeature = feature.getText().toString();

            myCategory = new Category();
                //create new category
                CID = "";
                for (int i=0 ; i < (int)(Math.random()*3) + 3; i++)
                    CID += (char)((Math.random()*26) + 'A');

                //MISSING INPUT VALIDATION!

                myCategory.setId(CID);
                myCategory.setName(cName);
                myCategory.setPriceHr(cPriceHr);
                myCategory.setPriceDay(cPriceDay);
                myCategory.setFeature(cFeature);

                //add user to database
                AddCategory(myCategory);


                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent i;
                        i = new Intent(AddCategory.this, MainManager.class);
                        startActivity(i);
                    }
                }, 5000);


            }
        });


    }



    // =====================
    public void AddCategory(Category myCategory) {

        try{

            Map<String, Object> category = new HashMap<>();

            category.put(key[0], myCategory.getId());
            category.put(key[1], myCategory.getName());
            category.put(key[2], myCategory.getPriceHr());
            category.put(key[3], myCategory.getPriceDay());
            category.put(key[4], myCategory.getFeature());


            // Add a new document with a generated ID
            db.collection(COLLECTION_NAME)
                    .add(category)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(AddCategory.this,
                                    "Category Add Successfully", Toast.LENGTH_LONG).show();
                            Log.d(TAG,
                                    "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Log.d(TAG,
                                    "Error adding document " + e);
                        }});
        }

        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
