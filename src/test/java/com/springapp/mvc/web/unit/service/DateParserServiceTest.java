package com.springapp.mvc.web.unit.service;

import com.springapp.mvc.web.service.DateParserService;
import org.joda.time.LocalDate;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class DateParserServiceTest {
    @Test
    public void shouldReturnLocalDateForDayMonthYearsString() throws Exception {
        String stringDate = "10/11/2011";

        assertThat(new DateParserService().parse(stringDate).toString(), is(new LocalDate(2011,10,11).toString()));

    }
}
