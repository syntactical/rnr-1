package com.springapp.mvc.web.model;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import static java.lang.Math.min;
import static org.joda.time.Days.daysBetween;

@Component
public class Calculator {

    private Double roundNumber(Double unrounded) {
        return ((double) (Math.round(unrounded * 100))) / 100;
    }

    public DateTime convertStringToDateTime(String dateAsString) {
        String[] dateFields = dateAsString.split("/");
        return new DateTime(Integer.parseInt(dateFields[2]), Integer.parseInt(dateFields[0]),
                Integer.parseInt(dateFields[1]), 0, 0);
    }

    public Double calculateVacationDaysGivenRate(DateTime startDate, double rolloverDays, double rate) {
        double cap = min(30.0, rate * 1.5);
        double accruedDays = roundNumber(rate / 365 * (double) getElapsedDaysToCurrentDate(startDate));
        accruedDays += rolloverDays;
        return min(accruedDays, cap);
    }

    public Double getAccrualRate(DateTime startDate) {
        int elapsedTime = getElapsedDaysToCurrentDate(startDate);

        final int ONEYEAR = 365;
        final int THREEYEARS = 1095;


        if (elapsedTime < ONEYEAR)
            return  10.0;
       else if (elapsedTime < THREEYEARS) {
            return  15.0;
        } else {
            return  20.0;
        }
    }

    private int getElapsedDaysToCurrentDate(DateTime startDate) {
        return daysBetween(startDate.toLocalDate(), new DateTime().toLocalDate()).getDays();
    }


}