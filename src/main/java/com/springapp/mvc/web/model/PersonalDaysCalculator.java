package com.springapp.mvc.web.model;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class PersonalDaysCalculator {
    public double calculatePersonalDays(Employee employee, LocalDate startDate, LocalDate endDate) {
        double personalDaysAllotted = calculatePersonalDaysBasedOnStartDate(startDate, endDate);
        double personalDaysTaken = employee.getPersonalDaysTaken();

        return personalDaysAllotted - personalDaysTaken;
    }

    private double calculatePersonalDaysBasedOnStartDate(LocalDate startDate, LocalDate endDate) {
        int personalDays = 7;

        if(startDate.getYear() == endDate.getYear()){
            int month = startDate.getMonthOfYear();
            if(month >= 9){
                personalDays = 3;
            } else if (month >= 5){
                personalDays = 4;
            }
        }

        return personalDays;
    }
}
