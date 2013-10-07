package service;

import model.Calculator;
import model.RolloverCalculator;
import org.joda.time.DateTime;

public class CalculatorService {
    private final RolloverCalculator rolloverCalculator;
    private final Calculator calculator;

    public CalculatorService() {
        this.rolloverCalculator = new RolloverCalculator();
        this.calculator = new Calculator();
    }

    public CalculatorService(RolloverCalculator rolloverCalculator, Calculator calculator) {
        this.rolloverCalculator = rolloverCalculator;
        this.calculator = calculator;
    }

    public double calculateVacationDays(double rolloverDays, String date) {
        System.out.println(date);
        DateTime startDate = calculator.convertStringToDateTime(date);
        double accruedDays = 0d;

        if(startDate.getYear()==DateTime.now().getYear()){
            accruedDays = calculator.calculateVacationDays(startDate, DateTime.now());
        }else{
           accruedDays = calculator.calculateAccruedDaysFromJanuaryFirstOfThisYear(startDate, DateTime.now());
        }
        return this.rolloverCalculator.calculateVacationDays(rolloverDays, accruedDays);
    }

}
