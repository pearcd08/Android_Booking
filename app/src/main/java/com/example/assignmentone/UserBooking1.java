package com.example.assignmentone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import java.util.Date;

public class UserBooking1 extends AppCompatActivity {

    private String userLicence;
    private String date;
    private TextView tvSelectTime;
    private CalendarView calendarView;
    private RecyclerView rvTime;
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_booking);

        tvSelectTime = (TextView) findViewById(R.id.tv_selectTime);
        rvTime = (RecyclerView) findViewById(R.id.rv_time);
        btnConfirm = (Button) findViewById(R.id.btn_confirmBooking);

        //disable the button on load
        btnConfirm.setEnabled(false);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userLicence = extras.getString("userLicence");
        }

        calendarView = (CalendarView) findViewById(R.id.cal_booking);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                date = (i1 + 1) + "/" + i2 + "/" + i;
                tvSelectTime.setText("Select time for " + date);
                rvTime.requestFocus();


            }
        });
        calendarView.setMinDate((new Date().getTime()));


    }

    public void confirmBooking(View view) {
        Intent intent = new Intent(UserBooking1.this, UserBooking2.class);
        intent.putExtra("date", date);
        startActivity(intent);    }
}