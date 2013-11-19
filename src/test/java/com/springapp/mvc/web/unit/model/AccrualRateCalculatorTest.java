package com.springapp.mvc.web.unit.model;

import com.springapp.mvc.web.model.AccrualRateCalculator;
import com.springapp.mvc.web.model.Employee;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccrualRateCalculatorTest {

    public static final double MAXIMUM_VACATION_DAYS = 30d;
    public static final double NO_ROLLOVER_DAYS = 0d;

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

    private final Map<LocalDate, Double> NO_TIME_OFF = new HashMap<LocalDate, Double>();

    AccrualRateCalculator accrualRateCalculator;
    Employee employee;

    @Before
    public void setUp() throws Exception {
        accrualRateCalculator = new AccrualRateCalculator();
        employee = new Employee(TODAY, NO_ROLLOVER_DAYS, NO_TIME_OFF, DEFAULT_ACCRUAL_RATE);
    }

    @Test
    public void shouldHaveAccrualRateOfTenDaysBeforeOneYearElapses() {
        assertThat(accrualRateCalculator.calculateDailyAccrualRate(employee, TOMORROW), is(DEFAULT_ACCRUAL_RATE / YEAR_IN_DAYS));
    }

    @Test
    public void shouldHaveAccrualRateOfFifteenDaysAfterOneYear() {
        assertThat(accrualRateCalculator.calculateDailyAccrualRate(employee, ONE_YEAR_FROM_NOW), is(ACCRUAL_RATE_AFTER_ONE_YEAR / YEAR_IN_DAYS));
    }

    @Test
    public void shouldHaveAccrualRateOfTwentyDaysAfterThreeYears() {
        assertThat(accrualRateCalculator.calculateDailyAccrualRate(employee, THREE_YEARS_FROM_NOW), is(ACCRUAL_RATE_AFTER_THREE_YEARS / YEAR_IN_DAYS));
    }

    @Test
    public void shouldHaveAccrualRateOfTwentyFiveDaysAfterSixYears() {
        assertThat(accrualRateCalculator.calculateDailyAccrualRate(employee, SIX_YEARS_FROM_NOW), is(ACCRUAL_RATE_AFTER_SIX_YEARS / YEAR_IN_DAYS));
    }

    @Test
    public void shouldHaveCustomAccrualRateIfSpecified() {
        Employee employeeWithCustomAccrualRate = new Employee(TODAY, NO_ROLLOVER_DAYS, NO_TIME_OFF, CUSTOM_INITIAL_ACCRUAL_RATE);

        assertThat(accrualRateCalculator.calculateDailyAccrualRate(employeeWithCustomAccrualRate, ONE_YEAR_FROM_NOW), is(CUSTOM_INITIAL_ACCRUAL_RATE / YEAR_IN_DAYS));
        assertThat(accrualRateCalculator.calculateDailyAccrualRate(employeeWithCustomAccrualRate, TOMORROW), is(CUSTOM_INITIAL_ACCRUAL_RATE / YEAR_IN_DAYS));
        assertThat(accrualRateCalculator.calculateDailyAccrualRate(employeeWithCustomAccrualRate, THREE_YEARS_FROM_NOW), is(ACCRUAL_RATE_AFTER_THREE_YEARS / YEAR_IN_DAYS));

    }

    @Test
    public void shouldReturnVacationDayCap() {
        assertThat(accrualRateCalculator.calculateVacationDayCap(employee, TOMORROW), is(DEFAULT_ACCRUAL_RATE * 1.5));
        assertThat(accrualRateCalculator.calculateVacationDayCap(employee, ONE_YEAR_FROM_NOW), is(ACCRUAL_RATE_AFTER_ONE_YEAR * 1.5));
        assertThat(accrualRateCalculator.calculateVacationDayCap(employee, SIX_YEARS_FROM_NOW), is(MAXIMUM_VACATION_DAYS));
    }
}
