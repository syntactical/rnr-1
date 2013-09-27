package integration;

import model.RolloverCalculator;
import org.junit.Test;
import service.CalculatorService;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class CalculatorServiceTest {
    @Test
    public void calculateVacationDays_shouldCalculateWithRolloverCalculator() throws Exception {
        RolloverCalculator rolloverCalculator = mock(RolloverCalculator.class);
        when(rolloverCalculator.calculateVacationDays(anyDouble(), anyDouble())).thenReturn(2.1);
        double accruedDays = 1.1;
        double rolloverDays = 1;
        CalculatorService calculatorService = new CalculatorService(rolloverCalculator);

        double vacationDayResult = calculatorService.calculateVacationDays(rolloverDays, accruedDays);

        verify(rolloverCalculator, times(1)).calculateVacationDays(1, 1.1);
        assertTrue(vacationDayResult == 2.1);
    }
}
