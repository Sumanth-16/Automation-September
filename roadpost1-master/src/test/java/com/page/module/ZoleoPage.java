package com.page.module;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.WebDriver;



import com.page.locators.ZoleoLogInPageLocators;
import com.page.locators.ZoleoSignInPageLocators;
import com.selenium.Dynamic;
import com.selenium.SafeActions;
import com.testng.Assert;

import ru.yandex.qatools.allure.annotations.Step;

public class ZoleoPage extends SafeActions implements ZoleoLogInPageLocators,ZoleoSignInPageLocators{
	private WebDriver driver;
	
	//Constructor to define/call methods	 
	public ZoleoPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	@Step("Handling Basic Authorization Window")
    public void Auth() throws AWTException, InterruptedException
    {
        Robot robot = new Robot();

        robot.keyPress(KeyEvent.VK_Z);
        robot.keyRelease(KeyEvent.VK_Z);
        robot.keyPress(KeyEvent.VK_E);
        robot.keyRelease(KeyEvent.VK_E);
        robot.keyPress(KeyEvent.VK_N);
        robot.keyRelease(KeyEvent.VK_N);
        robot.keyPress(KeyEvent.VK_Q);
        robot.keyRelease(KeyEvent.VK_Q);

        robot.keyPress(KeyEvent.VK_TAB);

        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_S);
        robot.keyRelease(KeyEvent.VK_S);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_P);
        robot.keyRelease(KeyEvent.VK_P);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);
        robot.keyPress(KeyEvent.VK_C);
        robot.keyRelease(KeyEvent.VK_C);
        robot.keyPress(KeyEvent.VK_3);
        robot.keyRelease(KeyEvent.VK_3);
        robot.keyPress(KeyEvent.VK_W);
        robot.keyRelease(KeyEvent.VK_W);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);
        robot.keyPress(KeyEvent.VK_L);
        robot.keyRelease(KeyEvent.VK_L);
        robot.keyPress(KeyEvent.VK_K);
        robot.keyRelease(KeyEvent.VK_K);
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_3);
        robot.keyRelease(KeyEvent.VK_3);

        robot.keyRelease(KeyEvent.VK_SHIFT);

        robot.keyPress(KeyEvent.VK_ENTER);
        Thread.sleep(5000);
    }
	/**
	 * purpose-To Verifies whether navigated to the Zoleo page or not
	 * throws Exception 
	 */
	@Step("Verifies the Zoleo page")
	public void verifyZoleoPage()
	{
		boolean bZoleoLogo = isElementPresent(ZOLEO_LOGO,VERYSHORTWAIT);
		Assert.assertTrue(bZoleoLogo, "'Zoleo Logo' is not being displayed on the 'Zoleo' page");	
	}
	
	/**
	 * purpose-To Click on the required item from the page
	 * 
	 * 
	 */
	@Step("Click on the Login/SignUp button from the page ")
	public ZoleoLoginPage clickOnLoginSignUpButton()
	{
		safeJavaScriptClick(NEXT_BUTTON,"'Login_SignUp' Button in 'ZoleoSignin' page", MEDIUMWAIT);
		return new ZoleoLoginPage(driver);
	}
	
	/**
	 * purpose-To Click on the required item from the page
	 * 
	 * 
	 */
	@Step("Click on the Login/SignUp button from the page ")
	public ZoleoLoginPage clickActivateButton()
	{
		safeJavaScriptClick(ACTIVATE,"'Activate' Button in 'ZoleoSignin' page", MEDIUMWAIT);
		return new ZoleoLoginPage(driver);
	}
	
	
	
}
