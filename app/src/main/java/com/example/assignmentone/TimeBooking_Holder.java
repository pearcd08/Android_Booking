package com.example.assignmentone;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmentone.db.User;

import java.util.ArrayList;
import java.util.List;

public class TimeBooking_Holder extends RecyclerView.ViewHolder {
    public TextView tv_Time, tv_BookingsAvailable, tv_Instructor;


    public TimeBooking_Holder(@NonNull View itemView) {
        super(itemView);

        tv_Time = itemView.findViewById(R.id.tv_time);
        tv_BookingsAvailable = itemView.findViewById(R.id.tv_bookingsAvailable);
        tv_Instructor = itemView.findViewById(R.id.tv_assignedInstructor);
        UserBooking1 uBooking = new UserBooking1();
        tv_Time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {



        String clickedTime = tv_Time.getText().toString();
                String clickedInstructor = tv_Instructor.getText().toString();
                Toast.makeText(tv_Time.getContext(), clickedTime + "Selected",
                        Toast.LENGTH_SHORT).show();
                tv_Time.setBackgroundColor(Color.parseColor("#FFDF35"));

                UserBooking1.selectedTime = clickedTime;
                UserBooking1.selectedInstructor = clickedInstructor;
                UserBooking1.btnNext.setEnabled(true);
                UserBooking1.btnNext.setBackgroundColor(Color.parseColor("#FFDF35"));


            }
        });

    }




}
