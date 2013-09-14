package model;

import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Thoughtworker
 * Date: 9/11/13
 * Time: 11:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class TWDate {

    private String date;
    private String[] parsedDate;


    public TWDate setDate(String date) {
        this.date = date;
        parsedDate = parseDate(date);
        return this;
    }

    public String getDate() {
        return date;
    }



    public Integer getMonth() {
        String month = parsedDate[0];

        return Integer.parseInt(month);
    }

    public Integer getDay() {
        String day = parsedDate[1];
        return Integer.parseInt(day);
    }

    public Integer getYear() {
        String year = parsedDate[2];
        return Integer.parseInt(year);
    }


    private String[] parseDate(String date) {
        Scanner sc = new Scanner(date);
        sc.useDelimiter("/");
        String[] dateInfo = new String[3];
        for (int i = 0; i < 3; i++) {
            dateInfo[i] = sc.next();
        }

        return dateInfo;
    }
}
