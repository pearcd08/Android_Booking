package com.example.assignmentone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UserBooking2 extends AppCompatActivity {

    private String date;
    public static String time;
    private String userLicence;
    private TextView tvDate;
    private RecyclerView rvTime;
    private TimeBooking_Holder rvHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_booking2);
        rvTime = findViewById(R.id.rv_time);

        displayDate();
        displayTimes();



    }

    private void displayDate(){
        tvDate = findViewById(R.id.tv_displayDate);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            date = extras.getString("date");
            userLicence = extras.getString("userLicence");
            tvDate.setText("Bookings for " + date);

        }

    }

    //TO DO: Look at confirmed bookings to disable times.
    private void displayTimes(){
        String []data = {"09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00"};
        TimeBooking_Adapter adapter = new TimeBooking_Adapter(data);
        rvTime.setLayoutManager( new LinearLayoutManager(this));
        rvTime.setAdapter(adapter);

    }


    public void openBookingThree(View view) {
        Intent intent = new Intent(this, UserBooking3.class);
        intent.putExtra("date", date);
        intent.putExtra("time", time);
        intent.putExtra("userLicence", userLicence);
        startActivity(intent);
    }
}



