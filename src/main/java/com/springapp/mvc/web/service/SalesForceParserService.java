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

    public Map<LocalDate, Double> extractVacationDaysUsed(String salesForceText) {
        return extractDatesAndHoursFromSalesForceText(salesForceText, "vacation");
    }

    public double extractPersonalDaysUsed(String salesForceText, LocalDate date) {
        double personalDaysUsed = 0.0;
        Map<LocalDate, Double> personalDaysMap = extractDatesAndHoursFromSalesForceText(salesForceText, "Personal/Sick");

        for (LocalDate dateOfPersonalDay : personalDaysMap.keySet()){
            if (dateOfPersonalDay.getYear() == date.getYear()){
                personalDaysUsed += personalDaysMap.get(dateOfPersonalDay) / 8;
            }
        }

        return personalDaysUsed;
    }
    
    private Map<LocalDate, Double> extractDatesAndHoursFromSalesForceText(String salesForceText, String typeOfTimeOff){
        Map<String, Double> daysAndHours = new HashMap<String, Double>();

        List<String> subProjects = Arrays.asList(salesForceText.split("Sub-Project Name"));

        for (String subProject : subProjects) {
            List<String> subProjectLines = Arrays.asList(subProject.split("\n"));

            if (subProjectLines.get(0).contains(typeOfTimeOff) && subProjectLines.size() > 2) {
                for (int line = 2; line < subProjectLines.size() - 1; line++) {
                    List<String> parsedInformation = Arrays.asList(subProjectLines.get(line).split("\t"));

                    String date = parsedInformation.get(3);
                    double hours = Double.parseDouble(parsedInformation.get(5));

                    daysAndHours.put(date, hours);
                }
            }
        }

        return convertStringsToLocalDates(daysAndHours);
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
