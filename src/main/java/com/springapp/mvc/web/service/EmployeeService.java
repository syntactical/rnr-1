package com.springapp.mvc.web.service;

import com.springapp.mvc.web.model.AccrualRateCalculator;
import com.springapp.mvc.web.model.Employee;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

public class EmployeeService {


    private final DateParserService dateParser;

    @Autowired
    public EmployeeService(DateParserService dateParser) {

        this.dateParser = dateParser;
    }

    public Employee createEmployee(String startDate, String rolloverDays, HashMap<LocalDate, Double> daysOff, String initialAccrualRate) {

        LocalDate date = dateParser.parse(startDate);

        double rollover = rolloverDays.equals("") ? 0 : Double.parseDouble(rolloverDays);
        Employee employee = initialAccrualRate.equals("") ? new Employee(date, rollover, daysOff, new AccrualRateCalculator())
                : new Employee(date, rollover, daysOff, new AccrualRateCalculator(), Double.parseDouble(initialAccrualRate));
        return employee;
    }
}

