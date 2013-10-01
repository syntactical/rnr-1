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

    public double calculateVacationDays(double rolloverDays) {
        double accruedDays = calculateAccruedDaysFromJanuaryFirstOfThisYear();
        return this.rolloverCalculator.calculateVacationDays(rolloverDays, accruedDays);
    }

    private double calculateAccruedDaysFromJanuaryFirstOfThisYear(){
        DateTime current = DateTime.now();
        DateTime firstOfYear = new DateTime(current.getYear(), 1, 1,0,0);
        return calculator.calculateVacationDays(firstOfYear, current);
    }
}
