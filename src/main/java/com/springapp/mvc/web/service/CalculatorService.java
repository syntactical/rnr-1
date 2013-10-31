package com.springapp.mvc.web.service;

import com.springapp.mvc.web.model.AccrualRate;
import com.springapp.mvc.web.model.Calculator;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CalculatorService {

    private Calculator calculator;
    private AccrualRate accrualRate;

    @Autowired
    public CalculatorService(Calculator calculator, AccrualRate accrualRate) {
        this.calculator = calculator;
        this.accrualRate = accrualRate;
    }


    public HashMap<LocalDate,Double> calculateUsedVacationDays(String salesForceText) {
        SalesForceParserService salesForceParser = new SalesForceParserService();
        return salesForceParser.parse(salesForceText);
    }

    public double calculateVacationDays(String startDateString, String rolloverString, String initialAccrualRate, String salesForceText) {

        DateTime startDate = calculator.convertStringToDateTime(startDateString);

        double usedDays = calculateUsedVacationDays(salesForceText).isEmpty()? 0: calculateUsedVacationDays(salesForceText)
                .get(new LocalDate()) ;
        double rollover = Double.parseDouble(rolloverString);

        accrualRate = new AccrualRate(initialAccrualRate).givenIStarted(startDate);
        double vacationDays = calculator.calculateVacationDaysGivenRate(startDate, rollover, accrualRate.calculate());

        vacationDays -= usedDays;

        return Math.round(vacationDays*100)/100d;
    }


}
