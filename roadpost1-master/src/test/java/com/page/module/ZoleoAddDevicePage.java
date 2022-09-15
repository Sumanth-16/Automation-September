package com.page.module;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ru.yandex.qatools.allure.annotations.Step;

import com.page.data.ZoleoData;
import com.page.locators.ZoleoAddDevicePageLocators;
import com.selenium.Dynamic;
import com.selenium.SafeActions;
import com.testng.Assert;
import com.utilities.TextFiles;

public class ZoleoAddDevicePage extends SafeActions implements ZoleoAddDevicePageLocators {
	public static String NEWIMEISERIALNO;
	public static String Plan;
	public static String NEWIMEI;
	public static String NewSerialNo;
	private WebDriver driver;
ZoleoData zoleoTestData =new ZoleoData();
	// Constructor to define/call methods
	ZoleoAddDevicePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	/**
	 * purpose-To verify the done tick mark is being displayed or not
	 * 
	 * @throws InterruptedException
	 *
	 */
	@Step("Verifying AddDevice page")
	public void VerifyAddDevicePage() throws InterruptedException {
		Thread.sleep(8000);
		boolean bDoneTickMark = isElementPresent(DONE_TICKMARK, VERYLONGWAIT);
		Assert.assertTrue(bDoneTickMark, "'DoneTickMark' is not being displayed on the 'ZoleoMyAccount' page");
		waitForPageToLoad(VERYLONGWAIT);
	}

	/**
	 * Purpose-Enter the required fields for Adding New Device click on Next button
	 * 
	 * @throws InterruptedException
	 */
	@Step("To enter the required fields for Adding New Device click on Next button")
	public void EnterDeviceDetails(String devicenickname, String zoleoemailaddress) throws Exception {
		waitForPageToLoad();
		Thread.sleep(10000);
		NEWIMEISERIALNO = TextFiles.CompareTwoTextFiles();
		String[] splitString = NEWIMEISERIALNO.split("\\s");
		NEWIMEI = splitString[0];
		NewSerialNo = splitString[1];
		Thread.sleep(10000);
		safeJavaScriptType(IMEI, NEWIMEI, "'IMEI NO'is given in the page'", LONGWAIT);
		safeJavaScriptType(SERIAL_NUMBER, NewSerialNo, "'Serial NO'is given in the page'", LONGWAIT);
		safeJavaScriptType(DEVICE_NICKNAME, devicenickname, "'Device NickName'is given in the page", LONGWAIT);
		safeJavaScriptType(ZOLEO_EMAILADDRESS, zoleoemailaddress, "'Zoleoemailaddress'is given in the page", LONGWAIT);
		safeJavaScriptClick(NEXT_BUTTON, "'Next' button in 'Add Device' page is clicked'", LONGWAIT);
	    waitForPageToLoad(MEDIUMWAIT);
	}
	/**
	 * Purpose-Enter the required fields for Adding New Device click on Next button
	 * 
	 * @throws InterruptedException
	 */
	@Step("To enter the required fields for Adding New Device click on Next button")
	public void EnterDeviceDetailsForReActivate() throws Exception {
		//waitForPageToLoad();
		Thread.sleep(10000);
		safeJavaScriptType(IMEI,ZoleoMyAccountPage.MyAccountImei, "'IMEI NO'is given in the page'", LONGWAIT);
		safeJavaScriptType(SERIAL_NUMBER, NewSerialNo, "'Serial NO'is given in the page'", LONGWAIT);
		//safeJavaScriptType(DEVICE_NICKNAME, devicenickname, "'Device NickName'is given in the page", LONGWAIT);
		//safeJavaScriptType(ZOLEO_EMAILADDRESS, zoleoemailaddress, "'Zoleoemailaddress'is given in the page", LONGWAIT);
		safeJavaScriptClick(NEXT_BUTTON, "'Next' button in 'Add Device' page is clicked'", LONGWAIT);
	    waitForPageToLoad(MEDIUMWAIT);
	}

	/**
	 * purpose-To verify the pick a plan section is being displayed or not
	 *
	 */
	@Step("Verifying pick a plan section")
	public void VerifyPickaPlanSection() {
		boolean bAddDeviceTickMark = isElementPresent(ADD_DEVICE_TICKMARK, VERYLONGWAIT);
		Assert.assertTrue(bAddDeviceTickMark,
				"'AddDeviceTickMark' is not being displayed on the 'ZoleoAddDevice' page");
	}

	/**
	 * purpose-To Click on the required items from the page
	 * 
	 * @throws InterruptedException
	 */
	@Step("Click on the required plan button from the page ")
	public void BasicPlanButton() throws InterruptedException {
		safeJavaScriptClick(BASIC_PLAN, "'REQUIRED_PLAN' Button in 'ZoleoAddDevice' page", MEDIUMWAIT);
		safeJavaScriptClick(PLAN_CHECKBOX, "'PLAN_CHECKBOX' Button in 'PopUp' page", MEDIUMWAIT);
		safeJavaScriptClick(PLAN_ACCEPT, "'PLAN_ACCEPT' Button in 'PopUp' page", MEDIUMWAIT);
		Thread.sleep(5000);
		safeJavaScriptClick(NEXT_BUTTON, "'NEXTBUTTON' Button in 'ZoleoAddDevice' page", MEDIUMWAIT);
		waitForPageToLoad(MEDIUMWAIT);
		Plan="Basic - £18.00";
		
	}
	
	/**
	 * purpose-To Click on the required items from the page
	 * 
	 * @throws InterruptedException
	 */
	@Step("Click on the required plan button from the page ")
	public void UnlimitedPlanButton() throws InterruptedException {
		safeJavaScriptClick(UNLIMITED_PLAN, "'REQUIRED_PLAN' Button in 'ZoleoAddDevice' page", MEDIUMWAIT);
		safeJavaScriptClick(PLAN_CHECKBOX, "'PLAN_CHECKBOX' Button in 'PopUp' page", MEDIUMWAIT);
		safeJavaScriptClick(PLAN_ACCEPT, "'PLAN_ACCEPT' Button in 'PopUp' page", MEDIUMWAIT);
		Thread.sleep(5000);
		safeJavaScriptClick(NEXT_BUTTON, "'NEXTBUTTON' Button in 'ZoleoAddDevice' page", MEDIUMWAIT);
		waitForPageToLoad(MEDIUMWAIT);
		Plan="Unlimited - £58.00";
		
	}
	/**
	 * purpose-To Click on the required items from the page
	 * 
	 * @throws InterruptedException
	 */
	@Step("Click on the required plan button from the page ")
	public void TouchPlanButton() throws InterruptedException {
		safeJavaScriptClick(IN_TOUCH_PLAN, "'REQUIRED_PLAN' Button in 'ZoleoAddDevice' page", MEDIUMWAIT);
		safeJavaScriptClick(PLAN_CHECKBOX, "'PLAN_CHECKBOX' Button in 'PopUp' page", MEDIUMWAIT);
		safeJavaScriptClick(PLAN_ACCEPT, "'PLAN_ACCEPT' Button in 'PopUp' page", MEDIUMWAIT);
		Thread.sleep(5000);
		safeJavaScriptClick(NEXT_BUTTON, "'NEXTBUTTON' Button in 'ZoleoAddDevice' page", MEDIUMWAIT);
		waitForPageToLoad(MEDIUMWAIT);
		Plan="In Touch";
		
	}
	
	
	
	
	
	

	/**
	 * purpose-To verify the add feature section is being displayed or not
	 * 
	 * @throws InterruptedException
	 *
	 */
	@Step("Verifying add feature section")
	public void VerifyAddFeatureSection() throws InterruptedException {
		Thread.sleep(5000);
		boolean bPlanTickMark = isElementPresent(PLAN_TICKMARK, VERYLONGWAIT);
		Assert.assertTrue(bPlanTickMark, "'PlanTickMark' is not being displayed on the 'ZoleoAddDevice' page");
		safeJavaScriptClick(NEXT_BUTTON, "'NEXTBUTTON' Button in 'ZoleoAddDevice' page", MEDIUMWAIT);
		waitForPageToLoad(MEDIUMWAIT);
	}
	/**
	 * purpose-To verify the add feature section is being displayed or not
	 * 
	 * @throws InterruptedException
	 *
	 */
	@Step("Verifying add feature section")
	public void VerifyAddFeatureSectionWithLSP() throws InterruptedException {
		Thread.sleep(5000);
		boolean bPlanTickMark = isElementPresent(PLAN_TICKMARK, VERYLONGWAIT);
		Assert.assertTrue(bPlanTickMark, "'PlanTickMark' is not being displayed on the 'ZoleoAddDevice' page");
		safeJavaScriptClick(LOCATION_SHARE,"'Location Share' option is Selected",MEDIUMWAIT);
		safeJavaScriptClick(NEXT_BUTTON, "'NEXTBUTTON' Button in 'ZoleoAddDevice' page", MEDIUMWAIT);
		waitForPageToLoad(MEDIUMWAIT);
	}

	/**
	 * Purpose-Enter the required fields for Adding New Device click on Next button
	 * 
	 * @throws InterruptedException
	 */
	@Step("To enter the required fields for Adding Contacts and click on Next button")
	public ZoleoCheckOutPage AddContactDetails(String sos1fullname, String sos1email, String sos1mobileno,
			String sos2fullname, String sos2email, String sos2mobileno, String checkincontactfullname,
			String checkincontactemail, String checkincontactmobileno) throws InterruptedException {

		/*safeJavaScriptClearAndType(SOS1_FULLNAME, sos1fullname, "'SOS1_FULLNAME'is given in the page'", MEDIUMWAIT);
		Thread.sleep(3000);
		safeJavaScriptClearAndType(SOS1_EMAIL, sos1email, "'SOS1_EMAIL'is given in the page'", MEDIUMWAIT);
		safeJavaScriptClearAndType(SOS1_MOBILENO, sos1mobileno, "'SOS1_MOBILENO'is given in the page", MEDIUMWAIT);
		safeJavaScriptClearAndType(SOS2_FULLNAME, sos2fullname, "'SOS2_FULLNAME'is given in the page", MEDIUMWAIT);
		safeJavaScriptClearAndType(SOS2_EMAIL, sos2email, "'SOS2_EMAIL'is given in the page", MEDIUMWAIT);
		safeJavaScriptClearAndType(SOS2_MOBILENO, sos2mobileno, "'SOS2_MOBILENO'is given in the page", MEDIUMWAIT);
		safeJavaScriptClearAndType(CHECKIN_CONTACT_FULLNAME, checkincontactfullname,
				"'CHECKIN_CONTACT_FULLNAME'is given in the page", MEDIUMWAIT);
		safeJavaScriptClearAndType(CHECKIN_CONTACT_EMAIL, checkincontactemail,
				"'CHECKIN_CONTACT_EMAILs'is given in the page", MEDIUMWAIT);
		safeJavaScriptClearAndType(CHECKIN_CONTACT_MOBILENO, checkincontactmobileno,
				"'CHECKIN_CONTACT_MOBILENO'is given in the page", MEDIUMWAIT);*/
		safeJavaScriptClick(NOTIFY_BY, "notify by is clicked", MEDIUMWAIT);
		safeJavaScriptClick(OPTIONS_TO_SELECT, "SMS option is selected", MEDIUMWAIT);
		Thread.sleep(8000);
		safeJavaScriptClick(NEXT_BUTTON, "'Next' button in 'Add Device' page is clicked'", MEDIUMWAIT);
		return new ZoleoCheckOutPage(driver);
	}
	/**
	 * Purpose-Enter the required fields for Adding New Device click on Next button
	 * 
	 * @throws InterruptedException
	 */
	@Step("To enter the required fields for Adding Contacts and click on Next button")
	public ZoleoCheckOutPage CheckincontactAddContactDetails(String sos1fullname, String sos1email, String sos1mobileno,
			String sos2fullname, String sos2email, String sos2mobileno, String checkincontactfullname) throws InterruptedException {

		safeJavaScriptClearAndType(SOS1_FULLNAME, sos1fullname, "'SOS1_FULLNAME'is given in the page'", MEDIUMWAIT);
		Thread.sleep(3000);
		safeJavaScriptClearAndType(SOS1_EMAIL, sos1email, "'SOS1_EMAIL'is given in the page'", MEDIUMWAIT);
		safeJavaScriptClearAndType(SOS1_MOBILENO, sos1mobileno, "'SOS1_MOBILENO'is given in the page", MEDIUMWAIT);
		safeJavaScriptClearAndType(SOS2_FULLNAME, sos2fullname, "'SOS2_FULLNAME'is given in the page", MEDIUMWAIT);
		safeJavaScriptClearAndType(SOS2_EMAIL, sos2email, "'SOS2_EMAIL'is given in the page", MEDIUMWAIT);
		safeJavaScriptClearAndType(SOS2_MOBILENO, sos2mobileno, "'SOS2_MOBILENO'is given in the page", MEDIUMWAIT);
		safeJavaScriptClearAndType(CHECKIN_CONTACT_FULLNAME, sos1fullname,"'CHECKIN_CONTACT_FULLNAME'is given in the page", MEDIUMWAIT);
		WebElement Checkin_Fullname = driver.findElement(CHECKIN_CONTACT_FULLNAME);
		Checkin_Fullname.sendKeys("\b");
		Thread.sleep(3000);
		safeJavaScriptClick(CHECKIN_SOS1,"SOS1 for CheckinContact is clicked", IMPLICITWAIT);
	//	safeJavaScriptClearAndType(CHECKIN_CONTACT_EMAIL, checkincontactemail,
	//			"'CHECKIN_CONTACT_EMAILs'is given in the page", MEDIUMWAIT);
	//	safeJavaScriptClearAndType(CHECKIN_CONTACT_MOBILENO, checkincontactmobileno,
	//			"'CHECKIN_CONTACT_MOBILENO'is given in the page", MEDIUMWAIT);
		safeJavaScriptClick(NOTIFY_BY, "notify by is clicked", MEDIUMWAIT);
		safeJavaScriptClick(OPTIONS_TO_SELECT, "SMS option is selected", MEDIUMWAIT);
		Thread.sleep(8000);
		safeJavaScriptClick(NEXT_BUTTON, "'Next' button in 'Add Device' page is clicked'", MEDIUMWAIT);
		return new ZoleoCheckOutPage(driver);
	}
	/**
	 * Purpose-click on my account option
	 * 
	 * @throws InterruptedException
	 */
	@Step("To click on my account option")
	public ZoleoMyAccountPage ClickOnMyAccountOption() throws InterruptedException {
		Thread.sleep(3000);
		safeJavaScriptClick(MY_ACCOUNT_OPTION, "Clicked on'My Account' Option in Add device Page", SHORTWAIT);
		return new ZoleoMyAccountPage(driver);
	}
	/**
	 * Purpose-click on my account option
	 * 
	 * @throws InterruptedException
	 */
	@Step("To click on my account option")
	public  void ChangeTheCountryURL() throws InterruptedException  {
		String NEWURL;
		String URL=driver.getCurrentUrl();
		String CountryName=zoleoTestData.countryRegion;
		if(CountryName=="United Kingdom");
		{
			driver.get("https://myzoleo-aws-stg.myzoleo.com/en-us/default-activation.html");
			Thread.sleep(15000);
			 NEWURL=driver.getCurrentUrl();
			
		}
		String CompareURL="https://myzoleo-aws-stg.myzoleo.com/en-gb/customer/account";
		if(NEWURL==CompareURL)
		{
			Assert.assertTrue(true);
		}
		
	}
}

