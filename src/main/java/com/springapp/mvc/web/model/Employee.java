package com.springapp.mvc.web.model;

import org.joda.time.LocalDate;
import java.util.HashMap;

public class Employee {
    private final LocalDate startDate;
    private final double rolloverDays;
    private final HashMap<LocalDate, Double> daysOff;
    private double initialAccrualRate;

    public Employee(LocalDate startDate, double rolloverDays, HashMap<LocalDate, Double> daysOff, double initialAccrualRate) {
        this.startDate = startDate;
        this.rolloverDays = rolloverDays;
        this.daysOff = daysOff;
        this.initialAccrualRate = initialAccrualRate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public double getRolloverDays() {
        return rolloverDays;
    }

    public HashMap<LocalDate, Double> getDaysOff() {
        return daysOff;
    }

    public double getInitialAccrualRate() {
        return initialAccrualRate;
    }
}
