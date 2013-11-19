package com.springapp.mvc.web.service;

import com.springapp.mvc.web.model.Constants;
import com.springapp.mvc.web.model.Employee;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class EmployeeService {

    private final DateParserService dateParser;

    @Autowired
    public EmployeeService(DateParserService dateParser) {
        this.dateParser = dateParser;
    }

    public Employee createEmployee(String startDate, String rolloverDays, Map<LocalDate, Double> daysOff, String initialAccrualRate) {
        LocalDate convertedStartDate = dateParser.parse(startDate);

        double convertedRolloverDays = parseStringWithDefaultValue(rolloverDays, 0d);
        double convertedInitialAccrualRate = parseStringWithDefaultValue(initialAccrualRate, Constants.DEFAULT_ACCRUAL_RATE);

        return new Employee(convertedStartDate, convertedRolloverDays, daysOff, convertedInitialAccrualRate);
    }

    private double parseStringWithDefaultValue(String userEntry, double defaultValue){
        double convertedValue = defaultValue;

        boolean userEnteredAValue = !userEntry.equals("");

        if(userEnteredAValue) {
            convertedValue = Double.parseDouble(userEntry);
        }

        return convertedValue;
    }
}