package com.springapp.mvc.web.integration;

import com.springapp.mvc.web.model.Calculator;
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
    Calculator calculator;
    CalculatorService calculatorService;


    @Before
    public void setUp() throws Exception {
        calculator = mock(Calculator.class);
        calculatorService = new CalculatorService(calculator);

    }

    @Test
    public void shouldCalculateVacationDaysGivenAccrualRateAndRolloverDays() throws Exception {
        double rolloverDays = 0;
        int rate = 10;

        String date = "08/08/2011";

        when(calculator.calculateVacationDaysGivenRate(Matchers.any(DateTime.class), Matchers.any(DateTime.class), Matchers.any(Double.class), Matchers.any(Double.class)))
                .thenReturn(15.0);
        CalculatorService calculatorService1 = new CalculatorService(calculator);
        double vacationDayResult = calculatorService1.calculateVacationDaysGivenRate(rolloverDays, date, rate);
        assertThat(vacationDayResult , is(15.0));


    }
}
