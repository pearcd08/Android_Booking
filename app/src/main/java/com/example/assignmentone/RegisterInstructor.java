package com.example.assignmentone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class RegisterInstructor extends AppCompatActivity {

    private FirebaseDatabase fbDB;
    private DatabaseReference dbRef;
    EditText txt_fName, txt_lName, txt_phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_instructor);

        fbDB = FirebaseDatabase.getInstance();
        dbRef = fbDB.getReference();

        txt_fName = findViewById(R.id.txt_instructorFirstName);
        txt_lName = findViewById(R.id.txt_instructorLastName);
        txt_phone = findViewById(R.id.txt_InstructorPhone);

    }

    public void goBack(View view) {
        Intent intent = new Intent(this, AdminHome.class);
        startActivity(intent);

    }

    public void saveInstructor(View view) {
        String fName = txt_fName.getText().toString().trim();
        String lName = txt_lName.getText().toString().trim();
        String name = fName + " " + lName;
        String phone = txt_phone.getText().toString();


        if (checkValues(fName, lName, phone) == true) {
            dbRef.child("instructors").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(phone)) {
                        Toast.makeText(RegisterInstructor.this,
                                "That phone number is already registered to another instructor",
                                Toast.LENGTH_SHORT).show();
                        txt_phone.requestFocus();

                    } else {

                        String id = dbRef.push().getKey();
                        Instructor i = new Instructor(id, name, Integer.parseInt(phone));
                        dbRef.child("instructors").child(id).setValue(i);
                        Toast.makeText(RegisterInstructor.this, name + " has been registered ",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterInstructor.this, AdminHome.class);
                        startActivity(intent);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        } else {
            Toast.makeText(RegisterInstructor.this, "Please fill in all fields",
                    Toast.LENGTH_SHORT).show();
        }


    }

    private boolean checkValues(String fName, String lName, String phone) {
        if (fName.isEmpty() || lName.isEmpty() || phone.isEmpty()) {
            return false;
        }
        return true;


    }
}