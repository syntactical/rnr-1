package com.springapp.mvc.web.unit.model;

import com.springapp.mvc.web.model.AccrualRateCalculator;
import com.springapp.mvc.web.model.Employee;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.mockito.Matchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EmployeeTest {

    private Employee employeeWithDefaultInitialAccrualRate;
    private LocalDate startDate;
    private double rolloverDays;
    private AccrualRateCalculator mockAccrualRateCalculator;
    private HashMap<LocalDate, Double> daysOff;

    private final LocalDate TODAY = new LocalDate();
    private final double DEFAULT_ACCRUAL_RATE = 10d;
    private final double CUSTOM_ACCRUAL_RATE = 17d;
    private final double YEAR_IN_DAYS = 365.25;

    @Before
    public void setup() {
        startDate = TODAY;
        rolloverDays = 0;
        daysOff = new HashMap<LocalDate, Double>();
        mockAccrualRateCalculator = mock(AccrualRateCalculator.class);
        employeeWithDefaultInitialAccrualRate = new Employee(startDate, rolloverDays, daysOff, mockAccrualRateCalculator);
    }

    @Test
    public void shouldCallAccrualRateWhenAskedForAccrualRate() {
        employeeWithDefaultInitialAccrualRate.calculateDailyAccrualRate(TODAY);
        verify(mockAccrualRateCalculator).calculateDailyAccrualRate(startDate, TODAY, DEFAULT_ACCRUAL_RATE);
    }

    @Test
    public void shouldCallAccrualRateWhenAskedForCap(){
        employeeWithDefaultInitialAccrualRate.calculateVacationDayCap(TODAY);
        verify(mockAccrualRateCalculator).calculateVacationDayCap(startDate, TODAY, DEFAULT_ACCRUAL_RATE);
    }

    @Test
    public void shouldCallAccrualRateWithCustomInitialAccrualRate() {
        Employee employeeWithCustomAccrualRate = new Employee(startDate, rolloverDays, daysOff, mockAccrualRateCalculator, CUSTOM_ACCRUAL_RATE);

        employeeWithCustomAccrualRate.calculateDailyAccrualRate(TODAY);
        verify(mockAccrualRateCalculator).calculateDailyAccrualRate(startDate, TODAY, CUSTOM_ACCRUAL_RATE);
    }

    @Test
    public void shouldDefaultWithAccrualRateOfTenDaysIfAccrualRateNotSpecified() {
        Employee expectedEmployee = new Employee(startDate, rolloverDays, daysOff, mockAccrualRateCalculator, DEFAULT_ACCRUAL_RATE);
        assertThat(expectedEmployee, is(employeeWithDefaultInitialAccrualRate));
    }
}
