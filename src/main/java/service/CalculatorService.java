package service;

import model.RolloverCalculator;

public class CalculatorService {
    private final RolloverCalculator rolloverCalculator;

    public CalculatorService() {
        this.rolloverCalculator = new RolloverCalculator();
    }

    public CalculatorService(RolloverCalculator rolloverCalculator) {
        this.rolloverCalculator = rolloverCalculator;
    }

    public double calculateVacationDays(double rolloverDays, double accruedDays) {
        return this.rolloverCalculator.calculateVacationDays(rolloverDays, accruedDays);
    }
}
