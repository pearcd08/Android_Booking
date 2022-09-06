package com.example.assignmentone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserHome extends AppCompatActivity {
    private TextView tvWelcome;
    private FirebaseDatabase fbDB;
    private DatabaseReference dbRef;

    private String userLicence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        fbDB = FirebaseDatabase.getInstance();
        dbRef = fbDB.getReference();

        tvWelcome = findViewById(R.id.txt_welcome);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userLicence = extras.getString("userLicence");
        }
        welcomeUser();


    }

    private void welcomeUser() {
        dbRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String userName = snapshot.child(userLicence).child("name").getValue(String.class);
                tvWelcome.setText("Welcome " + userName);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void openBookTest(View view) {
        Intent i = new Intent(UserHome.this, UserBooking1.class);
        i.putExtra("userLicence", userLicence);
        startActivity(i);

    }

    public void openRCGame(View view) {
    }

    public void logoutUser(View view) {
        Intent i = new Intent(UserHome.this, MainActivity.class);

    }
}