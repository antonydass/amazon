package com.amazon.step_definitions;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import com.amazon.pages.HomePage;
import com.amazon.pages.LoginPage;
import com.azamon.utilities.BrowserActions;

import cucumber.api.java.en.When;


public class LoginPageStep {
	 static WebDriver driver = new BrowserActions().getInstance();
	 static LoginPage loginPage = new LoginPage(driver);
	 static HomePage homePage = new HomePage(driver);
	 public boolean errormessage= false;
	
	public static void signin_To_Application(String username, String Password) throws Exception{
	 homePage = loginPage.navigate_To_HomePage_After_Signin(driver, username, Password);
	}
	
	@When("^I Enter (.+) and (.+)$")
	public void i_enter(String username, String Password) throws Exception{
		 homePage.navigateToLoginPage(driver);
		loginPage.enter_username(driver, username);
		
		if(username.contains("com"))
			errormessage=false;
		else if (!loginPage.getuserError(driver).isEmpty())
			errormessage=true;
		
		if(errormessage == false){
			loginPage.enter_Password(driver, Password);	
			errormessage =true;
		}
	}
	
	@When("^I should see the valid error message$")
	public void i_shoul_see_valid_error_message() throws Exception{
		if(errormessage==true){
			String errorMessage = loginPage.getuserError(driver);
			if(errorMessage.contains("email")){
				Assert.assertEquals("error message is not matched", errorMessage.trim(), "We cannot find an account with that email address");
				
			}if(errorMessage.contains("password"))
				Assert.assertEquals("error message is not matched", errorMessage.trim(), "Your password is incorrect");
			
			errormessage=false;
			
		}
	}
	
}
