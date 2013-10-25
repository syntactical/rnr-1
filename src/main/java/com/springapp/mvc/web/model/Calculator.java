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
        DateTime newDate = new DateTime(Integer.parseInt(dateFields[2]), Integer.parseInt(dateFields[0]),
                Integer.parseInt(dateFields[1]), 0, 0);
        return newDate;
    }

    public Double calculateVacationDaysGivenRate(DateTime startDate, double rolloverDays, double rate) {
        double cap = min(30.0, rate*365 * 1.5);
        double accruedDays = roundNumber(rate * (double) getElapsedDaysToCurrentDate(startDate));
        accruedDays = Math.round(accruedDays+rolloverDays);
        return min(accruedDays, cap);
    }

    public int getElapsedDaysToCurrentDate(DateTime startDate) {
        return daysBetween(startDate.toLocalDate(), new DateTime().toLocalDate()).getDays();
    }


}