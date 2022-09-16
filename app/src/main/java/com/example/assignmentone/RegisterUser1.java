package com.example.assignmentone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
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

    public void userReg1Next(View view) {
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String password2 = et_confirmPassword.getText().toString().trim();

        dbRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //check if the table user table exists
                if (snapshot.exists()) {
                    //check if a user in the db doesn't already has that email
                    if (!snapshot.hasChild(email)) {
                        validateFields(email, password, password2);

                    } else {
                        Toast.makeText(RegisterUser1.this, "That email is already registered",
                                Toast.LENGTH_SHORT).show();
                        et_email.requestFocus();
                    }
                }
                //table does not exist so skip email check
                else{
                    validateFields(email, password, password2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RegisterUser1.this, "Error connecting to database",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void validateFields(String email, String password, String password2) {
        //check if the email is in a valid format
        if (isValidEmail(email)) {
            //check if password is greater than 6 characters
            if (password.length() >= 6) {
                //check if passwords match
                if (password.equals(password2)) {
                    goToStep2(email, password);

                } else {
                    Toast.makeText(RegisterUser1.this, "Passwords do not match",
                            Toast.LENGTH_SHORT).show();
                    et_confirmPassword.requestFocus();
                }
            } else {
                Toast.makeText(RegisterUser1.this, "Password must be at least 6 characters",
                        Toast.LENGTH_SHORT).show();
                et_email.requestFocus();
            }
        } else {
            Toast.makeText(RegisterUser1.this, "Please enter a valid email",
                    Toast.LENGTH_SHORT).show();
            et_email.requestFocus();
        }
    }

    //open next activity, pass extras
    private void goToStep2(String email, String password){
        Intent i = new Intent(RegisterUser1.this, RegisterUser2.class);
        i.putExtra("email", email);
        i.putExtra("password", password);
        startActivity(i);
    }

    //open main activity
    public void goBack(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }



}