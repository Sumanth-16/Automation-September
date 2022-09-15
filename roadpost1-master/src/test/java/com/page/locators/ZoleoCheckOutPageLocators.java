package com.page.locators;

import org.openqa.selenium.By;

public interface ZoleoCheckOutPageLocators {
	By SECURE_CHECKOUT_LOGO = By.xpath("//span[text()='Secure Checkout']");
	By CREDIT_CARD_CHECKBOX = By.xpath("//input[@id='adyen_cc']");
	By PAYMENT_LOGO=By.xpath("//div[text()='Payment Method']");
	By EDIT_ADDRESS_BUTTON = By.xpath("//button[@class='action action-edit-address']/span");
	By BILLING_ADDRESS = By.xpath("//select[@name='billing_address_id']");
	By ADDRESS_FIRSTNAME = By.xpath("(//input[@name='firstname'])[2]");
	By ADDRESS_LASTNAME = By.xpath("(//input[@name='lastname'])[2]");
	By STREET_ADDRESS = By.xpath("(//input[@name='street[0]'])[2]");
	By CITY_NAME = By.xpath("(//input[@name='city'])[2]");
	By SELECT_STATE = By.xpath("(//select[@name='region_id'])[2]");
	By POSTAL_CODE = By.xpath("(//input[@name='postcode'])[2]");
	By SELECT_COUNTRY = By.xpath("(//select[@name='country_id'])[2]");
	By PHONE_NO = By.xpath("(//input[@name='telephone'])[2]");
	By SAVE_ADDRESS_BUTTON = By.xpath("//button[@data-bind='click: updateAddress']/span");
	By SAVE_ADDRESS_BUTTON_2 = By.xpath("//button[@data-bind='click: updateAddress']");
	By PAYMENT_MODE = By.xpath("//select[@id='user_payment_method']");
	By NAME_ON_CARD_UKEU = By.xpath("//div[@class='adyen-checkout__input-wrapper']//input");
	By NAME_ON_CARD = By.xpath("//input[@title='Name on Card']");
	By CC_NO_UKEU = By.xpath("//html[@lang='en-US']//body//div//input[@aria-label='Card number field']");
	By CREDIT_CARDNO = By.xpath("//input[@name='payment[cc_number]']");
	By EXP_DATE_UKEU = By.xpath("//html[@lang='en-US']//body//div//input[@aria-label='Expiry date field']");
	By EXPIRY_MONTH = By.xpath("//select[@class='select select-month']");
	By EXPIRY_YEAR = By.xpath("//select[@class='select select-year']");
	By CVV_UKEU = By.xpath("//html[@lang='en-US']//body//div//input[@aria-label='Security code field']");
	By CVV_NO = By.xpath("//input[@class='input-text cvv']");
	By CAPTCHA_CHECKBOX = By.xpath("//div[@class='recaptcha-checkbox-border']");
	By PLACE_ORDER = By.xpath("(//button[@class='action primary checkout'])[1]");
	By MY_ACCOUNT_BUTTON = By.xpath("//a[@class='button button-green-3 button-responsive']/span");

}