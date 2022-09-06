package com.example.assignmentone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignmentone.db.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText txtLicence, txtPassword;

    private FirebaseDatabase fbDB;
    private DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fbDB = FirebaseDatabase.getInstance();
        dbRef = fbDB.getReference();

        txtLicence = findViewById(R.id.txt_loginLicence);
        txtPassword = findViewById(R.id.txt_loginPassword);


    }

    public void loginUser(View view) {

        String licence = txtLicence.getText().toString();
        String password = txtPassword.getText().toString();

        if (txtLicence.equals("admin") && txtPassword.equals("admin")) {
            //open admin/employee page

        } else {
            //check LicenceNo with Password from firebase databse
            dbRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(licence)) {
                        final String dbPassword = snapshot.child(licence).child("password").getValue(String.class);

                        if (dbPassword.equals(password)) {
                            Intent i = new Intent(MainActivity.this, UserHome.class);

                            i.putExtra("userLicence", licence);
                            startActivity(i);

                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }


    }

    public void registerUser(View view) {

        Intent intent = new Intent(this, RegisterUser.class);
        startActivity(intent);

    }
}