package com.springapp.mvc.web.integration;

import com.springapp.mvc.web.model.Calculator;
import com.springapp.mvc.web.model.RolloverCalculator;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import com.springapp.mvc.web.service.CalculatorService;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CalculatorServiceTest {
    RolloverCalculator rolloverCalculator;
    Calculator calculator;
    CalculatorService calculatorService;


    @Before
    public void setUp() throws Exception {
        rolloverCalculator = mock(RolloverCalculator.class);
        calculator = mock(Calculator.class);
        calculatorService = new CalculatorService(rolloverCalculator, calculator);

    }

    @Test
    public void calculateVacationDays_shouldCalculateWithRolloverCalculator() throws Exception {
        when(calculator.calculateVacationDays(Matchers.any(DateTime.class), Matchers.any(DateTime.class))).thenReturn(1.1);
        when(calculator.calculateAccruedDaysFromJanuaryFirstOfThisYear(Matchers.any(DateTime.class), Matchers.any(DateTime.class))).thenReturn(1.1);
        when(calculator.convertStringToDateTime(anyString())).thenReturn(new DateTime(2011, 8, 8, 0, 0));
        when(rolloverCalculator.calculateVacationDays(anyDouble(), anyDouble())).thenReturn(2.1);
        when(rolloverCalculator.calculateCap(Matchers.any(DateTime.class), Matchers.any(DateTime.class))).thenReturn(5d);
        double rolloverDays = 1;
        String date = "08/08/2011";
        double vacationDayResult = calculatorService.calculateVacationDays(rolloverDays, date);

        verify(rolloverCalculator).calculateVacationDays(1, 1.1);
        assertTrue(vacationDayResult == 2.1);
    }

    @Test
    public void shouldCalculateVacationDaysGivenAccrualRateAndRolloverDays() throws Exception {
        double rolloverDays = 0;
        int rate = 10;

        String date = "08/08/2011";

        when(calculator.calculateVacationDaysGivenRate(Matchers.any(DateTime.class), Matchers.any(DateTime.class), Matchers.any(Double.class), Matchers.any(Double.class)))
                .thenReturn(15.0);
        CalculatorService calculatorService1 = new CalculatorService(rolloverCalculator, calculator);
        double vacationDayResult = calculatorService1.calculateVacationDaysGivenRate(rolloverDays, date, rate);
        assertThat(vacationDayResult , is(15.0));


    }
}
