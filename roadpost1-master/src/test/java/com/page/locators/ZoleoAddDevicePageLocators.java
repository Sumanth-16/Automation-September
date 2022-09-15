package com.page.locators;

import org.openqa.selenium.By;

public interface ZoleoAddDevicePageLocators 
{
	 
	 By DONE_TICKMARK = By.xpath("//div[@class='done numberIcon icon-checkmark']");
	 By	IMEI = By.xpath("//input[@id='options_445_text']");
     By SERIAL_NUMBER = By.xpath("//input[@placeholder='Insert your 6-digit Serial Number here']");
	 By DEVICE_NICKNAME=By.xpath("//input[@placeholder='e.g. My ZOLEO device']");
	 By ZOLEO_EMAILADDRESS=By.xpath("//input[@placeholder='e.g. johnsmith']");
     By NEXT_BUTTON =By.xpath("//button[text()='Next >']");
     By ADD_DEVICE_TICKMARK = By.xpath("//div[@class='step2 numberIcon icon-checkmark']");
     By BASIC_PLAN=By.xpath("(//div[@class='airtime-row airtime-product-group-1 airtime-product-label'])[1]");
     By UNLIMITED_PLAN=By.xpath("(//div[@class='airtime-row airtime-product-group-1 airtime-product-label'])[3]");
     By IN_TOUCH_PLAN=By.xpath("(//div[@class='airtime-row airtime-product-group-1 airtime-product-label'])[2]");
     By PLAN_CHECKBOX =By.xpath("//input[@name='accept'] ");
     By LOCATION_SHARE=By.xpath("(//div[@class='field choice']//input)[2]");
     By PLAN_ACCEPT =By.xpath("//button[text()='Accept']");
     By PLAN_TICKMARK =By.xpath("//div[@class='step3 numberIcon icon-checkmark']");
     By SOS1_FULLNAME = By.xpath("//input[@id='contact1_full_name']");
     By SOS1_EMAIL = By.xpath("//input[@id='contact1_email']");
     By SOS1_MOBILENO = By.xpath("//input[@id='contact1_phone']");
     By SOS2_FULLNAME = By.xpath("//input[@id='contact2_full_name']");
     By SOS2_EMAIL = By.xpath("//input[@id='contact2_email']");
     By SOS2_MOBILENO = By.xpath("//input[@id='contact2_phone']");
     By CHECKIN_SOS1=By.xpath("(//ul[@id='ui-id-3']//li)[2]");
     By CHECKIN_CONTACT_FULLNAME = By.xpath("//input[@id='checkin_full_name']");
     By CHECKIN_CONTACT_EMAIL = By.xpath("//input[@id='checkin_email']");
     By CHECKIN_CONTACT_MOBILENO = By.xpath("//input[@id='checkin_phone']");
     By NOTIFY_BY=By.xpath("//div[@class='checkin_delivery_opt pos-absolute']");
     By OPTIONS_NOTIFY= By.xpath("//select[@id='delivery']//option");
     By OPTIONS_TO_SELECT = By.xpath("//li[text()='SMS']");
     By MY_ACCOUNT_OPTION=By.xpath("//a[text()='MY ACCOUNT']");
     
}