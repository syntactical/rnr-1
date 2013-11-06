package com.springapp.mvc.web.unit.service;

import com.springapp.mvc.web.service.DateParserService;
import com.springapp.mvc.web.service.SalesForceParserService;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class SalesForceParserServiceTest {

    HashMap<LocalDate, Double> emptyHashMap;
    private DateParserService mockDateParser;
    private SalesForceParserService salesForceParserService;

    private static final String FIRST_DATE = "7/29/2013";
    private static final String SECOND_DATE = "8/5/2013";
    private static final String THIRD_DATE = "8/12/2013";
    private static final String FOURTH_DATE = "9/2/2013";

    public static final String SAMPLE_SALES_FORCE_TEXT = "\n" +
            "Filtered By:   Edit \n" +
            "   \tResource: Full Name equals Benjamin Davis,Davis Benjamin Clear \n" +
            "   \tAND Project Name contains sick Clear \n" +
            " \tProject Name\tTimecard Id\tStart Date\tEnd Date\tTotal Hours\tTotal Days\n" +
            "\tSub-Project Name: Annual lv; vacation (4 records)\n" +
            " \t \t \t \t \t \t11.50\n" +
            " \tNon-sick leave\tTCH-07-21-2013-016842\t" + FIRST_DATE + "\t8/4/2013\t28.00\t \n" +
            " \tNon-sick leave\tTCH-07-21-2013-016843\t" + SECOND_DATE + "\t8/11/2013\t40.00\t \n" +
            " \tNon-sick leave\tTCH-07-21-2013-016844\t" + THIRD_DATE + "\t8/18/2013\t8.00\t \n" +
            " \tNon-sick leave\tTCH-09-04-2013-044182\t" + FOURTH_DATE + "\t9/8/2013\t16.00\t \n" +
            "\tSub-Project Name: Maternity/paternity lv paid (1 record)\n" +
            " \t \t \t \t \t \t1.00\n" +
            " \tNon-sick leave\tTCH-12-21-2012-000613\t12/31/2012\t1/6/2013\t8.00\t \n" +
            "\tSub-Project Name: Personal/Sick (US/CAN only) (5 records)\n" +
            " \t \t \t \t \t \t7.00\n" +
            " \tSick leave\tTCH-02-25-2013-001699\t2/18/2013\t2/24/2013\t4.00\t \n" +
            " \tSick leave\tTCH-03-28-2013-002225\t3/25/2013\t3/31/2013\t20.00\t \n" +
            " \tSick leave\tTCH-04-22-2013-003111\t4/15/2013\t4/21/2013\t8.00\t \n" +
            " \tSick leave\tTCH-07-12-2013-011704\t" + FIRST_DATE + "\t8/4/2013\t12.00\t \n" +
            " \tSick leave\tTCH-07-21-2013-016841\t7/22/2013\t7/28/2013\t12.00\t \n" +
            "\tSub-Project Name: Public holiday (7 records)\n" +
            " \t \t \t \t \t \t5.00\n" +
            " \tNon-sick leave\tTCH-12-21-2012-000612\t12/31/2012\t1/6/2013\t8.00\t \n" +
            " \tNon-sick leave\tTCH-01-25-2013-001132\t1/21/2013\t1/27/2013\t8.00\t \n" +
            " \tNon-sick leave\tTCH-06-02-2013-004688\t5/27/2013\t6/2/2013\t4.00\t \n" +
            " \tNon-sick leave\tTCH-06-07-2013-004853\t6/3/2013\t6/9/2013\t4.00\t \n" +
            " \tNon-sick leave\tTCH-07-06-2013-009895\t7/1/2013\t7/7/2013\t4.00\t \n" +
            " \tNon-sick leave\tTCH-07-21-2013-016840\t7/22/2013\t7/28/2013\t4.00\t \n" +
            " \tNon-sick leave\tTCH-09-04-2013-044180\t" + FOURTH_DATE + "\t9/8/2013\t8.00\t \n" +
            " \tGrand Totals (17 records)\n" +
            " \t \t \t \t \t \t24.50";

    @Before
    public void setUp() throws Exception {
        emptyHashMap = new HashMap<LocalDate, Double>();
        mockDateParser = mock(DateParserService.class);
        salesForceParserService = new SalesForceParserService(mockDateParser);
    }

    @Test
    public void shouldParseSalesForceExampleTextAndReturnTheNumberOfVacationDays() throws Exception {

        LocalDate vacationDate = new LocalDate(2013, 7, 29);

        when(mockDateParser.parse(anyString()))
                .thenReturn(new LocalDate(2013, 7, 29),new LocalDate(2013, 8, 5))
                .thenReturn(new LocalDate(2013, 8, 12))
                .thenReturn(new LocalDate(2013, 9, 2));

        HashMap<LocalDate, Double> vacationDaysUsed = salesForceParserService.parse(SAMPLE_SALES_FORCE_TEXT);

        assertThat(vacationDaysUsed.get(vacationDate), is(28.00));
        verify(mockDateParser, times(4)).parse(anyString());

    }

    @Test
    public void shouldReturnEmptyHashMapIfNoLineContainsVacation() throws Exception {
        String salesForceText = "hi";

        HashMap<LocalDate, Double> actualHashMap = salesForceParserService.parse(salesForceText);

        assertThat(actualHashMap, is(emptyHashMap));
    }

    @Test
    public void shouldReturnEmptyHashMapIfLastLineContainsFirstInstanceOfWordVacation() throws Exception {
        String salesForceText = "vacation";

        HashMap<LocalDate, Double> vacationDayMap = salesForceParserService.parse(salesForceText);

        assertThat(vacationDayMap, is(emptyHashMap));
    }

    @Test
    public void shouldReturnEmptyHashMapIfRandomStringDoesNotContainNonSickLeave() throws Exception {
        String salesForceText = "laksjdflkasdjf" + "\nlaksjdflkasdjf" + "\nlaksjdflkasdjf" + "\nlaksjdflkasdjf" +
                "vacation" + "\nlaksjdflkasdjf" + "\nlaksjdflkasdjf" + "\nlaksjdflkasdjf";

        HashMap<LocalDate, Double> vacationDayMap = salesForceParserService.parse(salesForceText);

        assertThat(vacationDayMap, is(emptyHashMap));
    }

    @Test
    public void shouldReturnEmptyHashMapIfEmptyString() throws Exception {
        String salesForceText = "";

        HashMap<LocalDate, Double> vacationDayMap = salesForceParserService.parse(salesForceText);

        assertThat(vacationDayMap, is(emptyHashMap));
    }


}
