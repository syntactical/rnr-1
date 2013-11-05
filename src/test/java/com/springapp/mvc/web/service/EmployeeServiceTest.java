package com.springapp.mvc.web.service;

import com.springapp.mvc.web.model.AccrualRate;
import com.springapp.mvc.web.model.Employee;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class EmployeeServiceTest {

    public static final String TODAY = "10/20/2013";
    public static final String ROLLOVER_DAYS = "6";
    public static final String NO_ROLLOVER_DAYS = "";
    public static final String CUSTOM_ACCRUAL_RATE = "17";
    public static final HashMap<LocalDate, Double> NO_DAYS_OFF = new HashMap<LocalDate, Double>();
    public static final AccrualRate DEFAULT_ACCRUAL_RATE = new AccrualRate();

    @Test
    public void shouldCreateNewEmployeeWithGivenFields() throws Exception {
        Employee actualEmployee = new EmployeeService().createEmployee(TODAY, ROLLOVER_DAYS, NO_DAYS_OFF, "");

        double rolloverDaysDouble = Double.parseDouble(ROLLOVER_DAYS);

        DateTimeFormatter formatter = DateTimeFormat.forPattern("mm/dd/yyyy");
        LocalDate date = formatter.parseLocalDate(TODAY);

        Employee expectedEmployee = new Employee(date, rolloverDaysDouble, NO_DAYS_OFF, DEFAULT_ACCRUAL_RATE);
        assertThat(actualEmployee, is(expectedEmployee));
    }

    @Test
    public void shouldCreateNewEmployeeWithNoRolloverDaysIfNoRolloverDaysGiven() {
        Employee actualEmployee = new EmployeeService().createEmployee(TODAY, NO_ROLLOVER_DAYS, NO_DAYS_OFF, "");

        DateTimeFormatter formatter = DateTimeFormat.forPattern("mm/dd/yyyy");
        LocalDate date = formatter.parseLocalDate(TODAY);

        Employee expectedEmployee = new Employee(date, 0d, NO_DAYS_OFF, DEFAULT_ACCRUAL_RATE);
        assertThat(actualEmployee, is(expectedEmployee));
    }

    @Test
    public void shouldCreateEmployeeWithCustomAccrualRateIfGiven() {
        Employee actualEmployee = new EmployeeService().createEmployee(TODAY, NO_ROLLOVER_DAYS, NO_DAYS_OFF, CUSTOM_ACCRUAL_RATE);

        DateTimeFormatter formatter = DateTimeFormat.forPattern("mm/dd/yyyy");
        LocalDate date = formatter.parseLocalDate(TODAY);

        Employee expectedEmployee = new Employee(date, 0d, NO_DAYS_OFF, DEFAULT_ACCRUAL_RATE, 17d);
        assertThat(actualEmployee, is(expectedEmployee));

    }
}
