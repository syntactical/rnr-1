package com.springapp.mvc.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.springapp.mvc.web.service.CalculatorService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
    public ModelAndView postDate(HttpServletRequest request) throws IOException {
        double rolloverDays = 0d;
        String rollover = request.getParameter("rolloverdays") ;
        if(!rollover.isEmpty()) rolloverDays = Double.parseDouble(rollover);
        String dateAsString = request.getParameter("startdate");
        double vacationDays = calculatorService.calculateVacationDays(rolloverDays, dateAsString);
        return showVacationDays(vacationDays);
    }

    private ModelAndView showVacationDays(Double vacationDays) {
        ModelMap model = new ModelMap();
        model.put("days", vacationDays);
        return new ModelAndView("vacay", "postedValues", model);
    }
}