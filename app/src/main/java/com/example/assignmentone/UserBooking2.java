package com.example.assignmentone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignmentone.db.Booking;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserBooking2 extends AppCompatActivity {

    private String date, time, userID, userLicence, instructorID, instructorName, userName;
    private TextView tv_student, tv_date, tv_time, tv_instructor;
    private FirebaseDatabase fbDB;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);

        fbDB = FirebaseDatabase.getInstance();
        dbRef = fbDB.getReference();

        tv_student = (TextView) findViewById(R.id.tv_confirmBooking_student);
        tv_date = (TextView) findViewById(R.id.tv_confirmBooking_date);
        tv_time = (TextView) findViewById(R.id.tv_confirmBooking_time);
        tv_instructor = (TextView) findViewById(R.id.tv_confirmBooking_instructor);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            date = extras.getString("date");
            userID = extras.getString("userID");
            userLicence = extras.getString("userLicence");
            userName = extras.getString("userName");
            time = extras.getString("time");
            instructorID = extras.getString("instructorID");
            instructorName = extras.getString("instructorName");


        }
        if (extras != null) {

        }



        fillFields();
    }



    public void fillFields() {

        tv_student.setText(userName);
        tv_date.setText(date);
        tv_time.setText(time);
        tv_instructor.setText(instructorName);
    }

    public void confirmBooking(View view) {
        String id = dbRef.push().getKey();
        Booking b = new Booking(userLicence,instructorID, date, time, userName, instructorName);
        dbRef.child("bookings").child(id).setValue(b);
        Intent intent = new Intent (this, UserHome.class);
        intent.putExtra("userID", userID);
        intent.putExtra("userName", userName);
        intent.putExtra("userLicence", userLicence);
        startActivity(intent);
        //Intent intent = new Intent(Intent.ACTION_INSERT);
        // intent.setData(CalendarContract.Events.CONTENT_URI);
        // intent.putExtra(CalendarContract.Events.TITLE, "Driving Test");
        // intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Location of Test");
        //   intent.putExtra(CalendarContract.Events.DESCRIPTION, "Driving Test at "+ time);
        // startActivity(intent);
    }

    public void btn_confirmBooking_back(View view) {
        Intent intent = new Intent(this, UserBooking1.class);
        intent.putExtra("userID", userID);
        intent.putExtra("userName", userName);
        intent.putExtra("userLicence", userLicence);
        startActivity(intent);
;    }
}