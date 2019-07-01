package ca.douglas.rentalcar.sales;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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


        Button btnContinue = findViewById(R.id.btnContinue2);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(SelectCar.this, DriverInfo.class);
                i.putExtra("StartDate", getStartDate.getText().toString());
                i.putExtra("StartTime", getStartTime.getText().toString());
                i.putExtra("EndDate", getEndDate.getText().toString());
                i.putExtra("EndTime", getEndTime.getText().toString());
                startActivity(i);
            }
        });

    }
}
