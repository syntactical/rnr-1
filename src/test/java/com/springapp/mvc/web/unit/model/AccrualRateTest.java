package com.springapp.mvc.web.unit.model;

import com.springapp.mvc.web.model.AccrualRate;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AccrualRateTest {
    DateTime startDate;

    @Before
    public void setUp() throws Exception {
        startDate = new DateTime();
    }

    @Test
    public void shouldGetAccrualRateDaysOf10IfRateNullAndElapsedTimeIsLTOneYear() throws Exception {
        double expectedRate = 10. / 365;
        DateTime lessThanOneYearAgo = startDate.plusDays(1);
        assertThat(new AccrualRate().givenIStarted(lessThanOneYearAgo).calculate() , is(expectedRate));
    }

    @Test
    public void shouldGetAccrualRateDaysOf15IfRateNullAndElapsedTimeGTOneYear() {
        double expectedRate = 15. / 365;
        DateTime twoYearsAgo = startDate.minusYears(1);
        assertThat(new AccrualRate().givenIStarted(twoYearsAgo).calculate() , is(expectedRate));
    }

    @Test
    public void shouldGetAccrualRateDaysOf20IfRateNullAndElapsedTimeGT3Years() throws Exception {
        double expectedRate = 20. / 365;
        DateTime fourYearsAgo = startDate.minusYears(3);
        assertThat(new AccrualRate().givenIStarted(fourYearsAgo).calculate(), is(expectedRate));
    }

    @Test
    public void shouldGetAccrualRateDaysOf25IfRateNullAndElapsedTimeGT6Years() throws Exception {
        double expectedRate = 25. / 365;
        DateTime sevenYearsAgo = startDate.minusYears(6);
        assertThat(new AccrualRate().givenIStarted(sevenYearsAgo).calculate() , is(expectedRate));
    }


}
