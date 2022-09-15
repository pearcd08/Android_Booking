package com.example.assignmentone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
    }

    public void openRegisterInstructor(View view) {
        Intent intent = new Intent(this, RegisterInstructor.class);
        startActivity(intent);
    }

    public void openViewBookings(View view) {
        Intent intent = new Intent(this, ViewBookingsLicence.class);
        startActivity(intent);
    }

    public void logoutAdmin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}