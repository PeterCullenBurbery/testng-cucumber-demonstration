package com.example.locators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPageLocators {

    @FindBy(name = "username")
    public WebElement userName;

    @FindBy(name = "password")
    public WebElement password;

    // Fixed: OrangeHRM demo usually uses a generic login title or h5
    @FindBy(xpath = "//h5[contains(@class, 'login-title')]")
    public WebElement titleText;

    // Fixed: Using specific input grouping to distinguish between user and pass errors
    @FindBy(xpath = "//div[label[text()='Username']]/following-sibling::span")
    public WebElement missingUsernameErrorMessage;

    @FindBy(xpath = "//div[label[text()='Password']]/following-sibling::span")
    public WebElement missingPasswordErrorMessage;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement login;

    @FindBy(xpath = "//div[@role='alert']//p")
    public WebElement errorMessage;

    @FindBy(xpath = "//a[contains(@href, 'linkedin.com')]")
    public WebElement linkedInIcon;

    // Fixed: Changed from a hardcoded full URL to a partial match
    @FindBy(xpath = "//a[contains(@href, 'facebook.com')]")
    public WebElement faceBookIcon;

    @FindBy(xpath = "//p[contains(@class, 'login-forgot-header')]")
    public WebElement ForgotYourPasswordLink;

}