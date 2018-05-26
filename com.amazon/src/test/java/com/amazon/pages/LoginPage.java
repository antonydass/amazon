package com.amazon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import com.azamon.utilities.BrowserActions;
import com.azamon.utilities.Utils;

public class LoginPage extends BrowserActions{
	
	@FindBy(id="ap_email")
	WebElement txtEmail;
	
	
	@FindBy(id="continue")
	WebElement BtnContinue;
	
	@FindBy(id="ap_password")
	WebElement txtPassword;
	
	@FindBy(id="signInSubmit")
	WebElement signinBtn;
	
	@FindBy(css="span.a-list-item")
	WebElement userNameError;
	
	
	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	
	public HomePage navigate_To_HomePage_After_Signin(WebDriver driver, String username, String Password) throws Exception{
		enter_username(driver,username);
		enter_Password(driver,Password);
		return new HomePage(driver);
		
	}
	
	public  void enter_username(WebDriver driver, String username) throws Exception{
		BrowserActions.typeOnTextField(txtEmail, username, driver, "Entered username");
		BrowserActions.clickOnButton(BtnContinue, driver, "clicked continue button");
	}
	
	public  void enter_Password(WebDriver driver, String Password) throws Exception{
		BrowserActions.typeOnTextField(txtPassword, Password, driver, "Entered Password");
		BrowserActions.clickOnButton(signinBtn, driver, "clicked Signin button");
	}
	
	public String getuserError(WebDriver driver) throws Exception{	
		String returnValue = BrowserActions.getText(driver, userNameError, "user name error");
		if(returnValue.isEmpty())
			return "";
		else	
			return returnValue;
	}
}
