package model;

import org.joda.time.DateTime;

import static org.joda.time.Days.daysBetween;

public class Calculator {

    private static final Double DAILY_ACCRUAL_RATE_FOR_FIRST_YEAR = (10 / 365d);
    private static final Double DAILY_ACCRUAL_RATE_FOR_LESS_THAN_THREE_YEARS = (15 / 365d);
    private static final Double DAILY_ACCRUAL_RATE_FOR_LESS_THAN_SIX_YEARS = (20 / 365d);
    private static final Double DAILY_ACCRUAL_RATE_FOR_SIX_YEARS_OR_MORE = (25 / 365d);


    public Double calculateVacationDays(DateTime startDate, DateTime currentDate) {
        double accrualRate = getDailyAccrualRate(startDate,currentDate);
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
        if (years < 1) return DAILY_ACCRUAL_RATE_FOR_FIRST_YEAR;
        if (years >= 1 && years < 3) return DAILY_ACCRUAL_RATE_FOR_LESS_THAN_THREE_YEARS;
        if (years >= 3 && years < 6) return DAILY_ACCRUAL_RATE_FOR_LESS_THAN_SIX_YEARS;
        return DAILY_ACCRUAL_RATE_FOR_SIX_YEARS_OR_MORE;
    }

    public double calculateAccruedDaysFromJanuaryFirstOfThisYear(DateTime startDate, DateTime currentDate) {
        Double rate = getDailyAccrualRate(startDate, currentDate);
        DateTime firstOfYear = new DateTime(currentDate.getYear(), 1, 1,0,0);
        return roundNumber(rate*daysBetween(firstOfYear.toLocalDate(), currentDate.toLocalDate()).getDays());

    }

}