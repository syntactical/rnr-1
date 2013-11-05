package com.springapp.mvc.web.model;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import static com.google.common.primitives.Doubles.max;
import static java.lang.Math.min;
import static org.joda.time.Days.daysBetween;

@Component
public class AccrualRate {
    final double YEAR_IN_DAYS = 365.25;
    final double MAX_VACATION_DAYS = 30d;

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
