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


    public double calculateUsedVacationDays(String salesForceText) {
        SalesForceParserService salesForceParser = new SalesForceParserService();
        return salesForceParser.parse(salesForceText);
    }

    public double calculateVacationDays(String startDate, String rollover, String accrualRate, String salesForceText) {
        double usedDays = calculateUsedVacationDays(salesForceText);
        DateTime convertedStartDate = calculator.convertStringToDateTime(startDate);
        double rate;
        rate = assignNullRate(startDate, accrualRate);
        double vacationDays = calculator.calculateVacationDaysGivenRate(convertedStartDate,
                Double.parseDouble(rollover), rate);

           vacationDays -= usedDays;

        return Math.round(vacationDays*100)/100d;
    }

    private double assignNullRate(String startDate, String accrualRate) {
        if(accrualRate == ""){
          return calculator.getAccrualRate(calculator.convertStringToDateTime(startDate));
        }
        else
        return Double.parseDouble(accrualRate);
    }
}
