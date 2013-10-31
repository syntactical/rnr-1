package com.springapp.mvc.web.unit.model;

import com.springapp.mvc.web.model.Employee;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EmployeeTest {

    private Employee employee;
    private LocalDate startDate;
    private double rolloverDays;
    private HashMap<LocalDate, Double> daysOff;

    private final LocalDate ONE_YEAR_FROM_NOW = new LocalDate().plusYears(1).plusDays(1);
    private final LocalDate THREE_YEARS_FROM_NOW = new LocalDate().plusYears(3).plusDays(1);
    private final LocalDate SIX_YEARS_FROM_NOW = new LocalDate().plusYears(6).plusDays(1);
    private final LocalDate TODAY = new LocalDate();
    private final double YEAR_IN_DAYS = 365.25;

    @Before
    public void setup() {
        startDate = TODAY;
        rolloverDays = 0;
        daysOff = new HashMap<LocalDate, Double>();
        employee = new Employee(startDate, rolloverDays, daysOff);
    }

    @Test
    public void shouldHaveAccrualRateOfTenDaysBeforeOneYearElapses() {
        assertThat(employee.calculateDailyAccrualRate(TODAY), is(10d / YEAR_IN_DAYS));
    }

    @Test
    public void shouldHaveAccrualRateOfFifteenDaysAfterOneYear() {
        assertThat(employee.calculateDailyAccrualRate(ONE_YEAR_FROM_NOW), is(15d / YEAR_IN_DAYS));
    }

    @Test
    public void shouldHaveAccrualRateOfTwentyDaysAfterThreeYears() {
        assertThat(employee.calculateDailyAccrualRate(THREE_YEARS_FROM_NOW), is(20d / YEAR_IN_DAYS));
    }

    @Test
    public void shouldHaveAccrualRateOfTwentyFiveDaysAfterSixYears() {
        assertThat(employee.calculateDailyAccrualRate(SIX_YEARS_FROM_NOW), is(25d / YEAR_IN_DAYS));
    }

    @Test
    public void shouldHaveCustomAccrualRateIfSpecified() {
        Employee employeeWithCustomAccrualRate = new Employee(startDate, rolloverDays, daysOff, 17d);
        assertThat(employeeWithCustomAccrualRate.calculateDailyAccrualRate(TODAY), is(17d / YEAR_IN_DAYS));
        assertThat(employeeWithCustomAccrualRate.calculateDailyAccrualRate(ONE_YEAR_FROM_NOW), is(17d / YEAR_IN_DAYS));
        assertThat(employeeWithCustomAccrualRate.calculateDailyAccrualRate(THREE_YEARS_FROM_NOW), is(20d / YEAR_IN_DAYS));
    }


}
