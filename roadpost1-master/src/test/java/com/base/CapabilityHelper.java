package com.base;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.datamanager.ConfigManager;
import com.utilities.CloudNotFoundException;
import com.utilities.UtilityMethods;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

/**
 * Different RemoteWebdriver Capabilities can be constructed by varying
 * BrowserName, Version, Platform. These capabilities are needed to define
 * initialize RemoteWebdriver, which in turn runs your tests on SauceLabs.com or
 * Testingbot.com This Helper class will define several methods to get the
 * capabilities by reading the Cloud.Browser.Name, Cloud.Browser.Platform,
 * Cloud.Browser.Version config parameters. e.g Capability is Browser: Firefox
 * Version: 14 Platform :XP
 */

public class CapabilityHelper {

	DesiredCapabilities capabilities = new DesiredCapabilities();
	// private ConfigManager sys;
	private Logger log = LogManager.getLogger("CapabilityHelper");
	private IOSDriver<MobileElement> iOSDriver;
	private WebDriver driver;

	/**
	 * Gets the desiredCapability of respective CloudType(saucelabs or
	 * testinbot)
	 * 
	 * @param browserType,
	 * @param browserVersion,
	 * @param OSName,
	 * @param OSVersion,
	 * @param session
	 *
	 * @return DesiredCapabilities
	 */
	protected DesiredCapabilities addCapability(String browserType, String browserVersion, String OSName,
			String OSVersion, String session) {
		ConfigManager sys = new ConfigManager();
		String cloudURL = sys.getProperty("Cloud.Host.URL").toLowerCase();

		if (cloudURL.contains("saucelabs")) {
			setCloudCapabulities(browserVersion, session, browserType, OSName, OSVersion);
		}

		else if (cloudURL.contains("browserstack")) {
			setCloudCapabulities(browserVersion, session, browserType, OSName, OSVersion);
		}

		else if (cloudURL.contains("Testingbot")) {
			setCloudCapabulities(browserVersion, session, browserType, OSName, OSVersion);
		}

		else if (UtilityMethods.validateIP(cloudURL.split(":")[1].substring(2))) {

			setBrowser(browserType);
		} else {
			log.error("cloud entered does not exist - " + cloudURL);
			try {
				throw new CloudNotFoundException(cloudURL);
			} catch (CloudNotFoundException e) {
				e.printStackTrace();
			}
		}
		return capabilities;
	}

	/**
	 * 
	 * This method sets the desired capabilities based on browser type
	 *
	 * @param browserType
	 *            , Need to pass the browser type
	 */
	private void setBrowser(String browserType) {
		switch (browserType.toLowerCase()) {
		case "chrome":
			capabilities = DesiredCapabilities.chrome();
			break;
		case "firefox":
			capabilities = DesiredCapabilities.firefox();
			break;

		case "iexplore":
			capabilities.setBrowserName("internet explorer");
			// capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability("ignoreProtectedModeSettings", true);
			capabilities.setCapability("enablePersistentHover", false);
			capabilities.setCapability("native_events", false);
			break;
		case "safari":
			capabilities = DesiredCapabilities.safari();
			break;
		case "opera":
			capabilities = DesiredCapabilities.operaBlink();
			break;
		default:
			log.error("browser : " + browserType + " is invalid, Launching Firefox as browser of choice..");
			capabilities = DesiredCapabilities.firefox();
		}
	}

	/**
	 * Extracted method that does common setup of setting OS/Platform where
	 * cloud browser should be launched
	 */
	private void setOperatingSystem(String OSName, String OSVersion) {
		switch (OSName.toLowerCase()) {
		case "windows":
			switch (OSVersion.toLowerCase()) {
			case "xp":
				capabilities.setCapability("platform", Platform.XP);
				break;

			case "vista":
				capabilities.setCapability("platform", Platform.VISTA);
				break;

			case "7":
				capabilities.setCapability("platform", Platform.WINDOWS);
				break;

			case "8":
				capabilities.setCapability("platform", Platform.WIN8);
				break;

			case "8_1":
				capabilities.setCapability("platform", Platform.WIN8_1);
				break;
			}

			break;
		case "mac":
			capabilities.setCapability(CapabilityType.PLATFORM, "Mac");
			break;

		case "linux":
			capabilities.setCapability("platform", Platform.LINUX);
			break;

		case "unix":
			capabilities.setCapability("platform", Platform.UNIX);
			break;

		case "any":
			capabilities.setCapability("platform", Platform.ANY);
			break;

		default:
			log.warn(
					"Please select valid platform, contact your cloud providers for valid platform list \n For now executing on cloud default OS");
			capabilities.setCapability("platform", Platform.ANY);
			break;
		}
	}

	private void setCloudCapabulities(String browserVersion, String session, String browserType, String OSName,
			String OSVersion) {

		setBrowser(browserType);
		setOperatingSystem(OSName, OSVersion);
		capabilities.setCapability("version", browserVersion);
		capabilities.setCapability("name", session);
	}

	/**
	 * Method webAppCapabilities() is declared as part of @BeforeClass for
	 * setting up of Web Application Capabilities automatically The
	 * initialization() process includes read the
	 * devicename/platformVersion/platformName/browserName.. parameter from
	 * "Config.Properties" file and launch the application in local devices or
	 * in cloud
	 * 
	 * @param platformVersion
	 * @param deviceName
	 * @param deviceUrl
	 * @param udid
	 * @param sBrowserName
	 * @param osName
	 * @return WebDriver
	 */
	public WebDriver webAppCapabilities(String platformVersion, String deviceName, String deviceUrl, String udid,
			 String sBrowserName, String osName) {
		try {
			capabilities.setJavascriptEnabled(true);
			capabilities.setCapability("appActivity", "com.google.android.apps.chrome.Main");
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, osName);
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, sBrowserName);
			capabilities.setCapability(MobileCapabilityType.UDID, udid);
			driver = new AndroidDriver<MobileElement>(new URL(deviceUrl), capabilities);
			//context.setAttribute("driver", driver);
		} catch (Exception e) {
			log.info(
					"  Capabilities provided for Web App are not correct , Please cross check your capabilites once \n");
			e.printStackTrace();
		}
		log.info("..................... Executing scripts in for Web App ...............");
		return driver;
	}

	/**
	 * Method webAppCapabilitiesForRealDevice() is declared as part
	 * of @BeforeClass for setting up of Web App Capabilities on Real Device
	 * automatically The initialization() process includes read the
	 * devicename/platformVersion/browserName/udid/app... parameter from
	 * "Device.Properties" file and launch the application in local devices or
	 * in cloud
	 * 
	 * @param udid
	 * @param sURL
	 * @param sDeviceName
	 * @param sVersion
	 * @return WebDriver
	 */
	public WebDriver iOSWebAppCapabilitiesForRealDevice(String udid, String sURL, String sDeviceName, String sVersion
			) {
		try {

			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setJavascriptEnabled(true);
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
			capabilities.setCapability("platformVersion", sVersion);
			capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.SAFARI);
			capabilities.setCapability("deviceName", sDeviceName);
			capabilities.setCapability("udid", udid);
			capabilities.setCapability("automationName", "XCUITest");
			capabilities.setCapability("safariAllowPopups", false);
			capabilities.setCapability("safariIgnoreFraudWarning", true);
			iOSDriver = new IOSDriver<MobileElement>(new URL(sURL), capabilities);
			//context.setAttribute("driver", iOSDriver);
			log.info("<******.... Scripts are running in Web Application on Real Device ...******>");
		} catch (Exception e) {

			log.error(
					"Capabilities given for Web App on Real Device might be wrong,Cross check your capabilities once...");
			e.printStackTrace();
		}
		return iOSDriver;
	}

	/**
	 * Configuring the Test in Sauce Labs
	 * 
	 * @param deviceName
	 * @param deviceVersion
	 * @param browserName
	 * @param appiumVersion
	 * @param platformName
	 * @return
	 */
	public WebDriver SauceLabWebAppCapabilities( String deviceName, String platformVersion,
			String platformName) {
		// String cloudURL = sys.getProperty("Cloud.Host.URL").toLowerCase();
		try {
		//apabilities.setCapability("appiumVersion", appiumVersion);
			capabilities.setCapability("deviceName", deviceName);
			// capabilities.setCapability("deviceOrientation",
			// config.getProperty("Device.ORIENTATION"));
			capabilities.setCapability("browserName", "Browser");
			capabilities.setCapability("platformVersion", platformVersion);
			capabilities.setCapability("platformName", platformName);
			try {
				driver = new AndroidDriver<MobileElement>(new URL("http://192.168.0.186:4444/wd/hub"), capabilities);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			log.info(
					"Failed Sauce Lab Capabilities capability setup for Web App , Please cross check your capabilities once ");
		}
		log.info("..................... Executing scripts in SAUCE LABS with Web App ............... ");

		return driver;
	}

}
