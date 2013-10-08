package com.springapp.mvc.web.service;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Thoughtworker
 * Date: 9/27/13
 * Time: 1:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class CalculatorValidation {

    private int month;
    private int day;
    private String year;


    public HashMap verifyDate(String dateAsString) {
       parseDate(dateAsString);
       HashMap errors = new HashMap();

        if (month < 1 || month > 12) {
            errors.put("month", "Please enter a valid month.");
        }
        if (day < 1 || day > 31) {
            errors.put("day", "Please enter a valid day.");
        }
        if (year.length() != 4) {
            errors.put("year", "Please enter a valid year.");
        }
        return errors;
    }


    public boolean isSameYear(String dateAsString, int currentYear) {
        parseDate(dateAsString);
        Integer startYear = Integer.parseInt(this.year);

        return startYear==currentYear;
    }

    private void parseDate(String dateAsString){
        String[] dateFields = dateAsString.split("/");
        month = Integer.parseInt(dateFields[0]);
        day = Integer.parseInt(dateFields[1]);
        year = dateFields[2];

    }

}
