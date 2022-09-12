package com.example.assignmentone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TimeBooking_Adapter extends RecyclerView.Adapter<TimeBooking_Holder> {
    String[] data;



    public TimeBooking_Adapter(String[] data) {
        this.data = data;

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
        holder.tvTime.setText(data[position]);


    }

    @Override
    public int getItemCount() {
        return data.length;
    }
}
