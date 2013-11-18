package com.springapp.mvc.web.unit.model;

import com.springapp.mvc.web.model.AccrualRateCalculator;
import com.springapp.mvc.web.model.Employee;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccrualRateCalculatorTest {

    public static final double MAXIMUM_VACATION_DAYS = 30d;
    private final LocalDate TODAY = new LocalDate();
    private final LocalDate TOMORROW = new LocalDate().plusDays(1);
    private final LocalDate ONE_YEAR_FROM_NOW = new LocalDate().plusYears(1).plusDays(1);
    private final LocalDate THREE_YEARS_FROM_NOW = new LocalDate().plusYears(3).plusDays(1);
    private final LocalDate SIX_YEARS_FROM_NOW = new LocalDate().plusYears(6).plusDays(1);

    private final double DEFAULT_ACCRUAL_RATE = 10d;
    private final double ACCRUAL_RATE_AFTER_ONE_YEAR = 15d;
    private final double ACCRUAL_RATE_AFTER_THREE_YEARS = 20d;
    private final double ACCRUAL_RATE_AFTER_SIX_YEARS = 25d;

    private final double CUSTOM_INITIAL_ACCRUAL_RATE = 17d;

    private final double YEAR_IN_DAYS = 365.25;

    AccrualRateCalculator accrualRateCalculator;

    Employee mockEmployee;

    @Before
    public void setUp() throws Exception {
        accrualRateCalculator = new AccrualRateCalculator();
        mockEmployee = mock(Employee.class);
        when(mockEmployee.getInitialAccrualRate()).thenReturn(DEFAULT_ACCRUAL_RATE);
        when(mockEmployee.getStartDate()).thenReturn(TODAY);
    }

    @Test
    public void shouldHaveAccrualRateOfTenDaysBeforeOneYearElapses() {
        assertThat(accrualRateCalculator.calculateDailyAccrualRate(mockEmployee, TOMORROW), is(DEFAULT_ACCRUAL_RATE / YEAR_IN_DAYS));
    }

    @Test
    public void shouldHaveAccrualRateOfFifteenDaysAfterOneYear() {
        assertThat(accrualRateCalculator.calculateDailyAccrualRate(mockEmployee, ONE_YEAR_FROM_NOW), is(ACCRUAL_RATE_AFTER_ONE_YEAR / YEAR_IN_DAYS));
    }

    @Test
    public void shouldHaveAccrualRateOfTwentyDaysAfterThreeYears() {
        assertThat(accrualRateCalculator.calculateDailyAccrualRate(mockEmployee, THREE_YEARS_FROM_NOW), is(ACCRUAL_RATE_AFTER_THREE_YEARS / YEAR_IN_DAYS));
    }

    @Test
    public void shouldHaveAccrualRateOfTwentyFiveDaysAfterSixYears() {
        assertThat(accrualRateCalculator.calculateDailyAccrualRate(mockEmployee, SIX_YEARS_FROM_NOW), is(ACCRUAL_RATE_AFTER_SIX_YEARS / YEAR_IN_DAYS));
    }

    @Test
    public void shouldHaveCustomAccrualRateIfSpecified() {

        Employee mockEmployeeWithCustomAccrualRate = mock(Employee.class);
        when(mockEmployeeWithCustomAccrualRate.getInitialAccrualRate()).thenReturn(CUSTOM_INITIAL_ACCRUAL_RATE);
        when(mockEmployeeWithCustomAccrualRate.getStartDate()).thenReturn(TODAY);

        assertThat(accrualRateCalculator.calculateDailyAccrualRate(mockEmployeeWithCustomAccrualRate, ONE_YEAR_FROM_NOW), is(CUSTOM_INITIAL_ACCRUAL_RATE / YEAR_IN_DAYS));
        assertThat(accrualRateCalculator.calculateDailyAccrualRate(mockEmployeeWithCustomAccrualRate, TOMORROW), is(CUSTOM_INITIAL_ACCRUAL_RATE / YEAR_IN_DAYS));
        assertThat(accrualRateCalculator.calculateDailyAccrualRate(mockEmployeeWithCustomAccrualRate, THREE_YEARS_FROM_NOW), is(ACCRUAL_RATE_AFTER_THREE_YEARS / YEAR_IN_DAYS));

    }

    @Test
    public void shouldReturnVacationDayCap() {
        assertThat(accrualRateCalculator.calculateVacationDayCap(mockEmployee, TOMORROW), is(DEFAULT_ACCRUAL_RATE * 1.5));
        assertThat(accrualRateCalculator.calculateVacationDayCap(mockEmployee, ONE_YEAR_FROM_NOW), is(ACCRUAL_RATE_AFTER_ONE_YEAR * 1.5));
        assertThat(accrualRateCalculator.calculateVacationDayCap(mockEmployee, SIX_YEARS_FROM_NOW), is(MAXIMUM_VACATION_DAYS));
    }
}
