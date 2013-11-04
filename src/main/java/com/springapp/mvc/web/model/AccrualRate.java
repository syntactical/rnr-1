package com.springapp.mvc.web.model;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import static java.lang.Math.min;
import static com.google.common.primitives.Doubles.max;
import static org.joda.time.Days.daysBetween;

@Component
public class AccrualRate {
    private LocalDate startDate;
    private DateTime startDateDateTime;
    private String accrualRate;
    private double initialAccrualRate;
    final double YEAR_IN_DAYS = 365.25;
    final double MAX_VACATION_DAYS = 30d;

    public AccrualRate() {
        this("");
    }

    public AccrualRate(LocalDate startDate, double initialAccrualRate){
        this.startDate = startDate;
        this.initialAccrualRate = initialAccrualRate;
    }

    public AccrualRate(String initialAccrualRate) {
        this.accrualRate = initialAccrualRate;
    }
    
    public AccrualRate givenIStarted(DateTime startDate) {
        this.startDateDateTime = startDate;
        return this;
    }

    public double calculate() {
        return accrualRate.isEmpty() ? generateRate(startDateDateTime) : Math.max(Double.parseDouble(accrualRate), generateRate(startDateDateTime));
    }

    public Double generateRate(DateTime startDate) {
        int elapsedTime = daysBetween(startDate.toLocalDate(), new DateTime().toLocalDate()).getDays();

        if (elapsedTime < YEAR_IN_DAYS) {
            return  10.0/365;
        } else if (elapsedTime < 3 * YEAR_IN_DAYS) {
            return  15.0/365;
        } else if (elapsedTime < 6 * YEAR_IN_DAYS){
            return  20.0/365;
        } else {
            return 25.0/365;
        }
    }


    public double calculateDailyAccrualRate(LocalDate startDate, LocalDate endDate, double initialAccrualRate) {
        double tenure = daysBetween(startDate, endDate).getDays();

        if (tenure < YEAR_IN_DAYS) {
            return max(10.0, initialAccrualRate) / YEAR_IN_DAYS;
        } else if (tenure < 3 * YEAR_IN_DAYS) {
            return max(15.0, initialAccrualRate) / YEAR_IN_DAYS;
        } else if (tenure < 6 * YEAR_IN_DAYS) {
            return max(20.0, initialAccrualRate) / YEAR_IN_DAYS;
        } else {
            return max(25.0, initialAccrualRate) / YEAR_IN_DAYS;
        }
    }

    public double calculateVacationDayCap(LocalDate startDate, LocalDate endDate, double initialAccrualRate){
        double currentAccrualRate = calculateDailyAccrualRate(startDate, endDate, initialAccrualRate);
        return min(currentAccrualRate * 1.5 * YEAR_IN_DAYS, MAX_VACATION_DAYS);
    }

}
