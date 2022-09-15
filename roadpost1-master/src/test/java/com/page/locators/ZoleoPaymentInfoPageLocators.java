package com.page.locators;

import org.openqa.selenium.By;

import com.page.data.ZoleoData;

public interface ZoleoPaymentInfoPageLocators 
{
	
	By EDIT_PAYMENT=By.xpath("//span[text()='Edit Payment Method']");
	By CLICK_HERE=By.xpath("//a[text()='click here']");
	By STREET_ADDRESS=By.xpath("//input[@id='street_1']");
	By PHONENO=By.xpath("//input[@id='telephone']");
	By CITY=By.xpath("//input[@id='city']");
	By ZIPCODE=By.xpath("//input[@id='zip']");
	By SUBMIT_BUTTON=By.xpath("//span[text()='Save Address']");
	By PAYMENT_INFO=By.xpath("//span[text()='Payment Info']");
	By NAME_ON_CARD_UKEU=By.xpath("//div[@class='adyen-checkout__input-wrapper']//input");
	By NAME_ON_CARD=By.xpath("//input[@id='cc_owner']");
	By CC_NO_UKEU=By.xpath("//html[@lang='en-US']//body//div//input[@aria-label='Card number field']");
	By CC_NO=By.xpath("//input[@id='creditcardnumber']");
	By EXP_DATE_UKEU=By.xpath("//html[@lang='en-US']//body//div//input[@aria-label='Expiry date field']");
	By EXP_MONTH_DROPDOWN=By.xpath("//select[@id='expiration_m']");
	By EXP_YEAR_DROPDOWN=By.xpath("//select[@id='expiration_yr']");
	By CVV_UKEU=By.xpath("//html[@lang='en-US']//body//div//input[@aria-label='Security code field']");
	By CVV_NO=By.xpath("//input[@id='cc_cid']");
	By CONFIRM_BUTTON=By.xpath("//span[text()='Confirm preauthorization']");
	By MY_ACCOUNT=By.xpath("//span[text()='My Account']");
	By COUNTRY_ICON = By.xpath("//ul[@id='country-icon']//li");
	By FIRST_NAME=By.xpath("//input[@id='fname']");
	By LAST_NAME=By.xpath("//input[@id='lname']");
	By STATE_DROPDOWN=By.xpath("//select[@id='region_id']");
	By UPDATE_BUTTON=By.xpath("//button[@id='submitButton']");
	
	

	 
}