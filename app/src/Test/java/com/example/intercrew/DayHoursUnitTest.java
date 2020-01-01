package com.example.intercrew;

import org.junit.Test;

import static org.junit.Assert.*;

public class DayHoursUnitTest {
    DayHours d = new DayHours("123", 6, 30, 18, 0);

    @Test
    public void setStartHours() {
        int expected = 6;
        int output = d.getStartHours();
        assertEquals(expected, output);

        expected = 8;
        d.setStartHours(expected);
        output = d.getStartHours();
        assertEquals(expected, output);
    }

    @Test
    public void setStartMinutes() {
        int expected = 30;
        int output = d.getStartMinutes();
        assertEquals(expected, output);

        expected = 15;
        d.setStartMinutes(expected);
        output = d.getStartMinutes();
        assertEquals(expected, output);
    }

    @Test
    public void setEndHours() {
        int expected = 18;
        int output = d.getEndHours();
        assertEquals(expected, output);

        expected = 19;
        d.setEndHours(expected);
        output = d.getEndHours();
        assertEquals(expected, output);
    }

    @Test
    public void setEndMinutes() {
        int expected = 0;
        int output = d.getEndMinutes();
        assertEquals(expected, output);

        expected = 45;
        d.setEndMinutes(expected);
        output = d.getEndMinutes();
        assertEquals(expected, output);
    }
}