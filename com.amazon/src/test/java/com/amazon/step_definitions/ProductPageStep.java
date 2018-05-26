package com.amazon.step_definitions;

import java.util.LinkedHashMap;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import com.amazon.pages.HomePage;
import com.amazon.pages.LoginPage;
import com.amazon.pages.ProductPage;
import com.amazon.pages.ShoppingBagPage;
import com.azamon.utilities.BrowserActions;
import org.junit.Assert;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;


public class ProductPageStep {
	
	BrowserActions actions = new BrowserActions();
	WebDriver driver = actions.getInstance();
	ProductPage productPage = new ProductPage(driver);
	ShoppingBagPage shoppingBagPage;
	LoginPage loginPage = new LoginPage(driver);
	HomePage homePage;
	public LinkedHashMap<String, String> pdp_product_Details;
	public LinkedHashMap<String, String> bag_product_Details;
	
	@And("^I add product to bag$")
	public void i_add_product_to_bag() throws Exception{
		getProductDetails_inPDP();
		productPage.addProductToCard(driver);
		shoppingBagPage = productPage.navigateToCartAfterAddingProduct(driver);
	}
	
	
	public LinkedHashMap<String, String> getProductDetails_inPDP() throws Exception{
		return pdp_product_Details = productPage.getProductDetails(driver);
	}
	
	@Then("^I verify the added product details in shoppingBag$")
	public void i_verify_the_added_product_details_in_shoppingBag() throws Exception{
		bag_product_Details = shoppingBagPage.getProductDetailsInBag(driver);
		Assert.assertEquals("Product Name mis match", pdp_product_Details.get("productName"), bag_product_Details.get("productName"));
		Assert.assertEquals("Product price mis match", pdp_product_Details.get("productPrice"), bag_product_Details.get("productPrice"));
	}

	@Then("^I verify the product details after logout and login to application with (.+) and (.+)$")
	public void i_verify_product_details_logout_login_application(String Username, String Password) throws Exception{
		shoppingBagPage.signoutApp(driver);
		homePage = loginPage.navigate_To_HomePage_After_Signin(driver, Username, Password);
		homePage.navigate_ToCart(driver);
		Assert.assertEquals("Product Name mis match", pdp_product_Details.get("productName"), bag_product_Details.get("productName"));
		shoppingBagPage.signoutApp(driver);
	}
	
}
