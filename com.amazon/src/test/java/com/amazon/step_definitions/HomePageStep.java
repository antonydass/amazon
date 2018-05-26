package com.amazon.step_definitions;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import com.azamon.utilities.BrowserActions;
import com.azamon.utilities.ReadProperties;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;


import com.amazon.pages.*;

public class HomePageStep {
	BrowserActions actions = new BrowserActions();
	WebDriver driver = actions.getInstance();
	public static Properties envProperties = ReadProperties.getPropInstance().testProperties;
	String url = envProperties.getProperty("app.url");
	HomePage homepage = new HomePage(driver);
	LoginPage loginPage;
	
	
	@Given("^I am navigate to url$")
	public void navigate_url(){
		actions.open_url(url);
	}
	
	
	@Given("^User is logged in to the amazon application successfully with (.+) and (.+)$")
	public void user_is_logged_in_to_the_amazon_application_successfully_with_and(String UserName, String Password) throws Exception{
		navigate_url();
		loginPage = homepage.navigateToLoginPage(driver);
		LoginPageStep.signin_To_Application(UserName, Password);
	}
	
	
	@And("^I should see username after login to the application (.+)$")
	public void i_should_see_username_after_login_to_the_application(String username) throws Exception{
		String application_username = homepage.getUserNameTextAfterSigin(driver).trim().toLowerCase();
		String name = username.substring(0,application_username.length());	
		Assert.assertEquals("user name is not macthed",application_username, name);
	}
	
}
