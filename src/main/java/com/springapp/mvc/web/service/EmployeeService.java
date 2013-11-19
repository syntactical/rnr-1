package com.springapp.mvc.web.service;

import com.springapp.mvc.web.model.Employee;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class EmployeeService {

    private final DateParserService dateParser;

    private static final double DEFAULT_ACCRUAL_RATE = 10;

    @Autowired
    public EmployeeService(DateParserService dateParser) {
        this.dateParser = dateParser;
    }

    public Employee createEmployee(String startDate, String rolloverDays, Map<LocalDate, Double> daysOff, String initialAccrualRate) {
        LocalDate date = dateParser.parse(startDate);

        double rollover = rolloverDays.equals("") ? 0 : Double.parseDouble(rolloverDays);
        Employee employee = initialAccrualRate.equals("") ? new Employee(date, rollover, daysOff, DEFAULT_ACCRUAL_RATE)
                : new Employee(date, rollover, daysOff, Double.parseDouble(initialAccrualRate));
        return employee;
    }
}

