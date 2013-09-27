package model;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;

public class RolloverCalculatorTest {
    @Test
    public void addRolloverDays_shouldAddRolloverDaysToTotalVacationDays() throws Exception {
        double rolloverDayNumber = 1.2;
        double accruedVacationDays = 1;

        RolloverCalculator rolloverCalculator = new RolloverCalculator();
        double totalVacationDays = rolloverCalculator.calculateVacationDays(rolloverDayNumber, accruedVacationDays);

        assertThat(totalVacationDays, is(2.2));
    }
}
