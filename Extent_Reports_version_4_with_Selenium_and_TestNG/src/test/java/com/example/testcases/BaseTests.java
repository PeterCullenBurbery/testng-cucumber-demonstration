package com.example.testcases;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTests {

    public static WebDriver driver;
    public WebDriverWait wait;

    @BeforeTest
    public void setup() throws Exception {
        // Selenium Manager is invoked automatically when calling the ChromeDriver constructor
        // No need for WebDriverManager.chromedriver().setup()
        ChromeOptions options = new ChromeOptions();
        
        driver = new ChromeDriver(options);
        driver.get("https://opensource-demo.orangehrmlive.com/");
        
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @AfterTest
    public void close_browser() {
        if (driver != null) {
            driver.quit();
        }
    }
}