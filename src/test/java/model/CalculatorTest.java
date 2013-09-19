package model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CalculatorTest {

    private Calculator calculator;
    private TDate startDate;
    private TDate currentDate;

    @Before
    public void setUp() throws Exception {
        currentDate = new TDate("9/17/2013");
        calculator = new Calculator(currentDate);
    }

    private Double roundedNumber(Double unrounded){
        return (double)(Math.round(unrounded*100))/100;
    }

    @Test
    public void shouldCalculateVacationDaysForOneDayDifference() throws Exception {
        startDate = new TDate("9/16/2013");
        assertThat(calculator.getVacationBasedOnDays(startDate), is(roundedNumber(1 * 10 / 365d)));
    }

    @Test
    public void shouldCalculateVacationDaysForOneWeekDifference() throws Exception {
        startDate = new TDate("9/10/2013");
        assertThat(calculator.getVacationBasedOnDays(startDate), is(roundedNumber(7 * 10 / 365d)));
    }

    @Test
    public void shouldCalculateVacationDaysForOneMonthDifference() throws Exception {
        startDate = new TDate("8/17/2013");
        assertThat(calculator.getVacationBasedOnDays(startDate), is(roundedNumber(31 * 10 / 365d)));
    }

    @Test
    public void shouldCalculateVacationDaysOverlappingMonths() throws Exception {
        startDate = new TDate("8/1/2013");
        assertThat(calculator.getVacationBasedOnDays(startDate), is(roundedNumber(47 * 10 / 365d)));
    }

    @Test
    public void shouldCalculateVacationDaysForOneYearDifference() throws Exception {
        startDate = new TDate("9/17/2012");
        assertThat(calculator.getVacationBasedOnDays(startDate), is(roundedNumber(365 * 10 / 365d)));
    }

    @Test
    public void shouldCalculateVacationDaysForFiveYearDifference() throws Exception {
        startDate = new TDate("9/17/2008");
        assertThat(calculator.getVacationBasedOnDays(startDate), is(roundedNumber(5 * 365 * 10 / 365d)));
    }

    @Test
    public void shouldCalculateVacationDaysOverlappingYearsWithDifferentMonths() throws Exception {
        startDate = new TDate("10/4/2008");
        assertThat(calculator.getVacationBasedOnDays(startDate), is(roundedNumber((5 * 365 - 17) * 10 / 365d)));
    }

    @Test
    public void shouldCountOneDayBetweenDates() throws Exception {
        startDate = new TDate("8/13/2013");
        TDate currentDate = new TDate("8/14/2013");
        assertThat(calculator.daysBetween(startDate,currentDate), is(1));
    }

    @Test
    public void shouldCountTwoDaysBetweenDates() throws Exception {
        startDate = new TDate("8/13/2013");
        TDate currentDate = new TDate("8/15/2013");
        assertThat(calculator.daysBetween(startDate,currentDate), is(2));
    }

    @Test
    public void shouldCountDaysBetweenDatesOfDifferentMonths() throws Exception {
        startDate = new TDate("8/29/2013");
        TDate currentDate = new TDate("9/13/2013");
        assertThat(calculator.daysBetween(startDate,currentDate), is(15));
    }

    @Test
    public void shouldCountDaysBetweenDatesOfDifferentYears() throws Exception {
        startDate = new TDate("8/31/2012");
        TDate currentDate = new TDate("9/15/2013");
        assertThat(calculator.daysBetween(startDate,currentDate), is(380));
    }
}
