package model;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Thoughtworker
 * Date: 9/12/13
 * Time: 8:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class Calculator {
    private HashMap<Integer, Integer> daysInMonth;
    private Double MONTHLY_ACCRUAL_RATE = 5 / 6d;
    private Double DAILY_ACCRUAL_RATE = (5 / 180d);
    private Double NO_VACATION_DAYS = 0d;

    public Calculator() {
        daysInMonth = createMap();
    }

    private HashMap<Integer, Integer> createMap() {
        HashMap<Integer, Integer> numberOfDaysInAMonth = new HashMap<Integer, Integer>();
        numberOfDaysInAMonth.put(1, 31);
        numberOfDaysInAMonth.put(2, 28);
        numberOfDaysInAMonth.put(3, 31);
        numberOfDaysInAMonth.put(4, 30);
        numberOfDaysInAMonth.put(5, 31);
        numberOfDaysInAMonth.put(6, 30);
        numberOfDaysInAMonth.put(7, 31);
        numberOfDaysInAMonth.put(8, 31);
        numberOfDaysInAMonth.put(9, 30);
        numberOfDaysInAMonth.put(10, 31);
        numberOfDaysInAMonth.put(11, 30);
        numberOfDaysInAMonth.put(12, 31);
        return numberOfDaysInAMonth;
    }

    public Double getVacationDaysBasedOnMonth(TWDate startDate) {
        Calendar cal = getCurrentDate();
        int month = cal.get(Calendar.MONTH) + 1;
        if (cal.get(Calendar.YEAR) < startDate.getYear()) return NO_VACATION_DAYS;
        else if (cal.get(Calendar.YEAR) == startDate.getYear()) {
            return getDaysIfStartedInSameYear(startDate, month);
        } else {
            return getDaysIfStartedInADifferentYear(startDate, month);

        }
    }

    private Double getDaysIfStartedInSameYear(TWDate startDate, int month) {
        if (startDate.getMonth() > month) return NO_VACATION_DAYS;
        int timeDifference = month - startDate.getMonth();
        Double numberOfVacationDays = (MONTHLY_ACCRUAL_RATE * timeDifference);
        return roundedNumber(numberOfVacationDays);
    }

    private Double getDaysIfStartedInADifferentYear(TWDate startDate, int month) {
        int pastMonths = 11 - startDate.getMonth() + month;
        Double numberOfVacationDays = (MONTHLY_ACCRUAL_RATE * pastMonths);
        return roundedNumber(numberOfVacationDays);
    }

    public Double getVacationBasedOnDays(TWDate startDate) {
        return roundedNumber(DAILY_ACCRUAL_RATE);
    }

    private Calendar getCurrentDate() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    private Double roundedNumber(Double unrounded) {
        return (double) (Math.round(unrounded * 100)) / 100;
    }


}
