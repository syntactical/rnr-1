package com.springapp.mvc.web.unit.controller;

import com.springapp.mvc.web.controller.HomeController;
import com.springapp.mvc.web.model.AccrualRateCalculator;
import com.springapp.mvc.web.model.PersonalDaysCalculator;
import com.springapp.mvc.web.model.VacationCalculator;
import com.springapp.mvc.web.model.Employee;
import com.springapp.mvc.web.service.DateParserService;
import com.springapp.mvc.web.service.EmployeeService;
import com.springapp.mvc.web.service.SalesForceParserService;
import com.springapp.mvc.web.service.VacationCalculatorService;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class HomeControllerTest {
    HttpServletRequest mockHttpServletRequest;
    SalesForceParserService mockSalesForceParserService;
    EmployeeService mockEmployeeService;
    VacationCalculatorService mockVacationCalculatorService;
    AccrualRateCalculator mockAccrualRateCalculator;
    DateParserService mockDateParserService;
    PersonalDaysCalculator mockPersonalDaysCalculator;

    @Before
    public void setUp() throws Exception {
        mockHttpServletRequest = mock(HttpServletRequest.class);
        mockSalesForceParserService = mock(SalesForceParserService.class);
        mockEmployeeService = mock(EmployeeService.class);
        mockVacationCalculatorService = mock(VacationCalculatorService.class);
        mockAccrualRateCalculator = mock(AccrualRateCalculator.class);
        mockDateParserService = mock(DateParserService.class);
        mockPersonalDaysCalculator = mock(PersonalDaysCalculator.class);
    }

    @Test
    public void post_shouldReturnHomeView() throws IOException, ParseException {
        HomeController homeController = new HomeController(mockEmployeeService, mockSalesForceParserService, mockVacationCalculatorService, mockAccrualRateCalculator, mockDateParserService, mockPersonalDaysCalculator);
        when(mockHttpServletRequest.getParameter("rolloverdays")).thenReturn("1");
        when(mockHttpServletRequest.getParameter("accrualRate")).thenReturn("10");
        when(mockHttpServletRequest.getParameter("salesForceText")).thenReturn("Test");
        when(mockHttpServletRequest.getParameter("startDate")).thenReturn("10/22/2012");
        when(mockHttpServletRequest.getParameter("endDate")).thenReturn("11/22/2013");
        assertThat(homeController.postDate(mockHttpServletRequest).getViewName(), is("home"));
    }

    @Test
    public void get_shouldReturnHomeView() {
        HomeController homeController = new HomeController(mockEmployeeService, mockSalesForceParserService, mockVacationCalculatorService, mockAccrualRateCalculator, mockDateParserService, mockPersonalDaysCalculator);
        assertThat(homeController.get(), is("home"));
    }

    @Test
    public void shouldInteractWithSalesForceParserServiceAndEmployeeService() throws Exception {

        HomeController homeController = new HomeController(mockEmployeeService, mockSalesForceParserService, mockVacationCalculatorService, mockAccrualRateCalculator, mockDateParserService, mockPersonalDaysCalculator);

        when(mockHttpServletRequest.getParameter("rolloverdays")).thenReturn("1");
        when(mockHttpServletRequest.getParameter("accrualRate")).thenReturn("10");
        when(mockHttpServletRequest.getParameter("salesForceText")).thenReturn("Test");
        when(mockHttpServletRequest.getParameter("startDate")).thenReturn("10/22/2012");
        when(mockHttpServletRequest.getParameter("endDate")).thenReturn("11/22/2013");

        when(mockVacationCalculatorService.getVacationDays(any(Employee.class), any(AccrualRateCalculator.class), any(LocalDate.class))).thenReturn(20d);
        when(mockPersonalDaysCalculator.calculatePersonalDays(any(Employee.class), any(LocalDate.class), any(LocalDate.class))).thenReturn(0d);

        homeController.postDate(mockHttpServletRequest);

        verify(mockSalesForceParserService, times(1)).extractVacationDaysUsed(anyString());
        verify(mockEmployeeService, times(1)).createEmployee(any(LocalDate.class), anyString(), any(Map.class), anyDouble(), anyString());
        verify(mockDateParserService, times(2)).parse(anyString());
    }

}
