package com.example.definitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;

public class LoginPageDefinitions {
    private static WebDriver driver;
    public final static int TIMEOUT = 10; // Increased timeout for stability

    @Before
    public void set_up() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        // For Windows environments, sometimes this helps with stability
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
    }

    @Given("User is on HRMLogin page {string}")
    public void login_test(String url) {
        driver.get(url);
    }

    @When("User enters username as {string} and password as {string}")
    public void go_to_home_page(String user_name, String pass_word) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));

        // Wait for the username field to be present and visible
        WebElement username_field = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        username_field.sendKeys(user_name);

        driver.findElement(By.name("password")).sendKeys(pass_word);

        // Use a more robust selector for the login button
        driver.findElement(By.cssSelector("button.orangehrm-login-button")).submit();
    }

    @Then("User should be able to login successfully and new page open")
    public void verify_login() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));

        // Wait for the Dashboard breadcrumb to appear
        WebElement dashboard_header = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h6[contains(@class, 'oxd-topbar-header-breadcrumb-module')]")));

        String home_page_heading = dashboard_header.getText();
        Assert.assertEquals(home_page_heading, "Dashboard");
    }

    @Then("User should be able to see error message {string}")
    public void verify_error_message(String expected_error_message) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));

        // Wait for the error alert paragraph to appear
        WebElement error_element = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@role='alert']//p")));

        String actual_error_message = error_element.getText();
        Assert.assertEquals(actual_error_message, expected_error_message);
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}