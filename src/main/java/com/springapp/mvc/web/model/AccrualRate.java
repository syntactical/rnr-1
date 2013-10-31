package com.springapp.mvc.web.model;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import static org.joda.time.Days.daysBetween;

@Component
public class AccrualRate {
    private DateTime startDate;
    private String accrualRate;
    final int YEAR_IN_DAYS = 365;

    public AccrualRate() {
        this("");
    }

    public AccrualRate(String initialAccrualRate) {
        this.accrualRate = initialAccrualRate;
    }
    
    public AccrualRate givenIStarted(DateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public double calculate() {
        return accrualRate.isEmpty() ? calculateRate(startDate) : Math.max(Double.parseDouble(accrualRate), calculateRate(startDate))/360d;
    }

    public Double calculateRate(DateTime startDate) {
        int elapsedTime = daysBetween(startDate.toLocalDate(), new DateTime().toLocalDate()).getDays();

        if (elapsedTime < YEAR_IN_DAYS) {
            return  10.0/365;
        } else if (elapsedTime < 3 * YEAR_IN_DAYS) {
            return  15.0/365;
        } else if (elapsedTime < 6 * YEAR_IN_DAYS){
            return  20.0/365;
        } else {
            return 25.0/365;
        }
    }



}
