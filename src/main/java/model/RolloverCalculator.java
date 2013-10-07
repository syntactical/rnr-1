package model;

import org.joda.time.DateTime;

public class RolloverCalculator {

    public static final double PERCENT_OF_ACCRUAL = 1.5;//150%

    public double calculateVacationDays(double rolloverDays, double accruedVacationDays) {
        return rolloverDays + accruedVacationDays;
    }

    public double calculateCap(DateTime startDate, DateTime currentDate) {
        Calculator calculator = new Calculator();
        Double cap = calculator.getDailyAccrualRate(startDate, currentDate) * 365d * PERCENT_OF_ACCRUAL;
        if (cap > 30) return 30d;
        return cap;
    }
}
