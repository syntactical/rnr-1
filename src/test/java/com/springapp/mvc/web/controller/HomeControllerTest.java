package com.springapp.mvc.web.controller;

import org.junit.Test;
import com.springapp.mvc.web.service.CalculatorService;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class HomeControllerTest {
    @Test
    public void get_shouldReturnHomeView() {
        HomeController homeController = new HomeController(mock(CalculatorService.class));
        assertThat(homeController.get(), is("home"));
    }

    @Test
    public void postDate_shouldUseCalculatorService() throws Exception {
        CalculatorService mockCalculatorService = mock(CalculatorService.class);
        HttpServletRequest mockHttpServletRequest = mock(HttpServletRequest.class);

        HomeController homeController = new HomeController(mockCalculatorService);
        when(mockHttpServletRequest.getParameter("rolloverdays")).thenReturn("1");

        homeController.postDate(mockHttpServletRequest);

        verify(mockCalculatorService, times(1)).calculateVacationDays(anyDouble(), anyString());
    }

}
