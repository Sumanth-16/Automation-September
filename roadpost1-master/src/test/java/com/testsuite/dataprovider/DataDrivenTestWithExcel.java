/* ************************************** PURPOSE **********************************

 - This class contains Example of how to run data driven tests by using TestNG @DataProvider annotation with Excel as the external source
 */

package com.testsuite.dataprovider;

import org.testng.annotations.AfterMethod;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.datamanager.ExcelManager;

import com.selenium.Sync;

public class DataDrivenTestWithExcel extends BaseSetup 
{
	/*private OpenCartLandingPage openCartLandingPage;
	private OpenCartData openCartTestData;
	private LoginPage loginPage;
	private MyAccountHomePage myAccountHomePage;
	private String file = System.getProperty("user.dir") + "\\Resources\\Data\\OpenCart_AddAddress.xlsx";
	private String sModeOfExecution;

	
	// Initialize Page parts instances with Web drivers getter method -
	@BeforeMethod(alwaysRun = true)
	public void BaseClassSetUp() 
	{
		ConfigManager sys;
		sys=new ConfigManager();
		openCartLandingPage = new OpenCartLandingPage(getDriver());		
		openCartTestData = new OpenCartData(); 
		getDriver().manage().deleteAllCookies();
		getDriver().get(openCartTestData.openCartURL);
		(new Sync(getDriver())).waitForPageToLoad();
		sModeOfExecution=sys.getProperty("ModeOfExecution");
	}

	/**
	 * Purpose- This DataProvider method will store all the content read from the
	 * excel and pass the data to a @Test method.
	 *//*
	@DataProvider(name = "GetAddressFromExcel")
	public String[][] getAddressToAdd() {
		ExcelManager excel= new ExcelManager(file);
		return excel.getExcelSheetData("Address");
	}

	/**
	 * Purpose- This test method declares that its data should be supplied by
	 * the @dataProvider method named 'GetAddressFromExcel' Add the address
	 * read from the excel and delete the address
	 * 
	 * Each set of data will count as single test case. In the final report user
	 * can verify with set of data the tests are passed/failed
	 * 
	 * The thumb rule is -> Number of columns in excel = paremeters passing
	 * to @test method
	 * 
	 * The number of columns in excel and parameter count in @test method should
	 * be same number if not tests will failed with 'null pointer exception'.
	 * The parameter usage is optional
	 * 
	 *//*
	@Test(dataProvider = "GetAddressFromExcel")
	public void tc005_addNewAddressFromExcel(String firstName,String lastName,String address1,String city,String postCode,String country,String state)
	{
		//Verifies the Opencart application landing page
		openCartLandingPage.verifyOpenCartLandingPage();
		//Click on 'My Account' link in the landing page 
		openCartLandingPage.clickOnMyAccountLink();
		//Click on 'Login' link under 'My Account' link
		loginPage=openCartLandingPage.clickOnLoginLink();
		//Verifies the email address and password fields on login page
		loginPage.verifyLoginPage();
		//Enter the email address and password in login page
		loginPage.enterLoginCredentials(openCartTestData.emailAddress,openCartTestData.password);
		//Click on login button in loginpage
		myAccountHomePage=loginPage.clickLogInButton();
		//Verifies the headers 'My Account','My orders','My Affliate Account' and 'NewsLetter' on 'My Account Home' Page
		myAccountHomePage.verifyMyAccountHomePage(openCartTestData.myAccount,openCartTestData.myOrders, openCartTestData.myAffliateAccount,openCartTestData.newsLetter);
		//Click on 'Address book' and click on 'New address' button
		myAccountHomePage.clickOnAddressBookAndClickNewAddress();
		//Enter the required fields for creating a new address and click on continue button
		myAccountHomePage.enterAddAddressFieldValues(firstName, lastName, address1, city, postCode, country, state);
		//Verifies the text -'Your address has been successfully added' is displayed or not
		myAccountHomePage.verifyNewAddressSuccessMessage();
		//Delete the address and verifies the text -'Your address has been successfully deleted' is displayed or not 
		myAccountHomePage.deleteAddress();
		
		//The following if condition is to be added if you are expecting video recording for Grid nodes,
		//Basically the following piece of code is same the after method code
		if (sModeOfExecution.equalsIgnoreCase("Remote")) 
		{
			openCartLandingPage.clickOnMyAccountLink();
			openCartLandingPage.clickOnLogoutLink();
			openCartLandingPage.verifyLogout();		
		}
	}
	
	/**
	 * Purpose- This method contains the logic for 'Logout' functionality in Opencart
	 *//*
	@AfterMethod
	public void testOpenCartLogout() 
	{
		if (sModeOfExecution.equalsIgnoreCase("Linear")) 
		{
		openCartLandingPage.clickOnMyAccountLink();
		openCartLandingPage.clickOnLogoutLink();
		openCartLandingPage.verifyLogout();
		}
	}
	*/
}