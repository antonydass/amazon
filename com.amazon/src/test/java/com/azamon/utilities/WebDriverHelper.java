
package com.azamon.utilities;


import java.awt.Point;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazon.enums.IdType;
import com.amazon.enums.Swipe;
import com.azamon.utilities.ReadProperties;
import com.gargoylesoftware.htmlunit.javascript.host.file.File;

import cucumber.api.Scenario;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Platform;




public class WebDriverHelper {
	
	public static Properties envProperties = ReadProperties.getPropInstance().testProperties;
	private static RemoteWebDriver REAL_DRIVER = null;
	public static int maxPageLoadWait = 90;

	private static String BROWSER;
	private static String PLATFORM;
	private static String DRIVER_PATH;
	private static String DRIVER_ROOT_DIR;
	private static String SELENIUM_HOST;
	private static String SELENIUM_PORT;
	private static String SELENIUM_REMOTE_URL;
	private static String FIREFOX_BINARY;
	private static String PROJECT_DIRECTORY;
	private static Dimension BROWSER_WINDOW_SIZE;
	private static Integer BROWSER_WINDOW_WIDTH;
	private static Integer BROWSER_WINDOW_HEIGHT;
	private static String GECKO_DRIVER;
	private static String CHROME_DRIVER;
	private static String IE_DRIVER;
	private static String PHANTOM_DRIVER;
	private static String FIREFOX_PROFILE;
	//Edited in Mac
	private static String WDARUNNER_BUNDLEIDENTIFIER;

	//End of editing in Mac

	static {
		SELENIUM_HOST = envProperties.getProperty("driverhost");
		SELENIUM_PORT = envProperties.getProperty("driverport");
		PLATFORM = envProperties.getProperty("platform");
		BROWSER = envProperties.getProperty("browser").toLowerCase();
		FIREFOX_BINARY = envProperties.getProperty("firefox.binary");
		FIREFOX_PROFILE=envProperties.getProperty("firefox.profile");
		//	DRIVER_ROOT_DIR = envProperties.getProperty("driver.root.dir");
		GECKO_DRIVER = envProperties.getProperty("gecko.driver");
		CHROME_DRIVER = envProperties.getProperty("chrome.driver");
		IE_DRIVER = envProperties.getProperty("ie.driver");
		PHANTOM_DRIVER = envProperties.getProperty("phantomjs.driver");
		PROJECT_DIRECTORY= System.getProperty("user.dir");
		
		
		if (!BROWSER.isEmpty()) {
			System.setProperty("webdriver.gecko.driver", PROJECT_DIRECTORY+GECKO_DRIVER);            
			System.setProperty("webdriver.chrome.driver", PROJECT_DIRECTORY+CHROME_DRIVER);
			System.setProperty("webdriver.ie.driver", PROJECT_DIRECTORY+IE_DRIVER);
			System.setProperty("phantomjs.binary.path", PROJECT_DIRECTORY+PHANTOM_DRIVER);
		//	System.setProperty("webdriver.chrome.driver", PROJECT_DIRECTORY+MAC_CHROME_DRIVER);
		}
		
		
		try {
			switch (BROWSER) {
			case ("chrome"):
				startChromeDriver();
			break;

			case ("iexplore"):
				startIEDriver();
			break;

			default:
				throw new IllegalArgumentException("Browser " + BROWSER + " or Platform "
						+ PLATFORM + " type not supported");

			}


		} catch (IllegalStateException | MalformedURLException e) {
			/* LOG.error("FIX path for driver.root.dir in pom.xml " + DRIVER_ROOT_DIR
                    + " Browser parameter " + BROWSER + " Platform parameter " + PLATFORM
                    + " type not supported");*/
		}
		// Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private WebDriverHelper() {
		this.REAL_DRIVER= REAL_DRIVER;
	}
	
	private static String getDriverPath() {
		DRIVER_PATH = envProperties.getProperty("driver.root.dir");
		return DRIVER_PATH;
	}
	
	
	private static void startIEDriver() {
		DesiredCapabilities capabilities = getInternetExploreDesiredCapabilities();
		if (SELENIUM_HOST == null || SELENIUM_HOST.isEmpty())
			REAL_DRIVER = new InternetExplorerDriver(capabilities);
		else {
			try {
				capabilities.setPlatform(Platform.WINDOWS);
				System.setProperty("webdriver.id.driver", envProperties.getProperty("remotehost.iedriver.path"));
				REAL_DRIVER = getRemoteWebDriver(capabilities);
			} catch (MalformedURLException e) {
				//LOG.error(SELENIUM_REMOTE_URL + " Error " + e.getMessage());
			}
		}
		REAL_DRIVER.manage().window().maximize();
		REAL_DRIVER.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	private static RemoteWebDriver startChromeDriver() throws MalformedURLException {
		
		DesiredCapabilities capabilities = getChromeDesiredCapabilities();
		if(SELENIUM_HOST == null || SELENIUM_HOST.isEmpty()) {
			REAL_DRIVER = new ChromeDriver(capabilities);
			
		}else {
			try {
				capabilities.setPlatform(Platform.WINDOWS);
				REAL_DRIVER = getRemoteWebDriver(capabilities);
			} catch (MalformedURLException e) {
				//LOG.error(SELENIUM_REMOTE_URL + " Error " + e.getMessage());
			}
		}
		
		REAL_DRIVER.manage().window().maximize();
		REAL_DRIVER.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		return REAL_DRIVER;
	}

	/*private static RemoteWebDriver startFireFoxDriver() {
		DesiredCapabilities capabilities = getFireFoxDesiredCapabilities();
		if (SELENIUM_HOST == null || SELENIUM_HOST.isEmpty()){
			System.out.println("---INSIDE---");
			REAL_DRIVER = new FirefoxDriver(capabilities);
		}
		else {
			try {
				REAL_DRIVER = getRemoteWebDriver(capabilities);
			} catch (MalformedURLException e) {
				// LOG.error(SELENIUM_REMOTE_URL + " Error " + e.getMessage());
			}
		}
		REAL_DRIVER.manage().window().maximize();
		REAL_DRIVER.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		return REAL_DRIVER;
	}*/
	
	private static DesiredCapabilities getInternetExploreDesiredCapabilities() {
		LoggingPreferences logs = new LoggingPreferences();
		logs.enable(LogType.DRIVER, Level.OFF);
		DesiredCapabilities capabilities = DesiredCapabilities
				.internetExplorer();
		capabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);
		capabilities.setCapability("elementScrollBehavior", 0);
		capabilities
		.setCapability(
				InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
				true);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		//	capabilities.setVersion("9");
		return capabilities;
	}
	
	
	private static DesiredCapabilities getChromeDesiredCapabilities() {

		LoggingPreferences logs = new LoggingPreferences();
		logs.enable(LogType.DRIVER, Level.OFF);
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--disable-extensions");
		chromeOptions.addArguments("--disable-web-security");
		chromeOptions.addArguments("--test-type");
		capabilities.setCapability("chrome.verbose", false);

		capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
		return capabilities;
	}

	/*private static DesiredCapabilities getFireFoxDesiredCapabilities() {

		ProfilesIni profile = new ProfilesIni();  
		FirefoxProfile rmnavigatorProfile = profile.getProfile("noProxy");
		File f = new File("C:\\Users\\fs37px\\ProgramData\\MozillaFirefox\\firefox.exe");
//		File f = new File("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
		if(f.exists() && !f.isDirectory()) { 
			FIREFOX_BINARY = f.getAbsolutePath();
		}

		File pathBinary = new File(FIREFOX_BINARY);
		FirefoxBinary firefoxBinary = new FirefoxBinary(pathBinary);

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

		capabilities.setCapability("marionette", true);
		capabilities.setCapability("acceptInsecureCerts",true);

		capabilities.setCapability(FirefoxDriver.BINARY, firefoxBinary);
//		capabilities.setCapability(FirefoxDriver.PROFILE, rmnavigatorProfile);
		capabilities.setCapability("disable-restore-session-state", true);
		return capabilities;

	}*/



	private static RemoteWebDriver getRemoteWebDriver(DesiredCapabilities capabilities) throws MalformedURLException {
		SELENIUM_REMOTE_URL = "http://" + SELENIUM_HOST + ":" + SELENIUM_PORT + "/wd/hub";
		// LOG.info(SELENIUM_REMOTE_URL + " Checking Selenium Remote URL");
		return new RemoteWebDriver(new URL(SELENIUM_REMOTE_URL), (capabilities));
	}

	public static RemoteWebDriver getWebDriver() {
		if(Arrays.asList("firefox", "iexplore", "chrome", "phantomjs", "macsafari", "macchrome").contains(BROWSER))
			return REAL_DRIVER;	
		else
			
			System.out.println("Execution stopeed due the the given browser value :"+BROWSER+"is not matching to the predefined values \"firefox, iexplore, chrome, phantomjs, macsafari, iossafari, iosapp, simsafari, simapp, androidapp\"");
			System.exit(0);
		return null;
	}

		
}
