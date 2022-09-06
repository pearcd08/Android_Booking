package com.example.assignmentone.db;

import androidx.room.Entity;

@Entity
public class Booking {
    private String licence;
    private String date;
    private String time;

    public Booking(String licence, String date, String time) {
        this.licence = licence;
        this.date = date;
        this.time = time;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "licence='" + licence + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
