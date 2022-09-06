package com.example.assignmentone;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TimeBooking_Holder extends RecyclerView.ViewHolder {
    public TextView tvTime;


    public TimeBooking_Holder(@NonNull View itemView) {
        super(itemView);
        tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String clickedTime = tvTime.getText().toString();
                Toast.makeText(tvTime.getContext(), clickedTime + " Selected!",
                        Toast.LENGTH_SHORT).show();
                tvTime.setText(clickedTime + " Clicked");
                UserBooking2.time = clickedTime;

            }
        });

    }




}
