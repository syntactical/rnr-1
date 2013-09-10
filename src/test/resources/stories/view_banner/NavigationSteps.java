package stories.view_banner;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertTrue;

public class NavigationSteps extends Steps {

    private WebDriver driver;

    @Given("a user is using the browser Firefox")
    public void aFirefoxBrowser(){
        driver = new FirefoxDriver();
    }

    @When("the user goes to rnr.thoughtworks.com")
    public void userNavigatesToRnrWebsite() {
        driver.get("http://localhost:8080/");

    }

    @Then("a banner should be visible")
    public void bannerShouldBeVisible() {
        WebElement banner = driver.findElement(By.id("banner"));
        assertTrue(banner.isDisplayed());
    }


}
