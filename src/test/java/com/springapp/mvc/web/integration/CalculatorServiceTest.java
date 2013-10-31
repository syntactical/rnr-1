package com.springapp.mvc.web.integration;

import com.springapp.mvc.web.model.AccrualRate;
import com.springapp.mvc.web.model.Calculator;
import com.springapp.mvc.web.service.CalculatorService;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.*;

public class CalculatorServiceTest {
    private Calculator calculator;
    private CalculatorService calculatorService;
    private AccrualRate accrualRate;


    @Before
    public void setUp() throws Exception {
        calculator = mock(Calculator.class);
        accrualRate = mock(AccrualRate.class);
        calculatorService = new CalculatorService(calculator, accrualRate);
    }

    @Test
    public void shouldCalculateVacationDaysGivenStartDateAccrualRateRolloverDays() throws Exception {
        String rolloverDays = "0";
        String rate = "10";
        String startDate = "10/23/2013";

        when(calculator.convertStringToDateTime(anyString())).
                thenReturn(new DateTime(new Calculator().convertStringToDateTime(startDate)));
        when(calculator.calculateVacationDaysGivenRate(any(DateTime.class), any(Double.class), any(Double.class)))
                .thenReturn(05.0);
        double vacationDayResult = calculatorService.calculateVacationDays(startDate, rolloverDays, rate , "");

        assertThat(vacationDayResult , is(05.0));
    }

    @Test
    public void shouldNotChangeAccrualRateIfOneGiven() throws Exception {
        String givenRate  = "12.5";
        String rolloverDays = "0";
        String startDate = "10/23/2013";

        when(calculator.convertStringToDateTime(anyString())).
                thenReturn(new DateTime(new Calculator().convertStringToDateTime(startDate)));


         calculatorService.calculateVacationDays(startDate,rolloverDays,givenRate,"");

        verify(calculator).calculateVacationDaysGivenRate(any(DateTime.class),anyDouble(),anyDouble());
    }

    @Test
    public void shouldCalculateAccrualRateIfEmptyString() throws Exception {
        String givenRate  = "";
        String rolloverDays = "0";
        String startDateString = "10/23/2013";
        DateTime startDate = calculator.convertStringToDateTime(startDateString);

        when(calculator.convertStringToDateTime(anyString())).
                thenReturn(new DateTime(startDate));
        when(accrualRate.calculate()).thenReturn(10.0/365);

        calculatorService.calculateVacationDays(startDateString,rolloverDays,givenRate,"");

        verify(calculator).calculateVacationDaysGivenRate( any(DateTime.class), anyDouble(), anyDouble());
    }


}
