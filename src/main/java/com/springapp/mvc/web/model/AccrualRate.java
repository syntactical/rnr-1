package com.springapp.mvc.web.model;

public enum AccrualRate {
    FIRST_YEAR(10 / 365d),
    LESS_THAN_THREE_YEARS(15 / 365d),
    LESS_THAN_SIX_YEARS(20 / 365d),
    SIX_YEARS_OR_MORE(25 / 365d);

    private final double rate;

    AccrualRate(double rate){
        this.rate = rate;
    }

    public double rate(){
        return rate;
    }
}
