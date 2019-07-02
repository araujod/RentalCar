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
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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
import ca.douglas.rentalcar.entity.Category;

public class CategoryOption extends AppCompatActivity {
    private FirebaseFirestore db;
    private final String TAG = "Category" ;
    private String selected_val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_option);

        final Spinner catSpninner = findViewById(R.id.spinnerCat);

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);


        // Display the categories added to the DB into the Spinner

        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    final List<String> nameList = new ArrayList<String>();

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData().get("name").toString());
                                String catName = document.getData().get("name").toString();
                                nameList.add(catName);
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CategoryOption.this, android.R.layout.simple_spinner_item, nameList);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            catSpninner.setAdapter(arrayAdapter);

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


        //Get Category Selected from the Spinner to Edit in the next activity
        catSpninner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {

                 selected_val = catSpninner.getSelectedItem().toString();

                Toast.makeText(getApplicationContext(), selected_val,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        Button btnAddNewCat = (Button) findViewById(R.id.btnAddNewCat);
        Button btnEditCat = (Button) findViewById(R.id.btnEditCat);

        btnAddNewCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(CategoryOption.this, AddCategory.class);
                startActivity(i);
            }
        });

        btnEditCat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(CategoryOption.this, EditCategory.class);
                i.putExtra("CatName", selected_val);
                startActivity(i);
            }
        });

    }
}
