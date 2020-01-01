package com.example.intercrew;


public abstract class User {

    // instance variables
    String firstName;
    String lastName;
    String email;
    String password;
    String ID;

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }



    public User(){};

    public String getId(){return ID;}

    public void setId(String id){ID=id;}

    public String getFirstName() { return this.firstName; }

    public void setFirstName(String fName) { this.firstName = fName; }

    public String getLastName() {return this.lastName; }

    public void setLastName(String lName) {this.lastName = lName; }

    public String getEmail() { return this.email; }

    public void setEmail(String email) { this.email = email; }
}