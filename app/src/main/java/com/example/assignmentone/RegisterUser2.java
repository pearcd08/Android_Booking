package com.example.assignmentone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignmentone.db.Instructor;
import com.example.assignmentone.db.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterUser2 extends AppCompatActivity {

    private FirebaseDatabase fbDB;
    private DatabaseReference dbRef;
    private EditText et_fName, et_lName, et_licence;
    private String email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user2);
        //connect database
        fbDB = FirebaseDatabase.getInstance();
        dbRef = fbDB.getReference();
        //import extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            email = extras.getString("email");
            password = extras.getString("password");
        }
        //find edit text views
        et_fName = findViewById(R.id.et_firstName);
        et_lName = findViewById(R.id.et_lastName);
        et_licence = findViewById(R.id.et_licence);
    }


    public void saveUser(View view) {
        String fName = et_fName.getText().toString().trim();
        String lName = et_lName.getText().toString().trim();
        String name = fName + " " + lName;
        String licence = et_licence.getText().toString().trim();

        dbRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(licence)) {
                    Toast.makeText(RegisterUser2.this, "That licence is already registered",
                            Toast.LENGTH_SHORT).show();
                    et_licence.requestFocus();

                } else {
                    String id = dbRef.push().getKey();
                    User u = new User(name, licence, email, password);
                    dbRef.child("users").child(id).setValue(u);
                    Toast.makeText(RegisterUser2.this, name + " has been registered ",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterUser2.this, MainActivity.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void goBack(View view) {
        Intent i = new Intent(this, RegisterUser1.class);
        startActivity(i);
    }

    private boolean checkValues() {
        if (!et_fName.getText().toString().isEmpty() ||
                !et_lName.getText().toString().isEmpty() ||
                !et_licence.getText().toString().isEmpty()) {
            return true;
        }
        return false;


    }


}