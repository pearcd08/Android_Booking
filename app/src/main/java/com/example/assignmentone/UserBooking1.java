package com.example.assignmentone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class UserBooking1 extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private String userID, userLicence;
    private String date;
    public static String time;
    private DatePickerDialog datePickerDialog;
    private Button btnNext;
    private FirebaseDatabase fbDB;
    private DatabaseReference dbRef;
    private RecyclerView rvTime;
    private TextView tv_date;
    private static final String TAG = "UserBooking1";
    private ArrayList<String> instructorsArray = new ArrayList<>();
    private ArrayList<String> availableInstructorsArray = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_booking);

        fbDB = FirebaseDatabase.getInstance();
        dbRef = fbDB.getReference();
        rvTime = findViewById(R.id.rv_time);

        tv_date = findViewById(R.id.txt_userbooking_date);
        btnNext = findViewById(R.id.btn_confirmDate);
        //disable the button on load
        btnNext.setEnabled(false);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getString("userID");
            userLicence = extras.getString("licence");
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
        //loop though dates booked by logged in user

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

                    } else {
                        btnNext.setEnabled(true);
                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getInstructors() {
        //count of the instuctors list
        dbRef.child("instructors").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot iDB : snapshot.getChildren()) {
                    String dbInstructorID = iDB.getKey().toString();
                    instructorsArray.add(dbInstructorID);
                }
                getTimes(instructorsArray);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void getTimes(ArrayList<String> instructorsArray) {
        Toast.makeText(UserBooking1.this, "Instructors Count" + instructorsArray.size(),
                Toast.LENGTH_SHORT).show();

        String[] timeArray = {"09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00"};
        int[] timeSlotBookings = new int[timeArray.length];
        for (int i = 0; i < timeSlotBookings.length; i++) {
            timeSlotBookings[i] = instructorsArray.size();
            int tttime = timeSlotBookings[i];

            //2. loop through each booking to find date and time
            dbRef.child("bookings").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //Create a new Array for the timeslots
                    //Create an array to put in bookings available
                    //loop through booking database

                    Toast.makeText(UserBooking1.this, "Timeslotbooking lenght" + timeSlotBookings.length, Toast.LENGTH_SHORT).show();
                    if (snapshot.exists()) {
                        for (DataSnapshot bDB : snapshot.getChildren()) {
                            //check if the booking database exists
                            String dbDate = bDB.child("date").getValue(String.class);
                            String dbTime = bDB.child("time").getValue(String.class);
                            String dbInstructor = bDB.child("instructorID").getValue(String.class);
                            //if a booking has the selected date check each time

                            if (date.equals(dbDate)) {
                                int timeInstructorCount = instructorsArray.size();
                                for (int i = 0; i < timeSlotBookings.length; i++) {
                                    //find the time that has a booking that day
                                    if (timeArray[i].equals(dbTime)) {
                                        //loop through instructor list
                                        for (int j = 0; j < instructorsArray.size(); j++) {
                                            if (instructorsArray.get(j).equals(dbInstructor)) {
                                                timeInstructorCount = timeInstructorCount - 1;

                                            }
                                        }
                                    }
                                    //set that time for how many instructors are available
                                    timeSlotBookings[i] = timeInstructorCount;

                                }
                            }
                            //no bookings on that day, make each time slot the count of instructors array
                            else {
                                for (int i = 0; i < timeSlotBookings.length; i++) {
                                    timeSlotBookings[i] = instructorsArray.size();
                                    Toast.makeText(UserBooking1.this, timeSlotBookings[i], Toast.LENGTH_SHORT).show();

                                }

                            }
                        }

                    } else {
                        for (int i = 0; i < timeSlotBookings.length; i++) {
                            timeSlotBookings[i] = instructorsArray.size();
                            int ttime = timeSlotBookings[i];


                        }
                        Log.isLoggable(TAG, timeSlotBookings.length);

                        TimeBooking_Adapter adapter = new TimeBooking_Adapter(timeArray, timeSlotBookings);
                        rvTime.setLayoutManager(new

                                GridLayoutManager(UserBooking1.this, 2));
                        rvTime.setAdapter(adapter);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void assignInstructor(String time) {
        dbRef.child("bookings").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                availableInstructorsArray.clear();
                for (DataSnapshot bDB : snapshot.getChildren()) {
                    String dbDate = bDB.child("date").getValue(String.class);
                    String dbTime = bDB.child("time").getValue(String.class);
                    String dbInstructor = bDB.child("instructorID").getValue(String.class);
                    if (dbDate.equals(date) && dbTime.equals(time)) {
                        for (int i = 0; i < instructorsArray.size(); i++) {
                            if (!dbInstructor.equals(instructorsArray.get(i))) {
                                availableInstructorsArray.add(instructorsArray.get(i));
                            }
                        }
                    } else {
                        for (int i = 0; i < instructorsArray.size(); i++) {
                            availableInstructorsArray.add(instructorsArray.get(i));

                        }
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private String randomInstructor(){
        Random rand = new Random();
        int i = rand.nextInt(availableInstructorsArray.size());
        String instructor = availableInstructorsArray.get(i);
        return instructor;


    }


    public void openBookingTwo(View view) {
        Intent intent = new Intent(UserBooking1.this, UserBooking2.class);
        intent.putExtra("date", date);
        intent.putExtra("time", time);
        intent.putExtra("userID", userID);
        intent.putExtra("instructor", randomInstructor());
        startActivity(intent);
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        checkBooking();
        getInstructors();
        tv_date.setText(date);

    }

    public void userBookingBack(View view) {
        Intent intent = new Intent(UserBooking1.this, UserHome.class);
        intent.putExtra("userLicence", userID);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }

    public void openCalendar(View view) {
        showCalendar();
    }
}
