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
    private TWDate startDate;

    @Before
    public void setUp() throws Exception {
        calc = new Calculator();
        startDate = new TWDate();

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
    public void shouldReturnFiveOneHundredAndEightieths() throws Exception {
        startDate.setDate("8/13/2013");
        assertThat(calc.getVacationBasedOnDays(startDate), is(roundedNumber(5/180d)));

    }

    private Double roundedNumber(Double unrounded){
        return (double)(Math.round(unrounded*100))/100;
    }
}
