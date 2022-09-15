package com.page.module;

import com.datamanager.ConfigManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import ru.yandex.qatools.allure.annotations.Step;
import com.page.data.ZoleoData;

import com.page.locators.ZoleoLogInPageLocators;
import com.selenium.SafeActions;
import com.testng.Assert;


public class ZoleoLoginPage extends SafeActions implements ZoleoLogInPageLocators
{
	private WebDriver driver;
	ConfigManager app = new ConfigManager("App");
	//Constructor to define/call methods	 
	 ZoleoLoginPage(WebDriver driver)
	{ 
		super(driver);
		this.driver = driver;
    } 
	
	/**
	 * Purpose- To verify whether  login page is being displayed or not
	 *
	 */
	@Step("Verifying login page")
	public void verifyLoginPage() 
	{
		waitForPageToLoad(IMPLICITWAIT);
		boolean bIsEmailAddressExists = isElementPresent(EMAILADDRESS_FIELD, VERYSHORTWAIT);
		boolean bIsPasswordFieldExists = isElementPresent(PASSWORD_FIELD, VERYSHORTWAIT);
		Assert.assertTrue(bIsEmailAddressExists||bIsPasswordFieldExists,"Email field/Password field textbox is not being displayed on 'Zoleo Login' page");
	}
	
	/**
	 * Purpose- To enter login credentials i.e.,emailAddress and password 
	 * @param sEmailAddresss- we pass username of the user
	 * @param sPassword- we pass passowrd of the user
	 */
	@Step("Entering login credentials")
	public void enterLoginCredentials(String sEmailAddresss, String sPassword) 
	{
		safeType(EMAILADDRESS_FIELD, sEmailAddresss,"'Email' field in 'Login' page", VERYSHORTWAIT);
        safeType(PASSWORD_FIELD, sPassword,"'Password' field in 'Login' page", VERYSHORTWAIT);
	}
	
	/**
	 * Purpose- To click on Sign In button
	 */
	@Step("Clicking on LogIn button")
	public ZoleoMyAccountPage clickLogInButton() 
	{
		safeClick(LOGIN_BTN, "'Login' button in 'Login' page ",VERYSHORTWAIT);
		waitForPageToLoad(MEDIUMWAIT);
		return new ZoleoMyAccountPage(driver);
	}
	/**
	 * Purpose- To click on Sign In button
	 */
	@Step("Clicking on LogIn button")
	public ZoleoAddDevicePage clickNoDeviceAddedLogInButton() 
	{
		safeClick(LOGIN_BTN, "'Login' button in 'Login' page ",VERYSHORTWAIT);
		waitForPageToLoad(MEDIUMWAIT);
		return new ZoleoAddDevicePage(driver);
	}
	/**
	 * purpose-To Click on the required item from the page
	 * @param itemName item name
	 * 
	 */
	@Step("Click on the SignUp Option from the page ")
	public ZoleoSignUpPage clickOnSignUpOption()
	{
		safeJavaScriptClick(SIGNUP_BTN,"'SignUp' Button in 'ZoleoLogin' page",MEDIUMWAIT);
		return new ZoleoSignUpPage(driver);
	}
	/**
	 * purpose-To Click on the required item from the page
	 * 
	 * 
	 */
	@Step("Click on the Login/SignUp button from the page ")
	public ZoleoForgotPasswordPage clickOnForgotPasswordButton()
	{
		safeJavaScriptClick(FORGOT_PASSWORD_BTN,"'Forgot Password' Button in 'ZoleoSignin' page", VERYSHORTWAIT);
		return new ZoleoForgotPasswordPage(driver);
	}
	
}
