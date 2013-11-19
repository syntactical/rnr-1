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

    private int findLine(String stringToFind, List<String> listOfStrings) {
        for (String line : listOfStrings) {
            if (line.contains(stringToFind)) {
                return listOfStrings.indexOf(line);
            }
        }
        return -1;
    }

    public Map<LocalDate, Double> parse(String salesForceText) {
        Map<String, Double> vacationDaysAndHours = new HashMap<String, Double>();

        List<String> textLines = Arrays.asList(salesForceText.split("\n"));
        int indexOfVacationLine = findLine("vacation", textLines);
        boolean moreLinesToRead = (textLines.size() - findLine("vacation", textLines)) > 1;

        if (salesForceText.contains("vacation") && moreLinesToRead) {
            int vacationInformation = indexOfVacationLine + 2;

            while (textLines.get(vacationInformation).contains("Non-sick leave")) {
                List<String> parsedVacationInformation = Arrays.asList(textLines.get(vacationInformation).split("\t"));

                String date = parsedVacationInformation.get(3);
                double hours = Double.parseDouble(parsedVacationInformation.get(5));

                vacationDaysAndHours.put(date, hours);

                vacationInformation++;
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
