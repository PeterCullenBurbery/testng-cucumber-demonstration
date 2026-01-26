package com.example.testcases;

import static org.testng.Assert.assertTrue;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class LoginTests extends BaseTests {

    LoginPage obj_login;

    @Test(priority = 0)
    public void verify_login_page_title() {
        // Create Login Page object
        obj_login = new LoginPage(driver);

        // In 5.8, the title usually says "Login"
        String login_page_title = obj_login.get_login_title();
        Assert.assertTrue(login_page_title.contains("Login"), 
            "Expected title to contain 'Login' but found: " + login_page_title);
    }

    @Test(priority = 1)
    public void verify_forgot_password_link() {
        String expected_text = obj_login.get_forgot_password_link_text();
        // In 5.8, it often says "Forgot your password?"
        Assert.assertTrue(expected_text.contains("Forgot your password?"));
    }

    @Test(priority = 2)
    public void home_test() {
        // Attempt login with invalid credentials to trigger the error message
        obj_login.login("Admin1", "admin1234");

        String expected_error = obj_login.get_error_message();

        // In 5.8, the standard error is "Invalid credentials"
        Assert.assertTrue(expected_error.contains("Invalid credentials"), 
            "Error message did not match. Found: " + expected_error);
    }

    @Test(priority = 3)
    public void verify_linked_in_icon() {
        // LinkedIn icon is now usually in the footer; this checks if the element is present
        // Since we're keeping your logic, we'll keep the skip exception here
        System.out.println("LinkedIn status: " + obj_login.is_enabled_linked_in());
        assertTrue(obj_login.is_enabled_linked_in());

        System.out.println("Throwing skip exception as requested...");
        throw new SkipException("Skipping this test case for demonstration.");
    }
}