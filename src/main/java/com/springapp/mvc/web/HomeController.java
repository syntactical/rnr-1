package com.springapp.mvc.web;

import model.Calculator;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import service.CalculatorService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class HomeController {

    private CalculatorService calculatorService;

    public HomeController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    public HomeController() {
        this.calculatorService = new CalculatorService();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getDate() {
        return "home";
    }

    @RequestMapping(value = "/vacationDays", method = RequestMethod.POST)
    public ModelAndView postDate(HttpServletRequest request) throws IOException {
        double rolloverDays = Double.parseDouble(request.getParameter("rolloverdays"));
        String dateAsString = request.getParameter("user");
        double vacationDays = calculatorService.calculateVacationDays(rolloverDays);
        return showSuccess(vacationDays);
    }

    private ModelAndView showSuccess(Double vacationDays) {
        ModelMap model = new ModelMap();
        model.put("days", vacationDays);
        return new ModelAndView("vacay", "postedValues", model);
    }
}