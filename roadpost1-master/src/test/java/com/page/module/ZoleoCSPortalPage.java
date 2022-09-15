package com.page.module;

import org.openqa.selenium.WebDriver;
import com.page.locators.ZoleoCSPortalPageLocators;
import com.selenium.Dynamic;
import com.selenium.SafeActions;
import com.testng.Assert;

import ru.yandex.qatools.allure.annotations.Step;

public class ZoleoCSPortalPage extends SafeActions implements ZoleoCSPortalPageLocators{
	private WebDriver driver;
	
	//Constructor to define/call methods	 
	public ZoleoCSPortalPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	/**
	 * purpose-To Verifies whether navigated to the CS portal page or not
	 * throws Exception 
	 */
	@Step("Verifies the CS Portal page")
	public void verifyCSPortalPage()
	{
		boolean bCSLogo = isElementPresent(CS_LOGO,VERYSHORTWAIT);
		Assert.assertTrue(bCSLogo, "'CS Logo' is not being displayed on the 'Zoleo' page");	
	}
	/**
	 * Purpose- To verify whether  login page is being displayed or not
	 *
	 */
	@Step("Verifying login page")
	public void verifyLoginPage() 
	{
		boolean bCSEmailExists = isElementPresent(CS_EMAIL, VERYSHORTWAIT);
		boolean bCSPasswordExists = isElementPresent(CS_PASSWORD, VERYSHORTWAIT);
		Assert.assertTrue(bCSEmailExists||bCSPasswordExists,"Email field/Password field textbox is not being displayed on 'CS Login' page");
	}
	
	/**
	 * Purpose- To enter login credentials i.e.,emailAddress and password 
	 * @param sEmailAddresss- we pass username of the user
	 * @param sPassword- we pass passowrd of the user
	 */
	@Step("Entering login credentials")
	public void EnterCSLoginCredentials(String sCSEmailAddress, String sCSPassword) 
	{
		safeType(CS_EMAIL, sCSEmailAddress,"'Email' field in 'Login' page", VERYSHORTWAIT);
		safeType(CS_PASSWORD, sCSPassword,"'Password' field in 'Login' page", VERYSHORTWAIT);
	}
	
	/**
	 * Purpose- To click on Sign In button
	 */
	@Step("Clicking on LogIn button")
	public void clickCSLogInButton() 
	{
		safeClick(CS_LOGIN_BUTTON, "'Login' button in 'Login' page ",VERYSHORTWAIT);
		waitForPageToLoad(MEDIUMWAIT);
		
	}

	/**
	 * Purpose- To click on required fields
	 */
	@Step("Clicking on Dashboard button")
	public void clickDashBoardButton(String imei) 
	{
		safeClick( CS_DASHBOARD, "'dashboard' button in 'Login' page ",VERYSHORTWAIT);
		waitForPageToLoad(MEDIUMWAIT);
		safeClick(CS_SUBSCRIPTION, "'dashboard' button in 'Login' page ",VERYSHORTWAIT);
		waitForPageToLoad(MEDIUMWAIT);
		getListWebElementAssertion(CS_DEVICESUBSCRIPTION,imei);
		
	}
	
	
	
}
