package com.example.assignmentone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserBooking2 extends AppCompatActivity {

    private String date;
    public static String time;
    private String userID;
    private String selectedInstructor;
    private TextView tvDate;
    private RecyclerView rvTime;
    private TimeBooking_Holder rvHolder;
    private FirebaseDatabase fbDB;
    private DatabaseReference dbRef;
    private String[] data;
    private  ArrayList<String> instructorList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_booking2);
        rvTime = findViewById(R.id.rv_time);

        fbDB = FirebaseDatabase.getInstance();
        dbRef = fbDB.getReference();

        displayDate();
        getInstructors();

    }

    private void displayDate() {
        tvDate = findViewById(R.id.tv_displayDate);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            date = extras.getString("date");
            userID = extras.getString("userID");
            tvDate.setText("Bookings for " + date);

        }

    }

    private void getInstructors(){

        dbRef.child("instructors").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot instructorData : snapshot.getChildren()) {
                    String dbInstructorID = instructorData.getKey();
                    instructorList.add(dbInstructorID);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }




    public void openBookingThree(View view) {
        Intent intent = new Intent(this, UserBooking3.class);
        intent.putExtra("date", date);
        intent.putExtra("time", time);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }
}



