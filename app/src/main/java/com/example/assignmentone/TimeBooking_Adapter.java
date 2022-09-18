package com.example.assignmentone;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TimeBooking_Adapter extends RecyclerView.Adapter<TimeBooking_Holder> {
    String[] times;
    String[] availability;
    String[] instructors;

    public TimeBooking_Adapter(String[] timeArray, String[] availability, String[] instructors) {
        this.times = timeArray;
        this.availability = availability;
        this.instructors = instructors;
    }

    @NonNull
    @Override
    public TimeBooking_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View view = inflater.inflate(R.layout.time_layout,
                parent, false);
        return new TimeBooking_Holder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull TimeBooking_Holder holder, int position) {
        holder.tv_Time.setText(times[position]);
        holder.tv_BookingsAvailable.setText(availability[position]);


if (holder.tv_BookingsAvailable.getText().toString().equals("BOOKED")) {
            holder.tv_Time.setBackgroundColor(Color.parseColor("#FF7F7F"));
            holder.tv_Time.setClickable(false);

        } else if(holder.tv_BookingsAvailable.getText().toString().equals("AVAILABLE")){
            holder.tv_Time.setBackgroundColor(Color.parseColor("#FFFFFF"));
             holder.tv_Time.setClickable(true);    }

        holder.tv_Instructor.setText(instructors[position]);




    }

    @Override
    public int getItemCount() {
        return times.length;
    }
}
