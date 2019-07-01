package ca.douglas.rentalcar.sales;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ca.douglas.rentalcar.R;

public class DriverInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_info);

        Button btnContinue = findViewById(R.id.btnContinue3);

        Intent intent = getIntent();

        final String getStartDate = intent.getStringExtra("StartDate");
        final String getStartTime = intent.getStringExtra("StartTime");
        final String getEndDate=intent.getStringExtra("EndDate");
        final String getEndTime = intent.getStringExtra("EndTime");


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(DriverInfo.this, SummaryRent.class);
                i.putExtra("StartDate", getStartDate);
                i.putExtra("StartTime", getStartTime);
                i.putExtra("EndDate", getEndDate);
                i.putExtra("EndTime", getEndTime);
                startActivity(i);
            }
        });



    }
}
