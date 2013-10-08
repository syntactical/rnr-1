package com.springapp.mvc.web.service;

import com.springapp.mvc.web.model.Calculator;
import com.springapp.mvc.web.model.RolloverCalculator;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    private RolloverCalculator rolloverCalculator;
    private Calculator calculator;

    @Autowired
    public CalculatorService(RolloverCalculator rolloverCalculator, Calculator calculator) {
        this.rolloverCalculator = rolloverCalculator;
        this.calculator = calculator;
    }

    public double calculateVacationDays(double rolloverDays, String date) {
        DateTime startDate = calculator.convertStringToDateTime(date);
        double accruedDays;

        if(startDate.getYear()==DateTime.now().getYear()){
            accruedDays = calculator.calculateVacationDays(startDate, DateTime.now());
        }else{
           accruedDays = calculator.calculateAccruedDaysFromJanuaryFirstOfThisYear(startDate, DateTime.now());
        }
        Double cap = rolloverCalculator.calculateCap(startDate, DateTime.now());
        return Math.min(this.rolloverCalculator.calculateVacationDays(rolloverDays, accruedDays), cap);
    }

}
