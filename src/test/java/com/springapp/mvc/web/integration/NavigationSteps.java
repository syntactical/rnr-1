package com.springapp.mvc.web.integration;

import org.jbehave.core.annotations.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class NavigationSteps {

    private WebDriver driver;

    @Given("I am a Thoughtworker")
    public void iAmAUser() {
    }

    @Given("I am a user going to rnr.thoughtworks.com")
    public void iAmAUserGoingToRnR() {
        driver.get("http://localhost:8080/");
    }

    @When("I go to rnr.thoughtworks.com")
    public void iGoToRnR() {
        driver.get("http://localhost:8080/");
    }

    @When("I select a date")
    public void iSelectDate() {
        WebElement month = driver.findElement(By.id("monthdropdown"));
        WebElement day = driver.findElement(By.id("daydropdown"));
        WebElement year = driver.findElement(By.id("yeardropdown"));
        month.click();
        day.click();
        year.click();
    }


    @When("I enter my start date prior to the current calendar year")
    public void iEnterStartDatePriorToCalendarYear() {
        WebElement startDateField = driver.findElement(By.id("startdate_field"));
        startDateField.sendKeys("01/01/1999");
    }

    @When("I enter my rollover days")
    public void iEnterMyRolloverDays() {
        WebElement rolloverDaysField = driver.findElement(By.id("rolloverdays_field"));
        rolloverDaysField.sendKeys("1");
    }

    @When("I click submit")
    public void iClickSubmit() {
        WebElement submitButton = driver.findElement(By.id("submit_button"));
        submitButton.click();
    }

    @Then("a banner should be visible")
    public void bannerShouldBeVisible() {
        WebElement banner = driver.findElement(By.className("headerimg"));
        assertTrue(banner.isDisplayed());
    }



    @Then("the form box should contain a date")
    public void dateShouldBeInForm() {
        WebElement form = driver.findElement(By.id("startdate_field"));
        String date = form.getText();
        assertTrue(date != null);
    }

    @Then("my vacation days are displayed")
    public void vacationDaysShouldBeInForm() {
        WebElement form = driver.findElement(By.id("vacationDays"));
        String vacationDays = form.getText();
        assertThat(vacationDays, containsString("Hey, your balance is"));
    }

    @BeforeScenario
    public void openBrowser() {
        driver = new FirefoxDriver();
    }

    @AfterScenario
    public void closeBrowser() {
        driver.quit();
    }
}