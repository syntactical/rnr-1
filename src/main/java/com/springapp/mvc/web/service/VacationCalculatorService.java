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
        return "You have reached your vacation day cap.";
    }
}
