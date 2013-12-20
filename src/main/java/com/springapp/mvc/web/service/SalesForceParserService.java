package com.springapp.mvc.web.service;

import com.springapp.mvc.web.model.Constants;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SalesForceParserService {

    private final DateParserService dateParser;

    @Autowired
    public SalesForceParserService(DateParserService dateParser) {
        this.dateParser = dateParser;
    }

    public Map<LocalDate, Double> extractVacationDaysUsed(String salesForceText) {
        return extractDatesAndHoursFromSalesForceText(salesForceText, Constants.VACATION_DAY_CODES);
    }

    public double extractPersonalDaysUsed(String salesForceText, LocalDate date) {
        double personalDaysUsed = 0.0;
        Map<LocalDate, Double> personalDaysMap = extractDatesAndHoursFromSalesForceText(salesForceText, Constants.PERSONAL_DAY_CODES);

        for (LocalDate dateOfPersonalDay : personalDaysMap.keySet()) {
            if (dateOfPersonalDay.getYear() == date.getYear()) {
                personalDaysUsed += personalDaysMap.get(dateOfPersonalDay) / 8;
            }
        }

        return personalDaysUsed;
    }

    private Map<LocalDate, Double> extractDatesAndHoursFromSalesForceText(String salesForceText, List<String> listOfTimeOffCodes) {
        Map<String, Double> daysAndHours = new HashMap<String, Double>();

        List<String> subProjects = Arrays.asList(salesForceText.split("Sub-Project Name"));

        for (String subProject : subProjects) {
            List<String> subProjectLines = Arrays.asList(subProject.split("\n"));

            if (isATimeOffCode(subProjectLines.get(0), listOfTimeOffCodes) && subProjectLines.size() > 2) {
                for (int line = 2; line < subProjectLines.size() - 1; line++) {

                    if(subProjectLines.get(line).contains("Grand Totals")){
                        break;
                    }

                    List<String> parsedInformation = Arrays.asList(subProjectLines.get(line).split("\t"));

                    String date = parsedInformation.get(3);
                    double hours = Double.parseDouble(parsedInformation.get(5));

                    if (daysAndHours.get(date) == null) {
                        daysAndHours.put(date, hours);
                    } else {
                        double oldNumberOfHours = daysAndHours.get(date);
                        daysAndHours.put(date, hours + oldNumberOfHours);
                    }
                }
            }
        }

        return convertStringsToLocalDates(daysAndHours);
    }

    private boolean isATimeOffCode(String stringToCheck, List<String> listOfTimeOffCodes) {
        boolean isACode = false;

        for (String code : listOfTimeOffCodes) {
            if (stringToCheck.contains(code)) {
                isACode = true;
                break;
            }
        }

        return isACode;
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
