package com.springapp.mvc.web.model;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import static com.springapp.mvc.web.model.AccrualRate.*;
import static org.joda.time.Days.daysBetween;

@Component
public class Calculator {

    public Double calculateVacationDays(DateTime startDate, DateTime currentDate) {
        double accrualRate = getDailyAccrualRate(startDate, currentDate);
        return roundNumber(accrualRate *
                daysBetween(startDate.toLocalDate(), currentDate.toLocalDate()).getDays());
    }

    private Double roundNumber(Double unrounded) {
        return ((double) (Math.round(unrounded * 100))) / 100;
    }

    public DateTime convertStringToDateTime(String dateAsString) {
        String[] dateFields = dateAsString.split("/");
        return new DateTime(Integer.parseInt(dateFields[2]), Integer.parseInt(dateFields[0]),
                Integer.parseInt(dateFields[1]), 0, 0);
    }

    public Double getDailyAccrualRate(DateTime startDate, DateTime currentDate) {
        int daysAtThoughtWorks = daysBetween(startDate.toLocalDate(), currentDate.toLocalDate()).getDays();
        double years = daysAtThoughtWorks / 365d;
        if (years <= 1) return FIRST_YEAR.rate();
        if (years > 1 && years <= 3) return LESS_THAN_THREE_YEARS.rate();
        if (years > 3 && years <= 6) return LESS_THAN_SIX_YEARS.rate();
        return SIX_YEARS_OR_MORE.rate();
    }

    public double calculateAccruedDaysFromJanuaryFirstOfThisYear(DateTime startDate, DateTime currentDate) {
        Double rate = getDailyAccrualRate(startDate, currentDate);
        DateTime firstOfYear = new DateTime(currentDate.getYear(), 1, 1, 0, 0);
        return roundNumber(rate * daysBetween(firstOfYear.toLocalDate(), currentDate.toLocalDate()).getDays());

    }

}