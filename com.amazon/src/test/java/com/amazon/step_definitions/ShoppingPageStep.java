package com.amazon.step_definitions;

import org.openqa.selenium.WebDriver;

import com.amazon.pages.ShoppingBagPage;
import com.amazon.pages.HomePage;
import com.azamon.utilities.BrowserActions;

import cucumber.api.java.en.And;

public class ShoppingPageStep {
	static WebDriver driver = new BrowserActions().getInstance();
	HomePage homePage = new HomePage(driver);
	ShoppingBagPage cartPage;
	
	@And("^I remove the product if already exist$")
	public void i_remove_product_if_already_exist() throws Exception{
		System.out.println( "prod" + homePage.getBagProductCount(driver));
		int product_count = Integer.parseInt(homePage.getBagProductCount(driver));
		if(product_count >  0 ){
			cartPage = homePage.navigate_ToCart(driver);
			cartPage.deletAllProduct(driver);
			homePage = cartPage.navigateToHomePage(driver);
		}
	}
	

}
