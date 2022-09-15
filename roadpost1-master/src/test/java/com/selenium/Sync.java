/* ************************************** PURPOSE **********************************

 - This class contains all synchronization methods
*/


package com.selenium;




import java.util.concurrent.TimeUnit;

import com.utilities.ReportSetup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//import com.datamanager.ConfigManager;
import com.testng.Assert;
import com.utilities.TimeOuts;




public class Sync implements TimeOuts
{
	private Logger log = LogManager.getLogger("Sync");
	//ConfigManager app = new ConfigManager("App");
	private WebDriver driver;

	public Sync(WebDriver driver)
	{
		this.driver = driver;
	}

	String getTestCasename()
	{

		int len= Thread.currentThread().getStackTrace().length;
		String testCaseName = "null";
		for(int tcName=0;tcName<=len;tcName++)
		{
			if(Thread.currentThread().getStackTrace()[tcName].getMethodName().contains("invoke"))
			{
				testCaseName = Thread.currentThread().getStackTrace()[tcName-1].getMethodName();
				break;
			}


		}
		return testCaseName+":";

	}
	/**
	 * Sets implicitWait to ZERO. This helps in making explicitWait work...
	 */
	protected void nullifyImplicitWait()
	{
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait()
	}

	/**
	 * Set driver's implicitlyWait() time according given waitTime.
	 * @param waitTimeInSeconds (int) The time in seconds to specify implicit wait
	 */
	protected void setImplicitWait(int waitTimeInSeconds)
	{
		driver.manage().timeouts().implicitlyWait(waitTimeInSeconds, TimeUnit.SECONDS);
	}

	/**
	 * Waits for the Condition of JavaScript.
	 * @param javaScript (String) The javaScript condition we are waiting. e.g. "return (xmlhttp.readyState >= 2 && xml http.status == 200)"
	 * @param timeOutInSeconds (int) The time in seconds to wait until returning a failure
	 * @return True (boolean) if javaScript condition is satisfied within timeOutInSeconds
	 **/
	protected boolean waitForJavaScriptCondition(final String javaScript, int timeOutInSeconds)
	{
		boolean jscondition = false;
		nullifyImplicitWait();
		try
		{
			new WebDriverWait(driver, timeOutInSeconds).until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver driverObject) {
					return (Boolean) ((JavascriptExecutor) driverObject).executeScript(javaScript);
				}
			});
			jscondition = (Boolean) ((JavascriptExecutor) driver).executeScript(javaScript);
		}
		catch (Exception e)
		{
			log.error(getTestCasename()+"js condition not satisfied.." + " - " + e);
			Assert.fail("js condition not satisfied..");
		}
		setImplicitWait(IMPLICITWAIT);
		return jscondition;
	}



	/**
	 * Waits for an element till the timeout expires
	 * @param locator (By) locator object of the element to be found
	 * @param waitTime (int) The time in seconds to wait until returning a failure
	 * @return - True (Boolean) if element is located within timeout period else false
	 */
	protected boolean isElementPresent(By locator, int waitTime)
	{
    	/*int len= Thread.currentThread().getStackTrace().length;
    	String testCaseName = "null";
    	for(int i=0;i<=len;i++)
    	{
    		if(Thread.currentThread().getStackTrace()[i].getMethodName().contains("invoke"))
    		{
    			testCaseName = Thread.currentThread().getStackTrace()[i-1].getMethodName();
    			break;
    		}


    	}
    	log.info(getTestCasename()+"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
      	for(int j=0;j<len;j++)
    	{
    		log.info(getTestCasename()+"loop--->"+Thread.currentThread().getStackTrace()[j].getMethodName()+"<-----\n");
    	}
    	log.info(getTestCasename()+"Test case name is------>"+testCaseName);
    	log.info(getTestCasename()+"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");*/
		boolean bFlag = false;
		//	nullifyImplicitWait();
		log.info(getTestCasename()+"Waiting for presence of element " + locator);
		try
		{
			WebDriverWait wait = new WebDriverWait(driver, waitTime);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			if(driver.findElement(locator).isDisplayed()||driver.findElement(locator).isEnabled())
			{
				bFlag = true;
				log.info(getTestCasename()+"Element " + locator + " is displayed");
			}
		}
		catch (NoSuchElementException e)
		{
			log.info(getTestCasename()+"Element " + locator + " was not found in DOM in time - "+waitTime+" Seconds - NoSuchElementException");
		}
		catch (TimeoutException e)
		{
			log.info(getTestCasename()+"Element " + locator + " was not displayed in time - "+waitTime+" Seconds"+" - TimeoutException");
		}
		catch (Exception e)
		{

			log.error(getTestCasename()+"Element " + locator + " is not found - " + e);
//			Assert.fail("Element " + locator + " is not displayed");
		}
		setImplicitWait(IMPLICITWAIT);
		return bFlag;
	}


	/**
	 * Waits for an alert till the timeout expires
	 * @param waitTime (int) The time in seconds to wait until returning a failure
	 * @return - True (Boolean) if element is located within timeout period else false
	 */
	protected boolean isAlertPresent(int waitTime)
	{


		boolean bFlag = false;
		nullifyImplicitWait();
		log.info(getTestCasename()+"Waiting for presence of alert");
		try
		{
			WebDriverWait wait = new WebDriverWait(driver, waitTime);
			wait.until(ExpectedConditions.alertIsPresent());//presenceOfElementLocated(locator));
			Alert alert=driver.switchTo().alert();
			bFlag = true;
			log.info(getTestCasename()+"Alert with text '"+alert.getText()+"' is displayed");
		}
		catch (NoAlertPresentException e)
		{
			log.info(getTestCasename()+"alert not present in time - "+waitTime+" Seconds - NoAlertPresentException");
		}
		catch (TimeoutException e)
		{
			log.info(getTestCasename()+"alert not present in time - "+waitTime+" Seconds - TimeoutException");
		}
		catch (Exception e)
		{
			log.info(getTestCasename()+"alert not present - " + e);
//			Assert.fail("alert not present");
		}
		setImplicitWait(IMPLICITWAIT);
		return bFlag;
	}


    /* *
     * Waits for an element till the timeout expires - implemented using SeleniumRC
	 * @param driver (WebDriver) The driver object to be used to wait and find the element
	 * @param bylocator (By) locator object of the element to be found
	 * @param waitTime (int) The time in seconds to wait until returning a failure
	 * @return - True (Boolean) if element is located within timeout period else false
     */
	/*public boolean isElementPresentSeleniumRC(String locator, int waitTime)
	{
		Selenium selenium = new WebDriverBackedSelenium(driver, app.getProperty("App.URL"));
		int iSecond = 0;
		boolean bFlag = false;
		log.info(getTestCasename()+"Waiting for presence of element " + locator);
		while(iSecond <= waitTime)
		{
			try
			{
				if (selenium.isElementPresent(locator))
				{
					log.info(getTestCasename()+"Element" + locator + "is displayed");
					bFlag = true;
					break;
				}
				else
				{
					Thread.sleep(1000);
					iSecond = iSecond + 1;
				}
			}
    		catch (NoSuchElementException e)
    		{
    			log.info(getTestCasename()+"Element " + locator + " is not displayed in time - "+waitTime+" Seconds - NoSuchElementException");
    		}
			catch (TimeoutException e)
			{
				log.info(getTestCasename()+"Element " + locator + " was not displayed in time - "+waitTime+" Seconds - TimeoutException");
			}
    		catch (Exception e)
    		{
    			log.error(getTestCasename()+"Element " + locator + " is not displayed" + " - " + e);
//    			Assert.fail("Element " + locator + " is not displayed." );
    		}
		}
		return bFlag;
	}*/


	/**
	 * Method -  Waits for an element till the element is clickable
	 * @param friendlyWebElementName user friendly message
	 * @param locator (By) locator object of the element to be found
	 * @param optionWaitTime (int) The time in seconds to wait until returning a failure
	 * @return - True (Boolean) if element is located and is clickable within timeout period else false
	 */
	protected boolean waitUntilClickable(By locator, String friendlyWebElementName,int... optionWaitTime)
	{
		int waitTime =  getWaitTime(optionWaitTime);
		boolean bFlag = false;
		//	nullifyImplicitWait();
		try
		{
			log.info(getTestCasename()+"Waiting until " + friendlyWebElementName+" is clickable");
			WebDriverWait wait = new WebDriverWait(driver, waitTime);
			wait.until(ExpectedConditions.elementToBeClickable(locator));

			if(driver.findElement((locator)).isDisplayed())
			{
				bFlag = true;
				log.info(getTestCasename()+friendlyWebElementName + " is displayed and is clickable");
			}
		}

		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+"Unable to find " + friendlyWebElementName +" in DOM in time "+waitTime+" Seconds - NoSuchElementException");
			Assert.fail("Unable to find " + friendlyWebElementName +" in DOM in time "+waitTime+" Seconds - NoSuchElementException");
		}
		catch (TimeoutException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + "was not displayed in time - "+waitTime+" Seconds - TimeoutException");
			Assert.fail(friendlyWebElementName + " was not displayed in time - "+waitTime+" Seconds - TimeoutException");
		}
		catch (Exception e)
		{
			log.error(getTestCasename()+friendlyWebElementName + "was not displayed - " + e);
			Assert.fail(friendlyWebElementName + " was not displayed");
		}
		//	setImplicitWait(IMPLICITWAIT);
		return bFlag;
	}

	/**
	 *
	 * Method -  Waits for an element till the element is clickable
	 *
	 * @param locator (By) locator object of the element to be found
	 * @param optionWaitTime (int) The time in seconds to wait until returning a failure
	 * @return True (Boolean) if element is located and is clickable on screen within timeout period else false
	 */
	protected boolean isElementClickable(By locator, int... optionWaitTime)
	{
		int waitTime =  getWaitTime(optionWaitTime);
		boolean bFlag = false;
		//nullifyImplicitWait();
		try
		{
			log.info(getTestCasename()+"Waiting until element " + locator+" is clickable");
			WebDriverWait wait = new WebDriverWait(driver, waitTime);
			wait.until(ExpectedConditions.elementToBeClickable(locator));

			if(driver.findElement((locator)).isDisplayed())
			{
				bFlag = true;
				log.info(getTestCasename()+"Element " + locator + " is displayed and is clickable");
			}
		}

		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+"Element " + locator + " was not displayed in time - "+waitTime+" Seconds - NoSuchElementException");
		}
		catch (TimeoutException e)
		{
			log.error(getTestCasename()+"Element " + locator + " was not found in time - "+waitTime+" Seconds - TimeoutException");
		}
		catch (Exception e)
		{
			log.error(getTestCasename()+"Element " + locator + " was not found - " + e);
//			Assert.fail("Element " + locator + " was not found");
		}
		//	setImplicitWait(IMPLICITWAIT);
		return bFlag;
	}



	/**
	 * Method -  Waits for an element till the element is visible
	 * @param locator (By) locator object of the element to be found
	 * @param optionWaitTime (int) The time in seconds to wait until returning a failure
	 * @return - True (Boolean) if element is located and is visible on screen within timeout period else false
	 */
	protected boolean isElementVisible(By locator, int... optionWaitTime)
	{
		int waitTime =  getWaitTime(optionWaitTime);
		boolean bFlag = false;
		//nullifyImplicitWait();
		log.info(getTestCasename()+"Waiting until element " + locator+" is visible");
		try
		{
			WebDriverWait wait = new WebDriverWait(driver, waitTime);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			//setImplicitWait(IMPLICITWAIT);
			if(driver.findElement((locator)).isDisplayed())
			{
				bFlag = true;
				log.info(getTestCasename()+"Element " + locator + " is displayed");
			}
		}
		catch (NoSuchElementException e)
		{
			log.info(getTestCasename()+"Element " + locator + " was not displayed in time - "+waitTime+" Seconds - NoSuchElementException");
		}
		catch (TimeoutException e)
		{
			log.info(getTestCasename()+"Element " + locator + " was not displayed in time - "+waitTime+" Seconds - TimeoutException");
		}

		catch (Exception e)
		{
			log.error(getTestCasename()+"Element " + locator + " was not displayed - " + e);
//			Assert.fail("Element " + locator + " was not displayed");
		}
		return bFlag;
	}


	/**
	 * Purpose- Wait for an element till it is either invisible or not present on the DOM.
	 * @param friendlyWebElementName user friendly message
	 * @param locator (By) locator object of the element to be found
	 * @param optionWaitTime (int) The time in seconds to wait until returning a failure
	 * @return - True (Boolean) if the element disappears in specified waitTime.
	 */
	public boolean waitUntilElementDisappears(By locator,String friendlyWebElementName,int... optionWaitTime)
	{
		int waitTime =  getWaitTime(optionWaitTime);
		boolean isNotVisible = false;
		log.info(getTestCasename()+"Waiting until " + friendlyWebElementName+" is invisible");
		try
		{
			//	nullifyImplicitWait();
			WebDriverWait wait = new WebDriverWait(driver, waitTime);
			if(wait.until(ExpectedConditions.invisibilityOfElementLocated(locator)))
				isNotVisible = true;
			log.info(getTestCasename()+friendlyWebElementName + " is disappeared");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " is not disappearing in time - "+waitTime + " - " + e);
			Assert.fail(friendlyWebElementName + " is not disappearing in time - "+waitTime );
		}
		//	setImplicitWait(IMPLICITWAIT);
		return isNotVisible;
	}

	/** Waits for the completion of Ajax jQuery processing by checking "return jQuery.active == 0" condition.
	 *
	 * @param timeOutInSeconds - The time in seconds to wait until returning a failure
	 * @return True (Boolean) if jquery/ ajax is loaded within specified timeout.
	 * */
	protected boolean waitForJQueryProcessing(int timeOutInSeconds)
	{
		log.info(getTestCasename()+"Waiting ajax processing to complete..");
		boolean jQcondition = false;
		try
		{
			new WebDriverWait(driver, timeOutInSeconds).until(new ExpectedCondition<Boolean>()
			{
				@Override
				public Boolean apply(WebDriver driverObject)
				{
					return (Boolean) ((JavascriptExecutor) driverObject).executeScript("return jQuery.active == 0");
				}
			});
			jQcondition = (Boolean) ((JavascriptExecutor) driver).executeScript("return window.jQuery != undefined && jQuery.active === 0");
//			System.out.println(jQcondition);
			return jQcondition;
		}
		catch (Exception e)
		{
			log.error(getTestCasename()+"Page Loading is not completed in time - "+timeOutInSeconds + " - " + e);
			Assert.fail("Page Loading is not completed in time - "+timeOutInSeconds);
		}
		return jQcondition;
	}

	/**
	 * Method - Wait until a particular text appears on the screen
	 * @param text (String) - text until which the WebDriver should wait.
	 */
	public void waitForPageToLoad(final String text)
	{
		log.info(getTestCasename()+"Waiting for page to be loaded...");
		nullifyImplicitWait();
		(new WebDriverWait(driver, 20)).until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver d)
			{
				return d.findElement(By.partialLinkText(text));
			}
		});
		//setImplicitWait(IMPLICITWAIT);
	}


	/**
	 * Waits until page is loaded.
	 *
	 * @param timeOutInSeconds - timeout in seconds
	 * @return True (boolean)
	 */
	protected boolean waitForPageToLoad(int timeOutInSeconds)
	{
		log.info(getTestCasename()+"Waiting for page to be loaded...");
		boolean isPageLoadComplete = false;
		//nullifyImplicitWait(); //nullify implicitlyWait()
		try
		{
			new WebDriverWait(driver, timeOutInSeconds).until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver driverObject) {
					return (Boolean) ((String)((JavascriptExecutor)driver).executeScript("return document.readyState")).equals("complete");
				}
			});
			isPageLoadComplete = (Boolean) ((String)((JavascriptExecutor)driver).executeScript("return document.readyState")).equals("complete");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to load web page in time - "+timeOutInSeconds + " Seconds - " + e);
			Assert.fail("unable to load webpage in time - "+timeOutInSeconds);
		}
		//	setImplicitWait(IMPLICITWAIT);
		return isPageLoadComplete;
	}

	/**
	 * Waits until page is loaded. Default timeout is 250 seconds. Poll time is every 500 milliseconds.
	 */

	public void waitForPageToLoad()
	{
		log.info(getTestCasename()+"Waiting for page to be loaded...");
		try
		{
			int waitTime = 0;
			boolean isPageLoadComplete = false;
			do
			{

				isPageLoadComplete = ((String)((JavascriptExecutor)driver).executeScript("return document.readyState")).equals("complete");
				System.out.print(".");
				Thread.sleep(500);
				waitTime++;
				if(waitTime > 500)
				{
					break;
				}
			}
			while(!isPageLoadComplete);

			if(!isPageLoadComplete)
			{

				log.error(getTestCasename()+"Unable to load webpage with in default timeout 250 seconds");
				Assert.fail("unable to load webpage with in default timeout 250 seconds");
			}
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to load web page - " + e);
			Assert.fail("unable to load webpage");
		}

	}

	protected boolean isElementPresent(By locator)
	{
		log.info(getTestCasename()+"Waiting for presence of element " + locator);
		setImplicitWait(IMPLICITWAIT);
		return driver.findElements(locator).size()>0;
	}
	public boolean isElementEnabled(By locator)
	{
		log.info(getTestCasename()+"Verifying if element " + locator+ " is enabled");
		boolean isEnabled = false;
		//setImplicitWait(IMPLICITWAIT);
		try
		{
			if(isElementPresent(locator))
			{
				isEnabled = driver.findElement(locator).isEnabled();
			}
		}
		catch (Exception e)
		{
			log.error(getTestCasename()+"Exception occured while determining state of " + locator+" - " + e);
		}
		return isEnabled;
	}
	public boolean isElementSelected(By locator)
	{
		log.info(getTestCasename()+"Verifying if element " + locator+ " is selected");
		boolean isSelected = false;
		//	setImplicitWait(IMPLICITWAIT);
		try
		{
			if(isElementPresent(locator))
			{
				isSelected = driver.findElement(locator).isSelected();
			}
		}
		catch (Exception e)
		{
			log.error(getTestCasename()+"Exception occured while determining state of " + locator+" - " + e);
		}
		return isSelected;
	}


	protected int getWaitTime(int[] optionalWaitArray)
	{
		if(optionalWaitArray.length<=0)
		{
			return MEDIUMWAIT;
		}
		else
		{
			return optionalWaitArray[0];
		}
	}

	/**
	 *
	 * This method is used to verify whether required locator is displayed on screen or not using sikuli

	 * @param locator web element
	 * @return returns true if required locator is displayed else returns false
	 */
	public boolean isElementDisplayed(By locator)
	{
		log.info(getTestCasename()+"Verifying if element " + locator+ " is displayed");
		boolean isDisplayed = false;
		//setImplicitWait(IMPLICITWAIT);

		try
		{
			if(isElementPresent(locator))
			{
				isDisplayed = driver.findElement(locator).isDisplayed();
			}
		}
		catch (Exception e)
		{
			log.error(getTestCasename()+"Exception occured while determining state of " + locator+" - " + e);
		}
		return isDisplayed;
	}
/*public boolean isElementDisplayedUsingSikuli(String sImagePath, String sLocatorName,int waitTime)
{
	boolean bValue = false;
	try
	{
		Screen screen = new Screen();
		Settings.MinSimilarity = 0.87;
		screen.wait(sImagePath,waitTime);
		bValue = true;
		log.info(getTestCasename()+"Locator field with name '"+sLocatorName+"' is displayed using sikuli");
	}
	catch(FindFailed e)
	{
		log.error(getTestCasename()+"Locator with name '"+sLocatorName+"' is not displayed using sikuli, please check screenshot and image path"+"\n"+e.getMessage());
		bValue = false;
	}
	catch(Exception e)
	{
		log.error(getTestCasename()+"Locator with name '"+sLocatorName+"' is not displayed using sikuli, please check screenshot and image path"+"\n"+e.getMessage());
		Assert.fail("Locator with name '"+sLocatorName+"' is not displayed using sikuli, Some exception occured other than FindFailed, please check screenshot and image path");
	}
	return bValue;
}*/


	/** Waits for the completion of Ajax jQuery processing by checking "return jQuery.active == 0" condition and also stores page load time in PageLoadTime_Summary.html file.
	 *
	 * @param SourcePage - Need to pass the source page name
	 * @param DestinationPage - Need to pass the destination page name
	 * @param timeOutInSeconds - The time in seconds to wait until returning a failure
	 * @return True (Boolean) if jquery/ ajax is loaded within specified timeout.
	 * */
	public boolean waitForJQueryProcessingandRetrivePageLoadTime(String SourcePage, String DestinationPage,int timeOutInSeconds)
	{
		long start = System.currentTimeMillis();
		log.info(getTestCasename()+"Waiting ajax processing to complete..");
		boolean jQcondition = false;
		try
		{
			new WebDriverWait(driver, timeOutInSeconds).until(new ExpectedCondition<Boolean>()
			{
				@Override
				public Boolean apply(WebDriver driverObject)
				{
					return (Boolean) ((JavascriptExecutor) driverObject).executeScript("return jQuery.active == 0");
				}
			});
			jQcondition = (Boolean) ((JavascriptExecutor) driver).executeScript("return window.jQuery != undefined && jQuery.active === 0");
			System.out.println(jQcondition);
			return jQcondition;
		}
		catch (Exception e)
		{
			log.error(getTestCasename()+"Page Loading is not completed" + " - " + e);
			Assert.fail("Page Loading is not completed");
		}
		long finish = System.currentTimeMillis();
		long totalTime = finish - start;
		ReportSetup.Report_PageLoadTime(SourcePage, DestinationPage, totalTime);
		return jQcondition;
	}

	/**
	 * Waits until page is loaded and also stores page load time in PageLoadTime_Summary.html file.
	 *
	 * @param SourcePage - Need to pass the source page name
	 * @param DestinationPage - Need to pass the destination page name
	 * @param timeOutInSeconds - timeout in seconds
	 * @return True (boolean)
	 */
	public boolean waitForPageToLoadandRetrivePageLoadTime(String SourcePage, String DestinationPage, int timeOutInSeconds)
	{
		long start = System.currentTimeMillis();
		log.info(getTestCasename()+"Waiting for page to be loaded...");
		boolean isPageLoadComplete = false;
		nullifyImplicitWait(); //nullify implicitlyWait()
		try
		{
			new WebDriverWait(driver, timeOutInSeconds).until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver driverObject) {
					return (Boolean) ((String)((JavascriptExecutor)driver).executeScript("return document.readyState")).equals("complete");
				}
			});
			isPageLoadComplete = (Boolean) ((String)((JavascriptExecutor)driver).executeScript("return document.readyState")).equals("complete");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to load web page" + " - " + e);
			Assert.fail("unable to load webpage");
		}
		long finish = System.currentTimeMillis();
		long totalTime = finish - start;
		ReportSetup.Report_PageLoadTime(SourcePage, DestinationPage, totalTime);
		setImplicitWait(IMPLICITWAIT);
		return isPageLoadComplete;
	}
	/**
	 * Method - is to wait for some amount of time which is given in seconds.
	 * @param sec waiting time
	 */
	protected void waitForSecs(int sec)
	{
		log.info(getTestCasename()+"Waiting for "+sec+ " seconds.");
		try {
			Thread.sleep(sec*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to wait for given "+sec +" seconds.");
			e.printStackTrace();
		}
	}
}