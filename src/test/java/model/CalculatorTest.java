package model;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CalculatorTest {

    private Calculator calculator;
    private DateTime startDate;
    private DateTime currentDate;

    @Before
    public void setUp() throws Exception {
        currentDate = new DateTime(2013, 9, 17, 0, 0);
        calculator = new Calculator();
    }

    private Double roundedNumber(Double unrounded){
        return (double)(Math.round(unrounded*100))/100;
    }

    @Test
    public void shouldCalculateVacationDaysForOneDayDifference() throws Exception {
        startDate = new DateTime(2013, 9, 16, 0, 0);
        assertThat(calculator.calculateVacationDays(startDate, currentDate), is(roundedNumber(1 * 10 / 365d)));
    }

    @Test
    public void shouldCalculateVacationDaysForOneWeekDifference() throws Exception {
        startDate = new DateTime(2013, 9, 10, 0, 0);
        assertThat(calculator.calculateVacationDays(startDate, currentDate), is(roundedNumber(7 * 10 / 365d)));
    }

    @Test
    public void shouldCalculateVacationDaysForOneMonthDifference() throws Exception {
        startDate = new DateTime(2013, 8, 17, 0 , 0);
        assertThat(calculator.calculateVacationDays(startDate, currentDate), is(roundedNumber(31 * 10 / 365d)));
    }

    @Test
    public void shouldCalculateVacationDaysOverlappingMonths() throws Exception {
        startDate = new DateTime(2013, 8, 1, 0, 0);
        assertThat(calculator.calculateVacationDays(startDate, currentDate), is(roundedNumber(47 * 10 / 365d)));
    }

    @Test
    public void shouldCalculateVacationDaysForOneYearDifference() throws Exception {
        startDate = new DateTime(2012, 9, 17, 0, 0);
        assertThat(calculator.calculateVacationDays(startDate, currentDate), is(roundedNumber(365 * 10 / 365d)));
    }

    @Test
    public void shouldCalculateVacationDaysForFiveYearDifference() throws Exception {
        startDate = new DateTime(2008, 9, 17, 0, 0);
        assertThat(calculator.calculateVacationDays(startDate, currentDate),
                is(roundedNumber((5 * 365 + 1) * 10 / 365d)));
    }

    @Test
    public void shouldCalculateVacationDaysOverlappingYearsWithDifferentMonths() throws Exception {
        startDate = new DateTime(2008, 10, 4, 0, 0);
        assertThat(calculator.calculateVacationDays(startDate, currentDate),
                is(roundedNumber((5 * 365 - 16) * 10 / 365d)));
    }
}