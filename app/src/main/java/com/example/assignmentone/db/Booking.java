package com.example.assignmentone.db;

import androidx.room.Entity;

@Entity
public class Booking {
    private String userID;
    private String instructorID;
    private String date;
    private String time;
    private String status;

    public Booking(String userID, String instructorID, String date, String time, String status) {
        this.userID = userID;
        this.instructorID = instructorID;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(String instructorID) {
        this.instructorID = instructorID;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "userID='" + userID + '\'' +
                ", instructorID='" + instructorID + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
