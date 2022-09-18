package com.example.assignmentone.db;


public class Booking {
    private String licence;
    private String instructorID;
    private String date;
    private String time;
    private String userName;
    private String instructorName;

    public Booking() {
    }  // Needed for Firebase

    public Booking(String licence, String instructorID, String date, String time, String userName, String instructorName) {
        this.licence = licence;
        this.instructorID = instructorID;
        this.date = date;
        this.time = time;
        this.userName = userName;
        this.instructorName = instructorName;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "licence='" + licence + '\'' +
                ", instructorID='" + instructorID + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", userName='" + userName + '\'' +
                ", instructorName='" + instructorName + '\'' +
                '}';
    }
}