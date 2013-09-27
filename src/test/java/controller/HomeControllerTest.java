package controller;

import com.springapp.mvc.web.HomeController;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import service.CalculatorService;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class HomeControllerTest {
    @Test
    public void getDate_shouldReturnHomeView() {
        HomeController homeController = new HomeController();
        assertThat(homeController.getDate(), is("home"));
    }

    @Test
    public void postDate_shouldUseCalculatorService() throws Exception {
        CalculatorService mockCalculatorService = mock(CalculatorService.class);
        HttpServletRequest mockHttpServletRequest = mock(HttpServletRequest.class);

        HomeController homeController = new HomeController(mockCalculatorService);
        when(mockHttpServletRequest.getParameter("rolloverdays")).thenReturn("1");

        homeController.postDate(mockHttpServletRequest);

        verify(mockCalculatorService, times(1)).calculateVacationDays(anyDouble(), anyDouble());
    }

}
