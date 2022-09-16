package com.example.assignmentone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText txtEmail, txtPassword;

    private FirebaseDatabase fbDB;
    private DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fbDB = FirebaseDatabase.getInstance();
        dbRef = fbDB.getReference();

        txtEmail = findViewById(R.id.txt_loginEmail);
        txtPassword = findViewById(R.id.txt_loginPassword);


    }

    public void loginUser(View view) {
        String loginEmail = txtEmail.getText().toString();
        String loginPassword = txtPassword.getText().toString();

        //open admin menu, might change to user type (user/admin)
        if (loginEmail.equals("admin") && loginPassword.equals("admin")) {
            //open admin/employee page
            Intent adminIntent = new Intent(this, AdminHome.class);
            startActivity(adminIntent);

        } else {
            dbRef.child("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot userData : snapshot.getChildren()) {
                        String userID = userData.getKey();
                        String licence = userData.child("licence").getValue(String.class);
                        String dbEmail = userData.child("email").getValue(String.class);
                        if (dbEmail.equals(loginEmail)) {
                            final String dbPassword = userData.child("password").getValue(String.class);

                            if (dbPassword.equals(loginPassword)) {

                                Intent i = new Intent(MainActivity.this, UserHome.class);

                                i.putExtra("userID", userID);
                                i.putExtra("licence", licence);
                                startActivity(i);

                            } else {
                                Toast.makeText(MainActivity.this, "Incorrect Password",
                                        Toast.LENGTH_SHORT).show();
                                txtPassword.requestFocus();
                            }

                        }
                        else{
                            Toast.makeText(MainActivity.this, "Invaid Email",
                                    Toast.LENGTH_SHORT).show();
                            txtEmail.requestFocus();

                        }
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this, "Error connecting to database",
                            Toast.LENGTH_SHORT).show();

                }
            });

        }


    }

    public void registerUser(View view) {

        Intent intent = new Intent(this, RegisterUser1.class);
        startActivity(intent);

    }
}