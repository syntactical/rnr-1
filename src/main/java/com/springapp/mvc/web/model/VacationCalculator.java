package com.springapp.mvc.web.model;

import org.joda.time.*;
import org.springframework.stereotype.Component;

import static java.lang.Math.min;

@Component
public class VacationCalculator {

    public static final LocalDate SALESFORCE_START_DATE = new LocalDate(2013, 7, 1);

    public Double getVacationDays(Employee employee, AccrualRateCalculator accrualRateCalculator, LocalDate accrualEndDate) {

        LocalDate accrualStartDate = getAccrualStartDate(employee);

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

    private LocalDate getAccrualStartDate(Employee employee) {
        LocalDate accrualStartDate = SALESFORCE_START_DATE;

        if(employee.getStartDate().isAfter(SALESFORCE_START_DATE)){
            accrualStartDate = employee.getStartDate();
        }

        return accrualStartDate;
    }

    public Double getDaysUntilCapIsReached(Employee employee, AccrualRateCalculator accrualRateCalculator, LocalDate date) {

        LocalDate endDate = date;
        double daysUntilCap = -1.0;
        double vacationDays = employee.getRolloverDays();

        while (vacationDays <= accrualRateCalculator.calculateVacationDayCap(employee, endDate)){
            vacationDays += accrualRateCalculator.calculateDailyAccrualRate(employee, endDate);

            endDate = endDate.plusDays(1);
            daysUntilCap++;
        }

        return daysUntilCap;
    }
}