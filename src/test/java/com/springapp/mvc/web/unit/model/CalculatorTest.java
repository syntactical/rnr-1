package com.springapp.mvc.web.unit.model;

import com.springapp.mvc.web.model.Calculator;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CalculatorTest {

    private Calculator calculator;
    private DateTime exampleDate;

    @Before
    public void setUp() throws Exception {
        exampleDate = new DateTime(2014, 07, 1, 0, 0);
        calculator = new Calculator();
    }

    @Test
    public void shouldCalculateVacationDaysForOneYearGivenAnAccrualRate() throws Exception {
        double rate = 10;
        double rolloverDays = 0;
        assertThat(calculator.calculateVacationDaysGivenRate(exampleDate, rolloverDays, rate), is(rate));
    }

    @Test
    public void shouldCalculateVacationDaysForOneYearGivenAnAccrualRateAndRolloverDays() throws Exception {
        double rate = 10;
        double rolloverDays = 5;
        assertThat(calculator.calculateVacationDaysGivenRate(exampleDate, rolloverDays, rate), is(15.0));
    }

    @Test
    public void shouldNotLetYouHaveMoreVacationDaysThanTheCapAllows() throws Exception {
        double rate = 20;
        double rolloverDays = 25;
        assertThat(calculator.calculateVacationDaysGivenRate(exampleDate, rolloverDays, rate), is((double) 30));
    }

    @Test
    public void shouldNotLetYouHaveMoreVacationDaysThan150PercentOfRate() throws Exception {
        double rate = 10;
        double rolloverDays = 25;
        assertThat(calculator.calculateVacationDaysGivenRate(exampleDate, rolloverDays, rate), is((double) 15));
    }

    @Test
    public void shouldConvertStringToDateTime() throws Exception {
        DateTime expectedDate = new DateTime(2011, 11, 8, 0, 0);
        String date = "11/8/2011";
        assertThat(calculator.convertStringToDateTime(date), is(expectedDate));

    }

}