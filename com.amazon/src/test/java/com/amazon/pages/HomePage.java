package com.amazon.pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.LoadableComponent;

import com.azamon.utilities.BrowserActions;
import com.azamon.utilities.Utils;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;




public class HomePage extends BrowserActions{
	
	@FindBy(css="a[data-nav-ref='nav_ya_signin']")
	WebElement SignBtn;
	
	@FindBy(css="span[class^='nav-logo']")
	WebElement logo;
	
	
	@FindBy(id = "nav-cart-count")
	WebElement cartcount;
	
	@FindBy(id = "twotabsearchtextbox")
	WebElement txtSearch;
	
	public HomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public LoginPage navigateToLoginPage(WebDriver driver) throws Exception{
		BrowserActions.clickOnButton(SignBtn, driver, "clicked sign in button");
		return new LoginPage(driver);
	}
	
	public String  getUserNameTextAfterSigin(WebDriver driver) throws Exception{
		return BrowserActions.getText(driver, SignBtn, "user name text").split("\n")[0].split("\\,")[1];
	}
	
	public String getBagProductCount(WebDriver driver) throws Exception{
		return BrowserActions.getText(driver, cartcount, "cart count ");
	}
	
	public ShoppingBagPage navigate_ToCart(WebDriver driver) throws Exception{
		BrowserActions.clickOnButton(cartcount, driver, "clicked cart button");
		return new ShoppingBagPage(driver);
	}
	
	public SearchResultPage navigateToSearchResultPage(WebDriver driver, String product) throws Exception {
		BrowserActions.typeOnTextField(txtSearch, product, driver, "Enter the product");
		BrowserActions.pressEnter(driver, txtSearch, "Enter key pressed");
		return new SearchResultPage(driver);
	}

}
