package com.example.testcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage extends BaseTests {

    // Removed local WebDriver driver; declaration to use inherited static driver

    @FindBy(name = "username")
    WebElement user_name_field;

    @FindBy(name = "password")
    WebElement password_field;

    @FindBy(css = "button[type='submit']")
    WebElement login_button;

    @FindBy(css = ".orangehrm-login-title")
    WebElement title_text;

    @FindBy(css = ".oxd-alert-content-text")
    WebElement error_message;

    @FindBy(css = ".orangehrm-login-forgot-header")
    WebElement forgot_password_link;
    
    // Selector for LinkedIn icon (usually in the footer)
    @FindBy(css = "a[href*='linkedin']")
    WebElement linked_in_icon;

    public LoginPage(WebDriver driver) {
        // Initialize elements using the driver passed from the test
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void set_user_name(String user_name) {
        wait.until(ExpectedConditions.visibilityOf(user_name_field));
        user_name_field.sendKeys(user_name);
    }

    public void set_password(String password) {
        wait.until(ExpectedConditions.visibilityOf(password_field));
        password_field.sendKeys(password);
    }

    public void click_login() {
        wait.until(ExpectedConditions.elementToBeClickable(login_button)).click();
    }

    public String get_login_title() {
        wait.until(ExpectedConditions.visibilityOf(title_text));
        return title_text.getText();
    }

    public String get_forgot_password_link_text() {
        wait.until(ExpectedConditions.visibilityOf(forgot_password_link));
        return forgot_password_link.getText();
    }

    public String get_error_message() {
        wait.until(ExpectedConditions.visibilityOf(error_message));
        return error_message.getText();
    }
    
    // Added the missing method called in LoginTests
    public boolean is_enabled_linked_in() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(linked_in_icon)).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public void login(String user_name, String password) {
        this.set_user_name(user_name);
        this.set_password(password);
        this.click_login();
    }
}