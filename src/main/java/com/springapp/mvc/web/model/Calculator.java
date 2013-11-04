package com.springapp.mvc.web.model;

import org.joda.time.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static java.lang.Math.min;
import static org.joda.time.Days.daysBetween;

@Component
public class Calculator {

    public static final LocalDate SALESFORCE_START_DATE = new LocalDate(2013, 7, 1);
    public static final double YEAR_IN_DAYS = 365.25;
    public static final LocalDate TODAY = new LocalDate();

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
        double cap = min(30.0, rate * 365 * 1.5);
        double accruedDays = roundNumber(rate * (double) getElapsedDaysToCurrentDate(startDate));
        accruedDays = Math.round(accruedDays + rolloverDays);
        return min(accruedDays, cap);
    }

    public int getElapsedDaysToCurrentDate(DateTime startDate) {
        return daysBetween(startDate.toLocalDate(), new DateTime().toLocalDate()).getDays();
    }


    public Double getVacationBasedOnIntervals(Employee employee, LocalDate accrualEndDate) {

        LocalDate accrualStartDate = (employee.getStartDate().isAfter(SALESFORCE_START_DATE)) ? employee.getStartDate() : SALESFORCE_START_DATE;

        if (employee.getStartDate().isBefore(SALESFORCE_START_DATE)){
            accrualStartDate = SALESFORCE_START_DATE;
        }

        double vacationDays = employee.getRolloverDays();

        for (LocalDate date = accrualStartDate; date.isBefore(accrualEndDate); date = date.plusWeeks(1))
        {
            double accrualRate = employee.calculateDailyAccrualRate(date);
            double cap = min(30, accrualRate * 1.5);

            if (employee.getDaysOff().get(date) != null){
                vacationDays -= employee.getDaysOff().get(date) / 8;
            }

            vacationDays = min(cap, vacationDays + (accrualRate / YEAR_IN_DAYS) * 7);
        }
       return vacationDays;
    }
}