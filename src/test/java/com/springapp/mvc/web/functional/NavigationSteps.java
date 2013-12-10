package com.springapp.mvc.web.functional;

import org.jbehave.core.annotations.*;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class NavigationSteps extends UserJourneyBase {

    WebDriver driver;

    @BeforeScenario
    public void openBrowser() {
        driver = super.getDriver();
        driver.get("http://localhost:8080/");
    }

    @AfterStories
    public void closeBrowser() {
        driver.quit();
    }

    @Given("I started two weeks ago")
    public void iEnterStartDateTwoWeeksAgo(){
        LocalDate twoWeeksAgo = new LocalDate().minusWeeks(2);

        String date = twoWeeksAgo.getMonthOfYear() + "/" +
                twoWeeksAgo.getDayOfMonth() + "/" +
                twoWeeksAgo.getYear();

        sendDateToStartDateField(date);
    }

    @Given("I started on January 1 of this year")
    public void iStartedOnJanuary1(){
        int thisYear = new LocalDate().getYear();

        String date = "01/01/" + thisYear;

        sendDateToStartDateField(date);
    }

    @When("I request my number of vacation days as of today")
    public void iClickSubmit() {
        WebElement submitButton = driver.findElement(By.id("submit-button"));
        submitButton.click();
    }

    @Then("the number of vacation days I have is my daily accrual rate times 14")
    public void vacationDaysAccruedShouldBeTwoWeeksWorthOfAccrual(){
        String vacationDays = getTextFromFieldByID("vacation-days");
        String twoWeeksAccrued = String.valueOf(Math.round((10 / 365.25) * 7 * 2*100)/100);


        assertThat(vacationDays, containsString(twoWeeksAccrued));
    }

    @Then("I should have 7 personal days")
    public void shouldHaveSevenPersonalDays(){
        String personalDays = getTextFromFieldByID("personal-days");
        String sevenDays = "7";

        assertThat(personalDays, containsString(sevenDays));
    }

    private void sendDateToStartDateField(String date) {
        WebElement startDateField = driver.findElement(By.id("start-date-picker"));
        startDateField.sendKeys(date);
    }

    private String getTextFromFieldByID(String id){
        WebElement form = driver.findElement(By.id(id));
        return form.getText();
    }
}