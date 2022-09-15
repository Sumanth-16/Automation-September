package com.page.module;

import com.datamanager.ConfigManager;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import ru.yandex.qatools.allure.annotations.Step;
import com.page.data.ZoleoData;
import com.page.locators.GMAILLocators;
import com.page.locators.ZoleoForgotPasswordPageLocators;
import com.page.locators.ZoleoLogInPageLocators;
import com.selenium.SafeActions;
import com.testng.Assert;


public class ZoleoForgotPasswordPage extends SafeActions implements ZoleoForgotPasswordPageLocators,GMAILLocators
{
	private ZoleoData zoleoTestData;
	public static String sValue;
	private WebDriver driver;
	ConfigManager app = new ConfigManager("App");
	String isPasswordEncrypted = app.getProperty("App.IsPasswordEncrypted");
	//Constructor to define/call methods	 
	 ZoleoForgotPasswordPage(WebDriver driver)
	{ 
		super(driver);
		this.driver = driver;
    } 
	
	/**
	 * Purpose- To verify whether  Forgot password page is being displayed or not
	 *
	 */
	@Step("Verifying login page")
	public void verifyZoleoForgotPasswordPage() 
	{
		boolean bIsEmailAddressExists = isElementPresent(FORGOT_EMAILADDRESS, VERYSHORTWAIT);
		
		Assert.assertTrue(bIsEmailAddressExists,"Email field/Password field textbox is not being displayed on 'Zoleo Login' page");
	}
	
	/**
	 * Purpose- To enter required credentials i.e.,emailAddress 
	 * @param sEmailAddresss- we pass username of the user
	 * 
	 */
	@Step("Entering ForgotPasswordEmail")
	public void enterForgotPasswordEmail(String sForgot_EmailAddress) 
	{
		safeType(FORGOT_EMAILADDRESS, sForgot_EmailAddress,"'Email' field in 'Forgot Password' page", VERYSHORTWAIT);
		safeClick(RESET_MY_PASSWORD_BTN, "'Reset my passowrd button' in 'Forgot Password' page ",VERYSHORTWAIT);
	}
	
	/**
	 * Purpose- To get the OTP for Forgot Password
	 */
	@Step("To get the OTP for Forgot Password")
	public   void getOTPfrommailbox(String sgmailusername,String sgmailpassword) throws InterruptedException
	{
		zoleoTestData = new ZoleoData();
		JavascriptExecutor js = (JavascriptExecutor)driver ;
		String openString = "window.open('"+zoleoTestData.emailurl+"', '_blank');";
		js.executeScript(openString);
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		safeClick(SIGN_IN_BUTTON,"'next button' in gmail page",IMPLICITWAIT);
		setImplicitWait(5000);
		safeClearAndType(GMAIL_USERNAME,sgmailusername,"'Username'in gmailpage",IMPLICITWAIT);
		safeClick(GMAIL_NEXT,"'next button' in gmail page",IMPLICITWAIT);
		Thread.sleep(5000);
		safeClearAndType(GMAIL_PASSWORD,sgmailpassword,"'Password' in gmailpage",IMPLICITWAIT);
		Thread.sleep(5000);
		safeClick(GMAIL_NEXT,"'next button' in gmail page",IMPLICITWAIT);
		setImplicitWait(5000);
		safeClick(GMAIL_NEXT,"'next button' in gmail page",IMPLICITWAIT);
		setImplicitWait(5000);
		safeClick(GMAIL_INBOX_MSG,"'INBOX MSG' in the gmail inbox",IMPLICITWAIT);
		Thread.sleep(5000);
		By OTP = By.xpath("//div[text()='"+zoleoTestData.otpsignup+"@outlook.com']/ancestor::div[@class='wide-content-host']//div[@aria-label='Message body']//p//b");
		sValue = safeGetText(OTP,"OTP is retrieved", IMPLICITWAIT);
		System.out.println(sValue);
		driver.switchTo().window(tabs.get(0));
		}
	/**
	 * purpose-To enter the OTP and password in  the page
	 * @param itemName item name
	 * 
	 */
	@Step("To enter the OTP and password in the page ")
	public ZoleoLoginPage enterOTPandNewPassword(String sNew_Password,String sConfirm_New_Password) 
	{
		safeType(FORGOT_PASSWORD_CODE,sValue,"'OTP'field in 'Forgot Password' page",VERYSHORTWAIT);
		
		safeType(NEW_PASSWORD, sNew_Password,"'New Password' field in 'Forgot Password' page", VERYSHORTWAIT);
		safeType(CONFIRM_PASSWORD, sConfirm_New_Password,"'New Password' field in 'Forgot Password' page", VERYSHORTWAIT);
		safeClick(CHANGE_PASSWORD_BUTTON, "'Change passowrd button' in 'Forgot Password' page ",VERYSHORTWAIT);
		return new ZoleoLoginPage(driver);
	}
	
}
