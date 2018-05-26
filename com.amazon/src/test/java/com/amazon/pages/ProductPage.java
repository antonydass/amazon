package com.amazon.pages;

import java.util.LinkedHashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.azamon.utilities.BrowserActions;
import com.azamon.utilities.Utils;

public class ProductPage extends BrowserActions{

	@FindBy (id = "productTitle")
	WebElement productTitile;
	
	@FindBy(id="priceblock_ourprice")
	WebElement productPrice;
	
	@FindBy(id = "add-to-cart-button")
	WebElement addToCartButton;
	
	@FindBy(css = "span[class^='nav-cart-icon']")
	WebElement shoppingBagBtn;
	
	public ProductPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		PageFactory.initElements(driver, this);
	}
	
	public LinkedHashMap<String, String> getProductDetails(WebDriver driver) throws Exception{
		LinkedHashMap<String, String> productDetails = new LinkedHashMap<String, String>();
		productDetails.put("productName", getText(driver, productTitile, "productname"));
		productDetails.put("productPrice", getText(driver, productPrice, "product price"));
		return productDetails;	
	}

	public void addProductToCard(WebDriver driver) throws Exception{
		BrowserActions.clickOnButton(addToCartButton, driver, "clicked add to card button");
		Utils.waitForPageLoad(driver, BrowserActions.maxElementWait);
	}

	
	public ShoppingBagPage navigateToCartAfterAddingProduct(WebDriver driver) throws Exception{
		BrowserActions.clickOnButton(shoppingBagBtn, driver, "Clicked Shopping Bag Button");
		return new ShoppingBagPage(driver);
	}
}
