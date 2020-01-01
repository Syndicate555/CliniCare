package com.example.intercrew;

import com.example.intercrew.Admin.Service;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ClinicUnitTest {
    Service s1 = new Service("1", "CoolService", "Doctor");
    Service s2 = new Service("12", "OkayService", "NotADoctor");
    Service s3 = new Service("123", "DopeService", "Nurse");
    List<Service> services = Arrays.asList(s1,s2,s3);

    Clinic c = new Clinic("123 Street", 1234567, "Inter Clinic",
            "Inter Insurance", "InterPay", services, "Identifier");


    @Test
    public void setClinicID() {
        String expected = "Identifier";
        String output = c.getClinicID();
        assertEquals(expected, output);

        expected = "ID2";
        c.setClinicID(expected);
        output = c.getClinicID();
        assertEquals(expected, output);
    }

    @Test
    public void setServices() {
        List<Service> expected = services;
        List<Service> output = c.getServices();
        assertEquals(expected, output);

        s3 = new Service("143", "Emotional Support", "Friend");
        expected = Arrays.asList(s1,s2,s3);
        c.setServices(expected);
        output = c.getServices();
        assertEquals(expected, output);
    }

    @Test
    public void setWorkingHours() {
        DayHours d1 = new DayHours("12", 8, 30, 18, 45);
        DayHours d2 = new DayHours("123", 6, 00, 14,30);
        DayHours d3 = new DayHours("1", 10, 15, 19, 30);
        List<DayHours> expected = Arrays.asList(d1,d2,d3);

        c.setWorkingHours(expected);
        List<DayHours> output = c.getWorkingHours();
        assertEquals(expected, output);
    }

    @Test
    public void setAddress() {
        String expected = "123 Street";
        String output = c.getAddress();
        assertEquals(expected, output);

        expected = "Ottawa Street";
        c.setAddress(expected);
        output = c.getAddress();
        assertEquals(expected, output);
    }

    @Test
    public void setPhone() {
        Integer expected = 1234567;
        Integer output = c.getPhone();
        assertEquals(expected, output);

        expected = 7654321;
        c.setPhone(expected);
        output = c.getPhone();
        assertEquals(expected, output);
    }

    @Test
    public void setClinicName() {
        String expected = "Inter Clinic";
        String output = c.getClinicName();
        assertEquals(expected, output);

        expected = "Better Clinic";
        c.setClinicName(expected);
        output = c.getClinicName();
        assertEquals(expected, output);
    }

    @Test
    public void setInsuranceType() {
        String expected = "Inter Insurance";
        String output = c.getInsuranceType();
        assertEquals(expected, output);

        expected = "Better Insurance";
        c.setInsuranceType(expected);
        output = c.getInsuranceType();
        assertEquals(expected, output);
    }

    @Test
    public void setPaymentMethod() {
        String expected = "InterPay";
        String output = c.getPaymentMethod();
        assertEquals(expected, output);

        expected = "BetterPay";
        c.setPaymentMethod(expected);
        output = c.getPaymentMethod();
        assertEquals(expected, output);
    }

    @Test
    public void setRating() {
        float expected = 3.5f;
        c.setRating(expected);
        float output = c.getRating();
        double delta = 0.1;
        assertEquals(expected, output, delta);
    }

    @Test
    public void setWaitTime() {
        int expected = 15;
        c.setWaitTime(expected);
        int output = c.getWaitTime();
        assertEquals(expected, output);
    }
}