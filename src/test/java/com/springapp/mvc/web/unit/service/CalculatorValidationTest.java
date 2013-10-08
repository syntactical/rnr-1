package com.springapp.mvc.web.unit.service;

import com.springapp.mvc.web.service.CalculatorValidation;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static junit.framework.Assert.assertFalse;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Thoughtworker
 * Date: 9/27/13
 * Time: 1:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class CalculatorValidationTest {

    private CalculatorValidation validator;

    @Before
    public void setUp() throws Exception {
        validator = new CalculatorValidation();


    }

    @Test
    public void shouldHaveNoErrorsForAValidDate() throws Exception {
        String dateAsString = "03/11/2011";
        assertThat(validator.verifyDate(dateAsString).size(), is(0));

    }

    @Test
    public void shouldThrowErrorForInvalidMonth() throws Exception {
        String dateAsString = "13/11/2011";
        assertThereIsOneErrorFor("month", "Please enter a valid month.", validator.verifyDate(dateAsString));
    }

    @Test
    public void shouldThrowErrorForInvalidDayOfMonth() throws Exception {
        String dateAsString = "11/33/2000";
        assertThereIsOneErrorFor("day", "Please enter a valid day.", validator.verifyDate(dateAsString));

    }

    @Test
    public void shouldThrowErrorForInvalidYear() throws Exception {
        String dateAsString = "09/15/33";
        assertThereIsOneErrorFor("year", "Please enter a valid year.", validator.verifyDate(dateAsString));

    }

    @Test
    public void shouldReturnTrueIfStartDateIsInCurrentYear() throws Exception {
        String dateAsString = "07/25/2013";
        int year = 2013;
        assertTrue(validator.isSameYear(dateAsString, year));

    }

    @Test
    public void shouldReturnFalseIfStartDateIsNotInCurrentYear() throws Exception {
        String dateAsString = "07/25/2012";
        int year = 2013;
        assertFalse(validator.isSameYear(dateAsString, year));

    }

    private void assertThereIsOneErrorFor(String field, String expected, HashMap<String, String> errors) {
        assertThat(errors.size(), Is.is(1));
        assertThat(errors.get(field), Matchers.containsString(expected));
    }
}
