package com.example.intercrew.Admin;

import org.junit.Test;

import static org.junit.Assert.*;

public class ServiceTest {
    Service sTest = new Service("Identifier", "CoolService", "Doctor");

    @Test
    public void getID() {
        String expected = "Identifier";
        String output = sTest.getID();

        assertEquals(expected, output);
    }

    @Test
    public void getServiceName() {
        String expected = "CoolService";
        String output = sTest.getServiceName();

        assertEquals(expected, output);
    }

    @Test
    public void getRole() {
        String expected = "Doctor";
        String output = sTest.getRole();

        assertEquals(expected, output);
    }

    @Test
    public void toString1() {
        String expected = "CoolService";
        String output = sTest.toString();

        assertEquals(expected, output);
    }
}