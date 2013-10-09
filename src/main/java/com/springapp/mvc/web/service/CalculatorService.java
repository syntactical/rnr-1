package com.springapp.mvc.web.service;

import com.springapp.mvc.web.model.Calculator;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    private Calculator calculator;

    @Autowired
    public CalculatorService(Calculator calculator) {
        this.calculator = calculator;
    }

    public double calculateVacationDaysGivenRate(double rolloverDays, String date, double rate) {
        DateTime startDate = calculator.convertStringToDateTime(date);
        return calculator.calculateVacationDaysGivenRate(startDate, DateTime.now(), rolloverDays, rate);
    }
}
