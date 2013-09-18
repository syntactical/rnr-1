package com.springapp.mvc.web;

import model.Calculator;
import model.TDate;
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
        TDate startDate = new TDate(request.getParameter("user"));
        Calculator calc = new Calculator();
        Double vacationDays = calc.getVacationDaysBasedOnMonth(startDate);
        return showSuccess(startDate, vacationDays);
    }

    private ModelAndView showSuccess(TDate date, Double vacationDays) {
        ModelMap model = new ModelMap();
        model.put("date", date.getDate());
        model.put("days", vacationDays);
        return new ModelAndView("vacay", "postedValues", model);
    }

}

