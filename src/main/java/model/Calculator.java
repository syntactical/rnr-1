package model;

import java.util.HashMap;


public class Calculator {
    private final TDate currentDate;
    private HashMap<Integer, Integer> daysInMonth;
    private Double DAILY_ACCRUAL_RATE = (10 / 365d);
    private Double NO_VACATION_DAYS = 0d;


    public Calculator(TDate currentDate) {
        daysInMonth = createMap();
        this.currentDate = currentDate;
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

    public Double getVacationBasedOnDays(TDate startDate) {
        return roundedNumber(DAILY_ACCRUAL_RATE * daysBetween(startDate, currentDate));
    }

    private Double roundedNumber(Double unrounded) {
        return (double) (Math.round(unrounded * 100)) / 100;
    }

    public Integer daysBetween(TDate startDate, TDate currentDate) {
        int currentDays = getDaysPassedThisYear(currentDate);
        if (!startDate.getYear().equals(currentDate.getYear())){
            int yearsDifference = currentDate.getYear() - startDate.getYear();
            int startDays = getDaysPassedThisYear(startDate);
            int difference = 365 * yearsDifference - startDays;
            return currentDays + difference;
        }
        if (startDate.getMonth() == currentDate.getMonth()) return currentDate.getDay() - startDate.getDay();

        int startDays = getDaysPassedThisYear(startDate);
        return currentDays - startDays;

    }

    private int getDaysPassedThisYear(TDate date) {
        int days = 0;
        for (Integer month = 1; month <= date.getMonth(); month++) {
            days += daysInMonth.get(month);
        }
        int differenceOfDays = daysInMonth.get(date.getMonth()) - date.getDay();
        return days - differenceOfDays;
    }
}
