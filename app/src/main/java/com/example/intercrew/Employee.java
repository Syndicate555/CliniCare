package com.example.intercrew;

public class Employee extends User {

    // instance variables
    String employeeId;
    String department;
    int phone;
    Clinic clinic;
    String clinicID;

    // a constructor

    public Employee(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email,password);
        this.clinic=null;
    }

/**
    public Employee(String firstName, String lastName, String email, String password,Clinic clinic) {
        super(firstName, lastName, email,password);
        this.employeeId = (int)Math.random()*1000000000;
        this.clinic=clinic;
    }
 **/


    public Employee(String firstName, String lastName, String email, String password,String clinicID) {
        super(firstName, lastName, email,password);
        this.clinicID=clinicID;
        this.clinic=clinic;
    }

    public Employee(){};

    //public
    public String getClinicID() { return this.clinicID; }



    // returns Department of an Employee
    public String getDepartment() { return this.department; }

    // changes Department of an Employee
    public void setDepartment(String department) { this.department = department; }

    // returns phone number of an Employee
    public int getPhone() { return this.phone; }

    // changes phone number of an Employe
    public void setPhone(int phone) { this.phone = phone; }




    public void setClinicID(String clinicNum) { clinicID=clinicNum; }

    public void setClinic(Clinic a){clinic=a;};

    public Clinic getClinic(){return clinic;};

}