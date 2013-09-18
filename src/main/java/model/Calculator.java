package model;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


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

    public Double getVacationBasedOnDays(TDate startDate) {
        Calendar calendar = getCurrentDate();
        String date = (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.YEAR);
        TDate currentDate = new TDate(date);
        return roundedNumber(DAILY_ACCRUAL_RATE * daysBetween(startDate, currentDate));
    }

    private Calendar getCurrentDate() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    private Double roundedNumber(Double unrounded) {
        return (double) (Math.round(unrounded * 100)) / 100;
    }


    public Integer daysBetween(TDate startDate, TDate currentDate) {
        int currentDays = getDays(currentDate);
        if (!startDate.getYear().equals(currentDate.getYear())){
            int startDays = getDays(startDate);
            int difference = 365 - startDays;
            return currentDays + difference;
        }
        if (startDate.getMonth() == currentDate.getMonth()) return currentDate.getDay() - startDate.getDay();

        int startDays = getDays(startDate);
        return currentDays - startDays;

    }

    private int getDays(TDate date) {
        int days = 0;
        for (Integer month = 1; month <= date.getMonth(); month++) days += daysInMonth.get(month);
        int differenceOfDays = daysInMonth.get(date.getMonth()) - date.getDay();
        return days - differenceOfDays;
    }
}
