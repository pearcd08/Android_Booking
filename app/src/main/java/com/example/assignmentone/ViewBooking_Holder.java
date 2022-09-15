package com.example.assignmentone;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewBooking_Holder extends RecyclerView.ViewHolder {

    View mView;
    public ViewBooking_Holder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;





    }

    public void setDetails(Context ctx, String name, String date, String time, String instructor){
        TextView tv_name = mView.findViewById(R.id.tv_viewBooking_name);
        TextView tv_date = mView.findViewById(R.id.tv_viewBooking_date);
        TextView tv_time = mView.findViewById(R.id.tv_viewBooking_time);
        TextView tv_instructor = mView.findViewById(R.id.tv_viewBooking_instructor);

        tv_name.setText(name);
        tv_date.setText(date);
        tv_time.setText(time);
        tv_instructor.setText(instructor);


    }



}
