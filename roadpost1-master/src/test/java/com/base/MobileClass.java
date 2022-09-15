package com.base;

import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.datamanager.ConfigManager;
import com.testng.Assert;
import com.utilities.UtilityMethods;

public class MobileClass implements IBrowser {
	
	WebDriver driver;
//	ConfigManager sys=new ConfigManager();
	private Logger log = LogManager.getLogger("MobileBrowser");
	private String fileSeperator = System.getProperty("file.separator");
	
	public WebDriver init()
	{
		ConfigManager sys=new ConfigManager();
		if(sys.getProperty("BrowserName").equalsIgnoreCase("mobileChrome"))
			driver=initMobileChrome();
		if(sys.getProperty("BrowserName").equalsIgnoreCase("mobileSafari"))
			driver=initMobileSafari();
		return driver;
	}
	
	/**
	 * Purpose - This method launches chrome browser on Android device
	 * @return web driver
	 */
	public WebDriver initMobileChrome()
	{
		System.setProperty("webdriver.chrome.driver",getDriverPath());
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setExperimentalOption("androidPackage","com.android.chrome");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
		return new ChromeDriver(chromeOptions);
	}
	
	/**
	 * Purpose - This method launches Safari browser on iOS device
	 */
	public WebDriver initMobileSafari()
	{
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("platformName", "iOS");
		cap.setCapability("platformVersion", "9.3.2");
		cap.setCapability("browserName", "Safari");
		cap.setCapability("deviceName", "iPhone 5");
		cap.setCapability("udid", "50f15378ecc8628f757612b217021ec3c55abe36");
		try {
			driver = new RemoteWebDriver(new URL("http://0.0.0.0:4723/wd/hub"),cap);
			Thread.sleep(10000);
		} catch (Exception e) {
			log.error("Exeception occured while launching Safri on iOS");
			Assert.fail("Unable to launch Safari browser on ios device " + UtilityMethods.getStackTrace());
		}
		return driver;
	}
	
	/**
	 * purpose - This method will returns the chrome driver location
	 * @return - chrome driver location
	 */
	private String getDriverPath() {
		String chromeLocation = System.getProperty("user.dir") + fileSeperator
				+ "Resources" + fileSeperator + "Drivers" + fileSeperator;

		if (System.getProperty("os.name").toLowerCase().contains("windows"))
			chromeLocation = chromeLocation + "chromedriver.exe";
		else if (System.getProperty("os.name").toLowerCase().contains("mac"))
			chromeLocation = chromeLocation + "chromedriver";

		return chromeLocation;
	}

}