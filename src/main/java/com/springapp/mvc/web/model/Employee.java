package com.springapp.mvc.web.model;

import org.joda.time.LocalDate;
import java.util.HashMap;

public class Employee {
    private final LocalDate startDate;
    private final double rolloverDays;
    private final HashMap<LocalDate, Double> daysOff;
    private AccrualRate accrualRate;
    private double initialAccrualRate;

    private static final double DEFAULT_ACCRUAL_RATE = 10;

    public Employee(LocalDate startDate, double rolloverDays, HashMap<LocalDate, Double> daysOff, AccrualRate accrualRate) {
        this(startDate, rolloverDays, daysOff, accrualRate, DEFAULT_ACCRUAL_RATE);
    }

    public Employee(LocalDate startDate, double rolloverDays, HashMap<LocalDate, Double> daysOff, AccrualRate accrualRate, double initialAccrualRate) {
        this.startDate = startDate;
        this.rolloverDays = rolloverDays;
        this.daysOff = daysOff;
        this.accrualRate = accrualRate;
        this.initialAccrualRate = initialAccrualRate;
    }

    public double calculateDailyAccrualRate(LocalDate endDate) {
        return accrualRate.calculateDailyAccrualRate(startDate, endDate, initialAccrualRate);
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

    public double calculateVacationDayCap(LocalDate endDate) {
        return accrualRate.calculateVacationDayCap(startDate, endDate, initialAccrualRate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;

        Employee employee = (Employee) o;

        if (Double.compare(employee.initialAccrualRate, initialAccrualRate) != 0) return false;
        if (Double.compare(employee.rolloverDays, rolloverDays) != 0) return false;
        if (daysOff != null ? !daysOff.equals(employee.daysOff) : employee.daysOff != null) return false;
        if (startDate != null ? !startDate.equals(employee.startDate) : employee.startDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = startDate != null ? startDate.hashCode() : 0;
        temp = Double.doubleToLongBits(rolloverDays);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (daysOff != null ? daysOff.hashCode() : 0);
        temp = Double.doubleToLongBits(initialAccrualRate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
