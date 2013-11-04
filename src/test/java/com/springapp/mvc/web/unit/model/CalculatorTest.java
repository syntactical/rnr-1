package com.springapp.mvc.web.unit.model;

import com.springapp.mvc.web.model.AccrualRate;
import com.springapp.mvc.web.model.Calculator;
import com.springapp.mvc.web.model.Employee;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculatorTest {

    public static final LocalDate ONE_WEEK_AGO = new LocalDate().minusWeeks(1);
    public static final HashMap<LocalDate, Double> NO_TIME_OFF = new HashMap<LocalDate, Double>();
    public static final LocalDate TODAY = new LocalDate();
    public static final double DEFAULT_ACCRUAL_RATE = 10d;
    public static final double YEAR_IN_DAYS = 365.25;

    private Calculator calculator;
    private LocalDate oneYearAgo;
    private Employee employee;
    private AccrualRate mockAccrualRate;

    String emptyAccrualRate;

    @Before
    public void setUp() throws Exception {
        oneYearAgo = new LocalDate().minusYears(1);
        calculator = new Calculator();
        emptyAccrualRate = "";
        mockAccrualRate = mock(AccrualRate.class);
        employee = new Employee(ONE_WEEK_AGO, 0, NO_TIME_OFF, mockAccrualRate);
    }

    @Test
    public void shouldCalculateVacationDaysAfterIntervalOfTime() {
        double expectedVacationDays = DEFAULT_ACCRUAL_RATE / YEAR_IN_DAYS * 7;
        when(mockAccrualRate.calculateDailyAccrualRate(any(LocalDate.class), any(LocalDate.class), anyDouble())).thenReturn(10d);
        assertThat(calculator.getVacationBasedOnIntervals(employee, TODAY), is(expectedVacationDays));
    }


//
//    @Test
//    public void shouldCalculateVacationDaysForOneYearGivenAnAccrualRate() throws Exception {
//        double rateInDays = 10;
//        double rate = rateInDays / 365;
//        double rolloverDays = 0;
//        assertThat(calculator.calculateVacationDaysGivenRate(oneYearAgo, rolloverDays, rate), is(rateInDays));
//    }
//
//    @Test
//    public void shouldCalculateVacationDaysForOneYearGivenAnAccrualRateAndRolloverDays() throws Exception {
//        double rateInDays = 10;
//        double rolloverDays = 5;
//        double rate = rateInDays / 365;
//        assertThat(calculator.calculateVacationDaysGivenRate(oneYearAgo, rolloverDays, rate), is(15.0));
//    }
//
//    @Test
//    public void shouldNotAllowMoreVacationDaysThanTheCapAllows() throws Exception {
//        double rateInDays = 20;
//        double rate = rateInDays / 365;
//        double rolloverDays = 25;
//        assertThat(calculator.calculateVacationDaysGivenRate(oneYearAgo, rolloverDays, rate), is((double) 30));
//    }
//
//    @Test
//    public void shouldNotLetYouHaveMoreVacationDaysThan150PercentOfRate() throws Exception {
//        double rateInDays = 10;
//        double rolloverDays = 25;
//        double rate = rateInDays / 365;
//        assertThat(calculator.calculateVacationDaysGivenRate(oneYearAgo, rolloverDays, rate), is((double) 15));
//    }

    @Test
    public void shouldConvertStringToDateTime() throws Exception {
        DateTime expectedDate = new DateTime(2011, 11, 8, 0, 0);
        String date = "11/8/2011";
        assertThat(calculator.convertStringToDateTime(date), is(expectedDate));
    }

//    @Test
//    public void shouldCalculateVacationDaysBasedOnStartDate() throws Exception {
//        double rolloverDays = 0;
//        double rateInDays = 10;
//        int expectedVacationDays = 10;
//        double rate = rateInDays / 365;
//        assertThat(calculator.calculateVacationDaysGivenRate(oneYearAgo, rolloverDays, rate), is((double) expectedVacationDays));
//    }

//    @Test
//    public void shouldIncrementVacationDays(){
//        double rolloverDays = 0;
//        DateTime startDate = new DateTime().minusYears(2);
//        HashMap<LocalDate, Double> daysOff = new HashMap<LocalDate, Double>();
//        DateTime accrualPeriodStartDate = new DateTime().minusWeeks(2);
//
//        expectedVacationDays = new AccrualRate().givenIStarted()
//
//        assertThat()
//
//    }
}