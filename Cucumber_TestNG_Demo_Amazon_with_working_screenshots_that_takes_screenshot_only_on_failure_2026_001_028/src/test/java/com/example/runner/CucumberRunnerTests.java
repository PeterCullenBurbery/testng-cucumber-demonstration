package com.example.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = {"src/test/resources/features/AmazonCart.feature"},
    glue = {"com.example.definitions"},
    plugin = {
        "pretty", 
        "html:target/cucumber-reports.html",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" 
    }
)
public class CucumberRunnerTests extends AbstractTestNGCucumberTests {
}