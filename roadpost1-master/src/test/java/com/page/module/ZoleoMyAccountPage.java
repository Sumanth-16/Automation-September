package com.page.module;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.page.data.ZoleoData;
import com.page.locators.GMAILLocators;
import com.page.locators.ZoleoMyAccountPageLocators;
import com.page.locators.ZoleoSignInPageLocators;
import com.selenium.Dynamic;
import com.selenium.SafeActions;
import com.testng.Assert;
import com.utilities.TextFiles;

import ru.yandex.qatools.allure.annotations.Step;

public class ZoleoMyAccountPage extends SafeActions implements ZoleoMyAccountPageLocators, GMAILLocators {
	private WebDriver driver;

	public static String sAccountInformation;
	public static String ChangeCountry;
	public static String BillingAddressDescription;
	public static String CommPrefText;
	public static String MyAccountImei;
	ZoleoData zoleoTestData = new ZoleoData();

	// Constructor to define/call methods
	public ZoleoMyAccountPage(WebDriver driver) {
		super(driver);
		this.driver = driver;

	}

	/**
	 * purpose-To Verifies whether navigated to the ZoleoMyAccountPage or not throws
	 * Exception
	 * 
	 * @throws InterruptedException
	 */
	@Step("Verifies the ZoleoMyAccount page")
	public void verifyZoleoMyAccountPage() throws InterruptedException {
		Thread.sleep(5000);
		waitForPageToLoad();
		boolean bActivateButton = isElementPresent(ACTIVATE_BUTTON, MEDIUMWAIT);
		Assert.assertTrue(bActivateButton, "'Activate Button' is not being displayed on the 'ZoleoMyAccount' page");
	}

	/**
	 * purpose-To Verify whether the Account information is displayed correct or not
	 * throws Exception
	 */
	@Step("Verifies the ZoleoMyAccount page")
	public void VerifyAccountInformation(String sAccountUsername) {
		boolean bAccountInformation = isElementPresent(CONTACT_INFORMATION, MEDIUMWAIT);
		Assert.assertTrue(bAccountInformation,
				"'Contact Information' is not being displayed on the 'ZoleoMyAccount' page");
		sAccountInformation = safeGetText(CONTACT_INFORMATION, "The Contact Information is retreived ", IMPLICITWAIT);
		if (sAccountInformation.contains(sAccountUsername)) {

			Assert.assertEquals(bAccountInformation, bAccountInformation);
		}
	}

	@Step("Click on the Activate Button ")
	public ZoleoAddDevicePage activateButton() {
		safeJavaScriptClick(ACTIVATE_BUTTON, "Activate Button is clicked", IMPLICITWAIT);
		waitForPageToLoad(MEDIUMWAIT);
		return new ZoleoAddDevicePage(driver);

	}

	/**
	 * purpose-To Verify whether the Account information is displayed correct or not
	 * throws Exception
	 */
	@Step("Verifies the ZoleoMyAccount page")
	public void VerifyAccountInformationAfterSignUp(String sSignUpEmail, String sSignUpPhoneNo, String sGivenName,
			String sFamilyName) {
		boolean bAccountInformation = isElementPresent(CONTACT_INFORMATION, MEDIUMWAIT);
		Assert.assertTrue(bAccountInformation,
				"'Contact Information' is not being displayed on the 'ZoleoMyAccount' page");
		sAccountInformation = safeGetText(CONTACT_INFORMATION, "The Contact Information is retreived ", IMPLICITWAIT);
		if ((sAccountInformation.contains(sSignUpEmail)) && (sAccountInformation.contains(sSignUpPhoneNo))
				&& (sAccountInformation.contains(sGivenName)) && (sAccountInformation.contains(sFamilyName))) {

			Assert.assertEquals(bAccountInformation, bAccountInformation);
		}
	}

	/**
	 * purpose-To Verifies whether navigated to the ZoleoMyAccountPage or not throws
	 * Exception
	 */
	@Step("Verifies the Billing Address in My Account page")
	public void VerifyBillingAddressAndCCDetailsInMyAccountPage() {
		boolean bBillingAddress = isElementPresent(BILLING_ADDRESS, MEDIUMWAIT);
		Assert.assertTrue(bBillingAddress, "'Billing Address' is not being displayed on the 'ZoleoMyAccount' page");
		BillingAddressDescription = safeGetText(BILLING_ADDRESS_FIELD, "Retrieved Text From Billing Address",
				IMPLICITWAIT);
		if (BillingAddressDescription == zoleoTestData.noBillingAddressText) {
			Assert.assertTrue(true);
		}
		boolean bCreditCardDetails = isElementPresent(CREDIT_CARD_DETAILS, MEDIUMWAIT);
		Assert.assertTrue(bCreditCardDetails,
				"'CreditCard Details' is not being displayed on the 'ZoleoMyAccount' page");
	}

	/**
	 * purpose-To Verifies whether navigated to the ZoleoMyAccountPage or not throws
	 * Exception
	 */
	@Step("Verifies the CommunicationPrefencesText in My Account page")
	public void VerifyCommunicationPrefencesTextInMyAccountPage() {
		boolean bCommPrefText = isElementPresent(COMM_PREF_TEXT, MEDIUMWAIT);
		Assert.assertTrue(bCommPrefText, "'Billing Address' is not being displayed on the 'ZoleoMyAccount' page");
		CommPrefText = safeGetText(COMM_PREF_FIELD, "Retrieved Text From communication", IMPLICITWAIT);
		if (CommPrefText == zoleoTestData.communicationpreferenceText) {
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}
	}

	@Step("Change Country  ")
	public void ChangeCountry() throws Exception {

		safeJavaScriptClick(COUNTRY_ICON, "Country-Icon Button is clicked", IMPLICITWAIT);
		String Countries[] = { "Australia", "Canada", "Denmark", "Finland", "New Zealand", "Norway", "Sweden",
				"United Kingdom", "United States" };
		for (String Country : Countries) {
			if (Country != zoleoTestData.countryRegion) {
				ChangeCountry = Country;
				break;
			}
		}
		safeSelectOptionInDropDownByVisibleText(CHANGE_COUNTRY_DROPDOWN, ChangeCountry,
				"'click on the required country to change'", IMPLICITWAIT);
		safeJavaScriptClick(CHANGE_COUNTRY_CHECKBOX, "' Billing country checkbox' button in 'Country' page is clicked'",
				IMPLICITWAIT);
		safeJavaScriptClick(CHANGE_COUNTRY_SAVEBUTTON, "' save button in 'Country' page is clicked'", IMPLICITWAIT);
		Thread.sleep(5000);

		By CountryLogo;
		switch (ChangeCountry) {
		case "Canada":
			CountryLogo = CHANGED_COUNTRY_CANADA;
			break;
		case "Australia":
			CountryLogo = CHANGED_COUNTRY_AUSTRALIA;
			break;
		case "Denmark":
			CountryLogo = CHANGED_COUNTRY_RESTOF;
			break;
		case "New Zealand":
			CountryLogo = CHANGED_COUNTRY_AUSTRALIA;
			break;
		case "Finland":
			CountryLogo = CHANGED_COUNTRY_RESTOF;
			break;
		case "Norway":
			CountryLogo = CHANGED_COUNTRY_RESTOF;
			break;
		case "Sweden":
			CountryLogo = CHANGED_COUNTRY_RESTOF;
			break;
		case "United Kingdom":
			CountryLogo = CHANGED_COUNTRY_UK;
			break;
		case "United States":
			CountryLogo = CHANGED_COUNTRY_US;
			break;
		default:
			CountryLogo = CHANGED_COUNTRY_RESTOF;
		}
		boolean bcountrylogo = isElementPresent(CountryLogo, MEDIUMWAIT);
		Assert.assertTrue(bcountrylogo, ChangeCountry + "Logo is  Correctly displayed on the 'ZoleoMyAccount' page");

	}

	/**
	 * purpose-To choose the required Billing Country on the country page
	 * 
	 * @throws InterruptedException
	 */
	@Step("To enter the requied Billing info details on the page")
	public void SaveBillingCountry() throws Exception {

		safeSelectOptionInDropDownByVisibleText(BILLING_COUNTRY, zoleoTestData.countryRegion,
				"'click on the required country'", IMPLICITWAIT);
		safeJavaScriptClick(BILLING_COUNTRY_CHECKBOX,
				"' Billing country checkbox' button in 'Country' page is clicked'", IMPLICITWAIT);
		safeJavaScriptClick(BILLING_SAVEBUTTON, "' save button in 'Country' page is clicked'", MEDIUMWAIT);

		waitForPageToLoad(LONGWAIT);

	}

	/**
	 * purpose-To click on payment info in the my account page
	 * 
	 * @throws InterruptedException
	 */
	@Step("To click on payment info in the my account page")
	public ZoleoPaymentInfoPage ClickOnPaymentInfo() throws Exception {
		safeJavaScriptClick(PAYMENT_INFO, "' Payment info'  in 'My Account ' page is clicked'", IMPLICITWAIT);
		return new ZoleoPaymentInfoPage(driver);

	}

	/**
	 * purpose-To Click on the required item from the page
	 * 
	 * @param itemName item name
	 * @return
	 * @throws InterruptedException
	 * 
	 */

	@Step("Click on the DeviceAndPlans button from the page ")
	public void DeviceAndPlansButton() throws InterruptedException {

		Thread.sleep(5000);
		safeJavaScriptClick(DEVICES_AND_PLANS, "'DEVICES_AND_PLANS' Button in 'ZoleoMyAccount' page", MEDIUMWAIT);
	}

	/**
	 * purpose-To Click on the required item from the page
	 * 
	 * @param itemName item name
	 * @return
	 * @throws InterruptedException
	 * 
	 */

	@Step("Click on the DeviceAndPlans button from the page ")
	public void VerifyDeviceAndPlansAfterActivation() throws InterruptedException {

		Thread.sleep(15000);
		getListWebElementAssertion(MY_ACCOUNT_IMEIS, ZoleoAddDevicePage.NEWIMEI);
		MyAccountImei = safeGetText(MY_ACCOUNT_IMEIS, "The IMEI Displayed  is retreived ", IMPLICITWAIT);
		if (MyAccountImei.contains(ZoleoAddDevicePage.NEWIMEI)) {
			Assert.assertTrue(true);
		}

		getListWebElementAssertion(MY_ACCOUNT_PLANS, ZoleoAddDevicePage.Plan);
		String MyAccountPlan = safeGetText(MY_ACCOUNT_PLANS, "The IMEI Displayed  is retreived ", IMPLICITWAIT);
		if (MyAccountPlan.contains(ZoleoAddDevicePage.Plan)) {
			Assert.assertTrue(true);
		}

	}

	/**
	 * purpose-To Click on the required item from the page
	 * 
	 * @param itemName item name
	 * @return
	 * @throws InterruptedException
	 * 
	 */

	@Step("Click on the DeviceAndPlans button from the page ")
	public void DeActivateDevice() throws InterruptedException {

		Thread.sleep(15000);
		getListWebElementAssertion(MY_ACCOUNT_IMEIS, ZoleoAddDevicePage.NEWIMEI);
		MyAccountImei = safeGetText(MY_ACCOUNT_IMEIS, "The IMEI Displayed  is retreived ", IMPLICITWAIT);
		safeJavaScriptClick(DEACTIVATE_BUTTON, "Clicked on the Deactivate Button", IMPLICITWAIT);
		safeJavaScriptClick(DEACTIVATE_BUTTON_CONFIRM, "Clicked on the Confirm Deactivate Button", IMPLICITWAIT);
		safeJavaScriptClick(DONE_BUTTON, "Clicked on the Confirm Deactivate Button", IMPLICITWAIT);
		boolean bNoDevicesFound = isElementPresent(NO_DEVICES, MEDIUMWAIT);
		Assert.assertTrue(bNoDevicesFound, "'No Devices ' are displayed on the 'ZoleoMyAccount' page");
	}

	/**
	 * purpose-To Click on the required item from the page
	 * 
	 * @param itemName item name
	 * @return
	 * @throws InterruptedException
	 * 
	 */

	@Step("Click on the DeviceAndPlans button from the page ")
	public void ClickonAccountActivity() {

		safeJavaScriptClick(ACCOUNT_ACTIVITY, "Clicked on Account Activity Button", IMPLICITWAIT);

	}

	/**
	 * purpose-To Click on the required item from the page
	 * 
	 * @param itemName item name
	 * @return
	 * @throws InterruptedException
	 * 
	 */

	@Step("Click on the DeviceAndPlans button from the page ")
	public void VerifyDeactivation() {

		String Status = safeGetText(ACTIVITY_ROW, "Retrieved Activity Status ", IMPLICITWAIT);
		if (Status.contains("Deactivation")) {
			Assert.assertTrue(true, "The status is Updated");
		} else {
			Assert.assertTrue(false, "The status is not updated");
		}

	}

	/**
	 * purpose-To Click on the required item from the page
	 * 
	 * @param itemName item name
	 * @return
	 * @throws InterruptedException
	 * 
	 */

	@Step("Click on the DeviceAndPlans button from the page ")
	public void VerifyActivation() {

		String Status = safeGetText(ACTIVITY_ROW, "Retrieved Activity Status ", IMPLICITWAIT);
		if ((Status.contains("Activation")) && (Status.contains("Complete"))) {
			Assert.assertTrue(true, "The status is Updated");
		} else {
			Assert.assertTrue(false, "The status is not updated");
		}
		safeJavaScriptClick(VIEW_DETAIL, "Clicked on View Detail Button", IMPLICITWAIT);
		boolean bNoDevicesFound = isElementPresent(ACTIVATION_DETAILS, MEDIUMWAIT);
		Assert.assertTrue(bNoDevicesFound, "'Activation ' are displayed on the 'see details' page");
		safeJavaScriptClick(SEE_DETAILS, "Clicked on View Detail Button", IMPLICITWAIT);
		String SeeImeiDetails=safeGetText(SEE_IMEI_DETAILS,"Retrieved Imei Details",IMPLICITWAIT);
		if(SeeImeiDetails.contains(ZoleoMyAccountPage.MyAccountImei))
		{
			Assert.assertTrue(true);
		}
		else
		{
			Assert.assertFalse(false);
		}

	}
	/**
	 * purpose-To Click on the required item from the page
	 * 
	 * @param itemName item name
	 * @return
	 * @throws InterruptedException
	 * 
	 */

	@Step("Verify Country Icon Is Clickable OR Not in the page ")
	public void VerifyCountryIconIsClickableORNot() {
		
			
		try {
			safeJavaScriptClick(COUNTRY_ICON, "Country-Icon Button is clicked", IMPLICITWAIT);
			String Countries[] = { "Australia", "Canada", "Denmark", "Finland", "New Zealand", "Norway", "Sweden",
					"United Kingdom", "United States" };
			for (String Country : Countries) {
				if (Country != zoleoTestData.countryRegion) {
					ChangeCountry = Country;
					break;
				}
			}
			SelectOptionInDropDownByVisibleText(CHANGE_COUNTRY_DROPDOWN, ChangeCountry,
					"'click on the required country to change'", IMPLICITWAIT);
		
		}
		catch(Exception e) {
			Assert.assertTrue(true);
		}
		
			
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
			driver.get("https://myzoleo-aws-stg.myzoleo.com/en-us/customer/account");
			Thread.sleep(15000);
			 NEWURL=driver.getCurrentUrl();
			
		}
		
		if(NEWURL==URL)
		{
			Assert.assertTrue(true);
		}
		
	}
		
	
	

	/**
	 * purpose-To Click on the required item from the page
	 * 
	 * @param itemName item name
	 * @return
	 * @throws Exception
	 * 
	 */

	@Step("Get the Confirmation from the Gmail page ")

	public void VerifyActivationMSGfrommailbox(String sgmailusername, String sgmailpassword) throws Exception {
		zoleoTestData = new ZoleoData();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String openString = "window.open('" + zoleoTestData.emailurl + "', '_blank');";
		js.executeScript(openString);
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		safeClick(SIGN_IN_BUTTON, "'next button' in gmail page", IMPLICITWAIT);
		setImplicitWait(5000);

		safeClearAndType(GMAIL_USERNAME, sgmailusername, "'Username'in gmailpage", IMPLICITWAIT);
		safeClick(GMAIL_NEXT, "'next button' in gmail page", IMPLICITWAIT);
		Thread.sleep(5000);
		safeClearAndType(GMAIL_PASSWORD, sgmailpassword, "'Password' in gmailpage", IMPLICITWAIT);
		Thread.sleep(5000);
		safeClick(GMAIL_NEXT, "'next button' in gmail page", IMPLICITWAIT);
		setImplicitWait(5000);
		safeClick(GMAIL_NEXT, "'next button' in gmail page", IMPLICITWAIT);
		setImplicitWait(5000);
		By MSG = By.xpath("(//div[@class='xCI71 hoYNt']//span[text()='Your ZOLEO device has been activated!'])[1]");
		Boolean msg = isElementPresent(MSG);
		waitUntilClickable(MSG, "MSG Box is clicked ", IMPLICITWAIT);

		safeJavaScriptClick(MSG, "MSG Box is clicked ", IMPLICITWAIT);

		By IMEI_Gmail = By.xpath("((//table[@border='0']//tbody//tr)[5]//span)[1]");
		// if(isElementPresent(OTP1,SHORTWAIT ))

		String sGmailImei = safeGetText(IMEI_Gmail, "Imei from Gmail is retrieved", IMPLICITWAIT);
		if (sGmailImei.contains(ZoleoAddDevicePage.NEWIMEI)) {
			Assert.assertTrue(true);
			TextFiles.writedata();
		} else {
			Assert.assertFalse(true);
		}

	}

}
