package com.page.module;

import com.base.BaseSetup;
import com.datamanager.ConfigManager;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ru.yandex.qatools.allure.annotations.Step;
import com.page.data.ZoleoData;
import com.page.locators.GMAILLocators;

import com.page.locators.ZoleoLogInPageLocators;
import com.page.locators.ZoleoSignUpPageLocators;
import com.selenium.SafeActions;
import com.testng.Assert;


public class ZoleoSignUpPage extends SafeActions implements ZoleoLogInPageLocators,ZoleoSignUpPageLocators,GMAILLocators
{
	private ZoleoData zoleoTestData;
	public static String sValue;
	public static String sErrormsg;
	private Logger log =LogManager.getLogger("ZoleoSignUpPage");
	
	private WebDriver driver;
	ConfigManager app = new ConfigManager("App");
	//Constructor to define/call methods	 
	 public ZoleoSignUpPage(WebDriver driver)
	{ 
		super(driver);
		this.driver = driver;
    } 
	
	/**
	 * Purpose- To verify whether  login page is being displayed or not
	 *
	 */
	@Step("Verifying SignUp Page")
	public void verifySignUpPage() 
	{
		boolean bIsEmailAddressExists = isElementPresent(EMAILADDRESS_SIGNUP, SHORTWAIT);
		boolean bIsPasswordFieldExists = isElementPresent(PHONE_NUMBER_SIGNUP,SHORTWAIT);
		Assert.assertTrue(bIsEmailAddressExists||bIsPasswordFieldExists,"Email field/Password field textbox is not being displayed on 'Zoleo Login' page");
	}
	
	/**
	 * Purpose- To enter login credentials i.e.,emailAddress and password 
	 * @param sEmailAddresss- we pass username of the user
	 * @param sPassword- we pass passowrd of the user
	 */
	@Step("Entering SignUp credentials")
	public void enterSignUpCredentials(String sEmailAddress, String sPhoneNo,String sGivenName,String sFamilyName,String sPassword) 
	{
		safeType(EMAILADDRESS_SIGNUP, sEmailAddress,"'Email' field in 'SignUp' page", VERYSHORTWAIT);
		safeType(PHONE_NUMBER_SIGNUP, sPhoneNo,"'PhoneNo' field in 'SignUp' page", VERYSHORTWAIT);
		safeType(GIVEN_NAME, sGivenName,"'Given Name' field in 'SignUp' page", VERYSHORTWAIT);
		safeType(FAMILY_NAME,sFamilyName,"'Family Name' field in 'SignUp' page",VERYSHORTWAIT);
		safeType(PASSWORD_SIGNUP,sPassword,"'Password' field in 'SignUp'page",VERYSHORTWAIT);
	}
	
	/**
	 * Purpose- To click on Sign In button
	 */
	@Step("Clicking on SignUp button")
	public void clickonAccountSignUpButton()
	{
		safeClick(ACCOUNT_SIGNUP_BUTTON, "'Account SignUp' button in 'SignUp' page ",VERYSHORTWAIT);
		
	}
	/**
	 * Purpose- To click on Resend OTP button
	 */
	@Step("Clicking on ResendOTP button")
	public void clickonResendOTPButton()
	{
		safeClick(RESEND_OTP, "'ResendOTP' button in 'SignUp' page ",VERYSHORTWAIT);
		
		
	}
	/**
	 * Purpose- To get OTP from Mail box
	 * @throws InterruptedException 
	 */
	@Step("Get OTP from MailBox")
	
	
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
		
		
			By OTP1 = By.xpath("//div[text()='"+zoleoTestData.otpsignup+"@outlook.com']/ancestor::div[@class='wide-content-host']//div[@aria-label='Message body']//p//b");
			//if(isElementPresent(OTP1,SHORTWAIT ))
			
			sValue = safeGetText(OTP1,"OTP is retrieved", IMPLICITWAIT);
		
		/*catch(Exception e) {
			By OTP2 = By.xpath("//span[text()='"+zoleoTestData.otpsignup+"']/ancestor::div[@class='gs']//div[@class='ii gt adO']/div/p/b");
			if(isElementPresent(OTP2,SHORTWAIT ))
			sValue = safeGetText(OTP2,"OTP is retrieved", IMPLICITWAIT);
		}*/
		System.out.println(sValue);
		driver.switchTo().window(tabs.get(0));
		}
	/**
	 * Purpose- To get OTP from Mail box
	 * @throws InterruptedException 
	 */
	@Step("Get OTP from MailBox")
	
	
	public   void getRESENDOTPfrommailbox(String sgmailusername,String sgmailpassword) throws InterruptedException
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
		safeClick(GMAIL_NEXT,"'next button' in gmail page",IMPLICITWAIT);
		Thread.sleep(10000);
		driver.findElement(By.xpath("(//span[@title='noreply@zoleoinc.com'])[2]")).click();
		//safeClick(GMAIL_INBOX_MSG_OLD,"'INBOX MSG' in the gmail inbox",IMPLICITWAIT);
		Thread.sleep(5000);
		
		
			By OTP1 = By.xpath("//div[text()='"+zoleoTestData.otpsignup+"@outlook.com']/ancestor::div[@class='wide-content-host']//div[@aria-label='Message body']//p//b");
			//if(isElementPresent(OTP1,SHORTWAIT ))
			
			sValue = safeGetText(OTP1,"OTP is retrieved", IMPLICITWAIT);
		
		/*catch(Exception e) {
			By OTP2 = By.xpath("//span[text()='"+zoleoTestData.otpsignup+"']/ancestor::div[@class='gs']//div[@class='ii gt adO']/div/p/b");
			if(isElementPresent(OTP2,SHORTWAIT ))
			sValue = safeGetText(OTP2,"OTP is retrieved", IMPLICITWAIT);
		}*/
		System.out.println(sValue);
		driver.switchTo().window(tabs.get(0));
		safeType(VERIFICATION_CODE,sValue,"'OTP'field in 'SignUp' page",VERYSHORTWAIT);
		safeClick(CONFIRM_ACCOUNT,"'confirm Account' button in 'SignUp'page",VERYSHORTWAIT);
		sErrormsg=safeGetText(OTP_ERROR_MSG,"OTP is retrieved", IMPLICITWAIT);
		log.info(sErrormsg);
		driver.switchTo().window(tabs.get(1));
		//safeActionsClick(GMAIL_INBOX_BACK_BUTTON,"'Back Button' in 'Gmail'Page",VERYSHORTWAIT);
		driver.navigate().back();
		Thread.sleep(10000);
		driver.findElement(By.xpath("(//span[@title='noreply@zoleoinc.com'])[1]")).click();
		Thread.sleep(5000);
		
		
			//By OTP1 = By.xpath("//span[text()='"+zoleoTestData.otpsignup+"']/ancestor::div[@class='gs']//div[@class='ii gt']/div/p/b");
			//if(isElementPresent(OTP1,SHORTWAIT ))
			
			sValue = safeGetText(OTP1,"OTP is retrieved", IMPLICITWAIT);
		
		/*catch(Exception e) {
			By OTP2 = By.xpath("//span[text()='"+zoleoTestData.otpsignup+"']/ancestor::div[@class='gs']//div[@class='ii gt adO']/div/p/b");
			if(isElementPresent(OTP2,SHORTWAIT ))
			sValue = safeGetText(OTP2,"OTP is retrieved", IMPLICITWAIT);
		}*/
		System.out.println(sValue);
		driver.switchTo().window(tabs.get(0));
		
		
		
		
		}
	
	
	/**
	 * Purpose- To Enter and Verify OTP 
	 */
	@Step("Enter and Verify OTP")
	public ZoleoCountryPage EnterAndVerifyOTP()
	{
		
		safeType(VERIFICATION_CODE,sValue,"'OTP'field in 'SignUp' page",IMPLICITWAIT);
		safeClick(CONFIRM_ACCOUNT,"'confirm Account' button in 'SignUp'page",IMPLICITWAIT);
		return new  ZoleoCountryPage(driver);
	}
	
}
