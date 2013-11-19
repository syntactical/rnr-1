package com.springapp.mvc.web.unit.model;

import com.springapp.mvc.web.model.AccrualRateCalculator;
import com.springapp.mvc.web.model.VacationCalculator;
import com.springapp.mvc.web.model.Employee;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VacationCalculatorTest {

    private static final LocalDate ONE_WEEK_AGO = new LocalDate().minusWeeks(1);
    private static final LocalDate FOUR_WEEKS_AGO = new LocalDate().minusWeeks(4);
    private static final LocalDate SALESFORCE_START_DATE = new LocalDate(2013, 7, 1);
    private static final LocalDate TWO_WEEKS_AFTER_SALESFORCE_START_DATE = SALESFORCE_START_DATE.plusWeeks(2);
    private static final LocalDate SIX_MONTHS_BEFORE_SALESFORCE_START_DATE = SALESFORCE_START_DATE.minusMonths(6).minusDays(1);
    private static final LocalDate TODAY = new LocalDate();

    private HashMap<LocalDate, Double> NO_TIME_OFF;

    private static final double DEFAULT_ACCRUAL_RATE = 10d;
    private static final double CAP_FOR_DEFAULT_ACCRUAL_RATE = DEFAULT_ACCRUAL_RATE * 1.5;
    private static final double YEAR_IN_DAYS = 365.25;
    private static final double NO_ROLLOVER_DAYS = 0d;

    private VacationCalculator vacationCalculator;

    private Employee mockEmployee;
    private AccrualRateCalculator mockAccrualRateCalculator;

    @Before
    public void setUp() throws Exception {
        vacationCalculator = new VacationCalculator();
        mockEmployee = mock(Employee.class);
        mockAccrualRateCalculator = mock(AccrualRateCalculator.class);
        NO_TIME_OFF = new HashMap<LocalDate, Double>();

        when(mockAccrualRateCalculator.calculateDailyAccrualRate(any(Employee.class), any(LocalDate.class))).thenReturn(DEFAULT_ACCRUAL_RATE / YEAR_IN_DAYS);
        when(mockAccrualRateCalculator.calculateVacationDayCap(any(Employee.class), any(LocalDate.class))).thenReturn(CAP_FOR_DEFAULT_ACCRUAL_RATE);
    }

    @Test
    public void shouldCalculateVacationDaysAfterIntervalOfTime() {
        when(mockEmployee.getStartDate()).thenReturn(ONE_WEEK_AGO);
        when(mockEmployee.getRolloverDays()).thenReturn(NO_ROLLOVER_DAYS);
        when(mockEmployee.getDaysOff()).thenReturn(NO_TIME_OFF);

        double expectedVacationDays = DEFAULT_ACCRUAL_RATE / YEAR_IN_DAYS * 7;

        assertThat(vacationCalculator.getVacationDays(mockEmployee, mockAccrualRateCalculator, TODAY), is(expectedVacationDays));
    }

    @Test
    public void shouldNotAccrueDaysPastVacationCap() {
        double expectedAccrualForThreeWeeks = (DEFAULT_ACCRUAL_RATE) / YEAR_IN_DAYS * 3 * 7;

        when(mockEmployee.getStartDate()).thenReturn(FOUR_WEEKS_AGO);
        when(mockEmployee.getRolloverDays()).thenReturn(CAP_FOR_DEFAULT_ACCRUAL_RATE - expectedAccrualForThreeWeeks);
        when(mockEmployee.getDaysOff()).thenReturn(NO_TIME_OFF);

        assertThat(vacationCalculator.getVacationDays(mockEmployee, mockAccrualRateCalculator, TODAY), is(CAP_FOR_DEFAULT_ACCRUAL_RATE));
    }

    @Test
    public void shouldStartAccrualAtSalesforceStartDateIfEmployeeStartDateIsEarlierThanSalesforceStartDate() {
        when(mockEmployee.getStartDate()).thenReturn(SIX_MONTHS_BEFORE_SALESFORCE_START_DATE);
        when(mockEmployee.getRolloverDays()).thenReturn(NO_ROLLOVER_DAYS);
        when(mockEmployee.getDaysOff()).thenReturn(NO_TIME_OFF);

        double expectedVacationDays = (DEFAULT_ACCRUAL_RATE / YEAR_IN_DAYS) * 7 * 2;

        assertThat(vacationCalculator.getVacationDays(mockEmployee, mockAccrualRateCalculator,TWO_WEEKS_AFTER_SALESFORCE_START_DATE), is(expectedVacationDays));
    }

    @Test
    public void shouldDecrementVacationDaysFromTotal() {
        Map<LocalDate, Double> timeOff = new HashMap<LocalDate, Double>();
        timeOff.put(SALESFORCE_START_DATE, 40.0);

        when(mockEmployee.getStartDate()).thenReturn(SIX_MONTHS_BEFORE_SALESFORCE_START_DATE);
        when(mockEmployee.getRolloverDays()).thenReturn(5d);
        when(mockEmployee.getDaysOff()).thenReturn(timeOff);

        double expectedVacationDays = DEFAULT_ACCRUAL_RATE / YEAR_IN_DAYS * 7 * 2;

        assertThat(vacationCalculator.getVacationDays(mockEmployee, mockAccrualRateCalculator, TWO_WEEKS_AFTER_SALESFORCE_START_DATE), is(expectedVacationDays));
    }


}