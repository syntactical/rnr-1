package com.springapp.mvc.web.service;

import com.springapp.mvc.web.model.AccrualRateCalculator;
import com.springapp.mvc.web.model.Employee;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {

    public static final String TODAY = "10/20/2013";
    public static final String ROLLOVER_DAYS = "6";
    public static final String NO_ROLLOVER_DAYS = "";
    public static final String CUSTOM_ACCRUAL_RATE = "17";
    public static final HashMap<LocalDate, Double> NO_DAYS_OFF = new HashMap<LocalDate, Double>();
    public static final AccrualRateCalculator DEFAULT_ACCRUAL_RATE = new AccrualRateCalculator();

    EmployeeService employeeService;
    DateParserService dateParser;


    @Before
    public void setUp() {
        dateParser = mock(DateParserService.class);
        employeeService = new EmployeeService(dateParser);

    }

    @Test
    public void shouldCreateNewEmployeeWithGivenFields() throws Exception {

        double rolloverDaysDouble = Double.parseDouble(ROLLOVER_DAYS);

        LocalDate date = new DateParserService().parse(TODAY);
        when(dateParser.parse(anyString())).thenReturn(date);

        Employee actualEmployee = employeeService.createEmployee(TODAY, ROLLOVER_DAYS, NO_DAYS_OFF, "");


        Employee expectedEmployee = new Employee(date, rolloverDaysDouble, NO_DAYS_OFF, DEFAULT_ACCRUAL_RATE);
        assertThat(actualEmployee, is(expectedEmployee));
        verify(dateParser).parse(anyString());
    }

    @Test
    public void shouldCreateNewEmployeeWithNoRolloverDaysIfNoRolloverDaysGiven() {
        LocalDate date = new DateParserService().parse(TODAY);
        when(dateParser.parse(anyString())).thenReturn(date);

        Employee actualEmployee = employeeService.createEmployee(TODAY, NO_ROLLOVER_DAYS, NO_DAYS_OFF, "");

        Employee expectedEmployee = new Employee(date, 0d, NO_DAYS_OFF, DEFAULT_ACCRUAL_RATE);
        assertThat(actualEmployee, is(expectedEmployee));
    }

    @Test
    public void shouldCreateEmployeeWithCustomAccrualRateIfGiven() {
        LocalDate date = new DateParserService().parse(TODAY);
        when(dateParser.parse(anyString())).thenReturn(date);

        Employee actualEmployee = employeeService.createEmployee(TODAY, NO_ROLLOVER_DAYS, NO_DAYS_OFF, CUSTOM_ACCRUAL_RATE);

        Employee expectedEmployee = new Employee(date, 0d, NO_DAYS_OFF, DEFAULT_ACCRUAL_RATE, 17d);
        assertThat(actualEmployee, is(expectedEmployee));

    }
}
