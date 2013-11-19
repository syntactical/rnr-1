package com.springapp.mvc.web.service;

import com.springapp.mvc.web.model.Employee;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    public static final String DATE = "10/20/2013";
    public static final String SOME_ROLLOVER_DAYS = "6";
    public static final String NO_ROLLOVER_DAYS = "";
    public static final String NO_INITIAL_ACCRUAL_RATE = "";
    public static final String CUSTOM_ACCRUAL_RATE = "17";
    public static final Map<LocalDate, Double> NO_DAYS_OFF = new HashMap<LocalDate, Double>();
    public static final LocalDate DATE_AS_LOCALDATE = new DateParserService().parse(DATE);
    public static final double DEFAULT_ACCRUAL_RATE = 10d;

    EmployeeService employeeService;
    DateParserService mockDateParser;

    @Before
    public void setUp() {
        mockDateParser = mock(DateParserService.class);
        when(mockDateParser.parse(DATE)).thenReturn(DATE_AS_LOCALDATE);

        employeeService = new EmployeeService(mockDateParser);
    }

    @Test
    public void shouldCreateNewEmployeeWithNoRolloverDaysIfNoRolloverDaysGiven() {
        assertEmployeeEquality(DATE, NO_ROLLOVER_DAYS, NO_DAYS_OFF, NO_INITIAL_ACCRUAL_RATE);
    }

    @Test
    public void shouldCreateEmployeeWithCertainAmountOfRolloverDays() {
        assertEmployeeEquality(DATE, SOME_ROLLOVER_DAYS, NO_DAYS_OFF, NO_INITIAL_ACCRUAL_RATE);
    }

    @Test
    public void shouldCreateEmployeeWithCustomAccrualRateIfGiven() {
        assertEmployeeEquality(DATE, NO_ROLLOVER_DAYS, NO_DAYS_OFF, CUSTOM_ACCRUAL_RATE);
    }

    @Test
    public void shouldCreateEmployeeWithDefaultAccrualRateIfRateNotGiven(){
        assertEmployeeEquality(DATE, NO_ROLLOVER_DAYS, NO_DAYS_OFF, NO_INITIAL_ACCRUAL_RATE);
    }

    @Test
    public void shouldInteractWithDateParser() throws Exception {
        employeeService.createEmployee(DATE, SOME_ROLLOVER_DAYS, NO_DAYS_OFF, NO_INITIAL_ACCRUAL_RATE);
        verify(mockDateParser).parse(anyString());
    }

    private void assertEmployeeEquality(String date, String rolloverDays, Map<LocalDate, Double> daysOff, String accrualRate) {
        Employee actualEmployee = employeeService.createEmployee(date, rolloverDays, daysOff, accrualRate);

        double convertedRolloverDays = rolloverDays.equals("") ? 0 : Double.parseDouble(rolloverDays);
        double convertedAccrualRate = accrualRate.equals("") ? DEFAULT_ACCRUAL_RATE : Double.parseDouble(accrualRate);
        LocalDate convertedDate = new DateParserService().parse(date);

        assertThat(actualEmployee.getDaysOff(), is(daysOff));
        assertThat(actualEmployee.getRolloverDays(), is(convertedRolloverDays));
        assertThat(actualEmployee.getInitialAccrualRate(), is(convertedAccrualRate));
        assertThat(actualEmployee.getStartDate(), is(convertedDate));
    }
}
