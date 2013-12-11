package com.springapp.mvc.web.unit.service;

import com.springapp.mvc.web.model.AccrualRateCalculator;
import com.springapp.mvc.web.model.Employee;
import com.springapp.mvc.web.model.VacationCalculator;
import com.springapp.mvc.web.service.VacationCalculatorService;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VacationCalculatorServiceTest {

    Employee mockEmployee;
    AccrualRateCalculator mockAccrualRateCalculator;
    VacationCalculator mockVacationCalculator;
    VacationCalculatorService vacationCalculatorService;

    LocalDate endDate = new LocalDate();

    @Before
    public void setUp(){
        mockVacationCalculator = mock(VacationCalculator.class);
        mockEmployee = mock(Employee.class);
        mockAccrualRateCalculator = mock(AccrualRateCalculator.class);
        vacationCalculatorService = new VacationCalculatorService(mockVacationCalculator);
    }

    @Test
    public void shouldReturnCapReachedNoticeWhenCapIsReached(){
        when(mockVacationCalculator.getDaysUntilCapIsReached(any(Employee.class), any(AccrualRateCalculator.class), any(LocalDate.class))).thenReturn(0.0);

        String actualCapNotice = vacationCalculatorService.getVacationCapNotice(mockEmployee, mockAccrualRateCalculator, endDate);
        String expectedCapNotice = "You have reached your vacation day cap.";

        assertThat(actualCapNotice, is(expectedCapNotice));
    }

    @Test
    public void shouldReturnTimeUntilCapIsReachedIfUnderOneMonth(){

    }

    @Test
    public void shouldReturnBlankStringIfMoreThanOneMonthAwayFromCap(){

    }

    @Test
    public void shouldInteractWithVacationCalculator(){
        vacationCalculatorService.getVacationDays(mockEmployee, mockAccrualRateCalculator, endDate);

        verify(mockVacationCalculator).getVacationDays(mockEmployee, mockAccrualRateCalculator, endDate);
    }
}
