package ca.douglas.rentalcar.manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ca.douglas.rentalcar.MainActivity;
import ca.douglas.rentalcar.R;

public class MainManager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_manager);

        Button btnAddEditCat = (Button)findViewById(R.id.btnAddEditCategory);
        Button btnAddEditCar = (Button)findViewById(R.id.btnAddEditCar);

        btnAddEditCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MainManager.this, CategoryOption.class);
                startActivity(i);
            }
        });

        btnAddEditCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MainManager.this, CarOption.class);
                startActivity(i);
            }
        });



    }
}
