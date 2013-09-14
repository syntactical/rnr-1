package com.springapp.mvc.web;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.net.MalformedURLException;

import static com.thoughtworks.selenium.SeleneseTestBase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HomeControllerTest {

    private WebDriver chrome;
    private String baseUrl;


    @Before
    public void createDriver() throws MalformedURLException {
        chrome = new ChromeDriver();
        baseUrl = "http://localhost:8080";
        chrome.get(baseUrl);

    }

    @After
    public void quitDriver() {
        chrome.quit();
    }



    @Test
    public void simple() throws Exception {
        assertThat(true, is(true));
    }

    @Test
    public void shouldFindBanner() throws Exception {
        WebElement banner = chrome.findElement(By.className("headerimg"));
        assertTrue(banner!=null);

    }
    @Test
    public void shouldCheckTitle() throws Exception {
        assertThat(chrome.getTitle(), is("RnR at ThoughtWorks"));

    }

    @Test
    public void shouldFillFormWithDateWhenCalendarClicked() throws Exception {
       WebElement dateButton = chrome.findElement(By.xpath("/html/body/table[2]/tbody/tr/td[1]/div/button"));
       dateButton.click();
       WebElement calendar = chrome.findElement(By.id("datepicker"));
       calendar.click();
       WebElement form = chrome.findElement(By.id("date_form"));
       String date = form.getText();
       assertTrue(date!=null);

    }


}
