package model;

import java.util.Calendar;
import java.util.Date;

public class CurrentDateBuilder {
    public CurrentDateBuilder() {
    }

    public Calendar getCurrentDate() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}