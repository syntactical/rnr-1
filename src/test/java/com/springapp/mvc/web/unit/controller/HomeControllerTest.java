package com.springapp.mvc.web.unit.controller;

import com.springapp.mvc.web.controller.HomeController;
import com.springapp.mvc.web.model.Calculator;
import com.springapp.mvc.web.model.Employee;
import com.springapp.mvc.web.service.CalculatorService;
import com.springapp.mvc.web.service.EmployeeService;
import com.springapp.mvc.web.service.SalesForceParserService;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class HomeControllerTest {
    HttpServletRequest mockHttpServletRequest;
    SalesForceParserService mockSalesForceParserService;
    EmployeeService mockEmployeeService;
    Calculator mockCalculator;

    @Before
    public void setUp() throws Exception {
        mockHttpServletRequest = mock(HttpServletRequest.class);
        mockSalesForceParserService = mock(SalesForceParserService.class);
        mockEmployeeService = mock(EmployeeService.class);
        mockCalculator = mock(Calculator.class);
    }

    @Test
    public void get_shouldReturnHomeView() {
        HomeController homeController = new HomeController(mockEmployeeService, mockSalesForceParserService, mockCalculator);
        assertThat(homeController.get(), is("home"));
    }

    @Test
    public void shouldInteractWithSalesForceParserServiceAndEmployeeService() throws Exception {

        HomeController homeController = new HomeController(mockEmployeeService, mockSalesForceParserService, mockCalculator);

        when(mockHttpServletRequest.getParameter("rolloverdays")).thenReturn("1");
        when(mockHttpServletRequest.getParameter("accrualRate")).thenReturn("10");
        when(mockHttpServletRequest.getParameter("salesForceText")).thenReturn("Test");
        when(mockHttpServletRequest.getParameter("startDate")).thenReturn("10/22/2012");

        when(mockCalculator.getVacationBasedOnIntervals(any(Employee.class), any(LocalDate.class))).thenReturn(20d);

        homeController.postDate(mockHttpServletRequest);

        verify(mockSalesForceParserService, times(1)).parse(anyString());
        verify(mockEmployeeService, times(1)).createEmployee(anyString(), anyString(), any(HashMap.class), anyString());
    }

}
