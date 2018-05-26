package com.amazon.step_definitions;


import org.openqa.selenium.WebDriver;
import org.junit.Assert;
import com.amazon.pages.HomePage;
import com.amazon.pages.ProductPage;
import com.amazon.pages.SearchResultPage;
import com.azamon.utilities.BrowserActions;
import com.azamon.utilities.Utils;

import cucumber.api.java.en.Given;

public class SearchResultPageStep {
	
	BrowserActions actions = new BrowserActions();
	WebDriver driver = actions.getInstance();
	HomePage homePage = new HomePage(driver);
	SearchResultPage searchResultPage;
	ProductPage productPage;
	
	@Given("^I navigate to pdppage after search the (.+)$")
	public void i_navigate_pdpPager_after_the(String product) throws Exception{
		searchResultPage = homePage.navigateToSearchResultPage(driver, product);
		Utils.waitForPageLoad(driver);
		//Assert.assertEquals("search result mis match" +searchResultPage.getText_searchResult(driver).trim(), "Showing most relevant results. See all results for"+" "+product.trim()+".");
		productPage = searchResultPage.navigateToProductPage(driver);
		Utils.waitForPageLoad(driver);
	}
}
