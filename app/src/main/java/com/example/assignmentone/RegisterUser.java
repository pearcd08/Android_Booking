package com.example.assignmentone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignmentone.db.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterUser extends AppCompatActivity {

    private FirebaseDatabase fbDB;
    private DatabaseReference dbRef;

    EditText txt_fName, txt_lName, txt_licence, txt_email, txt_password, txt_confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        fbDB = FirebaseDatabase.getInstance();
        dbRef = fbDB.getReference();

        txt_fName = (EditText) findViewById(R.id.txt_firstName);
        txt_lName = (EditText) findViewById(R.id.txt_lastName);
        txt_licence = (EditText) findViewById(R.id.txt_licence);
        txt_email = (EditText) findViewById(R.id.txt_email);
        txt_password = (EditText) findViewById(R.id.txt_password);
        txt_confirmPassword = (EditText) findViewById(R.id.txt_confirmPassword);

    }

    public void goBack(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

    }

    public void saveUser(View view) {

        String name = txt_fName.getText().toString().trim() + " " +
                txt_lName.getText().toString().trim();
        String licence = txt_licence.getText().toString().trim();
        String email = txt_email.getText().toString().trim();
        String password = txt_password.getText().toString().trim();
        String password2 = txt_confirmPassword.getText().toString().trim();

        dbRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(licence)) {
                    Toast.makeText(RegisterUser.this, "Licence already exists",
                            Toast.LENGTH_SHORT).show();
                    txt_licence.requestFocus();

                } else {
                    if (checkValues() == true) {
                        if (checkPassword(password, password2) == true) {
                            User u = new User(name, email, password);
                            dbRef.child("users").child(licence).setValue(u);
                            Toast.makeText(RegisterUser.this, name + " has been registered ",
                                    Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(RegisterUser.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(RegisterUser.this, "Passwords do not match",
                                    Toast.LENGTH_SHORT).show();
                            txt_confirmPassword.requestFocus();

                        }
                    } else {
                        Toast.makeText(RegisterUser.this, "Fill in all fields",
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
        if (txt_fName.getText().toString().isEmpty() &&
                txt_lName.getText().toString().isEmpty() &&
                txt_licence.getText().toString().isEmpty() &&
                txt_email.getText().toString().isEmpty() &&
                txt_password.getText().toString().isEmpty() &&
                txt_confirmPassword.getText().toString().isEmpty()) {
            return false;
        }
        return true;

    }

    private boolean checkPassword(String p1, String p2) {
        if (!p1.equals(p2)) {
            return false;
        }
        return true;

    }
}