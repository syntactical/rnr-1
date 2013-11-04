package com.springapp.mvc.web.unit.model;

import com.springapp.mvc.web.model.AccrualRate;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class AccrualRateTest {

    public static final double MAXNIMUM_VACATION_DAYS = 30d;
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

    AccrualRate accrualRate;

    @Before
    public void setUp() throws Exception {
        accrualRate = new AccrualRate();
    }

    @Test
    public void shouldHaveAccrualRateOfTenDaysBeforeOneYearElapses() {
        assertThat(accrualRate.calculateDailyAccrualRate(TODAY, TOMORROW, DEFAULT_ACCRUAL_RATE), is(DEFAULT_ACCRUAL_RATE / YEAR_IN_DAYS));
    }

    @Test
    public void shouldHaveAccrualRateOfFifteenDaysAfterOneYear() {
        assertThat(accrualRate.calculateDailyAccrualRate(TODAY, ONE_YEAR_FROM_NOW, DEFAULT_ACCRUAL_RATE), is(ACCRUAL_RATE_AFTER_ONE_YEAR / YEAR_IN_DAYS));
    }

    @Test
    public void shouldHaveAccrualRateOfTwentyDaysAfterThreeYears() {
        assertThat(accrualRate.calculateDailyAccrualRate(TODAY, THREE_YEARS_FROM_NOW, DEFAULT_ACCRUAL_RATE), is(ACCRUAL_RATE_AFTER_THREE_YEARS / YEAR_IN_DAYS));
    }

    @Test
    public void shouldHaveAccrualRateOfTwentyFiveDaysAfterSixYears() {
        assertThat(accrualRate.calculateDailyAccrualRate(TODAY, SIX_YEARS_FROM_NOW, DEFAULT_ACCRUAL_RATE), is(ACCRUAL_RATE_AFTER_SIX_YEARS / YEAR_IN_DAYS));
    }

    @Test
    public void shouldHaveCustomAccrualRateIfSpecified() {
        assertThat(accrualRate.calculateDailyAccrualRate(TODAY, TOMORROW, CUSTOM_INITIAL_ACCRUAL_RATE), is(CUSTOM_INITIAL_ACCRUAL_RATE / YEAR_IN_DAYS));
        assertThat(accrualRate.calculateDailyAccrualRate(TODAY, ONE_YEAR_FROM_NOW, CUSTOM_INITIAL_ACCRUAL_RATE), is(CUSTOM_INITIAL_ACCRUAL_RATE / YEAR_IN_DAYS));
        assertThat(accrualRate.calculateDailyAccrualRate(TODAY, THREE_YEARS_FROM_NOW, CUSTOM_INITIAL_ACCRUAL_RATE), is(ACCRUAL_RATE_AFTER_THREE_YEARS / YEAR_IN_DAYS));

    }

    @Test
    public void shouldReturnVacationDayCap(){
        assertThat(accrualRate.calculateVacationDayCap(TODAY, TOMORROW, DEFAULT_ACCRUAL_RATE), is(DEFAULT_ACCRUAL_RATE * 1.5));
        assertThat(accrualRate.calculateVacationDayCap(TODAY, ONE_YEAR_FROM_NOW, DEFAULT_ACCRUAL_RATE), is(ACCRUAL_RATE_AFTER_ONE_YEAR * 1.5));
        assertThat(accrualRate.calculateVacationDayCap(TODAY, SIX_YEARS_FROM_NOW, DEFAULT_ACCRUAL_RATE), is(MAXNIMUM_VACATION_DAYS));
    }











//
//        @Test
//    public void shouldGetAccrualRateDaysOf10IfRateNullAndElapsedTimeIsLTOneYear() throws Exception {
//        double expectedRate = 10. / 365;
//        DateTime lessThanOneYearAgo = TODAY.plusDays(1);
//        assertThat(new AccrualRate().givenIStarted(lessThanOneYearAgo).calculate() , is(expectedRate));
//    }
//
//    @Test
//    public void shouldGetAccrualRateDaysOf15IfRateNullAndElapsedTimeGTOneYear() {
//        double expectedRate = 15. / 365;
//        DateTime twoYearsAgo = TODAY.minusYears(1);
//        assertThat(new AccrualRate().givenIStarted(twoYearsAgo).calculate() , is(expectedRate));
//    }
//
//    @Test
//    public void shouldGetAccrualRateDaysOf20IfRateNullAndElapsedTimeGT3Years() throws Exception {
//        double expectedRate = 20. / 365;
//        DateTime fourYearsAgo = TODAY.minusYears(3);
//        assertThat(new AccrualRate().givenIStarted(fourYearsAgo).calculate(), is(expectedRate));
//    }
//
//    @Test
//    public void shouldGetAccrualRateDaysOf25IfRateNullAndElapsedTimeGT6Years() throws Exception {
//        double expectedRate = 25. / 365;
//        DateTime sevenYearsAgo = TODAY.minusYears(6);
//        assertThat(new AccrualRate().givenIStarted(sevenYearsAgo).calculate() , is(expectedRate));
//    }




}
