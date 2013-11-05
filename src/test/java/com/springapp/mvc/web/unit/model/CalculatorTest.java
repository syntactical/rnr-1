package com.springapp.mvc.web.unit.model;

import com.springapp.mvc.web.model.AccrualRate;
import com.springapp.mvc.web.model.Calculator;
import com.springapp.mvc.web.model.Employee;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculatorTest {

    public static final LocalDate ONE_WEEK_AGO = new LocalDate().minusWeeks(1);
    public static final LocalDate FOUR_WEEKS_AGO = new LocalDate().minusWeeks(4);
    public static final LocalDate SALESFORCE_START_DATE = new LocalDate(2013, 7, 1);
    public static final LocalDate TWO_WEEKS_AFTER_SALESFORCE_START_DATE = SALESFORCE_START_DATE.plusWeeks(2);
    public static final LocalDate SIX_MONTHS_BEFORE_SALESFORCE_START_DATE = SALESFORCE_START_DATE.minusMonths(6).minusDays(1);
    public static final LocalDate TODAY = new LocalDate();

    public static final HashMap<LocalDate, Double> NO_TIME_OFF = new HashMap<LocalDate, Double>();

    public static final double DEFAULT_ACCRUAL_RATE = 10d;
    public static final double YEAR_IN_DAYS = 365.25;

    private Calculator calculator;
    private Employee employee;
    private AccrualRate mockAccrualRate;

    String emptyAccrualRate;

    @Before
    public void setUp() throws Exception {
        calculator = new Calculator();
        emptyAccrualRate = "";
        mockAccrualRate = mock(AccrualRate.class);
        employee = new Employee(ONE_WEEK_AGO, 0, NO_TIME_OFF, mockAccrualRate);
    }

    @Test
    public void shouldCalculateVacationDaysAfterIntervalOfTime() {
        double expectedVacationDays = DEFAULT_ACCRUAL_RATE / YEAR_IN_DAYS * 7;
        when(mockAccrualRate.calculateDailyAccrualRate(any(LocalDate.class), any(LocalDate.class), anyDouble())).thenReturn(DEFAULT_ACCRUAL_RATE);
        when(mockAccrualRate.calculateVacationDayCap(any(LocalDate.class), any(LocalDate.class), anyDouble())).thenReturn(15d);

        assertThat(calculator.getVacationDays(employee, TODAY), is(expectedVacationDays));
    }

    @Test
    public void shouldNotAccrueDaysPastVacationCap() {
        double expectedVacationDays = DEFAULT_ACCRUAL_RATE * 1.5;
        double expectedAccrualForThreeWeeks = (DEFAULT_ACCRUAL_RATE) / YEAR_IN_DAYS * 3 * 7;
        double rolloverDays = expectedVacationDays - expectedAccrualForThreeWeeks;

        when(mockAccrualRate.calculateDailyAccrualRate(any(LocalDate.class), any(LocalDate.class), anyDouble())).thenReturn(DEFAULT_ACCRUAL_RATE);
        when(mockAccrualRate.calculateVacationDayCap(any(LocalDate.class), any(LocalDate.class), anyDouble())).thenReturn(15d);

        Employee employeeCloseToVacationMax = new Employee(FOUR_WEEKS_AGO, rolloverDays, NO_TIME_OFF, mockAccrualRate);
        assertThat(calculator.getVacationDays(employeeCloseToVacationMax, TODAY), is(expectedVacationDays));
    }

    @Test
    public void shouldStartAccrualAtSalesforceStartDateIfEmployeeStartDateIsEarlierThanSalesforceStartDate() {
        Employee veteranEmployee = new Employee(SIX_MONTHS_BEFORE_SALESFORCE_START_DATE, 0, NO_TIME_OFF, mockAccrualRate);
        when(mockAccrualRate.calculateDailyAccrualRate(any(LocalDate.class), any(LocalDate.class), anyDouble())).thenReturn(DEFAULT_ACCRUAL_RATE);
        when(mockAccrualRate.calculateVacationDayCap(any(LocalDate.class), any(LocalDate.class), anyDouble())).thenReturn(15d);

        double expectedVacationDays = (DEFAULT_ACCRUAL_RATE / YEAR_IN_DAYS) * 7 * 2;
        assertThat(calculator.getVacationDays(veteranEmployee, TWO_WEEKS_AFTER_SALESFORCE_START_DATE), is(expectedVacationDays));
    }

    @Test
    public void shouldDecrementVacationDaysFromTotal() {
        HashMap<LocalDate, Double> timeOff = new HashMap<LocalDate, Double>();
        timeOff.put(SALESFORCE_START_DATE, 40.0);

        Employee employeeWithVacation = new Employee(SIX_MONTHS_BEFORE_SALESFORCE_START_DATE, 5, timeOff, mockAccrualRate);

        double expectedVacationDays = DEFAULT_ACCRUAL_RATE / YEAR_IN_DAYS * 7 * 2;

        when(mockAccrualRate.calculateDailyAccrualRate(any(LocalDate.class), any(LocalDate.class), anyDouble())).thenReturn(DEFAULT_ACCRUAL_RATE);
        when(mockAccrualRate.calculateVacationDayCap(any(LocalDate.class), any(LocalDate.class), anyDouble())).thenReturn(15d);

        assertThat(calculator.getVacationDays(employeeWithVacation, TWO_WEEKS_AFTER_SALESFORCE_START_DATE), is(expectedVacationDays));
    }

}