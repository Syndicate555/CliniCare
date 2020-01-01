package com.example.intercrew;

public class Account {
    User p;
    String role;
    String WelcomeMessage;
    String accountID;



    //constructor with user object
    public Account(User p,String role){
        this.p=p;
        this.role=role;

    }



    public Account(){};


    public User getUser() { return p ;};

    public void setUser(User a){p=a;}

    public String getRole() { return role ;};




}
