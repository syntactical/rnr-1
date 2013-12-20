package com.springapp.mvc.web.unit.service;

import com.springapp.mvc.web.service.DateParserService;
import com.springapp.mvc.web.service.SalesForceParserService;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class SalesForceParserServiceTest {

    Map<LocalDate, Double> emptyMap;
    private DateParserService mockDateParser;
    private SalesForceParserService salesForceParserService;

    private LocalDate DATE_IN_2013;

    public static final String SAMPLE_SALES_FORCE_TEXT = "\n" +
            "Filtered By:   Edit \n" +
            "   \tResource: Full Name equals Benjamin Davis,Davis Benjamin Clear \n" +
            "   \tAND Project Name contains sick Clear \n" +
            " \tProject Name\tTimecard Id\tStart Date\tEnd Date\tTotal Hours\tTotal Days\n" +
            "\tSub-Project Name: Annual lv; vacation (4 records)\n" +
            " \t \t \t \t \t \t11.50\n" +
            " \tNon-sick leave\tTCH-07-21-2013-016842\t7/29/2013\t8/4/2013\t28.00\t \n" +
            " \tNon-sick leave\tTCH-07-21-2013-016843\t8/5/2013\t8/11/2013\t40.00\t \n" +
            " \tNon-sick leave\tTCH-07-21-2013-016844\t8/12/2013\t8/18/2013\t8.00\t \n" +
            " \tNon-sick leave\tTCH-09-04-2013-044182\t9/2/2013\t9/8/2013\t16.00\t \n" +
            "\tSub-Project Name: Personal/Sick (US/CAN only) (5 records)\n" +
            " \t \t \t \t \t \t7.00\n" +
            " \tSick leave\tTCH-02-25-2013-001699\t2/18/2013\t2/24/2013\t4.00\t \n" +
            " \tSick leave\tTCH-03-28-2013-002225\t3/25/2013\t3/31/2013\t20.00\t \n" +
            " \tSick leave\tTCH-04-22-2013-003111\t4/15/2013\t4/21/2013\t8.00\t \n" +
            " \tSick leave\tTCH-07-12-2013-011704\t7/29/2013\t8/4/2013\t12.00\t \n" +
            " \tSick leave\tTCH-07-21-2013-016841\t7/22/2013\t7/28/2013\t12.00\t \n" +
            "\tSub-Project Name: Public holiday (7 records)\n" +
            " \t \t \t \t \t \t5.00\n" +
            " \tNon-sick leave\tTCH-12-21-2012-000612\t12/31/2012\t1/6/2013\t8.00\t \n" +
            " \tNon-sick leave\tTCH-01-25-2013-001132\t1/21/2013\t1/27/2013\t8.00\t \n" +
            " \tNon-sick leave\tTCH-06-02-2013-004688\t5/27/2013\t6/2/2013\t4.00\t \n" +
            " \tNon-sick leave\tTCH-06-07-2013-004853\t6/3/2013\t6/9/2013\t4.00\t \n" +
            " \tNon-sick leave\tTCH-07-06-2013-009895\t7/1/2013\t7/7/2013\t4.00\t \n" +
            " \tNon-sick leave\tTCH-07-21-2013-016840\t7/22/2013\t7/28/2013\t4.00\t \n" +
            " \tNon-sick leave\tTCH-09-04-2013-044180\t9/2/2013\t9/8/2013\t8.00\t \n" +
            " \tGrand Totals (17 records)\n" +
            " \t \t \t \t \t \t24.50";

    public static final String SAMPLE_SALES_FORCE_TEXT_WITH_ALTERNATE_VACATION_CODES = "\n" +
            "Filtered By:   Edit \n" +
            "   \tResource: Full Name equals Benjamin Davis,Davis Benjamin Clear \n" +
            "   \tAND Project Name contains sick Clear \n" +
            " \tProject Name\tTimecard Id\tStart Date\tEnd Date\tTotal Hours\tTotal Days\n" +
            "\tSub-Project Name: Annual lv; vacation (4 records)\n" +
            " \t \t \t \t \t \t11.50\n" +
            " \tNon-sick leave\tTCH-07-21-2013-016842\t7/29/2013\t8/4/2013\t28.00\t \n" +
            " \tNon-sick leave\tTCH-07-21-2013-016843\t8/5/2013\t8/11/2013\t40.00\t \n" +
            " \tNon-sick leave\tTCH-07-21-2013-016844\t8/12/2013\t8/18/2013\t8.00\t \n" +
            " \tNon-sick leave\tTCH-09-04-2013-044182\t9/2/2013\t9/8/2013\t16.00\t \n" +
            "\tSub-Project Name: FMLA Personal time (US only) (1 record)\n" +
            " \t \t \t \t \t \t1.00\n" +
            " \tNon-sick leave\tTCH-12-21-2012-000613\t12/31/2012\t1/6/2013\t8.00\t \n" +
            "\tSub-Project Name: Dr, dentist ,antenatel (UK) (5 records)\n" +
            " \t \t \t \t \t \t7.00\n" +
            " \tSick leave\tTCH-02-25-2013-001699\t2/18/2013\t2/24/2013\t4.00\t \n" +
            " \tSick leave\tTCH-03-28-2013-002225\t3/25/2013\t3/31/2013\t20.00\t \n" +
            " \tSick leave\tTCH-04-22-2013-003111\t4/15/2013\t4/21/2013\t8.00\t \n" +
            " \tSick leave\tTCH-07-12-2013-011704\t7/29/2013\t8/4/2013\t12.00\t \n" +
            " \tSick leave\tTCH-07-21-2013-016841\t7/22/2013\t7/28/2013\t12.00\t \n" +
            "\tSub-Project Name: FMLA Vacation time (US only) (7 records)\n" +
            " \t \t \t \t \t \t5.00\n" +
            " \tNon-sick leave\tTCH-12-21-2012-000612\t12/31/2012\t1/6/2013\t8.00\t \n" +
            " \tNon-sick leave\tTCH-01-25-2013-001132\t1/21/2013\t1/27/2013\t8.00\t \n" +
            " \tNon-sick leave\tTCH-06-02-2013-004688\t5/27/2013\t6/2/2013\t4.00\t \n" +
            " \tNon-sick leave\tTCH-06-07-2013-004853\t6/3/2013\t6/9/2013\t4.00\t \n" +
            " \tNon-sick leave\tTCH-07-06-2013-009895\t7/1/2013\t7/7/2013\t4.00\t \n" +
            " \tNon-sick leave\tTCH-07-21-2013-016840\t7/22/2013\t7/28/2013\t4.00\t \n" +
            " \tNon-sick leave\tTCH-09-04-2013-044180\t9/2/2013\t9/8/2013\t8.00\t \n" +
            " \tGrand Totals (17 records)\n" +
            " \t \t \t \t \t \t24.50";

    @Before
    public void setUp() throws Exception {
        emptyMap = new HashMap<LocalDate, Double>();
        mockDateParser = mock(DateParserService.class);
        salesForceParserService = new SalesForceParserService(mockDateParser);

        DATE_IN_2013 = new LocalDate(2013, 1, 1);
    }

    @Test
    public void shouldParseSalesForceExampleTextAndReturnTheNumberOfVacationDays() throws Exception {

        LocalDate vacationDate = new LocalDate(2013, 7, 29);
        LocalDate anotherVacationDate = new LocalDate(2013, 9, 2);

        when(mockDateParser.parse(anyString()))
                .thenReturn(new LocalDate(2013, 7, 29))
                .thenReturn(new LocalDate(2013, 8, 5))
                .thenReturn(new LocalDate(2013, 8, 12))
                .thenReturn(new LocalDate(2013, 9, 2));

        Map<LocalDate, Double> vacationDaysUsed = salesForceParserService.extractVacationDaysUsed(SAMPLE_SALES_FORCE_TEXT);

        assertThat(vacationDaysUsed.get(vacationDate), is(28.00));
        assertThat(vacationDaysUsed.get(anotherVacationDate), is(16.00));
        verify(mockDateParser, times(4)).parse(anyString());

    }

    @Test
    public void shouldReturnNumberOfPersonalDaysTakenInGivenYear() {
        when(mockDateParser.parse(anyString()))
                .thenReturn(new LocalDate(2013, 2, 18))
                .thenReturn(new LocalDate(2013, 3, 31))
                .thenReturn(new LocalDate(2013, 4, 15))
                .thenReturn(new LocalDate(2013, 7, 29))
                .thenReturn(new LocalDate(2013, 7, 22));

        double expectedPersonalDaysUsed = salesForceParserService.extractPersonalDaysUsed(SAMPLE_SALES_FORCE_TEXT, DATE_IN_2013);

        assertThat(expectedPersonalDaysUsed, is(7.0));
        verify(mockDateParser, times(5)).parse(anyString());
    }

    @Test
    public void shouldTakeTimeFromMultipleSubProjectNames() {

        LocalDate vacationDateOne = new LocalDate(2013, 7, 29);
        LocalDate vacationDateTwo = new LocalDate(2013, 9, 2);
        LocalDate vacationDateThree = new LocalDate(2013, 5, 27);
        LocalDate vacationDateFour = new LocalDate(2013, 9, 2);
        LocalDate vacationDateFive = new LocalDate(2013, 1, 21);

        when(mockDateParser.parse(anyString()))
                .thenReturn(new LocalDate(2013, 7, 29))
                .thenReturn(new LocalDate(2013, 8, 5))
                .thenReturn(new LocalDate(2013, 8, 12))
                .thenReturn(new LocalDate(2013, 9, 2))
                .thenReturn(new LocalDate(2012, 12, 31))
                .thenReturn(new LocalDate(2013, 1, 21))
                .thenReturn(new LocalDate(2013, 5, 27))
                .thenReturn(new LocalDate(2013, 6, 3))
                .thenReturn(new LocalDate(2013, 7, 1))
                .thenReturn(new LocalDate(2013, 7, 22))
                .thenReturn(new LocalDate(2013, 9, 2));

        Map<LocalDate, Double> vacationDaysUsed = salesForceParserService.extractVacationDaysUsed(SAMPLE_SALES_FORCE_TEXT_WITH_ALTERNATE_VACATION_CODES);

        assertThat(vacationDaysUsed.size(), is(10));

        assertThat(vacationDaysUsed.get(vacationDateOne), is(28.00));
        assertThat(vacationDaysUsed.get(vacationDateTwo), is(16.00));
        assertThat(vacationDaysUsed.get(vacationDateThree), is(4.00));
        assertThat(vacationDaysUsed.get(vacationDateFour), is(16.00 + 8.00));
        assertThat(vacationDaysUsed.get(vacationDateFive), is(8.00));
    }

    @Test
    public void shouldReturnEmptyMapIfNoLineContainsVacation() throws Exception {
        String salesForceText = "hi";

        Map<LocalDate, Double> actualMap = salesForceParserService.extractVacationDaysUsed(salesForceText);

        assertThat(actualMap, is(emptyMap));
    }

    @Test
    public void shouldReturnEmptyMapIfLastLineContainsFirstInstanceOfWordVacation() throws Exception {
        String salesForceText = "vacation";

        Map<LocalDate, Double> vacationDayMap = salesForceParserService.extractVacationDaysUsed(salesForceText);

        assertThat(vacationDayMap, is(emptyMap));
    }

    @Test
    public void shouldReturnEmptyMapIfRandomStringDoesNotContainNonSickLeave() throws Exception {
        String salesForceText = "laksjdflkasdjf" + "\nlaksjdflkasdjf" + "\nlaksjdflkasdjf" + "\nlaksjdflkasdjf" +
                "vacation" + "\nlaksjdflkasdjf" + "\nlaksjdflkasdjf" + "\nlaksjdflkasdjf";

        Map<LocalDate, Double> vacationDayMap = salesForceParserService.extractVacationDaysUsed(salesForceText);

        assertThat(vacationDayMap, is(emptyMap));
    }

    @Test
    public void shouldReturnEmptyMapIfEmptyString() throws Exception {
        String salesForceText = "";

        Map<LocalDate, Double> vacationDayMap = salesForceParserService.extractVacationDaysUsed(salesForceText);

        assertThat(vacationDayMap, is(emptyMap));
    }

    @Test
    public void shouldReturnZeroPersonalDaysUsedIfGivenEmptyOrBadInput() {
        String emptySalesforceText = "";
        String badSalesforceText = "hey\nhi\nhello\n";
        String salesforceTextWithPersonalDayHeader = "Personal/Sick";

        assertThat(salesForceParserService.extractPersonalDaysUsed(emptySalesforceText, DATE_IN_2013), is(0.0));
        assertThat(salesForceParserService.extractPersonalDaysUsed(badSalesforceText, DATE_IN_2013), is(0.0));
        assertThat(salesForceParserService.extractPersonalDaysUsed(salesforceTextWithPersonalDayHeader, DATE_IN_2013), is(0.0));
    }
}
