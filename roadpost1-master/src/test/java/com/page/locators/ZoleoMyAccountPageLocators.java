package com.page.locators;

import org.openqa.selenium.By;

public interface ZoleoMyAccountPageLocators {

	By ACTIVATE_BUTTON = By.xpath("//a[@class='button-activate']");
	By DEVICES_AND_PLANS = By.xpath("//a[@class='icon-zoleo-device']//span");
	By ADD_NEW_DEVICE = By.xpath("(//a[@title='Add New Device'])[2]");
	By MY_ACCOUNT_IMEIS = By.xpath("//span[text()='IMEI']/../../../div[@class='form-rest']/p");
	By MY_ACCOUNT_PLANS=By.xpath("//span[text()='Subscription']/../../..//div[@class='form-rest icon-chevron-down posRel']");
	By CONTACT_INFORMATION = By.xpath("(//div[@class='box-content'])[1]");
	By COUNTRY_ICON = By.xpath("//ul[@id='country-icon']//li");
	By BILLING_COUNTRY = By.xpath("(//select[@id='country'])[2]");
	By CHANGE_COUNTRY_DROPDOWN=By.xpath("(//select[@id='country'])[1]");
	By CHANGE_COUNTRY_CHECKBOX=By.xpath("(//input[@id='subscription'])[1]");
	By CHANGE_COUNTRY_SAVEBUTTON=By.xpath("(//button[@type='submit']/span)[1]");
	By BILLING_COUNTRY_CHECKBOX = By.xpath("(//input[@id='subscription'])[2]");
	By BILLING_SAVEBUTTON = By.xpath("(//button[@type='submit']/span)[2]");
	By CHANGED_COUNTRY_CANADA = By.xpath("(//ul[@id='country-icon']//img)[1]");
	By CHANGED_COUNTRY_US = By.xpath("(//ul[@id='country-icon']//img)[3]");
	By CHANGED_COUNTRY_AUSTRALIA = By.xpath("(//ul[@id='country-icon']//img)[2]");
	By CHANGED_COUNTRY_RESTOF = By.xpath("(//ul[@id='country-icon']//img)[5]");
	By CHANGED_COUNTRY_UK = By.xpath("(//ul[@id='country-icon']//img)[4]");
    By PAYMENT_INFO=By.xpath("//span[text()='Payment Info']");
    By CREDIT_CARD_DETAILS=By.xpath("//span[text()='Credit Card Detail']");
    By BILLING_ADDRESS=By.xpath("//span[text()='Billing Address']");
    By BILLING_ADDRESS_FIELD=By.xpath("//div[@class='box-content']//address");
    By COMM_PREF_TEXT=By.xpath("//span[text()='Communication Preferences']");
    By COMM_PREF_FIELD=By.xpath("(//div[@class='box-content']//p)[3]");
    By DEACTIVATE_BUTTON=By.xpath("//button[@class='button button-clear deactivate_device']");
    By DEACTIVATE_BUTTON_CONFIRM=By.xpath("//button[@class='button button-green-3 deactivate_device_btn']");
    By DONE_BUTTON=By.xpath("(//a[text()='Done'])[3]");
    By NO_DEVICES=By.xpath("//div[text()='No devices found']");
    By ACCOUNT_ACTIVITY=By.xpath("//span[text()='Account Activity']");
    By ACTIVITY_ROW=By.xpath("(//div[@id='my-orders-table-history_wrapper']//tbody//tr)[1]");
    By VIEW_DETAIL=By.xpath("(//div[@id='my-orders-table-history_wrapper']//tbody//tr//a)[1]");
    By ACTIVATION_DETAILS=By.xpath("//strong[text()='ZOLEO Activation']");
    By SEE_DETAILS=By.xpath("//a[@class='order-details-item-expand toggle-collpase']");
    By SEE_IMEI_DETAILS=By.xpath("(//table[@summary='Activity Details']//tbody//tr)[6]");
}


