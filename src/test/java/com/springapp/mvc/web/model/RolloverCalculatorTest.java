package com.springapp.mvc.web.model;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;

public class RolloverCalculatorTest {
    private DateTime currentDate;
    private RolloverCalculator rolloverCalculator;

    @Before
    public void setUp() throws Exception {
        currentDate = new DateTime(2013, 9, 11,0,0);
        rolloverCalculator = new RolloverCalculator();

    }

    @Test
    public void addRolloverDays_shouldAddRolloverDaysToTotalVacationDays() throws Exception {
        double rolloverDayNumber = 1.2;
        double accruedVacationDays = 1;

        double totalVacationDays = rolloverCalculator.calculateVacationDays(rolloverDayNumber, accruedVacationDays);

        assertThat(totalVacationDays, is(2.2));
    }

    @Test
    public void shouldCapVacationAt15Days() throws Exception {
        DateTime startDate = new DateTime(2012, 10, 11, 0, 0);
        double totalVacationDays = rolloverCalculator.calculateCap(startDate, currentDate);
        assertThat(totalVacationDays, is(15d));
    }

    @Test
    public void shouldCapVacationAt22AndOneHalfDays() throws Exception {
        DateTime startDate = new DateTime(2011, 9, 11, 0, 0);
        double totalVacationDays = rolloverCalculator.calculateCap(startDate, currentDate);
        assertThat(totalVacationDays, is(22.5d));
    }

    @Test
    public void shouldCapVacationAt30DaysTestOne() throws Exception {
        DateTime startDate = new DateTime(2009, 9, 11, 0, 0);
        double totalVacationDays = rolloverCalculator.calculateCap(startDate, currentDate);
        assertThat(totalVacationDays, is(30d));
    }

    @Test
    public void shouldCapVacationAt30DaysTestTwo() throws Exception {
        DateTime startDate = new DateTime(2005, 9, 11, 0, 0);
        double totalVacationDays = rolloverCalculator.calculateCap(startDate, currentDate);
        assertThat(totalVacationDays, is(30d));
    }

}
