package com.springapp.mvc.web.functional;

import com.springapp.mvc.web.model.Constants;
import org.jbehave.core.annotations.*;
import org.joda.time.LocalDate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class NavigationSteps extends UserJourneyBase {

    public static final String DATE_PICKER_MONTH =
            "//select[@class='ui-datepicker-month']";
    public static final String DATE_PICKER_YEAR =
            "//select[@class='ui-datepicker-year']";

    public static final String START_DATE_FIELD_ID = "start-date-picker";
    public static final String ROLLOVER_DAYS_FIELD_ID = "rolloverdays-field";
    public static final String PERSONAL_DAYS_FIELD_ID = "personal-days";
    public static final String SUBMIT_BUTTON_ID = "submit-button";
    public static final String VACATION_DAYS_ID = "vacation-days";

    WebDriver driver;

    @BeforeScenario
    public void openBrowser() {
        driver = super.getDriver();
    }

    @AfterScenario
    public void clearRolloverDaysField() {
        WebElement rolloverDaysField = driver.findElement(By.id(ROLLOVER_DAYS_FIELD_ID));
        rolloverDaysField.clear();
    }

    @AfterStories
    public void closeBrowser() {
        driver.quit();
    }

    @Given("I started <days> days ago")
    public void iEnterStartDate(@Named("days") int days) {
        LocalDate startDate = new LocalDate().minusDays(days);

        pickStartDate(startDate.getYear(), startDate.getMonthOfYear(), startDate.getDayOfMonth());

        enterRolloverDays(0);
    }

    @Given("I started on January 1 of this year")
    public void iStartedOnJanuary1() {
        pickStartDate(new LocalDate().getYear(), 1, 1);
        enterRolloverDays(0);
    }

    @When("I request my number of vacation days as of today")
    public void iClickSubmit() {
        WebElement submitButton = driver.findElement(By.id(SUBMIT_BUTTON_ID));
        submitButton.click();
    }

    @Then("I should have <days> personal days")
    public void shouldHaveSevenPersonalDays(@Named("days") double days) {
        double personalDays = Double.parseDouble(getTextFromFieldByID(PERSONAL_DAYS_FIELD_ID));

        assertThat(personalDays, is(days));
    }

    @Then("the number of vacation days I have is my daily accrual rate times <days>")
    public void vacationDaysAccruedShouldBeTwoWeeksWorthOfAccrual(@Named("days") int days) {
        double actualVacationDays = Double.parseDouble(getTextFromFieldByID(VACATION_DAYS_ID));
        double expectedVacationDays = Math.round((Constants.DEFAULT_ACCRUAL_RATE / Constants.YEAR_IN_DAYS) * days * 100) / 100.0;

        assertThat(actualVacationDays, is(expectedVacationDays));
    }

    private void pickStartDate(int year, int month, int day) {
        WebElement startDateField = driver.findElement(By.id(START_DATE_FIELD_ID));
        startDateField.click();

        Select yearSelect = new Select(driver.findElement(By.xpath(DATE_PICKER_YEAR)));
        yearSelect.selectByValue(String.valueOf(year));

        Select monthSelect = new Select(driver.findElement(By.xpath(DATE_PICKER_MONTH)));
        monthSelect.selectByValue(String.valueOf(month - 1));

        WebElement dateWidget = driver.findElement(By.id("ui-datepicker-div"));
        List<WebElement> rows = dateWidget.findElements(By.tagName("tr"));
        List<WebElement> columns = dateWidget.findElements(By.tagName("td"));

        for (WebElement cell : columns) {
            if (cell.getText().equals(String.valueOf(day))) {
                cell.findElement(By.linkText(String.valueOf(day))).click();
                break;
            }
        }
    }

    private void enterRolloverDays(double rolloverDays) {
        String rolloverDaysAsString = Double.toString(rolloverDays);
        WebElement rolloverDaysField = driver.findElement(By.id(ROLLOVER_DAYS_FIELD_ID));
        rolloverDaysField.sendKeys(rolloverDaysAsString);
    }

    private String getTextFromFieldByID(String id) {
        WebElement form = driver.findElement(By.id(id));
        return form.getText();
    }
}