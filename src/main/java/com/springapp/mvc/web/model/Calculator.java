package com.springapp.mvc.web.model;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import static com.springapp.mvc.web.model.AccrualRate.*;
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

    public Double calculateVacationDaysGivenRate(DateTime startDate, DateTime currentDate, double rolloverDays, double rate) {
        double cap = min(30.0, rate * 1.5);


        Double accruedDays = rolloverDays + roundNumber(rate/365*daysBetween(startDate.toLocalDate(),currentDate.toLocalDate()).getDays());

        return min(accruedDays, cap);
    }
}