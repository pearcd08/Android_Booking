package com.example.assignmentone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

public class DisplayDates extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private FirebaseDatabase fbDB;
    private DatabaseReference dbRef;
    private DatePickerDialog datePickerDialog;
    private GraphView graph;
    private TextView tv_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_dates);

        fbDB = FirebaseDatabase.getInstance();
        dbRef = fbDB.getReference();
        tv_date =(TextView) findViewById(R.id.txt_userbooking_date);


        showCalendar();
    }

    private void showCalendar() {
        Calendar today = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(DisplayDates.this,
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show(getSupportFragmentManager(), "DatePickerDialog");


        //Restrict to weekends
        ArrayList<Calendar> weekends = new ArrayList<Calendar>();
        Calendar day = Calendar.getInstance();
        for (int i = 0; i < 365; i++) {
            if (day.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                    day.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                Calendar d = (Calendar) day.clone();
                weekends.add(d);
            }
            day.add(Calendar.DATE, 1);
        }
        Calendar[] weekendDays = weekends.toArray(new Calendar[weekends.size()]);
        datePickerDialog.setDisabledDays(weekendDays);

        //Remove Booked Dates;
        //loop though dates booked by logged in user

    }

    private void getTimes(String newDate) {

        String[] timeArray = {"09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00"};
         int[] bookedTimes = new int[timeArray.length];

        //2. loop through each booking to find date and time
        dbRef.child("bookings").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                ArrayList<String> bookedTimesList = new ArrayList<>();
                for (DataSnapshot bookingData : snapshot.getChildren()) {
                    String dbDate = bookingData.child("date").getValue(String.class);
                    String dbTime = bookingData.child("time").getValue(String.class);

                    if (dbDate.equals(newDate)) {
                        bookedTimesList.add(dbTime);
                    }

                }
                for (int i = 0; i < timeArray.length; i++) {
                    if (bookedTimesList.contains(timeArray[i])) {
                        bookedTimes[i] = 1;
                    } else {
                        bookedTimes[i] = 0;
                    }
                }
                createGraph(timeArray, bookedTimes);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }

    public void createGraph(String[] timeArray, int[] bookedTimes) {

        graph = (GraphView) findViewById(R.id.time_graph);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{

                new DataPoint(1, bookedTimes[0]),
                new DataPoint(2, bookedTimes[1]),
                new DataPoint(3, bookedTimes[2]),
                new DataPoint(4, bookedTimes[3]),
                new DataPoint(5, bookedTimes[4]),
                new DataPoint(6, bookedTimes[5]),
                new DataPoint(7, bookedTimes[6]),
                new DataPoint(8, bookedTimes[7]),


        });

        series.setColor(Color.YELLOW);
        graph.getGridLabelRenderer().setGridColor(Color.WHITE);
        graph.getGridLabelRenderer().setHighlightZeroLines(false);
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        graph.getGridLabelRenderer().setTextSize(25f);
        graph.getGridLabelRenderer().reloadStyles();
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(timeArray);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        graph.addSeries(series);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        tv_date.setText(selectedDate);

        getTimes(selectedDate);

    }

    public void displayCalendar(View view) {
        graph.removeAllSeries();
        showCalendar();


    }

    public void displayDates(View view) {
        Intent intent = new Intent(this, AdminHome.class);
        startActivity(intent);

    }
}
