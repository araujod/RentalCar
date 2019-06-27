package ca.douglas.rentalcar.manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ca.douglas.rentalcar.R;

public class CarOption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_option);

        Button btnAddCar= (Button)findViewById(R.id.btnAddNewCar);

        btnAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(CarOption.this, AddNewCar.class);
                startActivity(i);
            }
        });

    }
}
