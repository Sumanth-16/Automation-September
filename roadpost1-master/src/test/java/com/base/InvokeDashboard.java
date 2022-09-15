package com.base;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;

public class InvokeDashboard {

	public static WebDriver driver; 
	private static Logger log = LogManager.getLogger("Invoke Dashboard");
	private static String Url=System.getProperty("user.dir")+"\\Dashboard\\overview.html";

	private static String fileSeparator = System.getProperty("file.separator");
	
	public static void main(String... args) throws IOException  {
		
		//Below code opens up the dashboard in Firefox. Comment/Uncomment accordingly.
		//driver=new FirefoxDriver();
				
		
		//Below code opens up the dashboard in chrome. Comment/Uncomment accordingly.
		String chromeDriverPath = System.getProperty("user.dir") + fileSeparator + "Resources" + fileSeparator + "Drivers" + fileSeparator + "chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		ChromeOptions cop = new ChromeOptions();
		cop.addArguments("--allow-file-access-from-files");
		cop.addArguments("disable-infobars"); 
		cop.addArguments("--proxy-server=null");//This is to increase performance
		driver = new ChromeDriver(cop);
		
		
		driver.get(Url);
		driver.manage().window().maximize();
		log.info("Dashboard launched Successfully......");
		
		
		
	}

}


