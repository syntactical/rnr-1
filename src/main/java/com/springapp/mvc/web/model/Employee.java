package com.springapp.mvc.web.model;

import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.util.HashMap;

import static com.google.common.primitives.Doubles.max;
import static org.joda.time.Days.days;
import static org.joda.time.Days.daysBetween;

/**
 * Created with IntelliJ IDEA.
 * User: Thoughtworker
 * Date: 10/31/13
 * Time: 4:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class Employee {
    private final LocalDate startDate;
    private final double initialAccrualRate;
    private final double rolloverDays;
    private final HashMap<LocalDate, Double> daysOff;

    private final double YEAR_IN_DAYS = 365.25;
    private static final double DEFAULT_INITIAL_ACCRUAL_RATE = 10;

    public Employee(LocalDate startDate, double rolloverDays, HashMap<LocalDate, Double> daysOff) {
        this(startDate, rolloverDays, daysOff, DEFAULT_INITIAL_ACCRUAL_RATE);
    }

    public Employee(LocalDate startDate, double rolloverDays, HashMap<LocalDate, Double> daysOff, double initialAccrualRate) {
        this.startDate = startDate;
        this.rolloverDays = rolloverDays;
        this.daysOff = daysOff;
        this.initialAccrualRate = initialAccrualRate;
    }

    public double calculateDailyAccrualRate(LocalDate endDate) {
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
}
