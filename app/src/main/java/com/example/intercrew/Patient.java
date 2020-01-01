package com.example.intercrew;

public class Patient extends User {

    // instance variables
    String patientId;
    String gender;
    boolean newPatient;
    int phone;


    // a constructor
    public Patient(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
        newPatient = true;

    }

    public Patient(){};




    // returns phone number if a Patient
    public int getPhone() { return this.phone; }

    // changes phone number of a Patient
    public void setPhone(int phone) { this.phone = phone; }


    // returns Gender of a Patient
    public String getGender() { return this.gender; }

    // sets Gender of a Patient
    public void setGender(String gender) { this.gender = gender; }

    // returns true if a Patient is new to the clinic. and false otherwise
    public boolean getNewPatient() { return this.newPatient; }

    // once called, set NewPatient to false
    public void setNewPatient() { this.newPatient = false; }
}