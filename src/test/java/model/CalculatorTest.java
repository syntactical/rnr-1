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

    private Calculator calculator;
    private TDate startDate;

    @Before
    public void setUp() throws Exception {
        calculator = new Calculator();

    }


    @Test
    public void shouldCountOneDayBetweenDates() throws Exception {
        startDate = new TDate("8/13/2013");
        TDate currentDate = new TDate("8/14/2013");
        assertThat(calculator.daysBetween(startDate,currentDate), is(1));
    }

    @Test
    public void shouldReturnFiveOneHundredEightieths() throws Exception {
        startDate = new TDate("9/15/2013");
        assertThat(calculator.getVacationBasedOnDays(startDate), is(roundedNumber(5/180d)));

    }

    @Test
    public void shouldCountTwoDaysBetweenDates() throws Exception {
        startDate = new TDate("8/13/2013");
        TDate currentDate = new TDate("8/15/2013");
        assertThat(calculator.daysBetween(startDate,currentDate), is(2));

    }

    @Test
    public void shouldReturnTenOneHundredEightieths() throws Exception {
        startDate = new TDate("9/14/2013");
        assertThat(calculator.getVacationBasedOnDays(startDate), is(roundedNumber(10/180d)));

    }

    @Test
    public void shouldCountDaysBetweenDatesOfDifferentMonths() throws Exception {
        startDate = new TDate("8/31/2013");
        TDate currentDate = new TDate("9/15/2013");
        assertThat(calculator.daysBetween(startDate,currentDate), is(15));

    }

    @Test
    public void shouldReturnFortyFourths() throws Exception {
        startDate = new TDate("8/31/2013");
        assertThat(calculator.getVacationBasedOnDays(startDate), is(roundedNumber(80/180d)));

    }



    @Test
    public void shouldCountDaysBetweenDatesOfDifferentYears() throws Exception {
        startDate = new TDate("8/31/2012");
        TDate currentDate = new TDate("9/15/2013");
        assertThat(calculator.daysBetween(startDate,currentDate), is(380));

    }

    @Test
    public void shouldReturnTenAndFiftyEightHundreths() throws Exception {
        startDate = new TDate("8/31/2012");
        assertThat(calculator.getVacationBasedOnDays(startDate), is(roundedNumber(1905/180d)));

    }



    private Double roundedNumber(Double unrounded){
        return (double)(Math.round(unrounded*100))/100;
    }
}
