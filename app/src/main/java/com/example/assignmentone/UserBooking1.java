package com.example.assignmentone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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

    private Button btnNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_booking);

        btnNext = findViewById(R.id.btn_confirmDate);
        //disable the button on load
        btnNext.setEnabled(false);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userLicence = extras.getString("userLicence");
        }
        calendarView = findViewById(R.id.cal_booking);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                date = (i1 + 1) + "/" + i2 + "/" + i;
                btnNext.setEnabled(true);

            }
        });
        calendarView.setMinDate((new Date().getTime()));
    }


    public void openBookingTwo(View view) {
        Intent intent = new Intent(UserBooking1.this, UserBooking2.class);
        intent.putExtra("date", date);
        intent.putExtra("userLicence", userLicence);
        startActivity(intent);
    }
}