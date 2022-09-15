package com.page.module;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import ru.yandex.qatools.allure.annotations.Step;

import com.page.data.AUSData;
import com.page.data.CanadaData;
import com.page.data.DenmarkData;
import com.page.data.FinlandData;
import com.page.data.NZData;
import com.page.data.NorwayData;
import com.page.data.SwedenData;
import com.page.data.UKData;
import com.page.data.USData;
import com.page.data.ZoleoData;
import com.page.locators.ZoleoCheckOutPageLocators;
import com.page.locators.ZoleoMyAccountPageLocators;
import com.page.locators.ZoleoPaymentInfoPageLocators;
import com.selenium.Dynamic;
import com.selenium.SafeActions;
//import org.testng.Assert;
import com.testng.Assert;

public class ZoleoPaymentInfoPage extends SafeActions implements ZoleoPaymentInfoPageLocators {
	private WebDriver driver;
	public static String CountryName;
	public static String PhoneNo;
	public static String StreetAddress;
	public static String City;
	public static String Zip;
	public static String NameOnCC;
	public static String CCNo;
	public static String ExpiryDate;
	public static String ExpiryMonth;
	public static String ExpiryYear;
	public static String CVV;
	public static String FirstName;
	public static String LastName;
	public static String State;
	ZoleoData zoleoTestData = new ZoleoData();
	UKData ukData = new UKData();
	FinlandData finlandData = new FinlandData();
	SwedenData swedenData = new SwedenData();
	NorwayData norwayData = new NorwayData();
	DenmarkData denmarkData = new DenmarkData();
	CanadaData canadaData = new CanadaData();
	USData usData = new USData();
	AUSData ausData = new AUSData();
	NZData nzData = new NZData();

	// Constructor to define/call methods
	public ZoleoPaymentInfoPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	/**
	 * Purpose- To verify whether contacts page headers is being displayed or not
	 *
	 */
	@Step("Verifying Payment Info  page")
	public void verifyPaymentInfoPage() {
		boolean beditpaymentTitle = isElementPresent(EDIT_PAYMENT, MEDIUMWAIT);

		Assert.assertTrue(beditpaymentTitle, "editpaymentTitle are not being displayed on checkout page");
	}

	/**
	 * Purpose- To Get and Enter the Billing Address and Credit Card Details
	 * 
	 * @throws Exception
	 */
	@Step("To Get and Enter the Billing Address and Credit Card Details")
	public void GetAndEnterTheBillingAddressAndCCDetails() throws Exception {
		Thread.sleep(5000);
		CountryName = zoleoTestData.countryRegion;
		String s[] = { "United Kingdom", "Denmark", "Sweden", "Finland" };
		ArrayList<String> ukeu = new ArrayList<String>();
		for (int i = 0; i < (s.length); i++) {
			ukeu.add(s[i]);
		}
		String r[] = { "United States", "Canada", "Australia", "New Zealand" };
		ArrayList<String> ocntry = new ArrayList<String>();
		for (int i = 0; i < (r.length); i++) {
			ocntry.add(r[i]);

		}

		if (ukeu.contains(CountryName)) {
			switch (CountryName) {
			case "United Kingdom":
				PhoneNo = ukData.ukPhoneNo;
				StreetAddress = ukData.ukStreetAddress;
				City = ukData.ukCity;
				Zip = ukData.ukZip;
				NameOnCC = ukData.ukNameOnCC;
				CCNo = ukData.ukCCNo;
				ExpiryDate = ukData.ukExpiryDate;
				CVV = ukData.ukCVV;
				break;
			case "Finland":
				PhoneNo = finlandData.finlandPhoneNo;
				StreetAddress = finlandData.finlandStreetAddress;
				City = finlandData.finlandCity;
				Zip = finlandData.finlandZip;
				NameOnCC = finlandData.finlandNameOnCC;
				CCNo = finlandData.finlandCCNo;
				ExpiryDate = finlandData.finlandExpiryDate;
				CVV = finlandData.finlandCVV;
				break;
			case "Sweden":
				PhoneNo = swedenData.swedenPhoneNo;
				StreetAddress = swedenData.swedenStreetAddress;
				City = swedenData.swedenCity;
				Zip = swedenData.swedenZip;
				NameOnCC = swedenData.swedenNameOnCC;
				CCNo = swedenData.swedenCCNo;
				ExpiryDate = swedenData.swedenExpiryDate;
				CVV = swedenData.swedenCVV;
				break;
			case "Norway":
				PhoneNo = norwayData.norwayPhoneNo;
				StreetAddress = norwayData.norwayStreetAddress;
				City = norwayData.norwayCity;
				Zip = norwayData.norwayZip;
				NameOnCC = norwayData.norwayNameOnCC;
				CCNo = norwayData.norwayCCNo;
				ExpiryDate = norwayData.norwayExpiryDate;
				CVV = norwayData.norwayCVV;
				break;
			case "Denmark":
				PhoneNo = denmarkData.denmarkPhoneNo;
				StreetAddress = denmarkData.denmarkStreetAddress;
				City = denmarkData.denmarkCity;
				Zip = denmarkData.denmarkZip;
				NameOnCC = denmarkData.denmarkNameOnCC;
				CCNo = denmarkData.denmarkCCNo;
				ExpiryDate = denmarkData.denmarkExpiryDate;
				CVV = denmarkData.denmarkCVV;
				break;
			default:
				Assert.assertTrue(false);

			}

			EnterBillingAddressAndCCDetailsUKEU();

		} else if (ocntry.contains(CountryName)) {
			switch (CountryName) {
			case "Canada":
				NameOnCC = canadaData.canadaNameOnCC;
				CCNo = canadaData.canadaCCNo;
				ExpiryMonth = canadaData.canadaExpiryMonth;
				ExpiryYear = canadaData.canadaExpiryYear;
				CVV = canadaData.canadaCVV;
				FirstName = canadaData.canadaFirstName;
				LastName = canadaData.canadaLastName;
				StreetAddress = canadaData.canadaStreetAddress;
				City = canadaData.canadaCity;
				State = canadaData.canadaState;
				Zip = canadaData.canadaZip;
				PhoneNo = canadaData.canadaPhoneNo;

				break;
			case "United States":
				NameOnCC = usData.usNameOnCC;
				CCNo = usData.usCCNo;
				ExpiryMonth = usData.usExpiryMonth;
				ExpiryYear = usData.usExpiryYear;
				CVV = usData.usCVV;
				FirstName = usData.usFirstName;
				LastName = usData.usLastName;
				StreetAddress = usData.usStreetAddress;
				City = usData.usCity;
				State = usData.usState;
				Zip = usData.usZip;
				PhoneNo = usData.usPhoneNo;
				break;
			case "Australia":
				NameOnCC = ausData.ausNameOnCC;
				CCNo = ausData.ausCCNo;
				ExpiryMonth = ausData.ausExpiryMonth;
				ExpiryYear = ausData.ausExpiryYear;
				CVV = ausData.ausCVV;
				FirstName = ausData.ausFirstName;
				LastName = ausData.ausLastName;
				StreetAddress = ausData.ausStreetAddress;
				City = ausData.ausCity;
				State = ausData.ausState;
				Zip = ausData.ausZip;
				PhoneNo = ausData.ausPhoneNo;
				break;
			case "New Zealand":
				NameOnCC = nzData.nzNameOnCC;
				CCNo = nzData.nzCCNo;
				ExpiryMonth = nzData.nzExpiryMonth;
				ExpiryYear = nzData.nzExpiryYear;
				CVV = nzData.nzCVV;
				FirstName = nzData.nzFirstName;
				LastName = nzData.nzLastName;
				StreetAddress = nzData.nzStreetAddress;
				City = nzData.nzCity;
				State = nzData.nzState;
				Zip = nzData.nzZip;
				PhoneNo = nzData.nzPhoneNo;
				break;
			default:
				Assert.assertTrue(false);

				
			}
			EnterBillingAddressAndCCDetails();
		}
	}

	/**
	 * purpose-To enter the required Billing info details on the page
	 * 
	 * @param address  address
	 * @param fName    firstName
	 * @param lName    lastName
	 * @param address1 address
	 * @param city     city
	 * @param country  country
	 * @param state    state
	 * @throws InterruptedException
	 */
	@Step("To enter the requied Billing info details on the page for UK EU Region")
	public void EnterBillingAddressAndCCDetailsUKEU() throws InterruptedException {
		Thread.sleep(5000);
		safeJavaScriptClick(CLICK_HERE, "' Click Here' in 'Payment info' page is clicked'", IMPLICITWAIT);
		Thread.sleep(5000);
		safeJavaScriptClearAndType(PHONENO, PhoneNo, "'phoneno'is given in the page", MEDIUMWAIT);

		safeJavaScriptClearAndType(STREET_ADDRESS, StreetAddress, "'StreetAddress'is given in the page", MEDIUMWAIT);

		safeJavaScriptClearAndType(CITY, City, "'City'is given in the page", MEDIUMWAIT);

		safeJavaScriptClearAndType(ZIPCODE, Zip, "'ZIP code'is given in the page", MEDIUMWAIT);

		safeJavaScriptClick(SUBMIT_BUTTON, "' save address button in 'Payment Info' page is clicked'", IMPLICITWAIT);
		Thread.sleep(5000);
	    safeJavaScriptClick(PAYMENT_INFO, "' save address button in 'Payment Info' page is clicked'", IMPLICITWAIT);
		Thread.sleep(5000);
		safeClearAndType(NAME_ON_CARD_UKEU, NameOnCC, "'nameoncard'is given in the page", MEDIUMWAIT);
		WebElement iframe1 = driver.findElement(By.xpath("//iframe[@title='Iframe for secured card number']"));
		driver.switchTo().frame(iframe1);
		safeClearAndType(CC_NO_UKEU, CCNo, "'creditcardno'is given in the page", MEDIUMWAIT);
		driver.switchTo().defaultContent();
		WebElement iframe2 = driver.findElement(By.xpath("//iframe[@title='Iframe for secured card expiry date']"));
		
		driver.switchTo().frame(iframe2);
		safeClearAndType(EXP_DATE_UKEU, ExpiryDate, "'click on the Expire Year'", IMPLICITWAIT);
		driver.switchTo().defaultContent();
		WebElement iframe3 = driver.findElement(By.xpath("//iframe[@title='Iframe for secured card security code']"));
		driver.switchTo().frame(iframe3);
		safeClearAndType(CVV_UKEU, CVV, "'cvvno'is given in the page", MEDIUMWAIT);
		driver.switchTo().defaultContent();
		safeJavaScriptClick(CONFIRM_BUTTON, "' confirm in 'payment info' page is clicked'", IMPLICITWAIT);
		Thread.sleep(5000);
		safeJavaScriptClick(MY_ACCOUNT, "' My Account' in  'payment info' page is clicked'", IMPLICITWAIT);
		Thread.sleep(5000);
		driver.navigate().refresh();
		try {
			safeClick(COUNTRY_ICON, "'Country Icon' in My Account Page is clicked", IMPLICITWAIT);
			Assert.assertFalse(true);
		}

		catch (Exception e) {
			Assert.assertTrue(true);

		}

	}

	/**
	 * purpose-To enter the required Billing info details on the page
	 * 
	 * @param address  address
	 * @param fName    firstName
	 * @param lName    lastName
	 * @param address1 address
	 * @param city     city
	 * @param country  country
	 * @param state    state
	 * @throws InterruptedException
	 */
	@Step("To enter the required Billing And CreditCard info details  on the Payment Info page")
	public void EnterBillingAddressAndCCDetails() throws InterruptedException {
		Thread.sleep(5000);
		safeJavaScriptClearAndType(NAME_ON_CARD, NameOnCC, "'nameoncard'is given in the page", MEDIUMWAIT);
		safeJavaScriptClearAndType(CC_NO, CCNo, "'creditcardno'is given in the page", MEDIUMWAIT);
		safeSelectOptionInDropDownByVisibleText(EXP_MONTH_DROPDOWN, ExpiryMonth, "'click on the Expire Month'",
				IMPLICITWAIT);
		safeSelectOptionInDropDownByVisibleText(EXP_YEAR_DROPDOWN, ExpiryYear, "'click on the Expire Year'",
				IMPLICITWAIT);
		safeJavaScriptClearAndType(CVV_NO, CVV, "'cvvno'is given in the page", MEDIUMWAIT);

		safeJavaScriptClearAndType(FIRST_NAME, FirstName, "'firstname'is given in the page", MEDIUMWAIT);
		safeJavaScriptClearAndType(LAST_NAME, LastName, "'Lastname'is given in  the page", MEDIUMWAIT);
		safeJavaScriptClearAndType(STREET_ADDRESS, StreetAddress, "'firstname'is given in the page", MEDIUMWAIT);
		safeJavaScriptClearAndType(CITY, City, "'firstname'is given in the page", MEDIUMWAIT);
		safeSelectOptionInDropDownByVisibleText(STATE_DROPDOWN, State, "'click on the state'", IMPLICITWAIT);
		safeJavaScriptClearAndType(ZIPCODE, Zip, "'postal code'is given in the page", MEDIUMWAIT);
		// safeSelectOptionInDropDownByVisibleText(SELECT_COUNTRY,"Canada","'click on
		// the country'",IMPLICITWAIT);
		safeJavaScriptClearAndType(PHONENO, PhoneNo, "'phoneno'is given in the page", MEDIUMWAIT);
		safeJavaScriptClick(UPDATE_BUTTON, "' Update button in 'Payment Info' page is clicked'", IMPLICITWAIT);
		Thread.sleep(MEDIUMWAIT);
		try {
			safeClick(COUNTRY_ICON, "'Country Icon' in My Account Page is clicked", IMPLICITWAIT);
			Assert.assertFalse(true);
		}

		catch (Exception e) {
			Assert.assertTrue(true);

		}

	}
}