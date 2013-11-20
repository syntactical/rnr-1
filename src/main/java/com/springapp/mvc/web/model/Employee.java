package com.springapp.mvc.web.model;

import org.joda.time.LocalDate;
import java.util.Map;

public class Employee {
    private final LocalDate startDate;
    private final double rolloverDays;
    private final Map<LocalDate, Double> daysOff;
    private double initialAccrualRate;

    public Employee(LocalDate startDate, double rolloverDays, Map<LocalDate, Double> daysOff, double initialAccrualRate) {
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

    public Map<LocalDate, Double> getDaysOff() {
        return daysOff;
    }

    public double getInitialAccrualRate() {
        return initialAccrualRate;
    }

    public void caffinate() {
	System.out.println("The employee sadly pours self a cheap and bitter cup of coffee from the breakroom.");
    }

    public void askBarbieForVacationDays() {
	System.out.println("I don't know how to get in touch with her.");
    }
}
