package com.page.module;

import org.openqa.selenium.WebDriver;

import ru.yandex.qatools.allure.annotations.Step;

import com.page.data.ZoleoData;
import com.page.locators.ZoleoCheckOutPageLocators;
import com.page.locators.ZoleoCountryPageLocators;
import com.page.locators.ZoleoMyAccountPageLocators;
import com.selenium.Dynamic;
import com.selenium.SafeActions;
//import org.testng.Assert;
import com.testng.Assert;

public class ZoleoCountryPage extends SafeActions implements ZoleoCheckOutPageLocators,ZoleoMyAccountPageLocators,ZoleoCountryPageLocators  {
	private WebDriver driver;

ZoleoData zoleoTestData = new ZoleoData();
	// Constructor to define/call methods
	 ZoleoCountryPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}


	/**
	 * purpose-To choose the required Billing Country on the country page
	 * @throws InterruptedException 
	 */
	@Step("To enter the requied Billing info details on the page")
	public ZoleoMyAccountPage SaveBillingCountry() throws InterruptedException
	{
		
		safeSelectOptionInDropDownByVisibleText(BILLING_COUNTRY,zoleoTestData.countryRegion,"'click on the required country'",IMPLICITWAIT);
		safeJavaScriptClick(BILLING_COUNTRY_CHECKBOX, "' Billing country checkbox' button in 'Country' page is clicked'", IMPLICITWAIT);
		safeJavaScriptClick(BILLING_SAVEBUTTON, "' save button in 'Country' page is clicked'", VERYLONGWAIT);
		Thread.sleep(20000);
		return new ZoleoMyAccountPage(driver);
		}
	
	
	  
}