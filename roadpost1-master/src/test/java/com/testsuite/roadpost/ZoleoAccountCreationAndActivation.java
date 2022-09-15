/* ************************************** PURPOSE **********************************

 - This class contains function calls related to Adding Item to cart and purchase item functionality of the application

 */

package com.testsuite.roadpost;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.testng.annotations.AfterMethod;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.datamanager.ExcelManager;
import com.page.data.ZoleoData;
import com.page.module.ZoleoActivationPage;
import com.page.module.ZoleoAddDevicePage;
import com.page.module.ZoleoCheckOutPage;
import com.page.module.ZoleoCountryPage;
import com.page.module.ZoleoForgotPasswordPage;
import com.page.module.ZoleoLoginPage;
import com.page.module.ZoleoMyAccountPage;
import com.page.module.ZoleoPage;
import com.page.module.ZoleoPaymentInfoPage;
import com.page.module.ZoleoSignUpPage;
import com.selenium.Sync;
import com.testng.Retry;
import com.utilities.EmailUtilities;

//Each TestSuite class must do the following
//1. It should extends the "BaseClass", so that @BeforeClass - BrowserInitialization, 
//	 launch, URL Navigation and @AfterClass - Browser Quit happens automatically
//2. It should create instances for respective PageParts classes along with LoadProperties class
//3. Create @BeforeMethod setup to initialize instances with getDriver(taken from Base class)

public class ZoleoAccountCreationAndActivation extends BaseSetup {
	private ZoleoData zoleoTestData;
	private ZoleoPage zoleoPage;
	private ZoleoLoginPage zoleologinPage;
	private ZoleoSignUpPage zoleosignupPage;
	private ZoleoCountryPage zoleoCountryPage;
	private ZoleoForgotPasswordPage zoleoforgotpasswordpage;
	private ZoleoMyAccountPage zoleomyaccountPage;
	private ZoleoAddDevicePage zoleoAddDevicePage;
	private ZoleoPaymentInfoPage zoleoPaymentInfopage;
	private ZoleoCheckOutPage zoleoCheckOutPage;
	private ZoleoActivationPage zoleoActivationPage;
	private ExcelManager excelmanager;
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
	 * Purpose- This method contains the logic for ' signup with new account
	 * Functionality in Zoleo Application
	 * 
	 * @throws InterruptedException
	 */
	@Test(/* groups ="regression" */)
	public void tc001_SignUpNewAccount() throws Exception {
		// zoleoPage.verifyZoleoPage();
		zoleoPage.Auth();
		zoleologinPage = zoleoPage.clickOnLoginSignUpButton();
		zoleosignupPage = zoleologinPage.clickOnSignUpOption();
		zoleosignupPage.verifySignUpPage();
		zoleosignupPage.enterSignUpCredentials(zoleoTestData.signupemail, zoleoTestData.signupphoneno,
				zoleoTestData.givenname, zoleoTestData.familyname, zoleoTestData.signuppassword);
		zoleosignupPage.clickonAccountSignUpButton();
		zoleosignupPage.getOTPfrommailbox(zoleoTestData.emailusername, zoleoTestData.emailpassword);
		zoleoCountryPage = zoleosignupPage.EnterAndVerifyOTP();
		zoleomyaccountPage = zoleoCountryPage.SaveBillingCountry();
		// zoleomyaccountPage.verifyZoleoMyAccountPage();
		zoleomyaccountPage.VerifyAccountInformationAfterSignUp(zoleoTestData.signupemail, zoleoTestData.signupphoneno,
				zoleoTestData.givenname, zoleoTestData.familyname);

	}

	/**
	 * Purpose- This method contains the logic for ' Resent Verification Code While
	 * SignUp New Account Functionality in Zoleo Application
	 * 
	 * @throws InterruptedException
	 */
	@Test(/* groups ="regression" */)
	public void tc002_ResentVerificationCodeWhileSignUpNewAccount() throws Exception {
		// zoleoPage.verifyZoleoPage();
		zoleologinPage = zoleoPage.clickOnLoginSignUpButton();
		zoleosignupPage = zoleologinPage.clickOnSignUpOption();
		zoleosignupPage.verifySignUpPage();
		zoleosignupPage.enterSignUpCredentials(zoleoTestData.signupemail, zoleoTestData.signupphoneno,
				zoleoTestData.givenname, zoleoTestData.familyname, zoleoTestData.signuppassword);
		zoleosignupPage.clickonAccountSignUpButton();
		zoleosignupPage.clickonResendOTPButton();
		zoleosignupPage.getRESENDOTPfrommailbox(zoleoTestData.emailusername, zoleoTestData.emailpassword);
		zoleoCountryPage = zoleosignupPage.EnterAndVerifyOTP();
		zoleomyaccountPage = zoleoCountryPage.SaveBillingCountry();
		zoleomyaccountPage.verifyZoleoMyAccountPage();
		zoleomyaccountPage.VerifyAccountInformationAfterSignUp(zoleoTestData.signupemail, zoleoTestData.signupphoneno,
				zoleoTestData.givenname, zoleoTestData.familyname);
	}

	/**
	 * Purpose- This method contains the logic for 'changing region after sign up
	 * new account in Zoleo Application
	 * 
	 * @throws InterruptedException
	 */
	@Test(/* groups ="regression" */)
	public void tc003_ChangeRegionAfterSignUp() throws Exception {
		// zoleoPage.verifyZoleoPage();
		// Click on Login_SignUp Button
		zoleologinPage = zoleoPage.clickOnLoginSignUpButton();
		// verifies the Login PAge
		zoleologinPage.verifyLoginPage();
		// Enter Login Credentials
		zoleologinPage.enterLoginCredentials(zoleoTestData.emailAddress, zoleoTestData.password);
		// Click on Login Button
		zoleoAddDevicePage = zoleologinPage.clickNoDeviceAddedLogInButton();
		zoleomyaccountPage = zoleoAddDevicePage.ClickOnMyAccountOption();
		zoleomyaccountPage.verifyZoleoMyAccountPage();
		zoleomyaccountPage.VerifyAccountInformation(zoleoTestData.emailAddress);
		zoleomyaccountPage.ChangeCountry();

	}

	/**
	 * Purpose- This method contains the logic for ' Login existing account after
	 * sign up inCognito Functionality in Zoleo Application
	 * 
	 * @throws InterruptedException
	 */
	@Test(/* groups ="regression" */)
	public void tc004_loginExistingAccountAfterSignUpInCognito() throws Exception {
		// verifies the Zoleo Page
		// zoleoPage.verifyZoleoPage();
		// Click on Login_SignUp Button
		zoleologinPage = zoleoPage.clickOnLoginSignUpButton();
		// verifies the Login PAge
		zoleologinPage.verifyLoginPage();
		// Enter Login Credentials
		zoleologinPage.enterLoginCredentials(zoleoTestData.emailAddress, zoleoTestData.password);
		// Click on Login Button
		zoleoAddDevicePage = zoleologinPage.clickNoDeviceAddedLogInButton();
		zoleomyaccountPage = zoleoAddDevicePage.ClickOnMyAccountOption();
		zoleomyaccountPage.verifyZoleoMyAccountPage();
		zoleomyaccountPage.VerifyAccountInformation(zoleoTestData.emailAddress);

	}

	/**
	 * Purpose- This method contains the logic for ' signin with original account
	 * Functionality in Zoleo Application
	 * 
	 * @throws InterruptedException
	 */
	@Test(/* groups ="regression" */)
	public void tc005_ForgotYourPassword() throws Exception {
		// zoleoPage.verifyZoleoPage();
		zoleologinPage = zoleoPage.clickOnLoginSignUpButton();
		zoleoforgotpasswordpage = zoleologinPage.clickOnForgotPasswordButton();
		zoleoforgotpasswordpage.enterForgotPasswordEmail(zoleoTestData.emailAddress);
		zoleoforgotpasswordpage.getOTPfrommailbox(zoleoTestData.emailusername, zoleoTestData.emailpassword);
		zoleoforgotpasswordpage.enterOTPandNewPassword(zoleoTestData.password, zoleoTestData.password);
		zoleologinPage.enterLoginCredentials(zoleoTestData.emailAddress, zoleoTestData.password);
		zoleoAddDevicePage = zoleologinPage.clickNoDeviceAddedLogInButton();
		zoleomyaccountPage = zoleoAddDevicePage.ClickOnMyAccountOption();
		zoleomyaccountPage.verifyZoleoMyAccountPage();
		zoleomyaccountPage.VerifyAccountInformation(zoleoTestData.emailAddress);

	}

	/**
	 * Purpose- This method contains the logic for ' Login existing account after
	 * sign up inCognito Functionality in Zoleo Application
	 * 
	 * @throws InterruptedException
	 */
	@Test(/* groups ="regression" */)
	public void tc006_AddBillingAddressBeforeFirstOrderInAccount() throws Exception {
		// verifies the Zoleo Page
		// zoleoPage.verifyZoleoPage();
		// Click on Login_SignUp Button
		zoleologinPage = zoleoPage.clickOnLoginSignUpButton();
		// verifies the Login PAge
		zoleologinPage.verifyLoginPage();
		// Enter Login Credentials
		zoleologinPage.enterLoginCredentials(zoleoTestData.emailAddress, zoleoTestData.password);
		// Click on Login Button
		zoleoAddDevicePage = zoleologinPage.clickNoDeviceAddedLogInButton();
		zoleomyaccountPage = zoleoAddDevicePage.ClickOnMyAccountOption();
		zoleomyaccountPage.verifyZoleoMyAccountPage();
		zoleomyaccountPage.VerifyAccountInformation(zoleoTestData.emailAddress);
		zoleoPaymentInfopage = zoleomyaccountPage.ClickOnPaymentInfo();
		zoleoPaymentInfopage.GetAndEnterTheBillingAddressAndCCDetails();

	}

	/**
	 * Purpose- This method contains the logic for ' signup with new account
	 * Functionality in Zoleo Application
	 * 
	 * @throws InterruptedException
	 */
	@Test(/* groups ="regression" */)
	public void tc007_TheCheckIncontactInfoCanBeAutoComplete() throws Exception {
		// zoleoPage.verifyZoleoPage();
		zoleologinPage = zoleoPage.clickOnLoginSignUpButton();
		zoleosignupPage = zoleologinPage.clickOnSignUpOption();
		zoleosignupPage.verifySignUpPage();
		zoleosignupPage.enterSignUpCredentials(zoleoTestData.signupemail, zoleoTestData.signupphoneno,
				zoleoTestData.givenname, zoleoTestData.familyname, zoleoTestData.signuppassword);
		zoleosignupPage.clickonAccountSignUpButton();
		zoleosignupPage.getOTPfrommailbox(zoleoTestData.emailusername, zoleoTestData.emailpassword);
		zoleoCountryPage = zoleosignupPage.EnterAndVerifyOTP();
		zoleomyaccountPage = zoleoCountryPage.SaveBillingCountry();
		zoleomyaccountPage.verifyZoleoMyAccountPage();
		zoleomyaccountPage.VerifyAccountInformationAfterSignUp(zoleoTestData.signupemail, zoleoTestData.signupphoneno,
				zoleoTestData.givenname, zoleoTestData.familyname);
		zoleoAddDevicePage = zoleomyaccountPage.activateButton();
		zoleoAddDevicePage.EnterDeviceDetails(zoleoTestData.devicenickname, zoleoTestData.zoleoemailaddress);
		zoleoAddDevicePage.BasicPlanButton();
		zoleoAddDevicePage.VerifyAddFeatureSection();
		zoleoAddDevicePage.CheckincontactAddContactDetails(zoleoTestData.sos1fullname, zoleoTestData.sos1email,
				zoleoTestData.sos1mobileno, zoleoTestData.sos2fullname, zoleoTestData.sos2email,
				zoleoTestData.sos2mobileno, zoleoTestData.sos1fullname);

	}

	/**
	 * Purpose- This method contains the logic for ' signup with new account
	 * Functionality in Zoleo Application
	 * 
	 * @throws Exception
	 */
	@Test(/* groups ="regression" */)
	public void tc008_OnlyCreateNewAccountAndDontActivateAnyDevice() throws Exception {
		// zoleoPage.verifyZoleoPage();
		zoleologinPage = zoleoPage.clickOnLoginSignUpButton();
		zoleosignupPage = zoleologinPage.clickOnSignUpOption();
		zoleosignupPage.verifySignUpPage();
		zoleosignupPage.enterSignUpCredentials(zoleoTestData.signupemail, zoleoTestData.signupphoneno,
				zoleoTestData.givenname, zoleoTestData.familyname, zoleoTestData.signuppassword);
		zoleosignupPage.clickonAccountSignUpButton();
		zoleosignupPage.getOTPfrommailbox(zoleoTestData.emailusername, zoleoTestData.emailpassword);
		zoleoCountryPage = zoleosignupPage.EnterAndVerifyOTP();
		zoleomyaccountPage = zoleoCountryPage.SaveBillingCountry();
		zoleomyaccountPage.verifyZoleoMyAccountPage();
		zoleomyaccountPage.VerifyAccountInformationAfterSignUp(zoleoTestData.signupemail, zoleoTestData.signupphoneno,
				zoleoTestData.givenname, zoleoTestData.familyname);
		zoleomyaccountPage.VerifyBillingAddressAndCCDetailsInMyAccountPage();

	}

	/**
	 * Purpose- This method contains the logic for ' signup with new account
	 * Functionality in Zoleo Application
	 * 
	 * @throws Exception
	 */
	@Test(/* groups ="regression" */)
	public void tc009_SubscribeNewsLettersDuringAccountCreation() throws Exception {
		// zoleoPage.verifyZoleoPage();
		zoleologinPage = zoleoPage.clickOnLoginSignUpButton();
		zoleosignupPage = zoleologinPage.clickOnSignUpOption();
		zoleosignupPage.verifySignUpPage();
		zoleosignupPage.enterSignUpCredentials(zoleoTestData.signupemail, zoleoTestData.signupphoneno,
				zoleoTestData.givenname, zoleoTestData.familyname, zoleoTestData.signuppassword);
		zoleosignupPage.clickonAccountSignUpButton();
		zoleosignupPage.getOTPfrommailbox(zoleoTestData.emailusername, zoleoTestData.emailpassword);
		zoleoCountryPage = zoleosignupPage.EnterAndVerifyOTP();
		zoleomyaccountPage = zoleoCountryPage.SaveBillingCountry();
		zoleomyaccountPage.verifyZoleoMyAccountPage();
		zoleomyaccountPage.VerifyAccountInformationAfterSignUp(zoleoTestData.signupemail, zoleoTestData.signupphoneno,
				zoleoTestData.givenname, zoleoTestData.familyname);
		zoleomyaccountPage.VerifyCommunicationPrefencesTextInMyAccountPage();
	}

	/**
	 * Purpose- This method contains the logic for ' signup with new account
	 * Functionality in Zoleo Application
	 * 
	 * @throws Exception
	 */
	@Test(/* groups ="regression" */)
	public void tc010_ExceptionActivateDeviceWithBasicPlanOnExistingAccountWithoutLSP() throws Exception

	{
		// verifies the Zoleo Page
		// zoleoPage.verifyZoleoPage();
		// Click on Login_SignUp Button
		zoleologinPage = zoleoPage.clickOnLoginSignUpButton();
		// verifies the Login PAge
		zoleologinPage.verifyLoginPage();
		// Enter Login Credentials
		zoleologinPage.enterLoginCredentials(zoleoTestData.emailAddress, zoleoTestData.password);
		// Click on Login Button
		zoleomyaccountPage = zoleologinPage.clickLogInButton();
		// verifies the ZoleoMyAccountPage
		zoleomyaccountPage.verifyZoleoMyAccountPage();
		// Click on DevicesAndPlans
		// zoleomyaccountPage.DeviceAndPlansButton(zoleoTestData.imei);
		// Click on AddNewDeviceOption
		zoleoAddDevicePage = zoleomyaccountPage.activateButton();
		// verify the AddDevicePage
		// zoleoAddDevicePage.VerifyAddDevicePage();
		// Enter the Details and Click on Next
		zoleoAddDevicePage.EnterDeviceDetails(zoleoTestData.devicenickname, zoleoTestData.zoleoemailaddress);
		// Select the plan and click next
		zoleoAddDevicePage.BasicPlanButton();
		// verify add feature section and click next
		zoleoAddDevicePage.VerifyAddFeatureSection();
		// Enter the contact details and click next
		zoleoCheckOutPage = zoleoAddDevicePage.CheckincontactAddContactDetails(zoleoTestData.sos1fullname,
				zoleoTestData.sos1email, zoleoTestData.sos1mobileno, zoleoTestData.sos2fullname,
				zoleoTestData.sos2email, zoleoTestData.sos2mobileno, zoleoTestData.sos1fullname);
		zoleoActivationPage = zoleoCheckOutPage.GetAndEnterTheBillingAddressAndCCDetails();
		zoleoActivationPage.verifyZoleoActivationPage();
		zoleoActivationPage.VerifyDeviceDetails();
		zoleomyaccountPage = zoleoActivationPage.ClickMyAccount();
		zoleomyaccountPage.DeviceAndPlansButton();
		zoleomyaccountPage.VerifyActivationMSGfrommailbox(zoleoTestData.emailusername, zoleoTestData.emailpassword);
	}

	/**
	 * Purpose- This method contains the logic for ' signup with new account
	 * Functionality in Zoleo Application
	 * 
	 * @throws Exception
	 */
	@Test(/* groups ="regression" */)
	public void tc011_ExceptionActivateDeviceWithUnlimitedPlanOnExistingAccountWithoutLSP() throws Exception

	{
		// verifies the Zoleo Page
		// zoleoPage.verifyZoleoPage();
		// Click on Login_SignUp Button
		zoleologinPage = zoleoPage.clickOnLoginSignUpButton();
		// verifies the Login PAge
		zoleologinPage.verifyLoginPage();
		// Enter Login Credentials
		zoleologinPage.enterLoginCredentials(zoleoTestData.emailAddress, zoleoTestData.password);
		// Click on Login Button
		zoleomyaccountPage = zoleologinPage.clickLogInButton();
		// verifies the ZoleoMyAccountPage
		zoleomyaccountPage.verifyZoleoMyAccountPage();
		// Click on DevicesAndPlans
		// zoleomyaccountPage.DeviceAndPlansButton(zoleoTestData.imei);
		// Click on AddNewDeviceOption
		zoleoAddDevicePage = zoleomyaccountPage.activateButton();
		// verify the AddDevicePage
		// zoleoAddDevicePage.VerifyAddDevicePage();
		// Enter the Details and Click on Next
		zoleoAddDevicePage.EnterDeviceDetails(zoleoTestData.devicenickname, zoleoTestData.zoleoemailaddress);
		// Select the plan and click next
		zoleoAddDevicePage.UnlimitedPlanButton();
		// verify add feature section and click next
		zoleoAddDevicePage.VerifyAddFeatureSection();
		// Enter the contact details and click next
		zoleoCheckOutPage = zoleoAddDevicePage.CheckincontactAddContactDetails(zoleoTestData.sos1fullname,
				zoleoTestData.sos1email, zoleoTestData.sos1mobileno, zoleoTestData.sos2fullname,
				zoleoTestData.sos2email, zoleoTestData.sos2mobileno, zoleoTestData.sos1fullname);
		zoleoActivationPage = zoleoCheckOutPage.GetAndEnterTheBillingAddressAndCCDetails();
		zoleoActivationPage.verifyZoleoActivationPage();
		zoleoActivationPage.VerifyDeviceDetails();
		zoleomyaccountPage = zoleoActivationPage.ClickMyAccount();
		zoleomyaccountPage.DeviceAndPlansButton();
		zoleomyaccountPage.VerifyActivationMSGfrommailbox(zoleoTestData.emailusername, zoleoTestData.emailpassword);
	}

	/**
	 * Purpose- This method contains the logic for ' signup with new account
	 * Functionality in Zoleo Application
	 * 
	 * @throws Exception
	 */
	@Test(/* groups ="regression" */)
	public void tc012_ExceptionActivateDeviceWithInTouchPlanOnExistingAccountWithoutLSP() throws Exception

	{
		// verifies the Zoleo Page
		// zoleoPage.verifyZoleoPage();
		// Click on Login_SignUp Button
		zoleologinPage = zoleoPage.clickOnLoginSignUpButton();
		// verifies the Login PAge
		zoleologinPage.verifyLoginPage();
		// Enter Login Credentials
		zoleologinPage.enterLoginCredentials(zoleoTestData.emailAddress, zoleoTestData.password);
		// Click on Login Button
		zoleomyaccountPage = zoleologinPage.clickLogInButton();
		// verifies the ZoleoMyAccountPage
		zoleomyaccountPage.verifyZoleoMyAccountPage();
		// Click on DevicesAndPlans
		// zoleomyaccountPage.DeviceAndPlansButton(zoleoTestData.imei);
		// Click on AddNewDeviceOption
		zoleoAddDevicePage = zoleomyaccountPage.activateButton();
		// verify the AddDevicePage
		// zoleoAddDevicePage.VerifyAddDevicePage();
		// Enter the Details and Click on Next
		zoleoAddDevicePage.EnterDeviceDetails(zoleoTestData.devicenickname, zoleoTestData.zoleoemailaddress);
		// Select the plan and click next
		zoleoAddDevicePage.TouchPlanButton();
		// verify add feature section and click next
		zoleoAddDevicePage.VerifyAddFeatureSection();
		// Enter the contact details and click next
		zoleoCheckOutPage = zoleoAddDevicePage.CheckincontactAddContactDetails(zoleoTestData.sos1fullname,
				zoleoTestData.sos1email, zoleoTestData.sos1mobileno, zoleoTestData.sos2fullname,
				zoleoTestData.sos2email, zoleoTestData.sos2mobileno, zoleoTestData.sos1fullname);
		zoleoActivationPage = zoleoCheckOutPage.GetAndEnterTheBillingAddressAndCCDetails();
		zoleoActivationPage.verifyZoleoActivationPage();
		zoleoActivationPage.VerifyDeviceDetails();
		zoleomyaccountPage = zoleoActivationPage.ClickMyAccount();
		zoleomyaccountPage.DeviceAndPlansButton();
		zoleomyaccountPage.VerifyActivationMSGfrommailbox(zoleoTestData.emailusername, zoleoTestData.emailpassword);
	}

	/**
	 * Purpose- This method contains the logic for ' signup with new account
	 * Functionality in Zoleo Application
	 * 
	 * @throws Exception
	 */
	@Test(/* groups ="regression" */)
	public void tc013_ActivateDeviceWithBasicPlanonExistingAccountWithLSP() throws Exception

	{
		// verifies the Zoleo Page
		// zoleoPage.verifyZoleoPage();
		// Click on Login_SignUp Button
		zoleologinPage = zoleoPage.clickOnLoginSignUpButton();
		// verifies the Login PAge
		zoleologinPage.verifyLoginPage();
		// Enter Login Credentials
		zoleologinPage.enterLoginCredentials(zoleoTestData.emailAddress, zoleoTestData.password);
		// Click on Login Button
		zoleomyaccountPage = zoleologinPage.clickLogInButton();
		// verifies the ZoleoMyAccountPage
		zoleomyaccountPage.verifyZoleoMyAccountPage();
		// Click on DevicesAndPlans
		// zoleomyaccountPage.DeviceAndPlansButton(zoleoTestData.imei);
		// Click on AddNewDeviceOption
		zoleoAddDevicePage = zoleomyaccountPage.activateButton();
		// verify the AddDevicePage
		// zoleoAddDevicePage.VerifyAddDevicePage();
		// Enter the Details and Click on Next
		zoleoAddDevicePage.EnterDeviceDetails(zoleoTestData.devicenickname, zoleoTestData.zoleoemailaddress);
		// Select the plan and click next
		zoleoAddDevicePage.BasicPlanButton();
		// verify add feature section and click next
		zoleoAddDevicePage.VerifyAddFeatureSectionWithLSP();
		// Enter the contact details and click next
		zoleoCheckOutPage = zoleoAddDevicePage.CheckincontactAddContactDetails(zoleoTestData.sos1fullname,
				zoleoTestData.sos1email, zoleoTestData.sos1mobileno, zoleoTestData.sos2fullname,
				zoleoTestData.sos2email, zoleoTestData.sos2mobileno, zoleoTestData.sos1fullname);
		zoleoActivationPage = zoleoCheckOutPage.GetAndEnterTheBillingAddressAndCCDetails();
		zoleoActivationPage.verifyZoleoActivationPage();
		zoleoActivationPage.VerifyDeviceDetails();
		zoleomyaccountPage = zoleoActivationPage.ClickMyAccount();
		zoleomyaccountPage.DeviceAndPlansButton();
		zoleomyaccountPage.VerifyActivationMSGfrommailbox(zoleoTestData.emailusername, zoleoTestData.emailpassword);
	}

	/**
	 * Purpose- This method contains the logic for ' signup with new account
	 * Functionality in Zoleo Application
	 * 
	 * @throws Exception
	 */
	@Test(/* groups ="regression" */)
	public void tc014_ActivateDeviceWithInTouchPlanonExistingAccountWithLSP() throws Exception

	{
		// verifies the Zoleo Page
		// zoleoPage.verifyZoleoPage();
		// Click on Login_SignUp Button
		zoleologinPage = zoleoPage.clickOnLoginSignUpButton();
		// verifies the Login PAge
		zoleologinPage.verifyLoginPage();
		// Enter Login Credentials
		zoleologinPage.enterLoginCredentials(zoleoTestData.emailAddress, zoleoTestData.password);
		// Click on Login Button
		zoleomyaccountPage = zoleologinPage.clickLogInButton();
		// verifies the ZoleoMyAccountPage
		zoleomyaccountPage.verifyZoleoMyAccountPage();
		// Click on DevicesAndPlans
		// zoleomyaccountPage.DeviceAndPlansButton(zoleoTestData.imei);
		// Click on AddNewDeviceOption
		zoleoAddDevicePage = zoleomyaccountPage.activateButton();
		// verify the AddDevicePage
		// zoleoAddDevicePage.VerifyAddDevicePage();
		// Enter the Details and Click on Next
		zoleoAddDevicePage.EnterDeviceDetails(zoleoTestData.devicenickname, zoleoTestData.zoleoemailaddress);
		// Select the plan and click next
		zoleoAddDevicePage.TouchPlanButton();
		// verify add feature section and click next
		zoleoAddDevicePage.VerifyAddFeatureSectionWithLSP();
		// Enter the contact details and click next
		zoleoCheckOutPage = zoleoAddDevicePage.CheckincontactAddContactDetails(zoleoTestData.sos1fullname,
				zoleoTestData.sos1email, zoleoTestData.sos1mobileno, zoleoTestData.sos2fullname,
				zoleoTestData.sos2email, zoleoTestData.sos2mobileno, zoleoTestData.sos1fullname);
		zoleoActivationPage = zoleoCheckOutPage.GetAndEnterTheBillingAddressAndCCDetails();
		zoleoActivationPage.verifyZoleoActivationPage();
		zoleoActivationPage.VerifyDeviceDetails();
		zoleomyaccountPage = zoleoActivationPage.ClickMyAccount();
		zoleomyaccountPage.DeviceAndPlansButton();
		zoleomyaccountPage.VerifyActivationMSGfrommailbox(zoleoTestData.emailusername, zoleoTestData.emailpassword);

	}

	/**
	 * Purpose- This method contains the logic for ' signup with new account
	 * Functionality in Zoleo Application
	 * 
	 * @throws Exception
	 */
	@Test(/* groups ="regression" */)
	public void tc015_ActivateDeviceWithUnlimitedPlanonExistingAccountWithLSP() throws Exception

	{
		// verifies the Zoleo Page
		// zoleoPage.verifyZoleoPage();
		// Click on Login_SignUp Button
		zoleologinPage = zoleoPage.clickOnLoginSignUpButton();
		// verifies the Login PAge
		zoleologinPage.verifyLoginPage();
		// Enter Login Credentials
		zoleologinPage.enterLoginCredentials(zoleoTestData.emailAddress, zoleoTestData.password);
		// Click on Login Button
		zoleomyaccountPage = zoleologinPage.clickLogInButton();
		// verifies the ZoleoMyAccountPage
		// zoleomyaccountPage.verifyZoleoMyAccountPage();
		// Click on DevicesAndPlans
		// zoleomyaccountPage.DeviceAndPlansButton(zoleoTestData.imei);
		// Click on AddNewDeviceOption
		zoleoAddDevicePage = zoleomyaccountPage.activateButton();
		// verify the AddDevicePage
		// zoleoAddDevicePage.VerifyAddDevicePage();
		// Enter the Details and Click on Next
		zoleoAddDevicePage.EnterDeviceDetails(zoleoTestData.devicenickname, zoleoTestData.zoleoemailaddress);
		// Select the plan and click next
		zoleoAddDevicePage.UnlimitedPlanButton();
		// verify add feature section and click next
		zoleoAddDevicePage.VerifyAddFeatureSectionWithLSP();
		// Enter the contact details and click next
		zoleoCheckOutPage = zoleoAddDevicePage.CheckincontactAddContactDetails(zoleoTestData.sos1fullname,
				zoleoTestData.sos1email, zoleoTestData.sos1mobileno, zoleoTestData.sos2fullname,
				zoleoTestData.sos2email, zoleoTestData.sos2mobileno, zoleoTestData.sos1fullname);
		zoleoActivationPage = zoleoCheckOutPage.GetAndEnterTheBillingAddressAndCCDetails();
		zoleoActivationPage.verifyZoleoActivationPage();
		zoleoActivationPage.VerifyDeviceDetails();
		zoleomyaccountPage = zoleoActivationPage.ClickMyAccount();
		zoleomyaccountPage.DeviceAndPlansButton();
		zoleomyaccountPage.VerifyDeviceAndPlansAfterActivation();
		zoleomyaccountPage.VerifyActivationMSGfrommailbox(zoleoTestData.emailusername, zoleoTestData.emailpassword);
	}

	/**
	 * Purpose- This method contains the logic for 'changing region after sign up
	 * new account in Zoleo Application
	 * 
	 * @throws InterruptedException
	 */
	@Test(/* groups ="regression" */)
	public void tc016_TheCustomerCanSelectCountryBeforeTheFirstOrderActivation() throws Exception {
		zoleoPage.Auth();
		zoleologinPage = zoleoPage.clickOnLoginSignUpButton();
		zoleosignupPage = zoleologinPage.clickOnSignUpOption();
		zoleosignupPage.verifySignUpPage();
		zoleosignupPage.enterSignUpCredentials(zoleoTestData.signupemail, zoleoTestData.signupphoneno,
				zoleoTestData.givenname, zoleoTestData.familyname, zoleoTestData.signuppassword);
		zoleosignupPage.clickonAccountSignUpButton();
		zoleosignupPage.getOTPfrommailbox(zoleoTestData.emailusername, zoleoTestData.emailpassword);
		zoleoCountryPage = zoleosignupPage.EnterAndVerifyOTP();
		zoleomyaccountPage = zoleoCountryPage.SaveBillingCountry();
		// zoleomyaccountPage.verifyZoleoMyAccountPage();
		zoleomyaccountPage.VerifyAccountInformationAfterSignUp(zoleoTestData.signupemail, zoleoTestData.signupphoneno,
				zoleoTestData.givenname, zoleoTestData.familyname);

		zoleomyaccountPage.ChangeCountry();

	}

	/**
	 * Purpose- This method contains the logic for 'changing region after sign up
	 * new account in Zoleo Application
	 * 
	 * @throws InterruptedException
	 */
	@Test(/* groups ="regression" */)
	public void tc017_TheCustomerCannotSelectCountryAfterActivatingFirstOrder() throws Exception {
		zoleoPage.Auth();
		// verifies the Zoleo Page
		// zoleoPage.verifyZoleoPage();
		// Click on Login_SignUp Button
		zoleologinPage = zoleoPage.clickOnLoginSignUpButton();
		// verifies the Login PAge
		zoleologinPage.verifyLoginPage();
		// Enter Login Credentials
		zoleologinPage.enterLoginCredentials(zoleoTestData.emailAddress, zoleoTestData.password);
		// Click on Login Button
		zoleomyaccountPage = zoleologinPage.clickLogInButton();
		// verifies the ZoleoMyAccountPage
		zoleomyaccountPage.verifyZoleoMyAccountPage();
		// Click on DevicesAndPlans
		// zoleomyaccountPage.DeviceAndPlansButton(zoleoTestData.imei);
		// Click on AddNewDeviceOption
		zoleoAddDevicePage = zoleomyaccountPage.activateButton();
		// verify the AddDevicePage
		// zoleoAddDevicePage.VerifyAddDevicePage();
		// Enter the Details and Click on Next
		zoleoAddDevicePage.EnterDeviceDetails(zoleoTestData.devicenickname, zoleoTestData.zoleoemailaddress);
		// Select the plan and click next
		zoleoAddDevicePage.BasicPlanButton();
		// verify add feature section and click next
		zoleoAddDevicePage.VerifyAddFeatureSection();
		// Enter the contact details and click next
		zoleoCheckOutPage = zoleoAddDevicePage.CheckincontactAddContactDetails(zoleoTestData.sos1fullname,
				zoleoTestData.sos1email, zoleoTestData.sos1mobileno, zoleoTestData.sos2fullname,
				zoleoTestData.sos2email, zoleoTestData.sos2mobileno, zoleoTestData.sos1fullname);
		zoleoActivationPage = zoleoCheckOutPage.GetAndEnterTheBillingAddressAndCCDetails();
		zoleoActivationPage.verifyZoleoActivationPage();
		zoleoActivationPage.VerifyDeviceDetails();
		zoleomyaccountPage = zoleoActivationPage.ClickMyAccount();
		zoleomyaccountPage.VerifyCountryIconIsClickableORNot();

	}

	/**
	 * Purpose- This method contains the logic for 'changing region after sign up
	 * new account in Zoleo Application
	 * 
	 * @throws InterruptedException
	 */
	@Test(/* groups ="regression" */)
	public void tc018_LoginAccountWhichHasHaveBillingAddressThenChangeRegionCodeOnBrowserURL() throws Exception {

		zoleoPage.Auth();
		zoleologinPage = zoleoPage.clickActivateButton();
		zoleologinPage.enterLoginCredentials(zoleoTestData.emailAddress, zoleoTestData.password);
		zoleoAddDevicePage = zoleologinPage.clickNoDeviceAddedLogInButton();
		zoleoAddDevicePage.ChangeTheCountryURL();

	}

	/**
	 * Purpose- This method contains the logic for ' signup with new account
	 * Functionality in Zoleo Application
	 * 
	 * @throws Exception
	 */
	@Test(/* groups ="regression" */)
	public void tc019_DeactivateAllDevicesTheCustomerStillCannotSelectRegion() throws Exception {
		// verifies the Zoleo Page
		// zoleoPage.verifyZoleoPage();
		// Click on Login_SignUp Button
		zoleologinPage = zoleoPage.clickOnLoginSignUpButton();
		// verifies the Login PAge
		zoleologinPage.verifyLoginPage();
		// Enter Login Credentials
		zoleologinPage.enterLoginCredentials(zoleoTestData.emailAddress, zoleoTestData.password);
		// Click on Login Button
		zoleomyaccountPage = zoleologinPage.clickLogInButton();
		// verifies the ZoleoMyAccountPage
		zoleomyaccountPage.verifyZoleoMyAccountPage();
		// Click on DevicesAndPlans
		zoleomyaccountPage.DeviceAndPlansButton();
		zoleomyaccountPage.DeActivateDevice();
		zoleomyaccountPage.VerifyCountryIconIsClickableORNot();
		

	}
	/**
	 * Purpose- This method contains the logic for ' signup with new account
	 * Functionality in Zoleo Application
	 * 
	 * @throws Exception
	 */
	@Test(/* groups ="regression" */)
	public void tc020_ChangeRegionsURLInBrowser() throws Exception {
		// verifies the Zoleo Page  
		// zoleoPage.verifyZoleoPage();
		// Click on Login_SignUp Button
		zoleologinPage = zoleoPage.clickOnLoginSignUpButton();
		// verifies the Login PAge
		zoleologinPage.verifyLoginPage();
		// Enter Login Credentials
		zoleologinPage.enterLoginCredentials(zoleoTestData.emailAddress, zoleoTestData.password);
		// Click on Login Button
		zoleomyaccountPage = zoleologinPage.clickLogInButton();
		// verifies the ZoleoMyAccountPage
		zoleomyaccountPage.verifyZoleoMyAccountPage();
		zoleomyaccountPage.ChangeTheCountryURL();
		
}
}
