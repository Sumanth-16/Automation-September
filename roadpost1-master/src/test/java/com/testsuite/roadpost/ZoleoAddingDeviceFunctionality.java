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
import com.page.module.ZoleoAddDevicePage;
import com.page.module.ZoleoCheckOutPage;
import com.page.module.ZoleoLoginPage;
import com.page.module.ZoleoMyAccountPage;
import com.page.module.ZoleoPage;
import com.selenium.Sync;
import com.testng.Retry;

//Each TestSuite class must do the following
//1. It should extends the "BaseClass", so that @BeforeClass - BrowserInitialization, 
//	 launch, URL Navigation and @AfterClass - Browser Quit happens automatically
//2. It should create instances for respective PageParts classes along with LoadProperties class
//3. Create @BeforeMethod setup to initialize instances with getDriver(taken from Base class)

    public class ZoleoAddingDeviceFunctionality extends BaseSetup {
	private ZoleoData zoleoTestData;
	private ZoleoPage zoleoPage;
	private ZoleoLoginPage zoleologinPage;
	private ZoleoMyAccountPage zoleomyaccountPage;
	private ZoleoAddDevicePage zoleoAddDevicePage;
	private ZoleoCheckOutPage zoleoCheckoutPage;
	private String sModeOfExecution;

	@BeforeMethod(alwaysRun = true)
	public void baseClassSetUp() throws InterruptedException {
		ConfigManager sys;
		sys = new ConfigManager();
		zoleoPage = new ZoleoPage(getDriver());
		zoleoTestData = new ZoleoData();
		getDriver().manage().deleteAllCookies();
		getDriver().get(zoleoTestData.zoleoURL);
		(new Sync(getDriver())).waitForPageToLoad();
		sModeOfExecution = sys.getProperty("ModeOfExecution");
	}

	/**
	 * Purpose- This method contains the logic for 'Add Zoleo Device to the
	 * User Zoleo Account' Functionality in Zoleo Application
	 * @throws InterruptedException 
	 */
	@Test(/* groups ="regression" */)
	public void tc001_activateZoleoDevice() throws Exception  
	{
		//verifies the Zoleo Page 
		zoleoPage.verifyZoleoPage();
		//Click on Login_SignUp Button
		zoleologinPage = zoleoPage.clickOnLoginSignUpButton();
		//verifies the Login PAge
		zoleologinPage.verifyLoginPage();
		//Enter Login Credentials
		zoleologinPage.enterLoginCredentials(zoleoTestData.emailAddress,zoleoTestData.password);
		//Click on Login Button
		zoleomyaccountPage=zoleologinPage.clickLogInButton();
		//verifies the ZoleoMyAccountPage
		zoleomyaccountPage.verifyZoleoMyAccountPage();
		//Click on DevicesAndPlans
        //zoleomyaccountPage.DeviceAndPlansButton(zoleoTestData.imei);
		//Click on AddNewDeviceOption
		zoleoAddDevicePage=zoleomyaccountPage.activateButton();
		//verify the AddDevicePage
		//zoleoAddDevicePage.VerifyAddDevicePage();
		//Enter the Details and Click on Next
		zoleoAddDevicePage.EnterDeviceDetails(zoleoTestData.devicenickname,zoleoTestData.zoleoemailaddress);
		//Select the plan and click next
		zoleoAddDevicePage.BasicPlanButton();
		//verify add feature section and click next
		zoleoAddDevicePage.VerifyAddFeatureSection();
		//Enter the contact details and click next
		zoleoCheckoutPage=zoleoAddDevicePage.AddContactDetails(zoleoTestData.sos1fullname,zoleoTestData.sos1email,zoleoTestData.sos1mobileno,zoleoTestData.sos2fullname,zoleoTestData.sos2email, zoleoTestData.sos2mobileno,zoleoTestData.checkincontactfullname,zoleoTestData.checkincontactemail,zoleoTestData.checkincontactmobileno);
		//Enter Details in Checkout Page
		zoleoCheckoutPage.enterBillingDetailsInfo(zoleoTestData.firstname, zoleoTestData.lastname, zoleoTestData.streetaddress, zoleoTestData.city, zoleoTestData.postalcode,zoleoTestData.checkoutphoneno,zoleoTestData.nameoncard,zoleoTestData.creditcardno,zoleoTestData.cvvno);
		zoleoCheckoutPage.DeviceActivationPage();
		zoleoCheckoutPage.DeviceAndPlansButton(zoleoTestData.imei);
	}
			
}