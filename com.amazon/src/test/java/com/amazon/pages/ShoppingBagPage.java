package com.amazon.pages;



import java.util.LinkedHashMap;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.azamon.utilities.BrowserActions;
import com.azamon.utilities.Utils;

public class ShoppingBagPage extends BrowserActions{

	@FindBy(css = "input[value='Delete']")
	List<WebElement> deleteBtn;
	
	@FindBy(css = "span[class^='nav-logo-base']")
	WebElement logo;
	
	@FindBy(xpath = "//span[@class='a-list-item']//span[contains(@class,'sc-product-title')]")
	WebElement productName;
	
	@FindBy(xpath = "//div[contains(@class,'a-span2')]//span[contains(@class,'sc-product-price')]")
	WebElement productPrice;
	
	@FindBy (xpath = "//span[@class='nav-line-2' and contains(text(),'Account')]/span")
	WebElement navArrow;
	
	@FindBy(id="nav-item-signout-sa")
	WebElement singoutBtn;
	
	public ShoppingBagPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		PageFactory.initElements(driver, this);
	}
	
	public void deletAllProduct(WebDriver driver) throws Exception{
		int delete_count = deleteBtn.size();
		while(delete_count > 0){
			BrowserActions.clickOnButton(deleteBtn.get(0), driver, "delete Button");
			Utils.waitForPageLoad(driver);
			delete_count--;
		}
	}
	
	public HomePage navigateToHomePage(WebDriver driver) throws Exception{
		BrowserActions.clickOnButton(logo, driver, "logo Button");
		return new  HomePage(driver);
	}
	
	public LinkedHashMap<String, String> getProductDetailsInBag(WebDriver driver) throws Exception{
		LinkedHashMap<String, String> productDetails = new LinkedHashMap<String, String>();
		productDetails.put("productName", getText(driver, productName, "productname"));
		productDetails.put("productPrice", getText(driver, productPrice, "product price"));
		return productDetails;	
	}
	
	public void signoutApp(WebDriver driver) throws Exception{
		Actions action = new Actions(driver);
		action.moveToElement(navArrow).moveToElement(singoutBtn).build().perform();
		Utils.waitForPageLoad(driver, BrowserActions.maxElementWait);
		BrowserActions.javascriptClick(singoutBtn, driver, "sing out button");
		Utils.waitForPageLoad(driver, BrowserActions.maxElementWait);
	}

}
