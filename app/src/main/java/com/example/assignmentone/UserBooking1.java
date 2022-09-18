package com.example.assignmentone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

public class UserBooking1 extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static String selectedTime, selectedDate, selectedInstructor, selectedInstructorID;
    private String userID, userLicence, userName, instructorName, instructorID;
    private DatePickerDialog datePickerDialog;
    public static Button btnNext;
    private FirebaseDatabase fbDB;
    private DatabaseReference dbRef;
    private RecyclerView rvTime;
    private TextView tv_date;
    private ArrayList<String> instructorsArray = new ArrayList<>();
    private ArrayList<String> availableInstructorsArray = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_booking);

        fbDB = FirebaseDatabase.getInstance();
        dbRef = fbDB.getReference();

        rvTime = findViewById(R.id.rv_time);

        selectedTime = "";
        selectedDate = "";

        tv_date = findViewById(R.id.txt_userbooking_date);
        btnNext = findViewById(R.id.btn_confirmDate);
        //disable the button on load


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getString("userID");
            userName = extras.getString("userName");
            userLicence = extras.getString("userLicence");

        }
        btnNext.setEnabled(false);
        btnNext.setBackgroundColor(Color.parseColor("#808080"));
        getInstructors();
        showCalendar();
    }

    private void getInstructors() {
        //count of the instuctors list
        dbRef.child("instructors").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot iDB : snapshot.getChildren()) {
                    String dbInstructorName = iDB.child("name").getValue(String.class);
                    instructorsArray.add(dbInstructorName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void showCalendar() {
        Calendar today = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(UserBooking1.this,
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show(getSupportFragmentManager(), "DatePickerDialog");
        datePickerDialog.setMinDate(today);

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

    private void checkBooking(String newDate) {
        dbRef.child("bookings").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int bookedDateCount = 0;
                for (DataSnapshot bookingData : snapshot.getChildren()) {
                    String dbLicence = bookingData.child("licence").getValue(String.class);
                    String dbDate = bookingData.child("date").getValue(String.class);

                    if (dbLicence.equals(userLicence) && dbDate.equals(newDate)) {
                        //get date, put in array, showCalender(array) to disable dates that are already booked
                        Toast.makeText(UserBooking1.this,
                                "You already have Booking on this date ",
                                Toast.LENGTH_SHORT).show();
                        bookedDateCount++;
                        rvTime.setAlpha(0);
                        break;
                    }
                }
                if (bookedDateCount == 0) {
                    getTimes(newDate);
                    rvTime.setAlpha(1);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void getTimes(String newDate) {

        String[] timeArray = {"09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00"};


        //2. loop through each booking to find date and time
        dbRef.child("bookings").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String[] bookedSlots = new String[timeArray.length];
                ArrayList<String> timesToBook = new ArrayList<>();
                int dateCount = 0;
                for (DataSnapshot bookingData : snapshot.getChildren()) {
                    String dbDate = bookingData.child("date").getValue(String.class);
                    String dbTime = bookingData.child("time").getValue(String.class);
                    for (int i = 0; i < timeArray.length; i++) {
                        if (dbDate.equals(newDate) && dbTime.equals(timeArray[i])) {
                            timesToBook.add(dbTime);
                        }
                    }
                }
                for (int i = 0; i < timeArray.length; i++) {
                    if (timesToBook.contains(timeArray[i])) {
                        bookedSlots[i] = "BOOKED";
                    } else if (timesToBook.isEmpty()) {
                        bookedSlots[i] = "AVAILABLE";
                    } else {
                        bookedSlots[i] = "AVAILABLE";
                    }
                }

                assignInstructors(newDate, bookedSlots);
              /*  TimeBooking_Adapter adapter = new TimeBooking_Adapter(timeArray, bookedSlots);
               rvTime.setLayoutManager(new
                       GridLayoutManager(UserBooking1.this, 2));
               rvTime.setAdapter(adapter);*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }

    private void assignInstructors(String newDate, String[] bookedSlots) {
        String[] timeArray = {"09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00"};


        //2. loop through each booking to find date and time
        dbRef.child("bookings").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String[] instructorsForBooking = new String[timeArray.length];
                int bookedDateCount = 0;
                for (DataSnapshot bookingData : snapshot.getChildren()) {
                    String dbDate = bookingData.child("date"
                    ).getValue(String.class);
                    String dbTime = bookingData.child("time").getValue(String.class);
                    String dbInstructorName = bookingData.child("instructorName").getValue(String.class);
                    for (int i = 0; i < timeArray.length; i++) {
                        ArrayList<String> instructorsToRemove = new ArrayList<String>();
                        instructorsToRemove = (ArrayList) instructorsArray.clone();
                        int instructorBookedCount = 0;
                        if (dbDate.equals(newDate) && dbTime.equals(timeArray[i])) {
                            bookedDateCount++;
                            for (int j = 0; j < instructorsToRemove.size(); j++) {
                                if (instructorsToRemove.contains(dbInstructorName)) {
                                    instructorBookedCount++;
                                    instructorsForBooking[i] = dbInstructorName;
                                }
                            }

                        }
                        if(instructorBookedCount == 0){
                            int selectedInstructor = (int) (Math.random() * instructorsArray.size());
                            instructorsForBooking[i] = instructorsArray.get(selectedInstructor);

                        }


                    }
                 /*  TimeBooking_Adapter adapter = new TimeBooking_Adapter
                            (timeArray, bookedSlots, instructorsForBooking);
                   rvTime.setLayoutManager(new
                           GridLayoutManager(UserBooking1.this, 2));
                   rvTime.setAdapter(adapter);*/
                }
                if (bookedDateCount == 0) {
                    for (int i = 0; i < timeArray.length; i++){
                        int selectedInstructor = (int) (Math.random() * instructorsArray.size());
                        instructorsForBooking[i] = instructorsArray.get(selectedInstructor);

                    }


                }
                TimeBooking_Adapter adapter = new TimeBooking_Adapter
                        (timeArray, bookedSlots, instructorsForBooking);
                rvTime.setLayoutManager(new
                        GridLayoutManager(UserBooking1.this, 2));
                rvTime.setAdapter(adapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }
     private void getInstructorID(){
         dbRef.child("instructors").addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 for (DataSnapshot iDB : snapshot.getChildren()) {

                     String dbInstructorID = iDB.child("instructorID").getValue(String.class);
                     String dbName = iDB.child("name").getValue(String.class);
                     if(dbName.equals(selectedInstructor)){
                         selectedInstructorID = dbInstructorID;
                     }
                 }


             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });


    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String newDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        selectedDate = newDate;
        tv_date.setText(newDate);
        checkBooking(newDate);

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

    public void openConfirmBooking(View view) {
        getInstructorID();
        Intent intent = new Intent(UserBooking1.this, UserBooking2.class);
        intent.putExtra("date", selectedDate);
        intent.putExtra("time", selectedTime);
        intent.putExtra("userID", userID);
        intent.putExtra("userLicence", userLicence);
        intent.putExtra("userName", userName);
        intent.putExtra("instructorID", selectedInstructorID);
        intent.putExtra("instructorName", selectedInstructor);
        startActivity(intent);
    }


}

