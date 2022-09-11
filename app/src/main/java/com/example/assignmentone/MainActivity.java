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

        if (loginEmail.equals("admin") && loginPassword.equals("admin")) {
            //open admin/employee page

        } else {

            dbRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot userData : snapshot.getChildren()) {
                        String userID = userData.getKey();
                        String dbEmail = userData.child("email").getValue(String.class);
                        if (dbEmail.equals(loginEmail)) {
                            final String dbPassword = userData.child("password").getValue(String.class);

                            if (dbPassword.equals(loginPassword)) {

                                Intent i = new Intent(MainActivity.this, UserHome.class);

                                i.putExtra("userID", userID);
                                startActivity(i);

                            }
                            else{
                                Toast.makeText(MainActivity.this, "Incorrect Password",
                                        Toast.LENGTH_SHORT).show();
                                txtEmail.requestFocus();
                            }

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