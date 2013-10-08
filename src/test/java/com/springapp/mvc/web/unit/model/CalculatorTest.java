package com.springapp.mvc.web.unit.model;

import com.springapp.mvc.web.model.Calculator;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static com.springapp.mvc.web.model.AccrualRate.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CalculatorTest {

    private Calculator calculator;
    private DateTime startDate;
    private DateTime exampleDate2013;

    @Before
    public void setUp() throws Exception {
        exampleDate2013 = new DateTime(2013, 9, 17, 0, 0);
        calculator = new Calculator();
    }

    private Double roundedNumber(Double unrounded){
        return (double)(Math.round(unrounded*100))/100;
    }

    @Test
    public void shouldCalculateVacationDaysForOneDayDifference() throws Exception {
        startDate = new DateTime(2013, 9, 16, 0, 0);
        assertThat(calculator.calculateVacationDays(startDate, exampleDate2013), is(roundedNumber(1 * 10 / 365d)));
    }

    @Test
    public void shouldCalculateVacationDaysForOneWeekDifference() throws Exception {
        startDate = new DateTime(2013, 9, 10, 0, 0);
        assertThat(calculator.calculateVacationDays(startDate, exampleDate2013), is(roundedNumber(7 * 10 / 365d)));
    }

    @Test
    public void shouldCalculateVacationDaysForOneMonthDifference() throws Exception {
        startDate = new DateTime(2013, 8, 17, 0 , 0);
        assertThat(calculator.calculateVacationDays(startDate, exampleDate2013), is(roundedNumber(31 * 10 / 365d)));
    }

    @Test
    public void shouldCalculateVacationDaysOverlappingMonths() throws Exception {
        startDate = new DateTime(2013, 8, 1, 0, 0);
        assertThat(calculator.calculateVacationDays(startDate, exampleDate2013), is(roundedNumber(47 * 10 / 365d)));
    }

    @Test
    public void shouldCalculateVacationDaysForOneYearDifference() throws Exception {
        startDate = new DateTime(2012, 9, 17, 0, 0);
        assertThat(calculator.calculateVacationDays(startDate, exampleDate2013), is(roundedNumber(365 * 10 / 365d)));
    }



    @Test
    public void shouldConvertStringToDateTime() throws Exception {
        DateTime expectedDate = new DateTime(2011, 11, 8, 0, 0);
        String date = "11/8/2011";
        assertThat(calculator.convertStringToDateTime(date), is(expectedDate));

    }

    @Test
    public void shouldReturnAccrualOfTenDays() throws Exception {
        Double rate = getRateForStartYear(2012);
        assertThat(rate, is(FIRST_YEAR.rate()));
    }

    @Test
    public void shouldReturnAccrualOfFifteenDays() throws Exception {
        Double rate = getRateForStartYear(2011);
        assertThat(rate, is(LESS_THAN_THREE_YEARS.rate()));
    }

    @Test
    public void shouldReturnAccrualOfTwentyDays() throws Exception {
        Double rate = getRateForStartYear(2009);
        assertThat(rate, is(LESS_THAN_SIX_YEARS.rate()));
    }

    @Test
    public void shouldReturnAccrualOfTwentyFiveDays() throws Exception {
        Double rate = getRateForStartYear(2006);
        assertThat(rate, is(SIX_YEARS_OR_MORE.rate()));
    }

    private Double getRateForStartYear(int year) {
        DateTime startDate = new DateTime(year, 11, 8, 0, 0);
        return calculator.getDailyAccrualRate(startDate, exampleDate2013);
    }


}