package com.springapp.mvc.web.service;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesForceParserService {

    private final DateParserService dateParser;

    @Autowired
    public SalesForceParserService(DateParserService dateParser) {
        this.dateParser = dateParser;
    }

    public Map<LocalDate, Double> parse(String salesForceText) {
        Map<String, Double> vacationDaysAndHours = new HashMap<String, Double>();

        List<String> subProjects = Arrays.asList(salesForceText.split("Sub-Project Name"));

        for (String subProject : subProjects) {
            List<String> subProjectLines = Arrays.asList(subProject.split("\n"));

            if (subProjectLines.get(0).contains("vacation") && subProjectLines.size() > 2) {
                for (int line = 2; line < subProjectLines.size() - 1; line++) {
                    List<String> parsedVacationInformation = Arrays.asList(subProjectLines.get(line).split("\t"));

                    String date = parsedVacationInformation.get(3);
                    double hours = Double.parseDouble(parsedVacationInformation.get(5));

                    vacationDaysAndHours.put(date, hours);
                }
            }
        }

        return convertStringsToLocalDates(vacationDaysAndHours);
    }

    private Map<LocalDate, Double> convertStringsToLocalDates(Map<String, Double> mapWithStringDates) {
        Map<LocalDate, Double> localDatesAndDoubles = new HashMap<LocalDate, Double>();

        for (String date : mapWithStringDates.keySet()) {
            LocalDate parsedDate = dateParser.parse(date);
            localDatesAndDoubles.put(parsedDate, mapWithStringDates.get(date));
        }

        return localDatesAndDoubles;
    }
}
