package com.springapp.mvc.web.service;

import com.springapp.mvc.web.model.Constants;
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

    public static final LocalDate DATE = new LocalDate(2013, 10, 20);
    public static final String SOME_ROLLOVER_DAYS = "6";
    public static final String NO_ROLLOVER_DAYS = "";
    public static final String NO_INITIAL_ACCRUAL_RATE = "";
    public static final String CUSTOM_ACCRUAL_RATE = "17";
    public static final double PERSONAL_DAYS_TAKEN = 3.0;
    public static final Map<LocalDate, Double> NO_DAYS_OFF = new HashMap<LocalDate, Double>();

    EmployeeService employeeService;

    @Before
    public void setUp() {
        employeeService = new EmployeeService();
    }

    @Test
    public void shouldCreateNewEmployeeWithNoRolloverDaysIfNoRolloverDaysGiven() {
        assertEmployeeEquality(DATE, NO_ROLLOVER_DAYS, NO_DAYS_OFF, PERSONAL_DAYS_TAKEN, NO_INITIAL_ACCRUAL_RATE);
    }

    @Test
    public void shouldCreateEmployeeWithCertainAmountOfRolloverDays() {
        assertEmployeeEquality(DATE, SOME_ROLLOVER_DAYS, NO_DAYS_OFF, PERSONAL_DAYS_TAKEN, NO_INITIAL_ACCRUAL_RATE);
    }

    @Test
    public void shouldCreateEmployeeWithCustomAccrualRateIfGiven() {
        assertEmployeeEquality(DATE, NO_ROLLOVER_DAYS, NO_DAYS_OFF, PERSONAL_DAYS_TAKEN, CUSTOM_ACCRUAL_RATE);
    }

    @Test
    public void shouldCreateEmployeeWithDefaultAccrualRateIfRateNotGiven(){
        assertEmployeeEquality(DATE, NO_ROLLOVER_DAYS, NO_DAYS_OFF, PERSONAL_DAYS_TAKEN, NO_INITIAL_ACCRUAL_RATE);
    }

    private void assertEmployeeEquality(LocalDate date, String rolloverDays, Map<LocalDate, Double> daysOff, double personalDaysTaken, String accrualRate) {
        Employee actualEmployee = employeeService.createEmployee(date, rolloverDays, daysOff, personalDaysTaken, accrualRate);

        double convertedRolloverDays = 0;
        double convertedAccrualRate = Constants.DEFAULT_ACCRUAL_RATE;

        if(!rolloverDays.equals("")){
            convertedRolloverDays = Double.parseDouble(rolloverDays);
        }

        if(!accrualRate.equals("")){
            convertedAccrualRate = Double.parseDouble(accrualRate);
        }

        assertThat(actualEmployee.getRolloverDays(), is(convertedRolloverDays));
        assertThat(actualEmployee.getInitialAccrualRate(), is(convertedAccrualRate));
    }
}
