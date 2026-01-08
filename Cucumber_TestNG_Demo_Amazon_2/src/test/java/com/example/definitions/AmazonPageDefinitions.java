package com.example.definitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class AmazonPageDefinitions {
    private static WebDriver driver;
    private final static int timeout_seconds = 15;

    @Before
    public void set_up() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
    }

    @Given("User is on Amazon homepage {string}")
    public void open_amazon_homepage(String url) {
        driver.get(url);
    }

    @Given("User handles the initial interstitial if present")
    public void handle_interstitial() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement continue_button = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(., 'Continue shopping')]"))
            );
            continue_button.click();
        } catch (TimeoutException ignored) {
            // Interstitial not found, proceed normally
        }
    }

    @When("User searches for {string}")
    public void search_for_item(String product_name) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout_seconds));
        WebElement search_box = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")));
        search_box.clear();
        search_box.sendKeys(product_name);
        driver.findElement(By.id("nav-search-submit-button")).click();
    }

    @When("User selects the first product from results")
    public void select_first_product() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout_seconds));
        By first_product_xpath = By.xpath("(//a[contains(@class,'a-link-normal') and contains(@href,'/dp/')])[1]");
        WebElement first_link = wait.until(ExpectedConditions.elementToBeClickable(first_product_xpath));
        first_link.click();
    }

    @When("User adds the product to the cart")
    public void add_to_cart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout_seconds));
        WebElement add_button = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button")));
        add_button.click();
    }

    @Then("User should be able to navigate to the cart page")
    public void verify_cart_navigation() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout_seconds));
        
        // Multi-locator strategy for "Go to Cart" button as seen in your original script
        By go_to_cart_selector = By.cssSelector("#sw-gtc, #attach-sidesheet-view-cart-button, [href*='/cart']");
        WebElement go_to_cart_btn = wait.until(ExpectedConditions.elementToBeClickable(go_to_cart_selector));
        go_to_cart_btn.click();

        boolean is_at_cart = wait.until(ExpectedConditions.or(
            ExpectedConditions.titleContains("Shopping Cart"),
            ExpectedConditions.urlContains("/cart")
        ));
        
        Assert.assertTrue(is_at_cart, "Failed to navigate to the shopping cart page.");
    }

    @After
    public void teardown() {
        // Keep browser open if needed for debugging, otherwise use driver.quit()
        // if (driver != null) driver.quit(); 
    }
}