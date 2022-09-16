package com.example.assignmentone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class ViewBookingsLicence extends AppCompatActivity {
    LinearLayoutManager linearLayoutManager;
    Spinner spinner;
    RecyclerView recyclerView;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    FirebaseRecyclerOptions firebaseRecyclerOptions;

    ArrayList<String> spinnerList;
    ArrayAdapter<String> arrayAdapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bookings_licence);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView = (RecyclerView) findViewById(R.id.rv_bookings_by_licence);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


        spinner = (Spinner) findViewById(R.id.spin_licence);


        spinnerList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(ViewBookingsLicence.this,
                android.R.layout.simple_spinner_dropdown_item, spinnerList);




        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = spinner.getSelectedItem().toString();
                Toast.makeText(ViewBookingsLicence.this, selection+"Selected", Toast.LENGTH_SHORT).show();
                showBookings(selection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        loadSpinner();
        spinner.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();






    }

    private void loadSpinner() {
        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot userDB : snapshot.getChildren()){
                    String licence = userDB.child("licence").getValue(String.class);
                    spinnerList.add(licence);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                        // Create a new instance of the ViewHolder, in this case we are using a custom
                        // layout called R.layout.message for each item
                        View itemView = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.booking_licence_list, parent, false);

                        ViewBooking_Holder viewBooking_holder = new ViewBooking_Holder(itemView);


                        return viewBooking_holder;
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull ViewBooking_Holder holder, int position, @NonNull Booking model) {
                        holder.setDetails(getApplicationContext(),
                                model.getLicence(),
                                model.getDate(),
                                model.getTime(),
                                model.getInstructorID());
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


}