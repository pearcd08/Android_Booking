package com.example.assignmentone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserBooking3 extends AppCompatActivity {

    private String date, time, userLicence, userName;
    private FirebaseDatabase fbDB;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);

        fbDB = FirebaseDatabase.getInstance();
        dbRef = fbDB.getReference();

        getBookingInfo();   }

    private void getBookingInfo(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            date = extras.getString("date");
            userLicence = extras.getString("userLicence");
            time = extras.getString("time");

            dbRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(userLicence)){
                        userName = snapshot.child(userLicence).child("name").getValue(String.class);
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
}