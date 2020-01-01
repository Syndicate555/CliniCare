package com.example.intercrew;

public class WalkInClinic {
    // instance variables
    String Name;
    String Address;
    String TypeOfService;
    private DayHoursList workingHrs;
    int Phone;
    int Rating;

    // returns name of a clinic
    public String getName(){ return this.Name; }

    // returns address of a clinic
    public String getAddress(){ return this.Address; }

    // returns type of service
    public String getTypeOfService(){ return this.TypeOfService; }

    // returns working hours
    public DayHoursList getWorkingHours(){ return this.workingHrs; }

    // returns phone number of a clinic
    public int getPhone(){ return this.Phone; }

    // returns rating of a clinic
    public int getRating(){ return this.Rating; }

    // changes name of a clinic
    public void setName(String Name) { this.Name = Name; }

    // changes address of a clinic
    public void setAddress(String Address){ this.Address = Address; }

    // changes type of service
    public void setTypeOfService(String TypeOfService){ this.TypeOfService = TypeOfService; }

    // changes working hours
    public void setWorkingHours(DayHoursList workingHrs){ this.workingHrs = workingHrs; }

    // changes phone number of a clinic
    public void setPhone(int Phone){ this.Phone = Phone; }

    // changes rating of a clinic
    public void setRating(int Rating){ this.Rating = Rating; }
}
