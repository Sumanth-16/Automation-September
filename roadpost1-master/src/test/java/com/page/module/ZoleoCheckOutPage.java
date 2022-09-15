package com.page.module;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
import com.selenium.Dynamic;
import com.selenium.SafeActions;
//import org.testng.Assert;
import com.testng.Assert;

public class ZoleoCheckOutPage extends SafeActions implements ZoleoCheckOutPageLocators,ZoleoMyAccountPageLocators {
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
	 ZoleoCheckOutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	/**
	 * Purpose- To verify whether contacts page headers is being displayed or not
	 *
	 */
	@Step("Verifying Checkout page")
	public void verifyCheckoutPage() 
	{
		boolean bSecureCheckoutLogo = isElementPresent(SECURE_CHECKOUT_LOGO, MEDIUMWAIT);
		
		Assert.assertTrue(bSecureCheckoutLogo, " SecureCheckoutLogo are not being displayed on checkout page");
	}
	
	
	/**
	 * Purpose- To Get and Enter the Billing Address and Credit Card Details
	 * 
	 * @throws Exception
	 */
	@Step("To Get and Enter the Billing Address and Credit Card Details")
	public ZoleoActivationPage GetAndEnterTheBillingAddressAndCCDetails() throws Exception {
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
		return new ZoleoActivationPage(driver);
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
		safeJavaScriptClick(CREDIT_CARD_CHECKBOX, "' Credit button in 'Payment Info' page is clicked'", IMPLICITWAIT);
		Thread.sleep(5000);
    boolean bPaymentMethodLogo = isElementDisplayed(PAYMENT_LOGO);
		
		Assert.assertTrue(bPaymentMethodLogo, " SecureCheckoutLogo are not being displayed on checkout page");
		
		safeType(STREET_ADDRESS, StreetAddress, "'StreetAddress'is given in the page", MEDIUMWAIT);

		safeType(CITY_NAME, City, "'City'is given in the page", MEDIUMWAIT);

		safeType(POSTAL_CODE, Zip, "'ZIP code'is given in the page", MEDIUMWAIT);
		safeClearAndType(PHONE_NO, PhoneNo, "'phoneno'is given in the page", MEDIUMWAIT);
        
		safeJavaScriptClick(SAVE_ADDRESS_BUTTON_2, "' save address button in 'Payment Info' page is clicked'", IMPLICITWAIT);
		Thread.sleep(5000);
		safeJavaScriptClick(SAVE_ADDRESS_BUTTON_2, "' save address button in 'Payment Info' page is clicked'", IMPLICITWAIT);
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
		Thread.sleep(5000);
		safeSClick(PLACE_ORDER,"' Place Order In checkout' page is clicked'",IMPLICITWAIT);
		Thread.sleep(5000);
	

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
		
		//safeJavaScriptClearAndType(FIRST_NAME, FirstName, "'firstname'is given in the page", MEDIUMWAIT);
		//safeJavaScriptClearAndType(LAST_NAME, LastName, "'Lastname'is given in  the page", MEDIUMWAIT);
		safeJavaScriptClearAndType(STREET_ADDRESS, StreetAddress, "'firstname'is given in the page", MEDIUMWAIT);
		safeJavaScriptClearAndType(CITY_NAME, City, "'firstname'is given in the page", MEDIUMWAIT);
		safeSelectOptionInDropDownByVisibleText(SELECT_STATE, State, "'click on the state'", IMPLICITWAIT);
		safeJavaScriptClearAndType(POSTAL_CODE, Zip, "'postal code'is given in the page", MEDIUMWAIT);
		// safeSelectOptionInDropDownByVisibleText(SELECT_COUNTRY,"Canada","'click on
		// the country'",IMPLICITWAIT);
		safeJavaScriptClearAndType(PHONE_NO, PhoneNo, "'phoneno'is given in the page", MEDIUMWAIT);
		safeJavaScriptClick(SAVE_ADDRESS_BUTTON, "' Update button in 'Payment Info' page is clicked'", IMPLICITWAIT);
		Thread.sleep(MEDIUMWAIT);
		safeJavaScriptClearAndType(NAME_ON_CARD, NameOnCC, "'nameoncard'is given in the page", MEDIUMWAIT);
		safeJavaScriptClearAndType(CREDIT_CARDNO, CCNo, "'creditcardno'is given in the page", MEDIUMWAIT);
		safeSelectOptionInDropDownByVisibleText(EXPIRY_MONTH, ExpiryMonth, "'click on the Expire Month'",
				IMPLICITWAIT);
		safeSelectOptionInDropDownByVisibleText(EXPIRY_YEAR, ExpiryYear, "'click on the Expire Year'",
				IMPLICITWAIT);
		safeJavaScriptClearAndType(CVV_NO, CVV, "'cvvno'is given in the page", MEDIUMWAIT);

		Thread.sleep(5000);
		safeJavaScriptClick(PLACE_ORDER, "' confirm in 'payment info' page is clicked'", IMPLICITWAIT);
		Thread.sleep(5000);

		}

	

	/**
	 * purpose-To enter the required Billing info details on the page
	 * @param address address
	 * @param fName  firstName
	 * @param lName  lastName
	 * @param address1 address
	 * @param city city
	 * @param country country
	 * @param state state
	 * @throws InterruptedException 
	 */
	@Step("To enter the requied Billing info details on the page")
	public void enterBillingDetailsInfo(String firstname,String lastname,String streetaddress,String city,String postalcode,String checkoutphoneno,String nameoncard,String creditcardno,String cvvno) throws InterruptedException
	{
		safeJavaScriptClick(CREDIT_CARD_CHECKBOX, "' Credit card checkbox button in 'Checkout' page is clicked'", IMPLICITWAIT);
		//safeJavaScriptClick(EDIT_ADDRESS_BUTTON, "' Edit address button in 'Checkout' page is clicked'", IMPLICITWAIT);
		//safeSelectOptionInDropDownByVisibleText(BILLING_ADDRESS,"New Address","'click on the New Address option'",IMPLICITWAIT);
		//safeJavaScriptClearAndType(ADDRESS_FIRSTNAME,firstname, "'firstname'is given in the page", MEDIUMWAIT);
		//safeJavaScriptClearAndType(ADDRESS_LASTNAME,lastname, "'firstname'is given in the page", MEDIUMWAIT);
		//safeJavaScriptClearAndType(STREET_ADDRESS,streetaddress, "'firstname'is given in the page", MEDIUMWAIT);
		//safeJavaScriptClearAndType(CITY_NAME,city, "'firstname'is given in the page", MEDIUMWAIT);
		//safeSelectOptionInDropDownByVisibleText(SELECT_STATE,"British Columbia","'click on the state'",IMPLICITWAIT);
		//safeJavaScriptClearAndType(POSTAL_CODE,postalcode, "'postal code'is given in the page", MEDIUMWAIT);
		//safeSelectOptionInDropDownByVisibleText(SELECT_COUNTRY,"Canada","'click on the country'",IMPLICITWAIT);
		//safeJavaScriptClearAndType(PHONE_NO,checkoutphoneno, "'checkoutphoneno'is given in the page", MEDIUMWAIT);
		//safeJavaScriptClick(SAVE_ADDRESS_BUTTON, "' save address button in 'Checkout' page is clicked'", IMPLICITWAIT);
		//safeSelectOptionInDropDownByVisibleText(PAYMENT_MODE,"New","'click on the Payment Mode'",IMPLICITWAIT);
		//safeJavaScriptClearAndType(NAME_ON_CARD,nameoncard, "'nameoncard'is given in the page", MEDIUMWAIT);
		//safeJavaScriptClearAndType(CREDIT_CARDNO,creditcardno, "'creditcardno'is given in the page", MEDIUMWAIT);
		//safeSelectOptionInDropDownByVisibleText(EXPIRE_MONTH,"03 - March","'click on the Expire Month'",IMPLICITWAIT);
		//safeSelectOptionInDropDownByVisibleText(EXPIRE_YEAR,"2030","'click on the Expire Year'",IMPLICITWAIT);
		//safeJavaScriptClearAndType(CVV_NO,cvvno, "'cvvno'is given in the page", MEDIUMWAIT);
		//safeJavaScriptClick(CAPTCHA_CHECKBOX, "'  captcha checkbox in 'Checkout' page is clicked'", IMPLICITWAIT);
		Thread.sleep(VERYLONGWAIT);
		safeJavaScriptClick(PLACE_ORDER, "' place order button in 'Checkout' page is clicked'", IMPLICITWAIT);
		
		}
	/**
	 * Purpose- To verify whether device activation  page is displayed  or not
	 *
	 */
	@Step("Verifying device activation page")
	public void DeviceActivationPage() 
	{
		boolean bmyaccountbutton = isElementPresent(MY_ACCOUNT_BUTTON, LONGWAIT);
		Assert.assertTrue(bmyaccountbutton, " my account button is  not being displayed on activation page");
		safeJavaScriptClick(MY_ACCOUNT_BUTTON,"clicked on my account button", MEDIUMWAIT);
	}
	/**
	 * purpose-To Click on the required item from the page
	 * @param itemName item name
	 * @return 
	 * 
	 */
	
	  @Step("Click on the DeviceAndPlans button from the page ")
	  public void DeviceAndPlansButton(String imei) 
	  { 
		  safeJavaScriptClick(DEVICES_AND_PLANS,"'DEVICES_AND_PLANS' Button in 'ZoleoMyAccount' page", MEDIUMWAIT);
		  getListWebElementAssertion(MY_ACCOUNT_IMEIS,imei);
	 
	  }
}