package com.example.intercrew;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccountUnitTest {
    User u = new Patient("Poppy", "Gloria", "pg@gmail.com", "pewds");
    Account a = new Account(u, "a Patient");
    @Test
    public void getUser() {
        User expected  = u;
        User output = a.getUser();

        assertEquals(expected, output);
    }

    @Test
    public void setUser() {
        User expected = new Patient("Gloria", "Borger", "gb@gmail.com", "pewds2");
        a.setUser(expected);

        User output = a.getUser();
        assertEquals(expected, output);
    }

    @Test
    public void getRole() {
        String expected = "a Patient";
        String output = a.getRole();

        assertEquals(expected, output);
    }
}