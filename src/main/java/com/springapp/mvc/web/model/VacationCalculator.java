package com.springapp.mvc.web.model;

import org.joda.time.*;
import org.springframework.stereotype.Component;

import static java.lang.Math.min;

@Component
public class VacationCalculator {

    public static final LocalDate SALESFORCE_START_DATE = new LocalDate(2013, 7, 1);

    public Double getVacationDays(Employee employee, AccrualRateCalculator accrualRateCalculator, LocalDate accrualEndDate) {

        LocalDate accrualStartDate = (employee.getStartDate().isAfter(SALESFORCE_START_DATE)) ? employee.getStartDate() : SALESFORCE_START_DATE;

        double vacationDays = employee.getRolloverDays();

        for (LocalDate date = accrualStartDate; date.isBefore(accrualEndDate); date = date.plusDays(1))
        {
            double accrualRate = accrualRateCalculator.calculateDailyAccrualRate(employee, date);

            if (employee.getDaysOff().get(date) != null){
                vacationDays -= employee.getDaysOff().get(date) / 8;
            }

            vacationDays = min(accrualRateCalculator.calculateVacationDayCap(employee, date), vacationDays + accrualRate);
        }

       return vacationDays;
    }


}