package com.springapp.mvc.web.service;

public class SalesForceParserService {

    public double parse(String salesForceText) {
        String[] textLines = salesForceText.split("\n");
        String vacationLine = "";

        for (int i = 0; i < textLines.length; i ++) {
            if (i != textLines.length - 1){
                if (textLines[i].contains("vacation")) {
                    vacationLine = textLines[i + 1];
                    vacationLine = vacationLine.trim();
                    return Double.parseDouble(vacationLine);
                }
            }
        }

        return 0d;

    }

}
