package com.testsuite.dataprovider;

import com.Zapi.ZephyrTestID;

import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.datamanager.ExcelManager;
import com.gurock.testrail.UseAsTestRailId;
import com.selenium.Sync;
import com.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataDrivenOrangeHRMTest extends BaseSetup {
  /*  private String excelFile = System.getProperty("user.dir") + "\\Resources\\Data\\orangeHRM_AddCredentials.xlsx";
    private OrangeHRMLogin orangeHRMLogin;
    private OrangeHRMHome orangeHRMHome;
    private String sModeOfExecution;
    private OpenCartData openCartTestData;

    @BeforeMethod(alwaysRun = true)
    public void baseClassSetUp() {
        ConfigManager sys;
        sys = new ConfigManager();
        orangeHRMLogin = new OrangeHRMLogin(getDriver());
        orangeHRMHome=new OrangeHRMHome(getDriver());
        openCartTestData = new OpenCartData();
        getDriver().manage().deleteAllCookies();
        getDriver().get(openCartTestData.openCartURL);
        (new Sync(getDriver())).waitForPageToLoad();
        sModeOfExecution = sys.getProperty("ModeOfExecution");
    }

    @DataProvider(name = "GetLoginCredentialsFromExcel")
    public String[][] getCredentialsToLogin() {
        ExcelManager excel= new ExcelManager(excelFile);
        return excel.getExcelSheetData("Credentials");
    }

    @Test(dataProvider = "GetLoginCredentialsFromExcel")
    @UseAsTestRailId(caseId = 3)
    @ZephyrTestID(TestID = "SELENIUM-3",TestCycleName = "OrangeHRMRuns")
    public void tc001_loginCredentialsFromExcel(String userName,String password)
    {
        //Verifies the Opencart application landing page
        orangeHRMLogin.verifyOpenCartLandingPage();
        //Click on 'My Account' link in the landing page
        orangeHRMLogin.enterDetails(userName, password);
        //Click on 'Login' link under 'My Account' link
        orangeHRMLogin.clickOnLoginLink();
        orangeHRMLogin.verifyLoginPage();
        boolean activeValue=orangeHRMHome.verifyActiveText("Dashboard");
        if(activeValue){

        }
        else{
            Assert.assertTrue(activeValue);
        }
    }

    @AfterMethod
    public void testOpenCartLogout() {
        if (sModeOfExecution.equalsIgnoreCase("Linear")) {
            orangeHRMLogin.clickOnDropdown();
            orangeHRMLogin.clickOnMyAccountLink();
            //openCartLandingPage.clickOnLogoutLink();
            //openCartLandingPage.verifyLogout();
        }

    }

*/
}