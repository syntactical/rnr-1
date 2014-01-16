package com.springapp.mvc.web.functional;

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

    WebDriver driver;

    @BeforeScenario
    public void openBrowser() {
        driver = super.getDriver();
    }

    @AfterStories
    public void closeBrowser() {
        driver.quit();
    }

    @Given("I started two weeks ago")
    public void iEnterStartDateTwoWeeksAgo() {
        LocalDate twoWeeksAgo = new LocalDate().minusWeeks(2);

        pickStartDate(twoWeeksAgo.getYear(), twoWeeksAgo.getMonthOfYear(), twoWeeksAgo.getDayOfMonth());

        enterRolloverDays(0);
    }

    @Given("I started on January 1 of this year")
    public void iStartedOnJanuary1() {
        pickStartDate(new LocalDate().getYear(), 1, 1);
        enterRolloverDays(0);
    }

    @When("I request my number of vacation days as of today")
    public void iClickSubmit() {
        WebElement submitButton = driver.findElement(By.id("submit-button"));
        submitButton.click();
    }

    @Then("the number of vacation days I have is my daily accrual rate times 14")
    public void vacationDaysAccruedShouldBeTwoWeeksWorthOfAccrual(@Named("days") double days) {
        double vacationDays = Double.parseDouble(getTextFromFieldByID("vacation-days"));
        double twoWeeksAccrued = Math.round((10 / 365.25) * days * 100) / 100;

        assertThat(vacationDays, is(twoWeeksAccrued));
    }

    @Then("I should have <days> personal days")
    public void shouldHaveSevenPersonalDays(@Named("days") double days) {
        double personalDays = Double.parseDouble(getTextFromFieldByID("personal-days"));

        assertThat(personalDays, is(days));
    }

    private void pickStartDate(int year, int month, int day) {
        WebElement startDateField = driver.findElement(By.id("start-date-picker"));
        startDateField.click();

        Select yearSelect = new Select(driver.findElement(By.xpath(DATE_PICKER_YEAR)));
        yearSelect.selectByValue(String.valueOf(year));

        Select monthSelect = new Select(driver.findElement(By.xpath(DATE_PICKER_MONTH)));
        monthSelect.selectByValue(String.valueOf(month - 1));

        WebElement dateWidget = driver.findElement(By.id("ui-datepicker-div"));
        List<WebElement> rows=dateWidget.findElements(By.tagName("tr"));
        List<WebElement> columns = dateWidget.findElements(By.tagName("td"));

        for (WebElement cell: columns){
            if (cell.getText().equals(String.valueOf(day))){
                cell.findElement(By.linkText(String.valueOf(day))).click();
                break;
            }
        }
    }

    private void enterRolloverDays(double rolloverDays){
        String rolloverDaysAsString = Double.toString(rolloverDays);
        WebElement rolloverDaysField = driver.findElement(By.id("rolloverdays-field"));
        rolloverDaysField.sendKeys(rolloverDaysAsString);
    }

    private String getTextFromFieldByID(String id) {
        WebElement form = driver.findElement(By.id(id));
        return form.getText();
    }
}