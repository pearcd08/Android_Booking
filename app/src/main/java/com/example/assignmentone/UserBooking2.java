package com.example.assignmentone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserBooking2 extends AppCompatActivity {

    private String date, time, userID, userName, instuctorID;
    private TextView tv_student, tv_date, tv_time, tv_instuctor;
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
        tv_instuctor = (TextView) findViewById(R.id.tv_confirmBooking_instructor);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            date = extras.getString("date");
            userID = extras.getString("userID");
            time = extras.getString("time");
            instuctorID = extras.getString("instructorID");
        }
        if (extras != null) {

        }

        fillFields();
    }



    public void fillFields() {
        tv_student.setText(getUserName(userID));
        tv_date.setText(date);
        tv_time.setText(time);
        tv_instuctor.setText(getInstructorName(instuctorID));

    }

    public String getUserName(String userID){
        final String[] name = {""};

        dbRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot uDB : snapshot.getChildren()) {

                    if(uDB.getKey().equals(userID)){
                        name[0] = uDB.child("name").getValue(String.class);


                    }
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return name[0];

    }

    public String getInstructorName(String instructorID){
        final String[] name = {""};

        dbRef.child("instructors").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot uDB : snapshot.getChildren()) {

                    if(uDB.getKey().equals(instructorID)){
                        name[0] = uDB.child("name").getValue(String.class);


                    }
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return name[0];

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