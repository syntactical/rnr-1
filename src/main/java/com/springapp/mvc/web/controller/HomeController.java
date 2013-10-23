package com.springapp.mvc.web.controller;

import com.springapp.mvc.web.service.CalculatorService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/")
public class HomeController {

    private CalculatorService calculatorService;

    @Autowired
    public HomeController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
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

        Double vacationDays = calculatorService.calculateVacationDays(startDate, rollover, accrualRate, salesForceText);

        return showVacationDays(vacationDays);
    }

    private ModelAndView showVacationDays(Double vacationDays) {
        ModelMap model = new ModelMap();
        model.put("days", vacationDays);
        return new ModelAndView("vacay", "postedValues", model);
    }
}