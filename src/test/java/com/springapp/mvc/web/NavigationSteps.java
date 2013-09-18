package com.springapp.mvc.web;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertTrue;

public class NavigationSteps {

    private WebDriver driver;

    @Given("a user is using the browser Firefox")
    public void aFirefoxBrowser() {
        driver = new FirefoxDriver();
    }

    @When("the user goes to rnr.thoughtworks.com")
    public void userNavigatesToRnrWebsite() {
        driver.get("http://localhost:8080/");

    }

    @Then("a banner should be visible")
    public void bannerShouldBeVisible() {
        WebElement banner = driver.findElement(By.className("headerimg"));
        assertTrue(banner.isDisplayed());
    }

//    @Given("a user is using the chrome browser")
//    public void aChromeBrowser() {
//        driver = new ChromeDriver();
//        String baseUrl = "http://localhost:8080";
//        driver.get(baseUrl);
//
//    }

    @When("the user clicks selects a date")
    public void userSelectsDate() {
        WebElement dateButton = driver.findElement(By.id("date_selector"));
        dateButton.click();
        WebElement calendar = driver.findElement(By.id("datepicker"));
        calendar.click();
    }

    @Then("the form box should contain a date")
    public void dateShouldBeInForm() {
        WebElement form = driver.findElement(By.id("date_form"));
        String date = form.getText();
        assertTrue(date!=null);
    }

    @AfterScenario
    public void closeBrowser(){
        driver.quit();
    }


}