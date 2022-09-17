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

import java.util.ArrayList;
import java.util.List;

public class TimeBooking_Holder extends RecyclerView.ViewHolder {
    public TextView tvTime, tvInstructor;


    //instance variable


    public TimeBooking_Holder(@NonNull View itemView) {
        super(itemView);
        tvTime = itemView.findViewById(R.id.tv_time);
        tvInstructor = itemView.findViewById(R.id.tv_bookingsAvailable);
        UserBooking1 uBooking = new UserBooking1();
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String clickedTime = tvTime.getText().toString();
                Toast.makeText(tvTime.getContext(), clickedTime + " Selected!",
                        Toast.LENGTH_SHORT).show();
                tvTime.setBackgroundColor(Color.parseColor("#808080"));
                UserBooking1.time = clickedTime;
                uBooking.assignInstructor(uBooking.time);




            }
        });

    }




}
