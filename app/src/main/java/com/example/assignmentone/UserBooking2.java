package com.example.assignmentone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class UserBooking2 extends AppCompatActivity {

    private String date;
    private TextView tvDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_booking2);
        displayDate();

    }

    private void displayDate(){
        tvDate = (TextView) findViewById(R.id.tv_displayDate);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            date = extras.getString("date");
        }

        tvDate.setText("Bookings for " + date);
    }
}

