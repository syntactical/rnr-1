package com.springapp.mvc.web.unit.model;

import com.springapp.mvc.web.model.Employee;
import com.springapp.mvc.web.model.PersonalDaysCalculator;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PersonalDaysCalculatorTest {

    PersonalDaysCalculator personalDaysCalculator;
    Employee mockEmployee;
    LocalDate endOf2013;

    @Before
    public void setUp() {
        personalDaysCalculator = new PersonalDaysCalculator();

        endOf2013 = new LocalDate(2013, 12, 31);

        mockEmployee = mock(Employee.class);
        when(mockEmployee.getPersonalDaysTaken()).thenReturn(0.0);
    }

    @Test
    public void shouldReturnSevenDaysIfStartDateBeforeMayFirst() {
        LocalDate beginningOf2013 = new LocalDate(2013, 1, 1);
        assertExpectedPersonalDays(beginningOf2013, endOf2013, 7.0);
    }

    @Test
    public void shouldReturnFourDaysForStartDateBetweenMayAndAugust() {
        LocalDate middleOf2013 = new LocalDate(2013, 6, 1);
        assertExpectedPersonalDays(middleOf2013, endOf2013, 4.0);
    }

    @Test
    public void shouldReturnThreeDaysForStartDateAfterSeptemberFirst() {
        assertExpectedPersonalDays(endOf2013, endOf2013, 3.0);
    }

    @Test
    public void shouldReturnPersonalDaysAllottedMinusPersonalDaysTaken() {
        double personalDaysTaken = 6.0;
        double personalDaysLeft = 1.0;

        when(mockEmployee.getPersonalDaysTaken()).thenReturn(personalDaysTaken);
        LocalDate beginningOf2013 = new LocalDate(2013, 1, 1);

        assertExpectedPersonalDays(beginningOf2013, endOf2013, personalDaysLeft);
    }

    @Test
    public void shouldReturnSevenDaysIfEndDateInDifferentYearThanStartDate() {
        LocalDate endOf2012 = new LocalDate(2012, 12, 31);
        assertExpectedPersonalDays(endOf2012, endOf2013, 7.0);
    }

    private void assertExpectedPersonalDays(LocalDate startDate, LocalDate endDate, double expectedPersonalDays) {
        assertThat(personalDaysCalculator.calculatePersonalDays(mockEmployee, startDate, endDate), is(expectedPersonalDays));
    }

}
