package ca.douglas.rentalcar.sales;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ca.douglas.rentalcar.MainActivity;
import ca.douglas.rentalcar.R;

public class MainSales extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sales);

        Button btnCreateRent = (Button)findViewById(R.id.btnCreatRent);

        btnCreateRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MainSales.this, SelectTime.class);
                startActivity(i);
            }
        });
    }
}
