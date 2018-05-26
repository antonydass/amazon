package com.azamon.utilities;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Properties;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.lang3.SystemUtils;

import com.amazon.enums.messageType;
public class ReadProperties {

	private static ReadProperties propInstance = null;

	public Properties testProperties;
	GeneralMethods gnMethods = new GeneralMethods();

	private ReadProperties() {
		testProperties = new Properties();
		FileInputStream propfile;
		try{
			propfile = new FileInputStream("src/main/resources/environment.properties");
			testProperties.load(propfile);
			System.out.println( "  as" + testProperties.toString());
			
			//Added in Windows
			//if(!(System.getProperty("browser").isEmpty() || System.getProperty("browser")==null)){
			try{	
				if(System.getProperty("browser").length() > 0){

					testProperties.setProperty("browser", System.getProperty("browser").toLowerCase());

				}
			}catch(NullPointerException e){

				gnMethods.specialInstractions(messageType.WARNING, "\"browser\" value is missing in command line, "
						+ "\nExecuting with default value : " + testProperties.getProperty("default.browser")
						+"\nAcceptable values : firefox, iexplore, chrome, phantomjs, macsafari, iossafari, iosapp, simsafari, simapp, androidchrome");

				testProperties.setProperty("browser", testProperties.getProperty("default.browser"));
			}
			
			//For Platform
			try{
				if((System.getProperty("os").length() >0)){					
					testProperties.setProperty("os",  System.getProperty("os").toLowerCase());					
					
				}
			}catch(NullPointerException e) {
				
				gnMethods.specialInstractions(messageType.WARNING, "\"os\" value is missing in command line, "
						+ "\nExecuting with default value : " + testProperties.getProperty("default.os")
						+"\nAcceptable value : windows, mac");
				
				testProperties.setProperty("os", testProperties.getProperty("default.os"));
			}

			try{
				if((System.getProperty("screenshot").length() > 0)){
					if(System.getProperty("screenshot").equalsIgnoreCase("off")){
						testProperties.setProperty("screenshot", System.getProperty("screenshot"));

					}else if(System.getProperty("screenshot").equalsIgnoreCase("on")){
						gnMethods.specialInstractions(messageType.INFO, "To increase execution speed and to Reduce the report size,"
								+ "please turn off the screenshot");
					}
				}
			}catch(NullPointerException e) {
				gnMethods.specialInstractions(messageType.WARNING, "\"screenshot\" value is missing in command line, "
						+ "\nExecuting with default value : " + testProperties.getProperty("screenshot")
						+"\nAcceptable value : on, off");
			}


			try{
				if((System.getProperty("highlight").length() > 0)){
					if(System.getProperty("highlight").equalsIgnoreCase("off")){
						testProperties.setProperty("highlight", System.getProperty("highlight"));

					}else if(System.getProperty("highlight").equalsIgnoreCase("on")){
						gnMethods.specialInstractions(messageType.INFO, "For Batch execution it is suggested to trun off the highlight options");
					}
				}
			}catch(NullPointerException e) {
				gnMethods.specialInstractions(messageType.WARNING, "\"highlight\" value is missing in command line, "
						+ "\nExecuting with default value : " + testProperties.getProperty("highlight")
						+"\nAcceptable value : on, off");
			}
			
			//End of adding in windows
		}catch (IOException e){
			System.out.println("Environment Properties file not found, please correct path pointing to src/main/resources");
			e.getMessage();
		}
	}

	public synchronized static ReadProperties getPropInstance(){
		if(propInstance==null){
			propInstance = new ReadProperties();
		}
		return propInstance;
	}


}
