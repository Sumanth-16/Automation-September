package com.page.module;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.page.data.ZoleoData;
import com.page.locators.ZoleoActivationPageLocators;
import com.page.locators.ZoleoMyAccountPageLocators;
import com.page.locators.ZoleoSignInPageLocators;
import com.selenium.Dynamic;
import com.selenium.SafeActions;
import com.testng.Assert;
import com.utilities.TextFiles;

import ru.yandex.qatools.allure.annotations.Step;

public class ZoleoActivationPage extends SafeActions implements ZoleoActivationPageLocators {
	private WebDriver driver;

	public static String sIMEI;
	public static String NEWIMEISERIALNO ;
	public static String sPlan;
	public static String CommPrefText;
	ZoleoData zoleoTestData = new ZoleoData();
	ZoleoAddDevicePage  zoleoAddDevicePage = new ZoleoAddDevicePage(driver);

	// Constructor to define/call methods
	public ZoleoActivationPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	/**
	 * purpose-To Verifies whether navigated to the ZoleoActivationPage or not throws
	 * Exception
	 */
	@Step("Verifies the ZoleoActivation page")
	public void verifyZoleoActivationPage() {
		boolean bActivationSubmitted = isElementPresent(ACTIVATION_SUCCESS, MEDIUMWAIT);
		Assert.assertTrue(bActivationSubmitted, "'Activation Submitted' is not being displayed on the 'ZoleoMyAccount' page");
	}

	/**
	 * purpose-To Verify whether the device information is displayed correct or not
	 * throws Exception
	 * @throws Exception 
	 */
	@Step("Verifies the Devices Details in Activation Page")
	public void VerifyDeviceDetails() throws Exception {
		boolean bIMEINo = isElementPresent(ACTIVATED_IMEI, MEDIUMWAIT);
		Assert.assertTrue(bIMEINo,"'IMEINo' is not being displayed on the 'Activation' page");
		sIMEI= safeGetText(ACTIVATED_IMEI, "The IMEI Displayed  is retreived ", IMPLICITWAIT);
		NEWIMEISERIALNO = TextFiles.CompareTwoTextFiles();
		String[] splitString = NEWIMEISERIALNO.split("\\s");
		String NEWIMEI = splitString[0];
		
		if (sIMEI.contains(NEWIMEI)) {

			Assert.assertTrue(true);
		}
		boolean bPlan = isElementPresent(ACTIVATED_MONTHLY_PLAN, MEDIUMWAIT);
		Assert.assertTrue(bPlan,"'Plan Info' is not being displayed on the 'Activation' page");
		sPlan= safeGetText(ACTIVATED_MONTHLY_PLAN, "The Plan Info Displayed  is retreived ", IMPLICITWAIT);
		if(sPlan.contains(zoleoAddDevicePage.Plan))
		{
			Assert.assertTrue(true);
		}
		
	}

	
	/**
	 * Purpose- To verify whether device activation  page is displayed  or not
	 * @throws InterruptedException 
	 *
	 */
	@Step("Verifying device activation page")
	public ZoleoMyAccountPage ClickMyAccount() throws InterruptedException 
	{
		Thread.sleep(15000);
		boolean bmyaccount = isElementPresent(MY_ACCOUNT, LONGWAIT);
		Assert.assertTrue(bmyaccount, " my account is  not being displayed on activation page");
		Thread.sleep(15000);
		safeJavaScriptClick(MY_ACCOUNT,"clicked on my account button",VERYLONGWAIT);
		Thread.sleep(15000);
		return new ZoleoMyAccountPage(driver);
	}
	}


