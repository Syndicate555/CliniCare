package com.example.intercrew;

import com.example.intercrew.Admin.Service;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class Clinic {

    List<DayHours> workingHours;
    List<Service> services;
    String clinicName;
    String address;
    int phone;
    String insuranceType;
    String paymentMethod;
    String clinicID;
    float rating;
    int waitTime;


    public Clinic(String address, int phone, String clinicName, String insuranceType, String paymentMethod,List<Service> services) {
        this.address = address;
        this.phone = phone;
        this.clinicName = clinicName;
        this.insuranceType = insuranceType;
        this.paymentMethod = paymentMethod;
        this.services=services;
        List<DayHours> workingHours = new ArrayList<>();
        rating=0;
        waitTime=0;

    }
    public Clinic(String address, int phone, String clinicName, String insuranceType, String paymentMethod,List<Service> services,String clinicID) {
        this.address = address;
        this.phone = phone;
        this.clinicName = clinicName;
        this.insuranceType = insuranceType;
        this.paymentMethod = paymentMethod;
        this.services=services;
        List<DayHours> workingHours = new ArrayList<>();
        this.clinicID=clinicID;
        rating=0;
        waitTime=0;

    }
    public Clinic(){ };

    //getters
    public String getAddress() {return address;}
    public int getPhone() {return phone;}
    public String getClinicName() {return clinicName;}
    public String getClinicID() {return clinicID;}
    public String getInsuranceType() {return insuranceType;}
    public String getPaymentMethod() {return paymentMethod;}
    public List<Service> getServices(){return services;}
    public float getRating(){return rating;}
    public int getWaitTime(){return waitTime;}
    public List<DayHours> getWorkingHours(){return workingHours;}

    //setters
    public void setClinicID(String clinicNum){clinicID=clinicNum;}
    public void setServices(List<Service> b){services=b;}
    public void setWorkingHours(List<DayHours> b){workingHours=b;}
    public void setAddress(String address) {this.address = address;}
    public void setPhone(int phone) {this.phone = phone;}
    public void setClinicName(String clinicName) {this.clinicName = clinicName;}
    public void setInsuranceType(String insuranceType) {this.insuranceType = insuranceType;}
    public void setPaymentMethod(String paymentMethod) {this.paymentMethod = paymentMethod;}
    public void setRating(float b){rating=b;}
    public void setWaitTime(int time){ waitTime=time;}
}