package model;

import org.joda.time.DateTime;

import static org.joda.time.Days.daysBetween;

public class Calculator {

    private static final Double DAILY_ACCRUAL_RATE = (10 / 365d);

    public Double calculateVacationDays(DateTime startDate, DateTime currentDate) {
        return roundNumber(DAILY_ACCRUAL_RATE *
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
}