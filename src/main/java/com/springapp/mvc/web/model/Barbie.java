package com.springapp.mvc.web.model;

import org.joda.time.LocalDate;

import java.util.Map;

public class Barbie extends Employee {
    public Barbie(LocalDate startDate, double rolloverDays, Map<LocalDate, Double> daysOff, double initialAccrualRate) {
        super(startDate, rolloverDays, daysOff, initialAccrualRate);
    }

    public void caffinate() {
        System.out.println("Barbie puts two bags of green tea in her Thermos and pours hot water over it.");
    }

    public void calculateVacationTimeFor(Employee employee) throws InterruptedException {
        System.out.println("Let me get back to you with that.");
        Thread.sleep(10000);
    }
}
