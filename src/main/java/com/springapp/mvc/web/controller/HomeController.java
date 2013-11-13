package com.springapp.mvc.web.controller;

import com.springapp.mvc.web.model.VacationCalculator;
import com.springapp.mvc.web.model.Employee;
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
import java.util.HashMap;

@Controller
@RequestMapping("/")
public class HomeController {

    private final EmployeeService employeeService;
    private final SalesForceParserService salesForceParserService;
    private final VacationCalculator vacationCalculator;

    @Autowired
    public HomeController(EmployeeService employeeService, SalesForceParserService salesForceParserService, VacationCalculator vacationCalculator) {
        this.employeeService = employeeService;
        this.salesForceParserService = salesForceParserService;
        this.vacationCalculator = vacationCalculator;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String get() {
        return "home";
    }

    @RequestMapping(value = "rnr/vacationDays", method = RequestMethod.POST)
    public ModelAndView postDate(HttpServletRequest request) throws IOException, ParseException {

        String rollover = request.getParameter("rolloverdays");
        String accrualRate = request.getParameter("accrualRate");
        String salesForceText = request.getParameter("salesForceText");
        String startDate = request.getParameter("startDate");


        HashMap<LocalDate, Double> parsedSalesForceData = salesForceParserService.parse(salesForceText);

        Employee employee = employeeService.createEmployee(startDate, rollover, parsedSalesForceData, accrualRate);

        double vacationDays = vacationCalculator.getVacationDays(employee, new LocalDate());

        return showVacationDays(vacationDays);
    }

    private ModelAndView showVacationDays(Double vacationDays) {
        ModelMap model = new ModelMap();
        model.put("days", vacationDays);
        return new ModelAndView("vacay", "postedValues", model);
    }
}