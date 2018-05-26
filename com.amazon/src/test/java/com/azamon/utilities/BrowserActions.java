package com.azamon.utilities;


import java.awt.RenderingHints.Key;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Wrapper for Selenium WebDriver actions which will be performed on browser
 * 
 * Wrappers are provided with exception handling which throws Skip Exception on occurrence of NoSuchElementException
 * 
 * @author harish.subramani
 * 
 */
public class BrowserActions {
	public static int maxElementWait = 25;
	protected RemoteWebDriver webDriver;
	public static Properties envProperties = ReadProperties.getPropInstance().testProperties;
	private String BROWSER = envProperties.getProperty("browser").toLowerCase();
	protected WebDriverWait wait;
	
	public BrowserActions() {

		//Start Editing in Mac

		this.webDriver = WebDriverHelper.getWebDriver();

		this.wait = new WebDriverWait(this.webDriver, maxElementWait);
	}
	
	/**
	 * Wrapper to type a text in browser text field
	 * 
	 * @param txt
	 *            : WebElement of the Text Field
	 * @param txtToType
	 *            : Text to type [String]
	 * @param driver
	 *            : WebDriver Instances
	 * @param elementDescription
	 *            : Description about the WebElement
	 * @throws Exception 
	 */
	public static void typeOnTextField(WebElement txt, String txtToType, WebDriver driver, String elementDescription) throws Exception {

		if (!Utils.waitForElement(driver, txt, 1))
			try {
				throw new Exception(elementDescription + " field not found in page!!");
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

		try {
			txt.clear();
			txt.click();
			txt.sendKeys(txtToType);
		}
		catch (NoSuchElementException e) {
			try {
				throw new Exception(elementDescription + " field not found in page!!");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}// typeOnTextField

	/**
	 * Wrapper to type a text in browser text field
	 * 
	 * @param txt
	 *            : String Input (CSS Locator)
	 * @param txtToType
	 *            : Text to type [String]
	 * @param driver
	 *            : WebDriver Instances
	 * @param elementDescription
	 *            : Description about the WebElement
	 * @throws Exception 
	 */
	public static void typeOnTextField(String txt, String txtToType, WebDriver driver, String elementDescription) throws Exception {

		WebElement element = driver.findElement(By.cssSelector(txt));
		if (!Utils.waitForElement(driver, element, 1))
			throw new Exception(elementDescription + " field not found in page!!");

		try {
			element.clear();
			element.click();
			element.sendKeys(txtToType);
		}
		catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " field not found in page!!");

		}

	}// typeOnTextField

	/**
	 * Wrapper to click on button/text/radio/checkbox in browser
	 * 
	 * @param btn
	 *            : WebElement of the Button Field
	 * @param driver
	 *            : WebDriver Instances
	 * @param elementDescription
	 *            : Description about the WebElement
	 * @throws Exception 
	 */
	public static void clickOnButton(WebElement btn, WebDriver driver, String elementDescription) throws Exception {

		if (!Utils.waitForElement(driver, btn, 5))
			throw new Exception(elementDescription + " not found in page!!");

		try {
			btn.click();
		}
		catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " not found in page!!");
		}

	}// clickOnButton

	/**
	 * Wrapper to click on button/text/radio/checkbox in browser
	 * 
	 * @param btn
	 *            : String Input (CSS Locator) [of the Button Field]
	 * @param driver
	 *            : WebDriver Instances
	 * @param elementDescription
	 *            : Description about the WebElement
	 * @throws Exception 
	 */
	public static void clickOnButton(String btn, WebDriver driver, String elementDescription) throws Exception {

		WebElement element = driver.findElement(By.cssSelector(btn));
		if (!Utils.waitForElement(driver, element, 1))
			throw new Exception(elementDescription + " not found in page!!");

		try {
			element.click();
		}
		catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " not found in page!!");
		}

	}// clickOnButton

	/**
	 * Wrapper to check element visibility
	 * 
	 * @param driver
	 *            : WebDriver Instance
	 * @param cssSelectorForWebElement
	 *            : CSS Selector of the WebElement which visibility to check in String format
	 * @return: Boolean form - True if element visible/ False if element not visible
	 */
	public static boolean elementDisplayed(WebDriver driver, String cssSelectorForWebElement) {

		boolean displayed = false;

		try {
			displayed = driver.findElement(By.cssSelector(cssSelectorForWebElement)).isDisplayed();
		}
		catch (NoSuchElementException e) {
		}

		return displayed;

	}// elementDisplayed

	/**
	 * Wrapper to get a text from the provided WebElement
	 * 
	 * @param driver
	 *            : WebDriver Instance
	 * @param fromWhichTxtShldExtract
	 *            : WebElement from which text to be extract in String format
	 * @return: String - text from web element
	 * @param elementDescription
	 *            : Description about the WebElement
	 * @throws Exception 
	 */
	public static String getText(WebDriver driver, WebElement fromWhichTxtShldExtract, String elementDescription) throws Exception {

		String textFromHTMLAttribute = "";

		try {
			textFromHTMLAttribute = fromWhichTxtShldExtract.getText();

			if (textFromHTMLAttribute.isEmpty())
				textFromHTMLAttribute = fromWhichTxtShldExtract.getAttribute("textContent");

		}
		catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " not found in page!!");
		}

		return textFromHTMLAttribute;

	}// getText

	/**
	 * Wrapper to get a text from the provided WebElement
	 * 
	 * @param driver
	 *            : WebDriver Instance
	 * @param fromWhichTxtShldExtract
	 *            : String Input (CSS Locator) [from which text to be extract in String format]
	 * @return: String - text from web element
	 * @param elementDescription
	 *            : Description about the WebElement
	 * @throws Exception 
	 */
	public static String getText(WebDriver driver, String fromWhichTxtShldExtract, String elementDescription) throws Exception {

		String textFromHTMLAttribute = "";
		WebElement element = driver.findElement(By.cssSelector(fromWhichTxtShldExtract));

		try {
			textFromHTMLAttribute = element.getText();

			if (textFromHTMLAttribute.isEmpty())
				textFromHTMLAttribute = element.getAttribute("textContent");

		}
		catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " not found in page!!");
		}

		return textFromHTMLAttribute;

	}// getText

	/**
	 * Wrapper to get a text from the provided WebElement's Attribute
	 * 
	 * @param driver
	 *            : WebDriver Instance
	 * @param fromWhichTxtShldExtract
	 *            : WebElement from which text to be extract in String format
	 * @param attributeName
	 *            : Attribute Name from which text should be extracted like "style, class, value,..."
	 * @return: String - text from web element
	 * @param elementDescription
	 *            : Description about the WebElement
	 * @throws Exception 
	 */
	public static String getTextFromAttribute(WebDriver driver, WebElement fromWhichTxtShldExtract, String attributeName, String elementDescription) throws Exception {

		String textFromHTMLAttribute = "";

		try {
			textFromHTMLAttribute = fromWhichTxtShldExtract.getAttribute(attributeName);
		}
		catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " not found in page!!");
		}

		return textFromHTMLAttribute;

	}// getTextFromAttribute

	/**
	 * Wrapper to get a text from the provided WebElement's Attribute
	 * 
	 * @param driver
	 *            : WebDriver Instance
	 * @param fromWhichTxtShldExtract
	 *            : String Input (CSS Locator) [from which text to be extract in String format]
	 * @param attributeName
	 *            : Attribute Name from which text should be extracted like "style, class, value,..."
	 * @return: String - text from web element
	 * @param elementDescription
	 *            : Description about the WebElement
	 * @throws Exception 
	 */
	public static String getTextFromAttribute(WebDriver driver, String fromWhichTxtShldExtract, String attributeName, String elementDescription) throws Exception {

		String textFromHTMLAttribute = "";
		WebElement element = driver.findElement(By.cssSelector(fromWhichTxtShldExtract));

		try {
			textFromHTMLAttribute = element.getAttribute(attributeName);
		}
		catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " not found in page!!");
		}

		return textFromHTMLAttribute;

	}// getTextFromAttribute

	/**
	 * Wrapper to select option from combobox in browser
	 * 
	 * @param btn
	 *            : WebElement of the combobox Field
	 * 
	 * @param optToSelect
	 *            : option to select from combobox
	 * 
	 * @param driver
	 *            : WebDriver Instances
	 * @param elementDescription
	 *            : Description about the WebElement
	 * @throws Exception 
	 */
	public static void selectFromComboBox(WebElement btn, String optToSelect, WebDriver driver, String elementDescription) throws Exception {

		if (!Utils.waitForElement(driver, btn, 1))
			throw new Exception(elementDescription + " not found in page!!");

		try {
			Select selectBox = new Select(btn);
			selectBox.selectByValue(optToSelect);
		}
		catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " not found in page!!");
		}

	}// selectFromComboBox

	/**
	 * Wrapper to select option from combobox in browser
	 * 
	 * @param btn
	 *            : String Input (CSS Locator) [of the ComboBox Field]
	 * 
	 * @param optToSelect
	 *            : option to select from combobox
	 * 
	 * @param driver
	 *            : WebDriver Instances
	 * @param elementDescription
	 *            : Description about the WebElement
	 * @throws java.lang.Exception 
	 */
	public static void selectFromComboBox(String btn, String optToSelect, WebDriver driver, String elementDescription) throws java.lang.Exception {

		WebElement element = driver.findElement(By.cssSelector(btn));
		if (!Utils.waitForElement(driver, element, 1))
			throw new Exception(elementDescription + " not found in page!!");

		try {
			Select selectBox = new Select(element);
			selectBox.selectByValue(optToSelect);
		}
		catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " not found in page!!");
		}

	}// selectFromComboBox
	
	public void open_url(String url){
		getInstance().get(url);
	}
	
	public RemoteWebDriver getInstance(){
		if(webDriver == null){

			//this.webDriver = WebDriverHelper.getWebDriver();
			this.webDriver = new BrowserActions().webDriver;
		}
		return webDriver;
	}
	
	public static void pressEnter(WebDriver driver, WebElement element, String elementDescription) throws Exception{
		try{
			element.sendKeys(Keys.ENTER);
		}catch(NoSuchElementException e){
			throw new Exception(elementDescription + " not found in page!!");
		}
	}
	
	public static void mouseHover(WebDriver driver, WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).clickAndHold(element).build().perform();
	}
	
	public static void scrollToView(WebElement element, WebDriver driver)
			throws InterruptedException {
		((JavascriptExecutor) driver).executeScript(
			"arguments[0].scrollIntoView();", element);
		
		
	}
	
	public static void javascriptClick(WebElement element, WebDriver driver, String elementDescription) throws Exception {
		if (!Utils.waitForElement(driver, element, 5))
			throw new Exception(elementDescription + " not found in page!!");

		try {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
			
		} catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " not found in page!!");
		}
	}
	
	
	public static boolean elementPresent(WebElement element, WebDriver driver){
		List<WebElement> el = (List<WebElement>) element;
		return el.size() > 0 ? true : false;
	
	}
	
	public void quitDriver(){
		getInstance().close();
		getInstance().quit();
	
	}
}// BrowserActions