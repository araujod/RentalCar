package ca.douglas.rentalcar.manager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
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

import ca.douglas.rentalcar.R;
import ca.douglas.rentalcar.entity.Category;

public class EditCategory extends AppCompatActivity {
    private String catName;
    private FirebaseFirestore db;
    private final String COLLECTION_NAME = "Category";
    private final String []key = {"id","name","priceHr","priceDay", "feature"};
    private final String TAG = "Category" ;
    private String CID, cName, cPriceHr, cPriceDay, cFeature;
    Category myCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        Intent intent = getIntent();
        final EditText mCatName = findViewById(R.id.editCatName);
        final EditText mCatPriceHr = findViewById(R.id.editPriceHr);
        final EditText mCatPriceDay = findViewById(R.id.editPriceDay);
        final EditText mCatFeature = findViewById(R.id.editFeatures);
        Button btnUpdate = findViewById(R.id.btnUpdateCat);
        Button btnDelete = findViewById(R.id.btnDeleteCat);

        catName = intent.getStringExtra("CatName");



        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);


        // Display the data from the category selected in the previous activity
        db.collection(COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                if(document.getData().get("name").toString().equals(catName)){
                                    CID = document.getData().get("id").toString();
                                    mCatName.setText(document.getData().get("name").toString());
                                    mCatPriceHr.setText(document.getData().get("priceHr").toString());
                                    mCatPriceDay.setText(document.getData().get("priceDay").toString());
                                    mCatFeature.setText(document.getData().get("feature").toString());
                                }
                            }

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cName = mCatName.getText().toString();
                cPriceHr = mCatPriceHr.getText().toString();
                cPriceDay = mCatPriceDay.getText().toString();
                cFeature = mCatFeature.getText().toString();

                myCategory = new Category();

                myCategory.setId(CID);
                myCategory.setName(cName);
                myCategory.setPriceHr(cPriceHr);
                myCategory.setPriceDay(cPriceDay);
                myCategory.setFeature(cFeature);


                UpdateCategory(myCategory);

            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeleteCategory(CID);

            }
        });

    }

    public void UpdateCategory(Category myCategory) {

        try{

            Map<String, Object> category = new HashMap<>();

            category.put(key[0], myCategory.getId());
            category.put(key[1], myCategory.getName());
            category.put(key[2], myCategory.getPriceHr());
            category.put(key[3], myCategory.getPriceDay());
            category.put(key[4], myCategory.getFeature());

            db.collection(COLLECTION_NAME).document(CID).update(category);

            Toast.makeText(EditCategory.this,
                    "Category Edited Successfully", Toast.LENGTH_LONG).show();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent i;
                    i = new Intent(EditCategory.this, MainManager.class);
                    startActivity(i);
                }
            }, 2000);


        }

        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void DeleteCategory(String CID_) {

        try{

            db.collection(COLLECTION_NAME).document(CID_).delete();

            Toast.makeText(EditCategory.this,
                    "Category Delete Successfully", Toast.LENGTH_LONG).show();

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent i;
                    i = new Intent(EditCategory.this, MainManager.class);
                    startActivity(i);
                }
            }, 2000);

        }

        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
