package com.springapp.mvc.web.service;

import com.springapp.mvc.web.model.AccrualRate;
import com.springapp.mvc.web.model.Calculator;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    private Calculator calculator;
    private AccrualRate accrualRate;

    @Autowired
    public CalculatorService(Calculator calculator, AccrualRate accrualRate) {
        this.calculator = calculator;
        this.accrualRate = accrualRate;
    }


    public double calculateUsedVacationDays(String salesForceText) {
        SalesForceParserService salesForceParser = new SalesForceParserService();
        return salesForceParser.parse(salesForceText);
    }

    public double calculateVacationDays(String startDateString, String rolloverString, String initialAccrualRate, String salesForceText) {
        double usedDays = calculateUsedVacationDays(salesForceText);
        double rollover = Double.parseDouble(rolloverString);
        DateTime startDate = calculator.convertStringToDateTime(startDateString);

        accrualRate = new AccrualRate(initialAccrualRate).givenIStarted(startDate);
        double vacationDays = calculator.calculateVacationDaysGivenRate(startDate, rollover, accrualRate.calculate());

        vacationDays -= usedDays;

        return Math.round(vacationDays*100)/100d;
    }


}
