package com.amazon.pages;

import java.util.LinkedList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.azamon.utilities.BrowserActions;

public class SearchResultPage extends BrowserActions{

	@FindBy(id = "quartsPagelet")
	WebElement searchResult_Txt;
	
	@FindBy(xpath = "//li[@id='result_0']//a[contains(@class,'s-color-twister-title-link')]")
	WebElement firstProductLnk;
		
	public SearchResultPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		PageFactory.initElements(driver, this);
	}
	
	public String getText_searchResult(WebDriver driver) throws Exception{
		return BrowserActions.getText(driver, searchResult_Txt, "get search result");
	}
	
	public ProductPage navigateToProductPage(WebDriver driver) throws Exception{
		BrowserActions.clickOnButton(firstProductLnk, driver, "clicked first product");
		return new ProductPage(driver);
	}
	
	
}
