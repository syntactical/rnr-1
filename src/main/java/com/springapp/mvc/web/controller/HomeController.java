package com.springapp.mvc.web.controller;

import com.springapp.mvc.web.model.AccrualRateCalculator;
import com.springapp.mvc.web.model.PersonalDaysCalculator;
import com.springapp.mvc.web.model.VacationCalculator;
import com.springapp.mvc.web.model.Employee;
import com.springapp.mvc.web.service.DateParserService;
import com.springapp.mvc.web.service.EmployeeService;
import com.springapp.mvc.web.service.SalesForceParserService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

@Controller
@RequestMapping("/")
public class HomeController {

    private final EmployeeService employeeService;
    private final SalesForceParserService salesForceParserService;
    private final VacationCalculator vacationCalculator;
    private final AccrualRateCalculator accrualRateCalculator;
    private final DateParserService dateParserService;
    private final PersonalDaysCalculator personalDaysCalculator;

    @Autowired
    public HomeController(EmployeeService employeeService, SalesForceParserService salesForceParserService,
                          VacationCalculator vacationCalculator, AccrualRateCalculator accrualRateCalculator,
                          DateParserService dateParserService, PersonalDaysCalculator personalDaysCalculator) {
        this.employeeService = employeeService;
        this.salesForceParserService = salesForceParserService;
        this.vacationCalculator = vacationCalculator;
        this.accrualRateCalculator = accrualRateCalculator;
        this.dateParserService = dateParserService;
        this.personalDaysCalculator = personalDaysCalculator;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String get() {
        return "home";
    }

    @RequestMapping(value = "/vacationDays", method = RequestMethod.POST)
    public ModelAndView postDate(HttpServletRequest request) throws IOException, ParseException {

        String rollover = request.getParameter("rolloverdays");
        String accrualRate = request.getParameter("accrualRate");
        String salesForceText = request.getParameter("salesForceText");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        LocalDate convertedStartDate = dateParserService.parse(startDate);
        LocalDate convertedEndDate = dateParserService.parse(endDate);

        Map<LocalDate, Double> parsedVacationDays = salesForceParserService.extractVacationDaysUsed(salesForceText);
        double personalDaysTaken = salesForceParserService.extractPersonalDaysUsed(salesForceText, convertedEndDate);

        Employee employee = employeeService.createEmployee(convertedStartDate, rollover, parsedVacationDays, personalDaysTaken, accrualRate);

        double vacationDays = vacationCalculator.getVacationDays(employee, accrualRateCalculator, convertedEndDate);
        double personalDays = personalDaysCalculator.calculatePersonalDays(employee, convertedStartDate, convertedEndDate);

        return showVacationDays(vacationDays, rollover, accrualRate, salesForceText, startDate, endDate);
    }

    private ModelAndView showVacationDays(Double vacationDays, String rollover, String accrualRate, String salesForceText, String startDate, String endDate) {
        ModelMap model = new ModelMap();
        model.put("days", roundToNearestHundredth(vacationDays));
        model.put("startDate", startDate);
        model.put("endDate", endDate);
        model.put("rollover", rollover);
        model.put("accrualRate", accrualRate);
        model.put("salesForceText", salesForceText);

        return new ModelAndView("home", "postedValues", model);
    }

    private Double roundToNearestHundredth(Double number){
        return (double) Math.round(number*100)/100;
    }
}