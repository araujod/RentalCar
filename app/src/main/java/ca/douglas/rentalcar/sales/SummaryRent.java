package ca.douglas.rentalcar.sales;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ca.douglas.rentalcar.R;

public class SummaryRent extends AppCompatActivity {
    private TextView getStartDate;
    private TextView getStartTime;
    private TextView getEndDate;
    private TextView getEndTime;
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



    }
}
