package com.example.intercrew;

import org.junit.Test;

import static org.junit.Assert.*;

public class AppointmentTest {
    Appointment a = new Appointment("123", "Monday", "18:00");

    @Test
    public void setID() {
        String expected = "12345";
        a.setID(expected);
        String output = a.getID();


        assertEquals(expected, output);
    }

    @Test
    public void getID() {
        String expected  = "123";
        String output = a.getID();

        assertEquals(expected, output);
    }

    @Test
    public void setDay() {
        String expected = "Tuesday";
        a.setDay(expected);
        String output = a.getDay();

        assertEquals(expected, output);
    }

    @Test
    public void getDay() {
        String expected = "Monday";
        String output = a.getDay();

        assertEquals(expected, output);
    }

    @Test
    public void setTime() {
        String expected = "9:00";
        a.setTime(expected);
        String output = a.getTime();

        assertEquals(expected, output);
    }

    @Test
    public void getTime() {
        String expected = "18:00";
        String output = a.getTime();

        assertEquals(expected, output);
    }
}