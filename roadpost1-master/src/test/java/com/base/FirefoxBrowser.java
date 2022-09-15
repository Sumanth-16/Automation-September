package com.base;
import com.datamanager.ConfigManager;
import com.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
/**
 * This class defines all methods required to initialize FireFoxDriver
 * Two methods are written so far - FFDriverDefault and with profile
 */
public class FirefoxBrowser implements IBrowser
{
	ConfigManager sys=new ConfigManager();
	WebDriver driver;
	FirefoxProfile firefoxProfile;
	FirefoxOptions firefoxOptions;

	private Logger log = LogManager.getLogger("FirefoxBrowser");
	String fileSeperator = System.getProperty("file.separator");
	
	/**
	 * 
	 * This method is used to initiate firefox browser 
	 *
	 * @return , Returns firefox browser driver object
	 */
	public WebDriver init()
	{	
    	if(isProfilePresent())
    	{
    		log.info("Firefox profile Exists");
    		log.info("Launching firefox with specified profile");
    		System.setProperty("webdriver.gecko.driver", getDriverPath());
			driver = FirefoxDriver(getProfilePath());
		}
		else
		{
			log.info("Launching firefox with a new profile");
			if(System.getProperty("os.name").toLowerCase().contains("windows")){
			System.setProperty("webdriver.gecko.driver", getProfilePath());
			System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,"true");
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"/dev/null");
			System.setProperty("webdriver.gecko.driver", getDriverPath());
			driver = FirefoxDriver();
			}
			if(System.getProperty("os.name").toLowerCase().contains("linux"))
			{
	        WebDriverManager.firefoxdriver().setup();
	        driver = FirefoxDriver();
			}
		}
	    return driver;
	}
	
	

	/**
	 * 
	 * This method returns the location of gekodriver based on Operating
	 * system he scripts executed
	 * 
	 * 
	 * @return, returns chromedriver.exe file path
	 */
	public String getDriverPath() {

		String gekoLocation = System.getProperty("user.dir") + fileSeperator
				+ "Resources" + fileSeperator + "Drivers" + fileSeperator;
		boolean is64bit = false;
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			is64bit = System.getenv("ProgramFiles(x86)") != null;
			if (is64bit) {
				gekoLocation = gekoLocation + "geckodriver_64.exe";
			} else {
				gekoLocation = gekoLocation + "geckodriver.exe";
			}
		}
		else if (System.getProperty("os.name").toLowerCase().contains("mac"))
			gekoLocation = gekoLocation + "geckodriver";
		else if (System.getProperty("os.name").toLowerCase().contains("linux"))
		//	gekoLocation = gekoLocation + "geckodriver_linux64";
			gekoLocation="/usr/bin/geckodriver";

		return gekoLocation;

	}
	
	
	/**
	 * Returns FirefoxDriver with default profile. This method will also disables the auto update of 
	 * firefox browser to next versions and takes care of accepting untrusted certificates 
	 * @return Webdriver initialized with FirefoxDrivers
	 */
	public WebDriver FirefoxDriver()
	{
		
		firefoxProfile = new FirefoxProfile();
		firefoxOptions = new FirefoxOptions();
		
		setProfilePreferences();
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);   
		cap.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		firefoxOptions.addCapabilities(cap);
		firefoxOptions.setProfile(firefoxProfile);
		if(isBinaryPathPresent())
		{	
			File pathToBinary = new File(getBinaryPath());
			FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
			firefoxOptions.setBinary(ffBinary);
			driver = new FirefoxDriver(firefoxOptions);
		}
		else
			driver = new FirefoxDriver(firefoxOptions);		
        return driver;
	}
	

	/**
	 * Returns FirefoxDriver with specific profile. This method will also disables the auto update of 
	 * firefox browser to next versions 
	 * @param profilePath where firefox profile is stored
	 * @return Webdriver intialized with Firefoxdriver
	 */
	
	public WebDriver FirefoxDriver(String profilePath)
	{
		//Initialize firefox browser with FF profile
		

		firefoxProfile = new FirefoxProfile(new File(profilePath));
		//set the Firefox preference "auto upgrade browser"  to false and to prevent compatibility issues
		firefoxOptions = new FirefoxOptions();
		setProfilePreferences();
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);   
		cap.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		firefoxOptions.addCapabilities(cap);
		firefoxOptions.setProfile(firefoxProfile);
	
		if(isBinaryPathPresent())
		{	
			File pathToBinary = new File(getBinaryPath());
			FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
			firefoxOptions.setBinary(ffBinary);
			driver = new FirefoxDriver(firefoxOptions);
		}
		else
			driver = new FirefoxDriver(firefoxOptions);
       	return driver;
	}
	
	/**
	 * 
	 * This method sets different profile preferences to firefox browser
	 *
	 */
	public void setProfilePreferences()
	{
		
		firefoxProfile.setAcceptUntrustedCertificates(true);
		firefoxProfile.setPreference("app.update.enabled", false);        
		firefoxProfile.setPreference("browser.download.folderList",2);
		firefoxProfile.setPreference("browser.download.manager.showWhenStarting",false);
		firefoxProfile.setPreference("browser.download.dir",getDownloadLocation());
		firefoxProfile.setPreference("browser.helperApps.neverAsk.openFile", "application/pdf, application/x-pdf, application/acrobat, applications/vnd.pdf, text/pdf, text/x-pdf, application/octet-stream, application/vnd.openxmlformats-officedocument.wordprocessingml.document, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/x-rar-compressed, application/zip");
		firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/pdf, application/x-pdf, application/acrobat, applications/vnd.pdf, text/pdf, text/x-pdf, application/octet-stream, application/vnd.openxmlformats-officedocument.wordprocessingml.document, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/x-rar-compressed, application/zip");
		firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
		firefoxProfile.setPreference("browser.download.manager.showAlertOnComplete",false);
		firefoxProfile.setPreference("pdfjs.enabledCache.state", false);
		firefoxProfile.setPreference("webdriver.load.strategy", "unstable"); //allows the driver to perform web actions even if the page is loading
		firefoxProfile.setAssumeUntrustedCertificateIssuer(false);
		
	}
	

	/**
	 * Method to retrieve the profile path given in Properties file
	 * @return - returns Firefox profile directory
	 */
	public String getProfilePath()
	{
		return sys.getProperty("FireFoxProfilePath");
			
	}
	
	/**
	 * 
	 * This method is used to verify whether specified firefox profile exists or not
	 *
	 * @return , Returns true if firefox profile exists, else returns false
	 */
	public boolean isProfilePresent()
	{
		String profilePath = getProfilePath();
		try
		{
			if(!profilePath.isEmpty())
			{
				File profileFolder = new File(profilePath);
				return profileFolder.exists();
			}
			else
			{
				return false;
			}
		}
		catch(NullPointerException e)
		{
			log.error("Firefox Profile does not exist - "+profilePath);
			Assert.fail("Firefox Profile does not exist - "+profilePath);
			return false;
		}
	}
	
	/**
	 * Method to get file download path location
	 * @return - returns file download path
	 */	
	public String getDownloadLocation()
	{
		String DownloadPath= System.getProperty("user.dir")+fileSeperator+"Downloaded Files";
		File fileDest = new File(DownloadPath);
		if (!fileDest.exists()) {
			fileDest.mkdir();
		}
		return DownloadPath;
	}

	/**
	 * 
	 * This method is used to get firefox browser binary path
	 *
	 * @return , return the firefox browser binary path
	 */
	public String getBinaryPath()
	{
		return sys.getProperty("FireFoxBinaryPath");
			
	}
	
	/**
	 * 
	 * This method is used to verify whether specified firefox binary path exists or not 
	 *
	 * @return , Returns true if binary path exists, else returns false
	 */
	public boolean isBinaryPathPresent()
	{
		String binaryPath = getBinaryPath();
		try
		{
			if(!binaryPath.isEmpty())
			{
				File binaryFile = new File(binaryPath);
				return binaryFile.exists();
			}
			else
			{
				return false;
			}
		}
		catch(NullPointerException e)
		{
			log.error("Firefox Profile does not exist - "+binaryPath);
			Assert.fail("Firefox Profile does not exist - "+binaryPath);
			return false;
		}
	}
}
