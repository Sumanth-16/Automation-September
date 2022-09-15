/**************************************** PURPOSE **********************************

 - This class contains the code related to Basic setup of TestSuites such as Instantiating Browser,
 - Launching Browser from selected Configuration, perform Clean Up etc

USAGE
 - Inherit this BaseClass for any TestSuite class. You don't have to write any @Beforeclass and @AfterClass
 - actions in your TestSuite Classes

 - Example: 
 --import Com.Base
 --- public class <TestSuiteClassName> extends BaseClass
 */
package com.base;

import com.TeamsIntegration.MicrosoftTeams;
import com.datamanager.ConfigManager;
import com.listener.WebListener;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.selenium.SlackIntegration;
import com.utilities.ReportSetup;
import com.utilities.TimeOuts;
import com.utilities.UtilityMethods;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

@Listeners({ com.listener.TestListener.class })
public class BaseSetup implements TimeOuts {
	private static long startTime;
	private static long duration;
	private static String executionTime;
	private WebDriver driver;
	private WebDriver driverWithoutWebListener;
	private boolean isReportFolderCreated = true;
	private Logger log = LogManager.getLogger("BaseClass");
	private MyEventFiringWebDriver efDriver;
	private ConfigManager sys = new ConfigManager();
	ConfigManager app = new ConfigManager("App");
	private ConfigManager test = new ConfigManager("TestDependency");
	private ConfigManager fail = new ConfigManager("FailedTestCases");
	private BrowserProvider browserProvider = new BrowserProvider();
	CapabilityHelper capabilitySetter = new CapabilityHelper();
    private String userDir=System.getProperty("user.dir");
	/**
	 * Getter method for WebDriver
	 * 
	 * @return driver
	 */
	public WebDriver getDriver() {
		return driver;
	}

	/**
	 * Setter method for WebDriver
	 * 
	 * @param driver
	 *            driver
	 */
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * Creates folder structure to store the automation reports
	 * 
	 * @throws IOException
	 *             throws IO Exception
	 */
	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() throws Exception {

		//configuring log4j.xml file for generating logs
		LoggerContext context = (LoggerContext) LogManager.getContext(false);
		context.setConfigLocation(new File(userDir+File.separator+"ConfigFiles"+File.separator+"log4j2.xml").toURI());
		
		startTime = Calendar.getInstance().getTimeInMillis();
		if (sys.getProperty("SlackNotification").equalsIgnoreCase("true")||sys.getProperty("TeamsNotification").equalsIgnoreCase("true")) {
			String testStartTime = UtilityMethods.getDateTimeInSpecificFormat("yyyy-MM-dd HH:mm:ss.SSS");
			if(sys.getProperty("SlackNotification").equalsIgnoreCase("true")) {
				SlackIntegration.sendMessage(testStartTime);
			}
			if (sys.getProperty("TeamsNotification").equalsIgnoreCase("true")) {
				MicrosoftTeams.sendTeamsMessage(testStartTime);
			}
		}
		fail.cleanFailureTestCases();
		if (isReportFolderCreated) {
			ReportSetup.createFolderStructure();
			isReportFolderCreated = false;
		}
		if (sys.getProperty("LaunchDashboard").equalsIgnoreCase("Manual")) {
			log.info("Dashboard need to be launched manually");
		} else if (sys.getProperty("LaunchDashboard").equalsIgnoreCase("Automatic")) {
			/**
			 * Here we need to invoke live results using "--allow-file-access-from-files" flag
			 * That cannot be acheieved by launching report using file system
			 * Use: InvokeDashboard.main(); for launching report
			 */
			InvokeDashboard.main();

			/*
			String Url=System.getProperty("user.dir")+"\\Dashboard\\overview.html";
			File htmlFile = new File(Url);
			Desktop.getDesktop().browse(htmlFile.toURI());
			 */

		}
		log.info("<h2>--------------------SuiteRunner Log-------------------------<h2>");
	}

	/**
	 * Method initialize() is declared as part of @BeforeClass If BaseClass.java
	 * is inherited from any TestSuite class, the initialization will happen
	 * automatically The initialization() process includes read the Browser name
	 * parameter from "Config.Properties" file and launch the selected browser
	 * and navigate to the given URL
	 */
	@Parameters({ "deviceName", "deviceVersion", "deviceUrl", "udid", "platformName", "browser-Type", "browserVersion",
			"osName", "osVersion", "session" })
	@BeforeClass(alwaysRun = true)
	public void initializeBaseSetup(@Optional("Android Mobile Phone") String deviceName,
			@Optional("6.0.1") String deviceVersion, @Optional("http://0.0.0.0:4723/wd/hub") String deviceUrl,
			@Optional("1fa71036") String udid, @Optional("Desktop") String sPlatform,
			@Optional("chrome") String sBrowsername, @Optional("30") String browserVersion,
			@Optional("windows") String sOSName, @Optional("7") String OSVersion, @Optional() String session,
			ITestContext context) {

		sys.writeProperty("BrowserName", sBrowsername);
		try {
			if (sys.getProperty("ModeOfExecution").equalsIgnoreCase("Linear")) {

				sPlatform = sPlatform.toUpperCase();

				switch (sPlatform) {
				case "DESKTOP":
					launchDriver(sBrowsername, browserVersion, sOSName, OSVersion, session, context);
					break;
				case "MOBILE":
					// Android Platform local execution
					if (sOSName.equalsIgnoreCase("android")) {
						setDriver(capabilitySetter.webAppCapabilities(sOSName, deviceName, deviceUrl, udid,
								sBrowsername, sOSName));
					} // iOS Platform local execution
					else if (sOSName.equalsIgnoreCase("ios")) {
						setDriver(capabilitySetter.iOSWebAppCapabilitiesForRealDevice(udid, deviceUrl, deviceName,
								deviceVersion));
					}
					break;
				default:
					Assert.fail("Please provide platform name as 'Desktop or Mobile'");
					break;
				}

				if (driver != null) {
					driverWithoutWebListener = driver;
				   // registerWebDriverListener();
					log.info("what driver is running? " + getBrowserName());
					sys.writeProperty("CurrentlyRunningBrowserName", getBrowserName());
					context.setAttribute("driverWithoutWebListener", driverWithoutWebListener);
					context.setAttribute("driver", driver);
				//	setPageLoadTimeOut(VERYLONGWAIT);
				} else {
					Assert.fail("Driver not initialized properly .Please re-initialize the driver");
				}
			}

		} catch (Exception e) {
  		log.error(e.getMessage() + "---" + UtilityMethods.getStackTrace());
			e.printStackTrace();
		}
	}

	@Parameters({ "browser-Type", "browserVersion", "osName", "osVersion", "session" })
	@BeforeMethod(alwaysRun = true)
	public void initializeBaseSetupForRemote(@Optional("chrome") String browserType,
			@Optional("30") String browserVersion, @Optional("WINDOWS") String OSName, @Optional("7") String OSVersion,
			@Optional() String session, ITestContext context) {
		sys.writeProperty("BrowserName", browserType);
		try {
			if (sys.getProperty("ModeOfExecution").equalsIgnoreCase("Remote")) {
				launchDriver(browserType, browserVersion, OSName, OSVersion, session, context);
			}
		} catch (Exception e) {
			log.error(e.getMessage() + "---" + UtilityMethods.getStackTrace());

		}
	}

	/**
	 * Purpose - to initiate driver based on the browser
	 */
	private void initiateDriver(String browserName, String browserVersion, String OSName, @Optional String OSVersion,
			String session) {
		log.info("Browser name present in config file :" + browserName);
				
		if (sys.getProperty("ModeOfExecution").equalsIgnoreCase("Remote")) {
			log.info("-----------------STARTED RUNNING SELENIUM TESTS ON CLOUD /GRID------------------");
			setDriver(new RemoteDriver().init(browserName, browserVersion, OSName, OSVersion, session));
		} else if (sys.getProperty("ModeOfExecution").equalsIgnoreCase("Linear")) {
			log.info("-----------------STARTED RUNNING SELENIUM TESTS ON LOCAL MACHINE------------------");
			setDriver(browserName);
		} else {
			log.error("Enter valid Execution Type i.e. Linear/Remote in sys.properties");
			log.info("For now running tests in Linear Mode");
			log.info("-----------------STARTED RUNNING SELENIUM TESTS ON LOCAL MACHINE------------------");
			setDriver(browserName);
		}
		driverWithoutWebListener = driver;
		registerWebDriverListener();
		log.info("what driver is running? " + getBrowserName());
    	sys.writeProperty("CurrentlyRunningBrowserName", getBrowserName());
	}

	/**
	 * Purpose - Registers the web driver instance with EventFiringWebDriver -
	 * Returns the EventFiringWebDriver Instance to respective Browser Instance
	 * type
	 */
	private void registerWebDriverListener() {
		efDriver = new MyEventFiringWebDriver((RemoteWebDriver) driver);
		WebListener webListener = new WebListener();
		driver = efDriver.register(webListener);
	}

	/**
	 * This method sets the driver object based on browser name
	 * 
	 * @param browserName
	 *            , Need to pass the browser type
	 */
	private void setDriver(String browserName) {
		driver = browserProvider.getBrowserInstance(browserName).init();
		if (driver == null) {
			Assert.fail("WebDriver is null... Please check browser configuration");
		}
	}

	/**
	 * This method since added in "AfterClass" group and when this class is
	 * inherited from a TestSuite class, it will be called automatically
	 */
	//@AfterClass(alwaysRun = true)
	public void CloseBrowser() {
		System.out.println(driver);
		if (driver != null) {
			//driver.quit();
			driver.close();
		}
	}

	/**
	 * This method adds Log file link to ReportNG report
	 */
	@AfterSuite(alwaysRun = true)
	public void AddLogFileToReport() throws IOException, ParseException, UnirestException {
		duration = Calendar.getInstance().getTimeInMillis() - startTime;
		executionTime = String.format("%02dh:%02dm:%02ds", duration / (3600 * 1000), duration / (60 * 1000) % 60,
				duration / 1000 % 60);
		if (sys.getProperty("SlackNotification").equalsIgnoreCase("true")||sys.getProperty("TeamsNotification").equalsIgnoreCase("true")) {
			String testEndTime = UtilityMethods.getDateTimeInSpecificFormat("yyyy-MM-dd HH:mm:ss.SSS");
			if(sys.getProperty("SlackNotification").equalsIgnoreCase("true")) {
				SlackIntegration.sendMessage(testEndTime);
			}
			if(sys.getProperty("TeamsNotification").equalsIgnoreCase("true")){
				MicrosoftTeams.sendTeamsMessage(testEndTime);
			}
		}
		fail.setExecutionTime(executionTime);
		log.info("after suite");
		String sSeperator = UtilityMethods.getFileSeperator();
		String logFilePath = ".." + sSeperator + "Logs.log";
		Reporter.log("<br>");
		Reporter.log("<a class=\"cbutton\" href=\"" + logFilePath + "\">Click to Open Log File</a>");
		String PageLoadTimeSummaryFilePath = ".." + sSeperator + "PageLoadTime_Summary.html";
		File f = new File(PageLoadTimeSummaryFilePath);
		if (f.exists()) {
			Reporter.log("<br>");
			Reporter.log("<a class=\"cbutton\" href=\"" + PageLoadTimeSummaryFilePath
					+ "\">Click to Open PageLoad Time Summary File</a>");
		}

		if(!new File("Automation Reports/LatestResults/Logs.log").exists()){
			try {
				FileUtils.copyFileToDirectory(new File("Automation Reports/Logs.log"),new File("Automation Reports/LatestResults"));
				
				//if we open the report in chrome linux its showing Logs in Raw format,To avoid that ,i am converting them into .html format 
				FileUtils.copyFile(new File("Automation Reports/Logs.log"),new File("Automation Reports/LatestResults/Log.html"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * This method sets page load timeout.
	 * 
	 * @param timeOut
	 *            , Need to pass the time in seconds
	 */
	public void setPageLoadTimeOut(int timeOut) {
		/*
		 * Except for Chrome Browser, set the default Page load time out to 200
		 * seconds maximum. There was a known issue with setting PageloadTimeout
		 * for Chrome browser. This still needs to be investigated
		 */
		if (!(isSafariBrowser() || isChromeBrowser())) {
			driver.manage().timeouts().pageLoadTimeout(timeOut, TimeUnit.SECONDS);
		}
	}

	/**
	 * This method is used to know whether the dependent test has passed or not
	 * 
	 * @param dependentTestName
	 *            , Need to pass the dependent test name
	 */
	public void hasDependentTestMethodPassed(String dependentTestName) {
		String currentTestName = Thread.currentThread().getStackTrace()[2].getMethodName();
		if (test.getProperty(dependentTestName) != null) {
			if (test.getProperty(dependentTestName).equalsIgnoreCase("pass")) {
				log.info("dependent test - " + dependentTestName + " has passed. Running test - " + currentTestName);
			} else {
				log.info("dependent test - " + dependentTestName + "-failed. Hence test - " + currentTestName
						+ "-Skipped");
				throw new SkipException("Dependent test - " + dependentTestName + " has failed. Hence test - "
						+ currentTestName + "-Skipped");
			}
		} else {
			log.info("dependent test - " + dependentTestName + " did not run. Hence test - " + currentTestName
					+ "is skipped");
			throw new SkipException("Dependent test - " + dependentTestName + " did not run. Hence test - "
					+ currentTestName + "-skipped");
		}
	}

	/**
	 * This method is used to verify the specified browser is safari or not -
	 * Returns true if the specified browser is safari browser, else returns
	 * false
	 */
	public boolean isSafariBrowser() {
		return getBrowserName().equalsIgnoreCase("safari");
	}

	/**
	 * This method is used to verify the specified browser is chrome or
	 * not @return, returns true if the specified browser is chrome browser,
	 * else returns false
	 */
	public boolean isChromeBrowser() {
		return getBrowserName().equalsIgnoreCase("chrome");
	}

	/**
	 * This method is used to get current browser name - Returns current browser
	 * name
	 */
	public String getBrowserName() {
		//efDriver = new MyEventFiringWebDriver((RemoteWebDriver) driver);
		Capabilities caps = efDriver.getCapabilities();
		return caps.getBrowserName();
	}

	/**
	 * Purpose - This method will invoke the driver based on the inputs provided
	 * 
	 * @param browserType
	 *            Browser type
	 * @param browserVersion
	 *            Browser version
	 * @param OSName
	 *            OS name
	 * @param OSVersion
	 *            OS Version
	 * @param session
	 *            OS Session
	 * @param context
	 *            Context
	 */
	public void launchDriver(String browserType, String browserVersion, String OSName, String OSVersion, String session,
			ITestContext context) {
		String[] name = this.getClass().getName().split("\\.");
		session = session + " - \"" + name[name.length - 1] + "\" executed on : " + OSName + "-" + browserType + "-"
				+ browserVersion.trim();
		initiateDriver(browserType, browserVersion, OSName, OSVersion, session);
		log.info("Initiated Webdriver...");
		context.setAttribute("driverWithoutWebListener", driverWithoutWebListener);
		context.setAttribute("driver", driver);
		setDriver(driver);
		driver.manage().window().maximize();
		if ((System.getProperty("os.name").toLowerCase().contains("mac")
				|| System.getProperty("os.name").toLowerCase().contains("linux")) && isChromeBrowser()) {
			driver.manage().window().setPosition(new Point(0, 0));
			java.awt.Dimension sz = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
			org.openqa.selenium.Dimension dim = new org.openqa.selenium.Dimension((int) sz.getWidth(),
					(int) sz.getHeight());
			driver.manage().window().setSize(dim);
		}
		//setPageLoadTimeOut(VERYLONGWAIT);
	}

	public void step(String stepMessage) {
		log.info("\n---------------------------------------------\n" + stepMessage + "\n---------------------------------------------");
	}

	/**
	 * Purpose - This method will get the remote session (used to while using
	 * robotil methods)
	 * 
	 * @return SessionId
	 */
	public SessionId getRemoteSession() {
		return ((RemoteWebDriver) driverWithoutWebListener).getSessionId();
	}
}
