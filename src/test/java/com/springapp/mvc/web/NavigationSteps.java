package com.springapp.mvc.web;

import org.jbehave.core.annotations.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertTrue;

public class NavigationSteps {

    private WebDriver driver;

    @Given("I am a user")
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
        WebElement calendar = driver.findElement(By.id("datepicker"));
        calendar.click();
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
        assertTrue(date!=null);
    }

    @BeforeScenario
    public void openBrowser() {
        driver = new FirefoxDriver();
    }

    @AfterScenario
    public void closeBrowser(){
        driver.quit();
    }
}