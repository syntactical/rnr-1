package com.springapp.mvc.web;

import model.Calculator;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class HomeController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getDate(Model model) {
        return new ModelAndView("home", "command", model);
    }

    @RequestMapping(value = "/vacationDays", method = RequestMethod.POST)
    public ModelAndView postDate(HttpServletRequest request) throws IOException {
        String dateAsString = request.getParameter("user");
        Calculator calc = new Calculator();
        DateTime startDate = calc.convertStringToDateTime(dateAsString);
        Double vacationDays = calc.calculateVacationDays(startDate, new DateTime());
        return showSuccess(dateAsString, vacationDays);
    }

    private ModelAndView showSuccess(String startDate, Double vacationDays) {
        ModelMap model = new ModelMap();
        model.put("date", startDate);
        model.put("days", vacationDays);
        return new ModelAndView("vacay", "postedValues", model);
    }
}