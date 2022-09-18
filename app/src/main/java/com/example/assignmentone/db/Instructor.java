package com.example.assignmentone.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Instructor {

    String instructorID;
    private String name;
    private int phone;

    public Instructor(String instructorID, String name, int phone) {
        this.instructorID = instructorID;
        this.name = name;
        this.phone = phone;
    }

    public String getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(String instructorID) {
        this.instructorID = instructorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "instructorID='" + instructorID + '\'' +
                ", name='" + name + '\'' +
                ", phone=" + phone +
                '}';
    }
}
