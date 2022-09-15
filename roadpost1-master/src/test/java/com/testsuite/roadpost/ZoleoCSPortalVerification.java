/* ************************************** PURPOSE **********************************

 - This class contains function calls related to Adding Item to cart and purchase item functionality of the application

 */

package com.testsuite.roadpost;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.page.data.ZoleoData;
import com.page.module.ZoleoCSPortalPage;
import com.selenium.Sync;
import com.testng.Retry;

//Each TestSuite class must do the following
//1. It should extends the "BaseClass", so that @BeforeClass - BrowserInitialization, 
// @AfterClass - Browser Quit happens automatically
//2. It should create instances for respective PageParts classes along with LoadProperties class
//3. Create @BeforeMethod setup to initialize instances with getDriver(taken from Base class)

    public class ZoleoCSPortalVerification extends BaseSetup {
	private ZoleoData zoleoTestData;
	private ZoleoCSPortalPage zoleoCSPortalPage;
	private String sModeOfExecution;

	@BeforeMethod(alwaysRun = true)
	public void baseClassSetUp() 
	{
		ConfigManager sys;
		sys = new ConfigManager();
		zoleoCSPortalPage = new ZoleoCSPortalPage(getDriver());
		zoleoTestData = new ZoleoData();
		getDriver().manage().deleteAllCookies();
		getDriver().get(zoleoTestData.csurl);
		(new Sync(getDriver())).waitForPageToLoad();
		sModeOfExecution = sys.getProperty("ModeOfExecution");
	}

	/**
	 * Purpose- This method contains the logic for 'Assert the Device
	 * IMEI Number' Functionality in CS Portal
	 * 
	 */
	@Test(/* groups ="regression" */)
	public void tc002_CSPortalVerification() throws InterruptedException 
	{
		zoleoCSPortalPage.verifyCSPortalPage();
		zoleoCSPortalPage.verifyLoginPage();
		zoleoCSPortalPage.EnterCSLoginCredentials(zoleoTestData.csusername, zoleoTestData.cspassword);
		zoleoCSPortalPage.clickCSLogInButton();
		zoleoCSPortalPage.clickDashBoardButton(zoleoTestData.imei);
		
	}	
}