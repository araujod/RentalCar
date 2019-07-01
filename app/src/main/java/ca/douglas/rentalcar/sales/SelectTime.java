package ca.douglas.rentalcar.sales;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import ca.douglas.rentalcar.MainActivity;
import ca.douglas.rentalcar.R;

public class SelectTime extends AppCompatActivity {

    private TextView mStartDate;
    private TextView mStartTime;
    private TextView mEndDate;
    private TextView mEndTime;
    private DatePickerDialog.OnDateSetListener mStartDateSetListener;
    private DatePickerDialog.OnDateSetListener mEndDateSetListener;
    private TimePickerDialog.OnTimeSetListener mStartTimeSetListener;
    private TimePickerDialog.OnTimeSetListener mEndTimeSetListener;
    Boolean checkStartDate = false;
    Boolean checkStartTime = false;
    Boolean checkEndDate = false;
    Boolean checkEndTime = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_time);

        mStartDate = findViewById(R.id.txtStartDate);
        mEndDate = findViewById(R.id.txtEndDate);
        mStartTime = findViewById(R.id.txtStartTime);
        mEndTime = findViewById(R.id.txtEndTime);
        Button btnContinue = findViewById(R.id.btnContinue);



        mStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SelectTime.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mStartDateSetListener,
                        year,month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mStartDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month+1;

                String date = month +"/" + day +"/" + year;
                mStartDate.setText(date);
                checkStartDate =true;

            }
        };



        mStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minutes = cal.get(Calendar.MINUTE);


                TimePickerDialog dialog = new TimePickerDialog(SelectTime.this,mStartTimeSetListener,hour,minutes,true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mStartTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mStartTime.setText(String.format("%02d:%02d", hourOfDay, minute));
                checkStartTime = true;

            }
        };

        //END TIME
        mEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SelectTime.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mEndDateSetListener,
                        year,month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mEndDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month+1;

                String date = month +"/" + day +"/" + year;
                mEndDate.setText(date);
                checkEndDate = true;

            }
        };



        mEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minutes = cal.get(Calendar.MINUTE);


                TimePickerDialog dialog = new TimePickerDialog(SelectTime.this,mEndTimeSetListener,hour,minutes,true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mEndTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mEndTime.setText(String.format("%02d:%02d", hourOfDay, minute));
                checkEndTime =true;

            }
        };


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!checkStartDate || !checkStartTime || !checkEndDate || !checkEndTime){
                    Toast.makeText(SelectTime.this,
                            "Please Select Date/Time!", Toast.LENGTH_LONG).show();
                }

                else if(!isDateAfter(mStartDate.getText().toString(),mEndDate.getText().toString())){
                }

                else{
                    Intent i;
                    i = new Intent(SelectTime.this, SelectCar.class);
                    i.putExtra("StartDate", mStartDate.getText().toString());
                    i.putExtra("StartTime", mStartTime.getText().toString());
                    i.putExtra("EndDate", mEndDate.getText().toString());
                    i.putExtra("EndTime", mEndTime.getText().toString());
                    startActivity(i);
                }
            }
        });


    }

    public  boolean isDateAfter(String startDate,String endDate)
    {
        try
        {
            String myFormatString = "MM/dd/yyyy"; // for example
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);
            Date date1 = df.parse(endDate);
            Date startingDate = df.parse(startDate);
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            month = month +1;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            String date = month +"/" + day +"/" + year;
            Date currentDate = df.parse(date);
            Boolean check = false;

            if (date1.after(startingDate))
                check= true;
            else {
                check = false;
                Toast.makeText(SelectTime.this,
                        "Your End Date Must Be After Start Date!", Toast.LENGTH_LONG).show();
            }

            if(startingDate.before(currentDate)){
                check = false;
                Toast.makeText(SelectTime.this,
                        "Your Start Date Must Be After Today's date!", Toast.LENGTH_LONG).show();
            }

            return check;
        }
        catch (Exception e)
        {

            return false;
        }
    }
}
