package com.springapp.mvc.web.service;

import com.springapp.mvc.web.model.AccrualRateCalculator;
import com.springapp.mvc.web.model.Employee;
import com.springapp.mvc.web.model.VacationCalculator;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;

public class VacationCalculatorService {

    @Autowired
    private VacationCalculator vacationCalculator;

    public VacationCalculatorService(VacationCalculator vacationCalculator) {

        this.vacationCalculator = vacationCalculator;
    }

    public Double getVacationDays(Employee employee, AccrualRateCalculator accrualRateCalculator, LocalDate date) {
        return vacationCalculator.getVacationDays(employee, accrualRateCalculator, date);
    }

    public String getVacationCapNotice(Employee employee, AccrualRateCalculator accrualRateCalculator, LocalDate date) {
        double daysUntilCap = vacationCalculator.getDaysUntilCapIsReached(employee, accrualRateCalculator, date);
        String message = "You have reached your vacation day cap.";

        if(daysUntilCap >= 30.0){
            message = "";
        } else if (daysUntilCap > 0.0) {
            message = "You are " + (int) Math.round(daysUntilCap) + " days away from reaching your vacation day cap.";
        }

        return message;
    }
}
