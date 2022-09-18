package com.example.assignmentone;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewBooking_Holder extends RecyclerView.ViewHolder {

    private FirebaseDatabase fbDB;
    private DatabaseReference dbRef;
    View mView;


    public ViewBooking_Holder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        fbDB = FirebaseDatabase.getInstance();
        dbRef = fbDB.getReference();


    }

    public void setDetails(Context ctx, String user, String date, String time, String instructor){
        TextView tv_name = mView.findViewById(R.id.tv_viewBooking_name);
        TextView tv_date = mView.findViewById(R.id.tv_viewBooking_date);
        TextView tv_instructor = mView.findViewById(R.id.tv_viewBooking_instructor);


        tv_name.setText("Student: "+user);
        tv_date.setText(time+ " on the "+date);
        tv_instructor.setText("Instructor "+instructor);


    }




}
