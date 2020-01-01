package com.example.intercrew;

public class DayHours {
    private int startHours;
    private int startMinutes;
    private int endHours;
    private int endMinutes;
    private String id;

    public DayHours(String id,int startHours,int startMinutes,int endHours,int endMinutes){
        this.startHours=startHours;
        this.startMinutes=startMinutes;
        this.endHours=endHours;
        this.endMinutes=endMinutes;
        this.id=id;

    }

    public DayHours(){};
    public DayHours(String id){
        this.id=id;
        startHours=0;
        startMinutes=0;
        endHours=0;
        endMinutes=0;
    }

    //getters
    public int getStartHours(){return startHours;}
    public int getStartMinutes(){return startMinutes;}
    public int getEndHours(){return endHours;}
    public int getEndMinutes(){return endMinutes;}
    public String getID(){return id;}


    //setters
    public void setStartHours(int a){startHours=a;}
    public void setStartMinutes(int a){startMinutes=a;}
    public void setEndHours(int a){endHours=a;}
    public void setEndMinutes(int a){endMinutes=a;}




}
