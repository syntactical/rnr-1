package com.springapp.mvc.web.unit.model;

import com.springapp.mvc.web.model.Calculator;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

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
        double rate = 10;
        double rolloverDays = 0;
        assertThat(calculator.calculateVacationDaysGivenRate(startDate, rolloverDays, rate), is(rate));
    }

    @Test
    public void shouldCalculateVacationDaysForOneYearGivenAnAccrualRateAndRolloverDays() throws Exception {
        double rate = 10;
        double rolloverDays = 5;
        assertThat(calculator.calculateVacationDaysGivenRate(startDate, rolloverDays, rate), is(15.0));
    }

    @Test
    public void shouldNotAllowMoreVacationDaysThanTheCapAllows() throws Exception {
        double rate = 20;
        double rolloverDays = 25;
        assertThat(calculator.calculateVacationDaysGivenRate(startDate, rolloverDays, rate), is((double) 30));
    }

    @Test
    public void shouldNotLetYouHaveMoreVacationDaysThan150PercentOfRate() throws Exception {
        double rate = 10;
        double rolloverDays = 25;
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
        double rate = 10;
        int expectedVacationDays = 10;
        assertThat(calculator.calculateVacationDaysGivenRate(startDate, rolloverDays, rate), is((double) expectedVacationDays));
    }

    @Test
    public void shouldGetAccrualRateOf10IfRateNullAndElapsedTimeIsLTOneYear() throws Exception {
        double expectedRate = 10;
        DateTime lessThanOneYearAgo = startDate.plusDays(1);
        assertThat(calculator.getAccrualRate(lessThanOneYearAgo), is(expectedRate));
    }

    @Test
    public void shouldGetAccrualRateOf15IfRateNullAndElapsedTimeGTOneYear() {
        double expectedRate = 15;
        DateTime twoYearsAgo = startDate.minusYears(1);
        assertThat(calculator.getAccrualRate(twoYearsAgo), is(expectedRate));
    }

    @Test
    public void shouldGetAccrualRateOf15IfRateNullAndElapsedTimeGT3Years() throws Exception {
        double expectedRate = 20;
        DateTime fourYearsAgo = startDate.minusYears(3);
        assertThat(calculator.getAccrualRate(fourYearsAgo), is(expectedRate));
    }


}