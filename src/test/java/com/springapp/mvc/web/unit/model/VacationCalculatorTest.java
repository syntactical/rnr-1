package com.springapp.mvc.web.unit.model;

import com.springapp.mvc.web.model.AccrualRateCalculator;
import com.springapp.mvc.web.model.Constants;
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

    private static final double CAP_FOR_DEFAULT_ACCRUAL_RATE = Constants.DEFAULT_ACCRUAL_RATE * 1.5;
    private static final double NO_ROLLOVER_DAYS = 0.0;
    private static final double NO_PERSONAL_DAYS_TAKEN = 0.0;

    private VacationCalculator vacationCalculator;
    private AccrualRateCalculator mockAccrualRateCalculator;

    @Before
    public void setUp() throws Exception {
        vacationCalculator = new VacationCalculator();
        mockAccrualRateCalculator = mock(AccrualRateCalculator.class);
        NO_TIME_OFF = new HashMap<LocalDate, Double>();

        when(mockAccrualRateCalculator.calculateDailyAccrualRate(any(Employee.class), any(LocalDate.class))).thenReturn(Constants.DEFAULT_ACCRUAL_RATE / Constants.YEAR_IN_DAYS);
        when(mockAccrualRateCalculator.calculateVacationDayCap(any(Employee.class), any(LocalDate.class))).thenReturn(CAP_FOR_DEFAULT_ACCRUAL_RATE);
    }

    @Test
    public void shouldCalculateVacationDaysAfterIntervalOfTime() {
        Employee employee = new Employee(ONE_WEEK_AGO, NO_ROLLOVER_DAYS, NO_TIME_OFF, NO_PERSONAL_DAYS_TAKEN, Constants.DEFAULT_ACCRUAL_RATE);

        double expectedVacationDays = Constants.DEFAULT_ACCRUAL_RATE / Constants.YEAR_IN_DAYS * 7;

        assertThat(vacationCalculator.getVacationDays(employee, mockAccrualRateCalculator, TODAY), is(expectedVacationDays));
    }

    @Test
    public void shouldNotAccrueDaysPastVacationCap() {
        double expectedAccrualForThreeWeeks = (Constants.DEFAULT_ACCRUAL_RATE) / Constants.YEAR_IN_DAYS * 3 * 7;
        double rolloverDays = CAP_FOR_DEFAULT_ACCRUAL_RATE - expectedAccrualForThreeWeeks;

        Employee employee = new Employee(FOUR_WEEKS_AGO, rolloverDays, NO_TIME_OFF, NO_PERSONAL_DAYS_TAKEN, Constants.DEFAULT_ACCRUAL_RATE);

        assertThat(vacationCalculator.getVacationDays(employee, mockAccrualRateCalculator, TODAY), is(CAP_FOR_DEFAULT_ACCRUAL_RATE));
    }

    @Test
    public void shouldStartAccrualAtSalesforceStartDateIfEmployeeStartDateIsEarlierThanSalesforceStartDate() {
        Employee employee = new Employee(SIX_MONTHS_BEFORE_SALESFORCE_START_DATE, NO_ROLLOVER_DAYS, NO_TIME_OFF, NO_PERSONAL_DAYS_TAKEN, Constants.DEFAULT_ACCRUAL_RATE);

        double expectedVacationDays = (Constants.DEFAULT_ACCRUAL_RATE / Constants.YEAR_IN_DAYS) * 7 * 2;

        assertThat(vacationCalculator.getVacationDays(employee, mockAccrualRateCalculator, TWO_WEEKS_AFTER_SALESFORCE_START_DATE), is(expectedVacationDays));
    }

    @Test
    public void shouldDecrementVacationDaysFromTotal() {
        Map<LocalDate, Double> timeOff = new HashMap<LocalDate, Double>();
        timeOff.put(SALESFORCE_START_DATE, 40.0);
        double rolloverDays = 5d;

        Employee employee = new Employee(SIX_MONTHS_BEFORE_SALESFORCE_START_DATE, rolloverDays, timeOff, NO_PERSONAL_DAYS_TAKEN, Constants.DEFAULT_ACCRUAL_RATE);

        double expectedVacationDays = Constants.DEFAULT_ACCRUAL_RATE / Constants.YEAR_IN_DAYS * 7 * 2;

        assertThat(vacationCalculator.getVacationDays(employee, mockAccrualRateCalculator, TWO_WEEKS_AFTER_SALESFORCE_START_DATE), is(expectedVacationDays));
    }

    @Test
    public void shouldReturnZeroDaysIfEmployeeAtCap(){
        Employee employee = new Employee(TODAY, CAP_FOR_DEFAULT_ACCRUAL_RATE, NO_TIME_OFF, NO_PERSONAL_DAYS_TAKEN, Constants.DEFAULT_ACCRUAL_RATE);

        assertThat(vacationCalculator.getDaysUntilCapIsReached(employee, mockAccrualRateCalculator, TODAY), is(0.0));
    }

    @Test
    public void shouldReturnNumberOfDaysBeforeCapIsReached(){
        double twoWeeksInDays = 14.0;
        double rolloverDays = CAP_FOR_DEFAULT_ACCRUAL_RATE - (Constants.DEFAULT_ACCRUAL_RATE / Constants.YEAR_IN_DAYS) * twoWeeksInDays;

        Employee employee = new Employee(TODAY, rolloverDays, NO_TIME_OFF, NO_PERSONAL_DAYS_TAKEN, Constants.DEFAULT_ACCRUAL_RATE);

        when(mockAccrualRateCalculator.calculateDailyAccrualRate(any(Employee.class), any(LocalDate.class))).thenReturn(Constants.DEFAULT_ACCRUAL_RATE / Constants.YEAR_IN_DAYS);

        assertThat(vacationCalculator.getDaysUntilCapIsReached(employee, mockAccrualRateCalculator, TODAY), is(twoWeeksInDays));
    }
}