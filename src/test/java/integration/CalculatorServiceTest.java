package integration;

import model.Calculator;
import model.RolloverCalculator;
import org.joda.time.DateTime;
import org.junit.Test;
import org.mockito.Matchers;
import service.CalculatorService;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class CalculatorServiceTest {
    @Test
    public void calculateVacationDays_shouldCalculateWithRolloverCalculator() throws Exception {
        RolloverCalculator rolloverCalculator = mock(RolloverCalculator.class);
        Calculator calculator = mock(Calculator.class);
        when(calculator.calculateVacationDays(Matchers.any(DateTime.class), Matchers.any(DateTime.class))).thenReturn(1.1);
        when(calculator.calculateAccruedDaysFromJanuaryFirstOfThisYear(Matchers.any(DateTime.class), Matchers.any(DateTime.class))).thenReturn(1.1);
        when(calculator.convertStringToDateTime(anyString())).thenReturn(new DateTime(2011, 8, 8, 0, 0));
        when(rolloverCalculator.calculateVacationDays(anyDouble(), anyDouble())).thenReturn(2.1);
        when(rolloverCalculator.calculateCap(Matchers.any(DateTime.class), Matchers.any(DateTime.class))).thenReturn(5d);
        double rolloverDays = 1;
        CalculatorService calculatorService = new CalculatorService(rolloverCalculator, calculator);
        String date = "08/08/2011";
        double vacationDayResult = calculatorService.calculateVacationDays(rolloverDays, date);

        verify(rolloverCalculator, times(1)).calculateVacationDays(1, 1.1);
        assertTrue(vacationDayResult == 2.1);
    }
}
