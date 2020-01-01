package com.example.intercrew;

public class Appointment {
    private String id;
    private String day;
    private String expectedTime;

    public Appointment(String id, String day, String expectedTime) {
        this.id = id;
        this.day = day;
        this.expectedTime=expectedTime;

    }

    public Appointment() {
    }

    public void setID(String id) {
        this.id = id;
    }
    public String getID(){return id;}

    public void setDay(String day) { this.day = day; }
    public String getDay() {return day;}

    public void setTime(String time) {
        this.expectedTime = time;
    }
    public String getTime() { return expectedTime; }


}
