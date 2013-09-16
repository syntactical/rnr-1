package model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Thoughtworker
 * Date: 9/12/13
 * Time: 8:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class CalculatorTest {

    private Calculator calc;
    private TDate startDate;

    @Before
    public void setUp() throws Exception {
        calc = new Calculator();
        startDate = new TDate();

    }

    @Test
    public void shouldReturnFiveSixths() throws Exception {

        startDate.setDate("8/1/2013");
        assertThat(calc.getVacationDaysBasedOnMonth(startDate), is(roundedNumber(5/6d)));

    }

    @Test
    public void shouldReturnTenSixths() throws Exception {

        startDate.setDate("7/1/2013");
        assertThat(calc.getVacationDaysBasedOnMonth(startDate), is(roundedNumber(10/6d)));

    }

    @Test
    public void shouldReturnFive() throws Exception {
        startDate.setDate("3/15/2013");
        assertThat(calc.getVacationDaysBasedOnMonth(startDate), is(roundedNumber(5d)));

    }

    @Test
    public void shouldReturnTen() throws Exception {
        startDate.setDate("8/1/2012");
        assertThat(calc.getVacationDaysBasedOnMonth(startDate), is(roundedNumber(10d)));

    }

    @Test
    public void shouldReturnNineAndSeventeenTenths() throws Exception {
        startDate.setDate("9/1/2012");
        assertThat(calc.getVacationDaysBasedOnMonth(startDate), is(roundedNumber(10d-(5/6d))));

    }

    @Test
    public void shouldCountOneDayBetweenDates() throws Exception {
        startDate.setDate("8/13/2013");
        TDate currentDate = new TDate().setDate("8/14/2013");
        assertThat(calc.daysBetween(startDate,currentDate), is(1));
    }

    @Test
    public void shouldReturnFiveOneHundredEightieths() throws Exception {
        startDate.setDate("9/15/2013");
        assertThat(calc.getVacationBasedOnDays(startDate), is(roundedNumber(5/180d)));

    }

    @Test
    public void shouldCountTwoDaysBetweenDates() throws Exception {
        startDate.setDate("8/13/2013");
        TDate currentDate = new TDate().setDate("8/15/2013");
        assertThat(calc.daysBetween(startDate,currentDate), is(2));

    }

    @Test
    public void shouldReturnTenOneHundredEightieths() throws Exception {
        startDate.setDate("9/14/2013");
        assertThat(calc.getVacationBasedOnDays(startDate), is(roundedNumber(10/180d)));

    }

    @Test
    public void shouldCountDaysBetweenDatesOfDifferentMonths() throws Exception {
        startDate.setDate("8/31/2013");
        TDate currentDate = new TDate().setDate("9/15/2013");
        assertThat(calc.daysBetween(startDate,currentDate), is(15));

    }

    @Test
    public void shouldReturnFortyFourths() throws Exception {
        startDate.setDate("8/31/2013");
        assertThat(calc.getVacationBasedOnDays(startDate), is(roundedNumber(80/180d)));

    }



    @Test
    public void shouldCountDaysBetweenDatesOfDifferentYears() throws Exception {
        startDate.setDate("8/31/2012");
        TDate currentDate = new TDate().setDate("9/15/2013");
        assertThat(calc.daysBetween(startDate,currentDate), is(380));

    }

    @Test
    public void shouldReturnTenAndFiftyEightHundreths() throws Exception {
        startDate.setDate("8/31/2012");
        assertThat(calc.getVacationBasedOnDays(startDate), is(roundedNumber(1905/180d)));

    }



    private Double roundedNumber(Double unrounded){
        return (double)(Math.round(unrounded*100))/100;
    }
}
