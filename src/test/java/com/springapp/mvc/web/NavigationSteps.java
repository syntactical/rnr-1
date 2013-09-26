package com.springapp.mvc.web;

import org.jbehave.core.annotations.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

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
        WebElement dateButton = driver.findElement(By.id("date_selector"));
        dateButton.click();
        WebElement month = driver.findElement(By.id("monthdropdown"));
        WebElement day = driver.findElement(By.id("daydropdown"));
        WebElement year = driver.findElement(By.id("yeardropdown"));
        month.click();
        day.click();
        year.click();
    }

    @Then("a banner should be visible")
    public void bannerShouldBeVisible() {
        WebElement banner = driver.findElement(By.className("headerimg"));
        assertTrue(banner.isDisplayed());
    }

    @Then("the form box should contain a date")
    public void dateShouldBeInForm() {
        WebElement form = driver.findElement(By.id("date_form"));
        String date = form.getText();
        assertTrue(date != null);
    }

    @Then("my vacation days are displayed")
    public void vacationDaysShouldBeInForm() {
        WebElement form = driver.findElement(By.id("vacationDays"));
        String vacationDays = form.getText();
        assertTrue(vacationDays.equals("Hey, your balance is 0.00"));
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