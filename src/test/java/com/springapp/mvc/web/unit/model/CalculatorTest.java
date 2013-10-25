package com.springapp.mvc.web.unit.model;

import com.springapp.mvc.web.model.Calculator;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CalculatorTest {

    private Calculator calculator;
    private DateTime startDate;
    String emptyAccrualRate;

    @Before
    public void setUp() throws Exception {
        startDate = new DateTime().minusYears(1);
        calculator = new Calculator();
        emptyAccrualRate = "";

    }

    @Test
    public void shouldCalculateVacationDaysForOneYearGivenAnAccrualRate() throws Exception {
        double rateInDays = 10;
        double rate = rateInDays/365;
        double rolloverDays = 0;
        assertThat(calculator.calculateVacationDaysGivenRate(startDate, rolloverDays, rate), is(rateInDays));
    }

    @Test
    public void shouldCalculateVacationDaysForOneYearGivenAnAccrualRateAndRolloverDays() throws Exception {
        double rateInDays = 10;
        double rolloverDays = 5;
        double rate = rateInDays/365;
        assertThat(calculator.calculateVacationDaysGivenRate(startDate, rolloverDays, rate), is(15.0));
    }

    @Test
    public void shouldNotAllowMoreVacationDaysThanTheCapAllows() throws Exception {
        double rateInDays = 20;
        double rate = rateInDays/365;
        double rolloverDays = 25;
        assertThat(calculator.calculateVacationDaysGivenRate(startDate, rolloverDays, rate), is((double) 30));
    }

    @Test
    public void shouldNotLetYouHaveMoreVacationDaysThan150PercentOfRate() throws Exception {
        double rateInDays = 10;
        double rolloverDays = 25;
        double rate = rateInDays/365;
        assertThat(calculator.calculateVacationDaysGivenRate(startDate, rolloverDays, rate), is((double) 15));
    }

    @Test
    public void shouldConvertStringToDateTime() throws Exception {
        DateTime expectedDate = new DateTime(2011, 11, 8, 0, 0);
        String date = "11/8/2011";
        assertThat(calculator.convertStringToDateTime(date), is(expectedDate));
    }

    @Test
    public void shouldCalculateVacationDaysBasedOnStartDate() throws Exception {
        double rolloverDays = 0;
        double rateInDays = 10;
        int expectedVacationDays = 10;
        double rate = rateInDays/365;
        assertThat(calculator.calculateVacationDaysGivenRate(startDate, rolloverDays, rate), is((double) expectedVacationDays));
    }

}