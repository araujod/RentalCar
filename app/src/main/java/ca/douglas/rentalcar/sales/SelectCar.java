package ca.douglas.rentalcar.sales;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ca.douglas.rentalcar.R;

public class SelectCar extends AppCompatActivity {
    private TextView getStartDate;
    private TextView getStartTime;
    private TextView getEndDate;
    private TextView getEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_car);

        Intent intent = getIntent();

        getStartDate = findViewById(R.id.txtStartDate2);
        getEndDate = findViewById(R.id.txtEndDate2);
        getStartTime = findViewById(R.id.txtStartTime2);
        getEndTime = findViewById(R.id.txtEndTime2);

        getStartDate.setText(intent.getStringExtra("StartDate"));
        getStartTime.setText(intent.getStringExtra("StartTime"));
        getEndDate.setText(intent.getStringExtra("EndDate"));
        getEndTime.setText(intent.getStringExtra("EndTime"));

    }
}
