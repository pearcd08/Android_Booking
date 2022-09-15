package com.example.assignmentone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmentone.db.Booking;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class UserBooking1 extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private String userID;
    private String date;
    private DatePickerDialog datePickerDialog;
    private Button btnNext;
    private FirebaseDatabase fbDB;
    private DatabaseReference dbRef;

    private RecyclerView rvTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_booking);

        fbDB = FirebaseDatabase.getInstance();
        dbRef = fbDB.getReference();
        rvTime = findViewById(R.id.rv_time);




        btnNext = findViewById(R.id.btn_confirmDate);
        //disable the button on load
        btnNext.setEnabled(false);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getString("userID");
        }
        showCalendar();

    }



    private void showCalendar() {
        Calendar today = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(UserBooking1.this,
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show(getSupportFragmentManager(), "DatePickerDialog");

        //Restrict to weekends
        ArrayList<Calendar> weekends = new ArrayList<Calendar>();
        Calendar day = Calendar.getInstance();
        for (int i = 0; i < 365; i++) {
            if (day.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                    day.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                Calendar d = (Calendar) day.clone();
                weekends.add(d);
            }
            day.add(Calendar.DATE, 1);
        }
        Calendar[] weekendDays = weekends.toArray(new Calendar[weekends.size()]);
        datePickerDialog.setDisabledDays(weekendDays);

        //Remove Booked Dates;

    }


    private void checkBooking() {
        dbRef.child("bookings").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> bookedDates = new ArrayList<>();
                for (DataSnapshot bookingData : snapshot.getChildren()) {
                    String dbUserID = bookingData.child("userID").getValue(String.class);
                    String dbDate = bookingData.child("date").getValue(String.class);

                    if (dbUserID.equals(userID) && dbDate.equals(date)) {

                        //get date, put in array, showCalender(array) to disable dates that are already booked
                        btnNext.setEnabled(false);
                        Toast.makeText(UserBooking1.this, "You already have a booking on " + date,
                                Toast.LENGTH_SHORT).show();

                    }

                    else {
                        btnNext.setEnabled(true);
                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkTimes() {

        dbRef.child("bookings").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Create a new Array for the timeslots
                String[] data = {"09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00"};

                //loop bookings and minus 1 for each booking at that time from available bookings

                TimeBooking_Adapter adapter = new TimeBooking_Adapter(data);
                rvTime.setLayoutManager(new LinearLayoutManager(UserBooking1.this));
                rvTime.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void openBookingTwo(View view) {
        Intent intent = new Intent(UserBooking1.this, UserBooking2.class);
        intent.putExtra("date", date);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date = dayOfMonth +"/" +  (monthOfYear + 1) + "/" + year;
        checkBooking();
        checkTimes();

    }
}
