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
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;

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
                    ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(., 'Continue shopping')]")));
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

        // This XPath specifically looks for a search result that is NOT a sponsored
        // 'sparkle' ad
        // It targets the first organic product link in the search grid
        By organic_product_xpath = By.xpath(
                "//div[@data-component-type='s-search-result']" + // Main result container
                        "[not(.//span[contains(text(),'Sponsored')])]" + // Exclude sponsored labels
                        "//a[contains(@class,'a-link-normal') and contains(@href,'/dp/')]" // The product link
        );

        WebElement first_link = wait.until(ExpectedConditions.elementToBeClickable(organic_product_xpath));

        // Scroll it into view just to be safe before clicking
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", first_link);

        // Use Javascript click to avoid any other potential interceptions
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", first_link);
    }

    @When("User adds the product to the cart")
    public void add_to_cart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout_seconds));
        WebElement add_button = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button")));
        add_button.click();

        // Wait for ANY confirmation element to be visible so the screenshot is
        // meaningful
        // We look for the side-sheet, the warranty header, or the success message
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.id("attach-sidesheet-content")),
                ExpectedConditions.visibilityOfElementLocated(By.id("attach-warranty-header")),
                ExpectedConditions.visibilityOfElementLocated(By.id("NATC_SMART_WAGON_CONF_MSG_SUCCESS"))));
    }

    @When("User dismisses the protection plan if offered")
    public void dismiss_protection_plan() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout_seconds));
        try {
            // Target the "No Thanks" button
            By no_thanks_selector = By.id("attachSiNoCoverage");
            WebElement no_thanks_btn = wait.until(ExpectedConditions.elementToBeClickable(no_thanks_selector));
            no_thanks_btn.click();

            // CRITICAL: Wait for the entire side-sheet container to vanish
            // This ensures the next screenshot is of the actual confirmation page
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("attach-sidesheet-content")));
        } catch (TimeoutException ignored) {
            // Plan didn't show up, which is fine for organic results
        }
    }

    @Then("User should see the item in the shopping cart")
    public void verify_item_in_cart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout_seconds));

        // Navigate to the cart
        driver.get("https://www.amazon.com/gp/cart/view.html");

        // Wait for the cart items list to be present (this proves the page loaded)
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("sc-active-cart")));

        // Check if the "empty" header exists
        boolean is_empty = driver.findElements(By.xpath("//h1[contains(text(),'Your Amazon Cart is empty')]"))
                .size() > 0;

        Assert.assertFalse(is_empty, "The cart is empty! The item was not added successfully.");
    }

    @After
    public void teardown() {
        // Keep browser open if needed for debugging, otherwise use driver.quit()
        // if (driver != null) driver.quit();
    }

    @AfterStep
    public void add_screenshot(Scenario scenario) {
        // validate if driver is initialized before taking screenshot
        if (driver != null) {
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            // Attach the screenshot to the Cucumber report
            scenario.attach(screenshot, "image/png", "image_at_step_" + scenario.getLine());
        }
    }
}