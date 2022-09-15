package com.listener;

import com.TeamsIntegration.MicrosoftTeams;
import com.Zapi.ZapiMethods;
import com.atlassian.jira.LogIssue_Jira;
import com.datamanager.ConfigManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gurock.testrail.TestRailResultUpdator;
import com.jetbrains.youtrack.LogIssue;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.testng.Assert;
import com.utilities.ReportSetup;
import com.utilities.ScreenCapture;
import com.utilities.UtilityMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.BuildInfo;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.*;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.selenium.SlackIntegration.testCasesCount;

public class TestListener extends TestListenerAdapter implements ISuiteListener {

	private static char cQuote = '"';
	private ConfigManager sys = new ConfigManager();
	private ConfigManager depend = new ConfigManager("TestDependency");
	private ConfigManager failures = new ConfigManager("FailedTestCases");
	private static String fileSeperator = System.getProperty("file.separator");
	private Logger log = LogManager.getLogger("TestListener");
	private final String PASSED = "PASSED";
	private final String FAILED = "FAILED";
	private final String SKIPPED = "SKIPPED";
	private final String DASHBOARD_DIR = System.getProperty("user.dir") + fileSeperator + "Dashboard";

	private TestRailResultUpdator testRailResultUpdator = new TestRailResultUpdator();
	private ZapiMethods zapiMethods = new ZapiMethods();
	private int projectID, issueID;
	private String executionID;

	public static List<Map<String, String>> list = new ArrayList<Map<String, String>>();

	private int noOfTestsPassed;
	public int noOfTestsFailed;
	public int noOfTestsSkipped;

	private static String suiteName = "";
	private static String runID = "";
	private String testStartTime = null;
	private String testEndTime = null;
	private String testSerialNo = null;

	private static boolean milestoneCreated = false;

	private String nodevideoUrl;
	private String sessionId;
	private String sModeOfExecution;

	private String startTime = null;
	private String endTime = null;
	private String sBrowserName = null;

	/**
	 * This method will be called if a test case is failed. Purpose - For attaching
	 * captured screenshots and videos in ReportNG report
	 */
	public void onTestFailure(ITestResult result) {
		String sTestName = getTestCaseName(result);
		depend.writeProperty(result.getName(), "Fail");
		failures.writePropertyForFailures(result.getMethod().getXmlTest().getName(), result.getName(), "fail");
		log.error("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
		log.error("ERROR ----------" + result.getName() + " has failed-----------------");
		log.error("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
		ITestContext context = result.getTestContext();
		WebDriver driver = (WebDriver) context.getAttribute("driver");
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		Reporter.setCurrentTestResult(result);
		// update results to testrail
		String browserDetails = (String) ((JavascriptExecutor) driver).executeScript("return navigator.userAgent;");
		// String id = getCaseId(result.getName());
		String id = testRailResultUpdator.returnTestId(result);
		updateToTestRail(id, result.getName(), 5, UtilityMethods.getBrowserName(browserDetails),
				result.getThrowable().getMessage());
		if (sys.getProperty("updateZephyr").equalsIgnoreCase("true")) {
			String cycleName = zapiMethods.returnTestAnnotationParameters(result, 2);
			executionID = zapiMethods.getExecutionID(projectID, issueID, "GET", cycleName);
			zapiMethods.updateExecutionStatusinZephyr(executionID, "PUT", ZapiMethods.Status.FAIL, issueID, projectID);
			log.info("Updated test result in Zephyr sucessfully");
		}
		String screenshotName = ScreenCapture.saveScreenShot(driver);
		String screenshot = System.getProperty("user.dir") + fileSeperator + "Automation Reports" + fileSeperator
				+ "LatestResults" + fileSeperator + "Screenshots" + fileSeperator + screenshotName;
		Throwable errorMessage = result.getThrowable();
		String sErrorMessage;
		try {
			sErrorMessage = result.getThrowable().getMessage();
		} catch (NullPointerException e) {
			sErrorMessage = "No Error message to display";
		}
		if (errorMessage != null) {
			if (sys.getProperty("issueLogToJira").equalsIgnoreCase("true")) {

				new LogIssue_Jira().createIssueInZira(result.getName(), UtilityMethods.getBrowserName(browserDetails),
						result.getThrowable().getMessage(), UtilityMethods.getStackTraceFromListners(errorMessage),
						screenshot);
			}
			new LogIssue().createIssueInYouTrack(result.getName(), result.getThrowable().getMessage(),
					UtilityMethods.getStackTraceFromListners(errorMessage), screenshot);
		}
		String imagepath = ".." + fileSeperator + "Screenshots" + fileSeperator + screenshotName;
		createAttachment(driver);
		String sDashboardImagepath = System.getProperty("user.dir") + fileSeperator + "Automation Reports"
				+ fileSeperator + "LatestResults" + fileSeperator + "Screenshots" + fileSeperator + screenshotName;
		Reporter.log("<a href=" + cQuote + sDashboardImagepath + cQuote + ">" + " <img src=" + cQuote
				+ sDashboardImagepath + cQuote + " height=48 width=48 ></a>");
		String dateAndTime = ScreenCapture.stopVideoCapture(result.getName());

		String sTimeTaken = Long.toString((result.getEndMillis() - result.getStartMillis()) / 1000);
		sTimeTaken = getTimeInMins(sTimeTaken);
		updateRuntimeReport(result, Thread.currentThread().getId(), sTestName, FAILED, sErrorMessage,
				sDashboardImagepath, sTimeTaken);
		// updateRuntimeReport(result, Thread.currentThread().getId(), FAILED);
		UtilityMethods.verifyPopUp();
		sModeOfExecution = new ConfigManager().getProperty("ModeOfExecution");
		String sValue = new ConfigManager().getProperty("VideoCapture");
		String sVideoPath = "";
		if (sValue.equalsIgnoreCase("true") && sModeOfExecution.equalsIgnoreCase("linear")) {

			sVideoPath = testCaseVideoRecordingLink(result.getName(), dateAndTime);
			Reporter.log("<a href=" + cQuote + sVideoPath + cQuote + " style=" + cQuote
					+ "text-decoration: none; color: white;" + cQuote
					+ "><div class = cbutton>Download Video</div></a>");
			Reporter.log("<font color='Blue' face='verdana' size='2'><b>" + Assert.doAssert() + "</b></font>");

		} else {
			if ((new ConfigManager().getProperty("RemoteVideo")).equalsIgnoreCase("true"))
				nodeVideoRecording(driver, result);

		}
		Reporter.setCurrentTestResult(null);

		testEndTime = UtilityMethods.getDateTimeInSpecificFormat("yyyy-MM-dd HH:mm:ss.SSS");
		/*
		 * String execDate = UtilityMethods.getDateTimeInSpecificFormat("yyyy-MM-dd");
		 * String testName = result.getName(); String functionalArea =
		 * result.getTestClass().getName(); functionalArea =
		 * functionalArea.split("testsuite.")[1].split("\\.")[0]; functionalArea =
		 * functionalArea.substring(0, 1).toUpperCase() + functionalArea.substring(1);
		 * String browserInfo[] = getBrowserNameandVersion(context); String errormessage
		 * = result.getThrowable().getMessage(); String failStackTrace =
		 * UtilityMethods.getStackTraceFromListners(errorMessage); String testOS =
		 * System.getProperty("os.name"); String testMachine = ""; try { testMachine =
		 * InetAddress.getLocalHost().getHostName(); } catch (UnknownHostException e) {
		 * e.printStackTrace(); }
		 */

	}

	private void updateRuntimeReport(ITestResult result, long theadId, String sName, String sStatus,
			String sErrorMessage, String sAbsoluteImagePath, String executionTime) {
		int count = 0;
		// int dpmethodcount = 0;
		StringBuffer param = new StringBuffer();
		String p = "";
		param.append("(");
		ConfigManager rs = new ConfigManager(getFilePath(theadId));
		// ConfigManager rs = new ConfigManager());
		Object[] params = result.getParameters();
		for (Object obj : params) {
			param.append(obj + ",");
		}
		// param.append(")");
		if (param.length() > 2) {
			count++;
			p = param.substring(0, param.length() - 2) + ")";
		} else {
			count = 0;
		}
		// rs.writeToDashboard(result.getMethod().getRealClass().getName() + "-"
		// + result.getName() + p, strStatus);
		rs.writeToDashboard(result.getMethod().getRealClass().getName() + "-" + result.getName() + p,
				sStatus + ":-" + sErrorMessage + ":-" + sAbsoluteImagePath + ":-" + executionTime);
		rs = null;

	}

	private String getTimeInMins(String sTimeTaken) {
		float fTimeTaken;
		fTimeTaken = Float.parseFloat(sTimeTaken);
		if (fTimeTaken > 60) {
			fTimeTaken = fTimeTaken / 60;
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(2);
			sTimeTaken = nf.format(fTimeTaken) + " MINS";
			return sTimeTaken;
		} else {
			sTimeTaken = sTimeTaken + " SECS";
			return sTimeTaken;
		}
	}

	/**
	 * Method to capture screenshot for allure reports
	 *
	 * @param driver , need to pass the driver object
	 * @return , returns the captured image file in the form of bytes
	 */
	@Attachment(value = "Screenshot", type = "image/png")
	private byte[] createAttachment(WebDriver driver) {
		try {
			return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		} catch (Exception e) {
			log.error(
					"An exception occured while saving screenshot of current browser window from createAttachment method.."
							+ e.getCause());
			return null;
		}
	}

	public void test() {

	}

	/**
	 * This method will be called if a test case is skipped.
	 *
	 */
	public void onTestSkipped(ITestResult result) {
		log.warn("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		log.warn("WARN ------------" + result.getName() + " has skipped-----------------");
		log.warn("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		String sTimeTaken = Long.toString((result.getEndMillis() - result.getStartMillis()) / 1000);
		sTimeTaken = getTimeInMins(sTimeTaken);
		String sTestName = getTestCaseName(result);
		if (sTimeTaken == null) {
			updateRuntimeReport(result, Thread.currentThread().getId(), sTestName, SKIPPED, "------", "null", "NA");
		} else {
			updateRuntimeReport(result, Thread.currentThread().getId(), sTestName, SKIPPED,
					result.getThrowable().getMessage(), "null", sTimeTaken);
		}

		/* updateRuntimeReport(result, Thread.currentThread().getId(), SKIPPED); */
		depend.writeProperty(result.getName(), "Skip");

		// ************* comment below code if you are using TestNG dependency
		// methods
		ITestContext context = result.getTestContext();
		WebDriver driver = (WebDriver) context.getAttribute("driver");
		String browserDetails = (String) ((JavascriptExecutor) driver).executeScript("return navigator.userAgent;");
		String id = testRailResultUpdator.returnTestId(result);
		updateToTestRail(id, result.getName(), 2, UtilityMethods.getBrowserName(browserDetails));
		Reporter.setCurrentTestResult(result);
		ScreenCapture.stopVideoCapture(result.getName());
		UtilityMethods.verifyPopUp();
		Reporter.setCurrentTestResult(null);

	}

	/**
	 * This method will be called if a test case is passed. Purpose - For attaching
	 * captured videos in ReportNG report
	 */
	public void onTestSuccess(ITestResult result) {
		System.out.println();
		depend.writeProperty(result.getName(), "Pass");
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		ITestContext context = result.getTestContext();
		WebDriver driver = (WebDriver) context.getAttribute("driver");
		log.info("###############################################################");
		log.info("SUCCESS ---------" + result.getName() + " has passed-----------------");
		log.info("###############################################################");
		noOfTestsPassed = ++noOfTestsPassed;
		String browserDetails = (String) ((JavascriptExecutor) driver).executeScript("return navigator.userAgent;");
		// String id = getCaseId(result.getName());
		String id = testRailResultUpdator.returnTestId(result);
		updateToTestRail(id, result.getName(), 1, UtilityMethods.getBrowserName(browserDetails));
		if (sys.getProperty("updateZephyr").equalsIgnoreCase("true")) {
			String cycleName = zapiMethods.returnTestAnnotationParameters(result, 2);
			executionID = zapiMethods.getExecutionID(projectID, issueID, "GET", cycleName);
			zapiMethods.updateExecutionStatusinZephyr(executionID, "PUT", ZapiMethods.Status.PASS, issueID, projectID);
			log.info("Updated test result in Zephyr sucessfully");
		}
		Reporter.setCurrentTestResult(result);
		String dateAndTime = ScreenCapture.stopVideoCapture(result.getName());
		String sTestName = getTestCaseName(result);
		String sTimeTaken = Long.toString((result.getEndMillis() - result.getStartMillis()) / 1000);
		sTimeTaken = getTimeInMins(sTimeTaken);
		updateRuntimeReport(result, Thread.currentThread().getId(), sTestName, PASSED, "         ", "null", sTimeTaken);
		// UtilityMethods.verifyPopUp();
		String sValue = new ConfigManager().getProperty("VideoCapture");
		sModeOfExecution = new ConfigManager().getProperty("ModeOfExecution");
		String sVideoPath = "";
		if (sValue.equalsIgnoreCase("true") && sModeOfExecution.equalsIgnoreCase("linear")) {
			sVideoPath = testCaseVideoRecordingLink(result.getName(), dateAndTime);
			Reporter.log("<a href=" + cQuote + sVideoPath + cQuote + " style=" + cQuote
					+ "text-decoration: none; color: white;" + cQuote
					+ "><div class = cbutton>Download Video</div></a>");
		} else {
			if ((new ConfigManager().getProperty("RemoteVideo")).equalsIgnoreCase("true"))
				nodeVideoRecording(driver, result);
		}
		Reporter.setCurrentTestResult(null);

		testEndTime = UtilityMethods.getDateTimeInSpecificFormat("yyyy-MM-dd HH:mm:ss.SSS");
		/*
		 * String execDate = UtilityMethods.getDateTimeInSpecificFormat("yyyy-MM-dd");
		 * String testName = result.getName(); String functionalArea =
		 * result.getTestClass().getName(); functionalArea =
		 * functionalArea.split("testsuite.")[1].split("\\.")[0]; functionalArea =
		 * functionalArea.substring(0, 1).toUpperCase() + functionalArea.substring(1);
		 * // String browserInfo[] = getBrowserNameandVersion(context); String testOS =
		 * System.getProperty("os.name"); String testMachine = "";
		 */
		/*
		 * try { testMachine = InetAddress.getLocalHost().getHostName(); } catch
		 * (UnknownHostException e) { e.printStackTrace(); }
		 */

	}

	/**
	 * purpose to get the test name
	 * 
	 * @return returns the testcase name
	 */
	private String getTestCaseName(ITestResult result) {
		return result.getName();
	}

	/**
	 * This method will be called before a test case is executed. Purpose - For
	 * starting video capture and launching balloon popup in ReportNG report
	 */
	public void onTestStart(ITestResult result) {
		// testStartTime = getDateWithTime();
		WebDriver driver = (WebDriver) result.getTestContext().getAttribute("driver");
		sBrowserName = (String) ((JavascriptExecutor) driver).executeScript("return navigator.userAgent;");
		String sTestName = getTestCaseName(result);
		testStartTime = UtilityMethods.getDateTimeInSpecificFormat("yyyy-MM-dd HH:mm:ss.SSS");
		log.info("<h2>**************CURRENTLY RUNNING TEST************ " + result.getName() + "</h2>");
		createFile(Thread.currentThread().getId());
		sModeOfExecution = new ConfigManager().getProperty("ModeOfExecution");
		if (sModeOfExecution.equalsIgnoreCase("Remote")) {
			ITestContext context = result.getTestContext();
			RemoteWebDriver rDriver = (RemoteWebDriver) context.getAttribute("driverWithoutWebListener");
			String[] data = UtilityMethods.getNodevideoUrl(rDriver);
			nodevideoUrl = data[1];
			sessionId = data[0];
		}
		if (sys.getProperty("updateZephyr").equalsIgnoreCase("true")) {
			String testid = zapiMethods.returnTestAnnotationParameters(result, 1);
			int[] issue_projectIDs = zapiMethods.getIssue_project_IDs(testid);
			issueID = issue_projectIDs[0];
			projectID = issue_projectIDs[1];
			log.info("Get the ProjectID, IssueID brfore test starts");
		}
		updateRuntimeReport(result, Thread.currentThread().getId(), sTestName, "Started execution", "              ",
				"null", "          ");
		ScreenCapture.startVideoCapture();
		// UtilityMethods.currentRunningTestCaseBalloonPopUp(result.getName());
	}

	public void onStart(ITestContext context) {
	}

	public void onFinish(ITestContext context) {
		Iterator<ITestResult> failedTestCases = context.getFailedTests().getAllResults().iterator();
		Iterator<ITestResult> skippedTestCases = context.getSkippedTests().getAllResults().iterator();
		while (skippedTestCases.hasNext()) {
			ITestResult skippedTestCase = skippedTestCases.next();
			ITestNGMethod smethod = skippedTestCase.getMethod();
			// If the same test case has more than one skipped result status the
			// following code will get executed
			if (context.getSkippedTests().getResults(smethod).size() > 1) {
				if (sys.getProperty("KeepSkippedResult").equalsIgnoreCase("false")) {
					skippedTestCases.remove();
				}
			}
			// If the same test case one skip and other either pass or fail this
			// will get executed.
			else {
				if (context.getPassedTests().getResults(smethod).size() > 0
						|| context.getFailedTests().getResults(smethod).size() > 0) {
					if (sys.getProperty("KeepSkippedResult").equalsIgnoreCase("false")) {
						skippedTestCases.remove();
					}
				}
			}
		}
		while (failedTestCases.hasNext()) {
			ITestResult failedTestCase = failedTestCases.next();
			ITestNGMethod method = failedTestCase.getMethod();
			if (context.getFailedTests().getResults(method).size() > 1) {
				if (sys.getProperty("KeepFailedResult").equalsIgnoreCase("false")) {
					// log.info("failed test case remove as dup:" +
					// failedTestCase.getTestClass().toString());
					failedTestCases.remove();
				}
			} else {
				if (context.getPassedTests().getResults(method).size() > 0) {
					if (sys.getProperty("KeepFailedResult").equalsIgnoreCase("false")) {
						// log.info("failed test case remove as pass retry:" +
						// failedTestCase.getTestClass().toString());
						failedTestCases.remove();
					}
				}
			}
		}
	}

	/**
	 *
	 * To identify the latest captured screenshot
	 * 
	 */
	public String capturedScreenShot() {

		File mediaFolder = new File(ReportSetup.getImagesPath());
		File[] files = mediaFolder.listFiles();
		Arrays.sort(files, new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				// return new Long(((File)o1).lastModified()).compareTo(new
				// Long(((File)o2).lastModified())); // for ascending order
				return -1 * (new Long(((File) o1).lastModified()).compareTo(new Long(((File) o2).lastModified()))); // for
				// descending
				// order
			}
		});
		return files[0].getName();
	}

	/**
	 *
	 * This method is used to rename the captured video with test case name
	 *
	 * @param tname , Need to pass the test case name @return, Returns the captured
	 *              video path name
	 */
	private String testCaseVideoRecordingLink(String tname, String dateAndTIme) {

		String sVideoPath = ".." + fileSeperator + "Videos" + fileSeperator + tname + dateAndTIme + ".avi";
		return sVideoPath;
		/*
		 * if (new File(ReportSetup.getVideosPath() + fileSeperator + tname +
		 * "(1).avi").exists()) { return sVideoPath; } else { String sVideoPath2 =
		 * sVideoPath.substring(0, sVideoPath.length() - 7) +
		 * UtilityMethods.getCurrentDateTime()+ ".avi"; return sVideoPath2; }
		 */
	}

	@Override
	public void onFinish(ISuite arg0) {
		int totalTestCount = arg0.getAllMethods().size();
		int successPercentage = (int) (noOfTestsPassed * 100 / totalTestCount);
		int baseSuccessPercentage = 65;
		if (sys.getProperty("SlackNotification").equalsIgnoreCase("true")
				|| sys.getProperty("TeamsNotification").equalsIgnoreCase("true")) {
			int passedCount = 0;
			int failedCount = 0;
			int skippedCount = 0;
			String suiteName = arg0.getName();
			// Getting the results for the said suite
			Map<String, ISuiteResult> suiteResults = arg0.getResults();
			for (ISuiteResult sr : suiteResults.values()) {
				ITestContext tc = sr.getTestContext();
				passedCount = passedCount + tc.getPassedTests().getAllResults().size();
				failedCount = failedCount + tc.getFailedTests().getAllResults().size();
				skippedCount = skippedCount + tc.getSkippedTests().getAllResults().size();
			}
			int totalCount = passedCount + failedCount + skippedCount;
			try {
				if (sys.getProperty("SlackNotification").equalsIgnoreCase("true")) {
					testCasesCount(totalCount, passedCount, failedCount, skippedCount);
				}
				if (sys.getProperty("TeamsNotification").equalsIgnoreCase("true")) {
					MicrosoftTeams.testCasesCountTeams(totalCount, passedCount, failedCount, skippedCount);
				}
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (UnirestException e) {
				e.printStackTrace();
			}
		}
		end();
		ConfigManager suiteFile = new ConfigManager(getFilePath("suiteInfo"));
		suiteFile.writeToDashboard("SUITE_STATUS", "COMPLETED");
		suiteFile.writeToDashboard("SUITE_EXECUTION_DURATION", getTotalTime());
		suiteFile.writeToDashboard("BROWSER_NAME", UtilityMethods.getBrowserName(sBrowserName).toUpperCase());
		suiteFile.writeToDashboard("SELENIUM_VERSION", new BuildInfo().getReleaseLabel());
		suiteFile.writeToDashboard("JAVA_VERSION", "Java " + System.getProperty("java.version"));
		suiteFile.writeToDashboard("OS_NAME", System.getProperty("os.name"));
		suiteFile.writeToDashboard("SYSTEM_NAME", System.getProperty("user.name"));

	}

	@Override
	public void onStart(ISuite iSuite) {
		start();
		for (File file : UtilityMethods.fileList(DASHBOARD_DIR, ".properties")) {
			file.delete();
		}
		File dir = new File(DASHBOARD_DIR);
		if (!dir.exists()) {
			dir.mkdir();
		}
		createFile("suiteInfo");
		suiteName = iSuite.getName();
		// runID =iSuite.getName()+"_"+generateRandomRunID(suiteName);
		runID = suiteName + "_" + UtilityMethods.getDateTimeInSpecificFormat("ddMMyyHHmmss");
		ConfigManager suiteFile = new ConfigManager(getFilePath("suiteInfo"));
		suiteFile.writeToDashboard("TOTAL_TEST_SCRIPTS", String.valueOf(iSuite.getAllMethods().size()));
		suiteFile.writeToDashboard("SUITE_NAME", iSuite.getName());
		if (sys.getProperty("UpdateResultsToTestRail").equalsIgnoreCase("true")) {
			if (!milestoneCreated) {
				testRailResultUpdator.createMilestoneAndAddRuns();
				milestoneCreated = true;
			}
		}
	}

	private String getFilePath(long ld) {
		return DASHBOARD_DIR + fileSeperator + ld;
	}

	private String getFilePath(String suiteInfo) {
		return DASHBOARD_DIR + fileSeperator + suiteInfo;
	}

	private void createFile(long ld) {
		File file = new File(getFilePath(ld) + ".properties");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("Can not create file in specified path : " + file.getAbsolutePath()
						+ UtilityMethods.getStackTrace());
				Assert.fail("Can not create file in specified path : " + file.getAbsolutePath() + e.getMessage());
			}
			file = null;
		}
	}

	private void createFile(String suiteInfo) {
		File file = new File(getFilePath(suiteInfo) + ".properties");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("Can not create file in specified path : " + file.getAbsolutePath()
						+ UtilityMethods.getStackTrace());
				Assert.fail("Can not create file in specified path : " + file.getAbsolutePath() + e.getMessage());
			}
			file = null;
		}
	}

	/**
	 *
	 * This method is used to rename the captured video with test case name
	 *
	 * @param tname      , Need to pass the test case name
	 * @param sessionId, Need to pass the remote webdriver session id
	 * @return tcNameVideo, Returns the captured video path name
	 */
	public String testCaseGridVideoRecordingLink(String tname, String sessionId) {
		String sessionVideo = ReportSetup.getVideosPath() + fileSeperator + sessionId + ".mp4";
		File sessionVideoFile = new File(sessionVideo);
		String tcNameVideo = ReportSetup.getVideosPath() + fileSeperator + tname + ".mp4";
		File tcNameFile = new File(tcNameVideo);
		if (sessionVideoFile.renameTo(tcNameFile)) {
			log.info("renamed");
		} else {
			log.error("Error - File is not renamed");
		}
		if (tcNameFile.exists()) {
			return tcNameVideo;
		} else {
			log.error("Error - File is not found");
		}
		return tcNameVideo;
	}

	/**
	 * This method is used to update the results to testrail
	 *
	 * @param id         - test case id
	 * @param testName   - name of the test method
	 * @param testStatus - test method status
	 */
	private void updateToTestRail(String id, String testName, int testStatus, String current_Browser,
			String... error_Message) {
		if (sys.getProperty("UpdateResultsToTestRail").equalsIgnoreCase("true")) {
			if (UtilityMethods.isNumeric(id))
				testRailResultUpdator.addResultToTestRail(testStatus, Integer.parseInt(id), current_Browser,
						error_Message);
		}
	}

	/**
	 * This method is used to get the test case id
	 *
	 * @param strTestName - name of the test method
	 * @return test case id if test method contains id, otherwise return null
	 */
	private String getCaseId(String strTestName) {
		if (sys.getProperty("UpdateResultsToTestRail").equalsIgnoreCase("true")) {
			String[] arr = strTestName.split("_");
			return arr[arr.length - 1];
		} else
			return null;
	}

	private void captureTestStatusIntoDB(ITestResult result, String testResult, String errorMessage, String img) {

		// Connection String related values fetched from sys.properties file
		String JDBC_DRIVER = sys.getProperty("JDBC_DRIVER");
		String DB_URL = sys.getProperty("DB_URL");
		String USERNAME = sys.getProperty("USERNAME");
		String PASSWORD = sys.getProperty("PASSWORD");
		String TABLENAME = sys.getProperty("TABLENAME");

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			// Registering JDBC driver
			Class.forName(JDBC_DRIVER);

			// Opening a connection
			log.info("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

			// Creating statement
			log.info("Creating statement...");
			stmt = conn.createStatement();

			String toolName = "Selenium";
			String projectName = "SeleniumJavaFramework";
			String testName = result.getName();

			long l1 = result.getStartMillis();
			long l2 = result.getEndMillis();
			Date date1 = new Date(l1);
			String timeStamp = date1.toString();

			long time = l2 - l1;
			String executionTime = String.valueOf(time / 1000);

			String desktoposname = System.getProperty("os.name");

			ITestContext context = result.getTestContext();
			WebDriver wd = (WebDriver) context.getAttribute("driverWithoutWebListener");
			Capabilities cap = ((RemoteWebDriver) wd).getCapabilities();
			String browserName = cap.getBrowserName();
			String browserVersion = cap.getVersion();

			String local_remote = sys.getProperty("ModeOfExecution");

			String insertQuery = "INSERT INTO " + TABLENAME
					+ " (toolname, projectname, testname, result, errormessage, timestamp, screenshot,mobiledeviceosname,mobiledeviceosversion,mobiledevicemodel,desktoposname,desktopbrowsername,desktopbrowserversion,executiontime,localorremote) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement statement = conn.prepareStatement(insertQuery);
			statement.setString(1, toolName);
			statement.setString(2, projectName);
			statement.setString(3, testName);
			statement.setString(4, testResult);
			statement.setString(5, errorMessage);
			statement.setString(6, timeStamp);
			if (testResult.equalsIgnoreCase("FAILED")) {
				File file = new File(img);
				FileInputStream fis = new FileInputStream(file);
				int len = (int) file.length();
				statement.setBinaryStream(7, fis, len);
			} else {
				statement.setBinaryStream(7, null);
			}

			statement.setString(8, "");
			statement.setString(9, "");
			statement.setString(10, "");
			statement.setString(11, desktoposname);
			statement.setString(12, browserName);
			statement.setString(13, browserVersion);
			statement.setString(14, executionTime);
			statement.setString(15, local_remote);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				log.info("Current test's status was inserted into DB table successfully!");
			} else {
				log.info("Unable to insert current test's status into DB!");
			}

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
			log.info(" SQLException -- " + se.getMessage());
		} catch (Exception e) {
			// Handle errors for Class.forName
			// e.printStackTrace();
			log.error("Exception occurred is -- " + e.getMessage());
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
				log.info("Finally Statement cannot be closed due to SQLException -- " + se2.getMessage());
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
				log.info("Finally Connection cannot be closed due to SQLException -- " + se.getMessage());
			} // end finally try
		} // end try
		log.info("DB part is done");
	}

	/**
	 * Purpose -This method records video of the node machine in webm html
	 */
	private void nodeVideoRecording(WebDriver driver, ITestResult result) {
		String dateAndTime = UtilityMethods.getCurrentDateTime();
		driver.quit();
		String fileName = UtilityMethods.getNodeVideoFileName(sessionId);
		String sVideoPath = ReportSetup.getVideosPath() + fileSeperator;
		UtilityMethods.copyNodeVideoFile(fileName, sVideoPath, result.getName(), dateAndTime);
		String srVideoPath = ".." + fileSeperator + "Videos" + fileSeperator + result.getName() + dateAndTime + ".webm";
		Reporter.log("<a href=" + cQuote + srVideoPath + cQuote + " style=" + cQuote
				+ "text-decoration: none; color: white;" + cQuote + "><div class = cbutton>Play Video</div></a>");
		Reporter.log("<font color='Blue' face='verdana' size='2'><b>" + Assert.doAssert() + "</b></font>");
	}

	private String[] getBrowserNameandVersion(ITestContext context) {
		WebDriver wd = (WebDriver) context.getAttribute("driverWithoutWebListener");
		Capabilities cap = ((RemoteWebDriver) wd).getCapabilities();
		String browserName = cap.getBrowserName();
		System.out.println(browserName);
		String browserVersion = cap.getVersion();
		return new String[] { browserName, browserVersion };
	}

	private String getCurrentTime() {
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		return sdf.format(cal.getTime());
	}

	private String getEndTime() {
		return endTime;
	}

	private String getTotalTime() {
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		try {
			return timeDifference(format.parse(getStartTime()), format.parse(getEndTime()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String timeDifference(Date d1, Date d2) {
		long diff = d2.getTime() - d1.getTime();
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		return (diffHours + ":" + diffMinutes + ":" + diffSeconds);
	}

	private String getStartTime() {
		return startTime;
	}

	private void end() {
		endTime = getCurrentTime();
	}

	private void start() {
		startTime = getCurrentTime();
	}
}