package com.example.assignmentone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.assignmentone.db.Booking;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class ViewBookingsLicence extends AppCompatActivity {
    LinearLayoutManager linearLayoutManager;

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    FirebaseRecyclerOptions firebaseRecyclerOptions;

    ArrayList<String> spinnerList;
    ArrayAdapter<String> arrayAdapter;

    EditText et_licence;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bookings_licence);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView = findViewById(R.id.rv_bookings_by_licence);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        //spinner = (Spinner) findViewById(R.id.spin_licence);
        et_licence = findViewById(R.id.et_viewBooking_licence);

    }

    private void showBookings(String licence) {
        Query query = databaseReference
                .child("bookings")
                .orderByChild("licence")
                .equalTo(licence);

        firebaseRecyclerOptions =
                new FirebaseRecyclerOptions.Builder<Booking>()
                        .setQuery(query, Booking.class)
                        .build();

        firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Booking, ViewBooking_Holder>(firebaseRecyclerOptions) {
                    @NonNull
                    @Override
                    public ViewBooking_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View itemView = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.booking_licence_list, parent, false);
                        ViewBooking_Holder viewBooking_holder = new ViewBooking_Holder(itemView);
                        return viewBooking_holder;
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull ViewBooking_Holder holder, int position, @NonNull Booking model) {
                        holder.setDetails(getApplicationContext(),
                                model.getUserName(),
                                model.getDate(),
                                model.getTime(),
                                model.getInstructorName());
                        // ...
                    }
                };

        recyclerView.setLayoutManager(linearLayoutManager);
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    protected void onStart() {
        super.onStart();
        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.startListening();
        }


    }

    public void searchBookingsByLicence(View view) {
        String licence = et_licence.getText().toString().trim().toUpperCase();
        checkLicence(licence);
        showBookings(licence);
    }

    private void checkLicence(String licence) {

        databaseReference.child("bookings").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int licenceCount = 0;
                for (DataSnapshot bDB : snapshot.getChildren()) {
                    String dbLicence = bDB.child("licence").getValue(String.class);
                    if (dbLicence.equals(licence)) {
                        licenceCount++;
                    }
                }
                if(licenceCount == 0){
                    Toast.makeText(ViewBookingsLicence.this,
                            "That licence has no bookings",
                            Toast.LENGTH_SHORT).show();}
            }

                @Override
                public void onCancelled (@NonNull DatabaseError error){


                }
            });
        }


        public void viewBookingsLicenceBack (View view){
            Intent intent = new Intent(this, AdminHome.class);
            startActivity(intent);
        }
    }