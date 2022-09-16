package com.example.assignmentone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignmentone.db.Booking;
import com.example.assignmentone.db.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserBooking3 extends AppCompatActivity {

    private String date, time, userID, userName, userLicence;
    private EditText txt_date, txt_time, txt_instructor;
    private FirebaseDatabase fbDB;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);

        fbDB = FirebaseDatabase.getInstance();
        dbRef = fbDB.getReference();
        txt_date = findViewById(R.id.txt_confirmBooking_date);
        txt_time = findViewById(R.id.txt_confirmBooking_time);
        txt_instructor = findViewById(R.id.txt_confirmBooking_Instructor);


        getBookingInfo();
        fillFields();
    }

    private void getBookingInfo() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            date = extras.getString("date");
            userID = extras.getString("userID");
            time = extras.getString("time");

            dbRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(userID)) {
                        userName = snapshot.child(userID).child("name").getValue(String.class);
                        userLicence = snapshot.child(userID).child("licence").getValue(String.class);
                        Toast.makeText(UserBooking3.this, "Customer: " + userName +
                                " Date: " + date + " Time: " + time, Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

    }

    public void fillFields() {
        txt_date.setText(date);
        txt_time.setText(time);

    }

    public void confirmBooking(View view) {
        String id = dbRef.push().getKey();
        //Booking b = new Booking(userID, "null", date, time, "pending");
        //dbRef.child("bookings").child(id).setValue(b);
        Intent intent = new Intent (this, UserHome.class);
        startActivity(intent);
        //Intent intent = new Intent(Intent.ACTION_INSERT);
        // intent.setData(CalendarContract.Events.CONTENT_URI);
        // intent.putExtra(CalendarContract.Events.TITLE, "Driving Test");
        // intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Location of Test");
        //   intent.putExtra(CalendarContract.Events.DESCRIPTION, "Driving Test at "+ time);

        // startActivity(intent);


    }
}