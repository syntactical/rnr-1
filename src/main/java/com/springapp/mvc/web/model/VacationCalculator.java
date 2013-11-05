package com.springapp.mvc.web.model;

import org.joda.time.*;
import org.springframework.stereotype.Component;

import static java.lang.Math.min;
import static org.joda.time.Days.daysBetween;

@Component
public class VacationCalculator {

    public static final LocalDate SALESFORCE_START_DATE = new LocalDate(2013, 7, 1);

    public Double getVacationDays(Employee employee, LocalDate accrualEndDate) {

        LocalDate accrualStartDate = (employee.getStartDate().isAfter(SALESFORCE_START_DATE)) ? employee.getStartDate() : SALESFORCE_START_DATE;
        if (employee.getStartDate().isBefore(SALESFORCE_START_DATE)){
            accrualStartDate = SALESFORCE_START_DATE;
        }
        double vacationDays = employee.getRolloverDays();

        for (LocalDate date = accrualStartDate; date.isBefore(accrualEndDate); date = date.plusWeeks(1))
        {
            double accrualRate = employee.calculateDailyAccrualRate(date);

            if (employee.getDaysOff().get(date) != null){
                vacationDays -= employee.getDaysOff().get(date) / 8;
            }

            double accruedDaysPerWeekThisYear = (accrualRate) * 7;

            vacationDays = min(employee.calculateVacationDayCap(date), vacationDays + accruedDaysPerWeekThisYear);
        }

       return vacationDays;
    }
}