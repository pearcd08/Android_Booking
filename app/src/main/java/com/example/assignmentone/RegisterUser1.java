package com.example.assignmentone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignmentone.db.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterUser1 extends AppCompatActivity {

    private FirebaseDatabase fbDB;
    private DatabaseReference dbRef;

    EditText et_email, et_password, et_confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        //connect database
        fbDB = FirebaseDatabase.getInstance();
        dbRef = fbDB.getReference();
        //find edit text views
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_confirmPassword = findViewById(R.id.et_confirmPassword);

    }

    public void goBack(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

    }

    public void saveUser(View view) {




        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String password2 = et_confirmPassword.getText().toString().trim();

        dbRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(email)) {
                    Toast.makeText(RegisterUser1.this, "That email is already registered",
                            Toast.LENGTH_SHORT).show();
                    et_email.requestFocus();

                } else {
                    if (checkValues() == true) {
                        if (password.equals(password2)) {

                            Intent i = new Intent(RegisterUser1.this, RegisterUser2.class);
                            i.putExtra("email", email);
                            i.putExtra("password", password);
                            startActivity(i);
                        } else {
                            Toast.makeText(RegisterUser1.this, "Passwords do not match",
                                    Toast.LENGTH_SHORT).show();
                            et_confirmPassword.requestFocus();

                        }
                    } else {
                        Toast.makeText(RegisterUser1.this, "Fill in all fields",
                                Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }




    private boolean checkValues() {
        if(!et_email.getText().toString().isEmpty() ||
                !et_password.getText().toString().isEmpty() ||
                !et_confirmPassword.getText().toString().isEmpty())
        {
            return true;
        }
        return false;


    }


}