package com.amazon.test_runner;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class) 
@CucumberOptions(features="src/test/resources", tags= {"@Amazon"}, 
plugin = {"pretty", "html:target/cucumber-report/", "json:target/cucumber.json", "html:target/reports/cucumber-pretty"},
glue = "com.amazon.step_definitions", monochrome=true)

public class RunTest {

}
