package com.example.assignmentone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.assignmentone.db.Booking;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ViewBookingsLicence extends AppCompatActivity {
    LinearLayoutManager linearLayoutManager;
    Spinner spinner;
    RecyclerView recyclerView;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    FirebaseRecyclerOptions firebaseRecyclerOptions;


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

        showData();


    }

    private void showData() {



        Query query = databaseReference
                .child("bookings")
                .orderByChild("date")
                .equalTo("9/18/2022");



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
                                model.getUserID(),
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