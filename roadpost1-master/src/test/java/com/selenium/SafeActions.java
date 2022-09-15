/* ************************************* PURPOSE **********************************

 - This class contains all UserAction methods
 */

package com.selenium;


import Client.Robotil;
import com.datamanager.ConfigManager;
import com.google.common.base.CharMatcher;
import com.testng.Assert;
import com.utilities.ReportSetup;
import com.utilities.RobotilHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;

import java.util.Base64;
import java.util.Collections;
import java.util.List;


public class SafeActions extends Sync
{

	//Local WebDriver instance
	private WebDriver driver;
	private Logger log =LogManager.getLogger("SafeActions");
	private ConfigManager sys = new ConfigManager();

	//Constructor to initialize the local WebDriver variable with the WebDriver variable that,
	//has been passed from each PageParts Java class
	public SafeActions(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
	}
	/**
	 * Method - Safe Method for User Click, waits until the element is loaded and then performs a click action
	 * @param locator locator
	 * @param optionWaitTime optionWaitTime
	 */
	public void safeClickWithoutScroll(By locator, String friendlyWebElementName,int... optionWaitTime)
	{
		int waitTime = 0;
		try
		{
			waitTime =  getWaitTime(optionWaitTime);
			if(waitUntilClickable(locator, friendlyWebElementName,waitTime))
			{
				WebElement element = driver.findElement(locator);
				setHighlight(element);
				element.click();
				log.info(getTestCasename()+"Clicked on the  " + friendlyWebElementName);
			}
			else
			{
				log.error(getTestCasename()+friendlyWebElementName + " is not clickable in time - "+waitTime+" Seconds");
				Assert.fail( friendlyWebElementName + " is not clickable in time - "+waitTime+" Seconds");
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
			Assert.fail(friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " was not found in DOM in time - "+waitTime+" Seconds"+" - NoSuchElementException");
			Assert.fail(friendlyWebElementName + " was not found in DOM in time - "+waitTime+" Seconds"+" - NoSuchElementException");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+ friendlyWebElementName + " was not clickable" + " - " + e);
			Assert.fail(friendlyWebElementName + " was not found on the web page");
		}
	}

	/**
	 * Method - Safe Method for User Click, waits until the element is loaded and then performs a click action
	 * @param locator locator
	 * @param optionWaitTime optionWaitTime
	 */
	public void safeClick(By locator, String friendlyWebElementName,int... optionWaitTime)
	{
		int waitTime = 0;
		try
		{
			waitTime =  getWaitTime(optionWaitTime);
			if(waitUntilClickable(locator, friendlyWebElementName,waitTime))
			{
				scrollIntoElementView(locator,friendlyWebElementName);
				WebElement element = driver.findElement(locator);
				setHighlight(element);
				element.click();
				log.info(getTestCasename()+"Clicked on the  " + friendlyWebElementName);
			}
			else
			{
				log.error(getTestCasename()+friendlyWebElementName + " is not clickable in time - "+waitTime+" Seconds");
				Assert.fail( friendlyWebElementName + " is not clickable in time - "+waitTime+" Seconds");
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
			Assert.fail(friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " was not found in DOM in time - "+waitTime+" Seconds"+" - NoSuchElementException");
			Assert.fail(friendlyWebElementName + " was not found in DOM in time - "+waitTime+" Seconds"+" - NoSuchElementException");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+ friendlyWebElementName + " was not clickable" + " - " + e);
			Assert.fail(friendlyWebElementName + " was not found on the web page");
		}
	}
	
	

	/**
	 * Method - Safe Method for User Click using Actions.click, waits until the element is loaded and then performs a click action
	 * @param locator locator
	 * @param waitTime waitTime
	 */

	public void safeActionsClick(By locator,String friendlyWebElementName,int waitTime)
	{
		try
		{
			if(isElementVisible(locator, waitTime))
			{
				WebElement element=driver.findElement(locator);
				setHighlight(element);
				Actions builder = new Actions(driver);
				builder.moveToElement(element).click().build().perform();
				log.info(getTestCasename()+"Clicked on " + friendlyWebElementName);
			}
			else
			{
				log.error(getTestCasename()+friendlyWebElementName +" was not visible to click in time - "+waitTime+" Seconds");
				Assert.fail(friendlyWebElementName +" was not visible to click in time - "+waitTime+" Seconds");
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
			Assert.fail(friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " was not found in DOM in time - "+waitTime+" Seconds"+" - NoSuchElementException");
			Assert.fail(friendlyWebElementName + " was not found in DOM in time - "+waitTime+" Seconds"+" - NoSuchElementException");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to click the cursor on " + friendlyWebElementName + " - " + e);
			Assert.fail(friendlyWebElementName+" was not visible on the web page");
		}
	}


	/**
	 * Method - Safe Method for User Double Click, waits until the element is loaded and then performs a double click action
	 * @param locator locator
	 * @param optionWaitTime optionWaitTime
	 */
	public void safeDblClick(By locator,String friendlyWebElementName, int... optionWaitTime)
	{
		int waitTime=0;
		try
		{
			waitTime =  getWaitTime(optionWaitTime);
			if(waitUntilClickable(locator, friendlyWebElementName,waitTime))
			{
				scrollIntoElementView(locator,friendlyWebElementName);
				WebElement element = driver.findElement(locator);
				setHighlight(element);
				Actions userAction = new Actions(driver).doubleClick(element);
				userAction.build().perform();
				log.info(getTestCasename()+"Double clicked on " + friendlyWebElementName);
			}
			else
			{
				log.error(getTestCasename()+"Unable to find " + friendlyWebElementName+" in web page in time - "+waitTime);
				Assert.fail("Unable to find " + friendlyWebElementName+" in web page in time - "+waitTime);
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
			Assert.fail(friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
			Assert.fail( friendlyWebElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " was not clickable" + " - " + e);
			Assert.fail("Unable to find " + friendlyWebElementName+" in web page");
		}
	}



	/**
	 * Method - Safe Method for User Clear and Type, waits until the element is loaded and then enters some text
	 * @param locator locator
	 * @param text text to enter in Textbox
	 * @param optionWaitTime optionWaitTime
	 */
	public void safeClearAndType(By locator, String text,String friendlyWebElementName, int... optionWaitTime)
	{
		int waitTime=0;
		try
		{
			waitTime =  getWaitTime(optionWaitTime);
			if(isElementPresent(locator, waitTime))
			{
				scrollIntoElementView(locator,friendlyWebElementName);
				WebElement element=driver.findElement(locator);
				setHighlight(element);
				element.clear();
				element.sendKeys(text);
				log.info(getTestCasename()+"Cleared the " +friendlyWebElementName+ "and entered - '"+text);
			}
			else
			{
				log.error(getTestCasename()+"Text " + text + " to be entered after " + " clear the   " + friendlyWebElementName + " was not found in DOM in time - "+waitTime);
				Assert.fail("Text " + text + " to be entered after " + " clear the   " + friendlyWebElementName + " was not found in DOM in time - "+waitTime);
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+"Text " + text + " to be entered after " + " clear the  " + friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
			Assert.fail("Text " + text + " to be entered after " + " clear the   " + friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+"Text " + text + " to be entered after " + " clear the   " + friendlyWebElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
			Assert.fail("Text " + text + " to be entered after " + " clear the   " + friendlyWebElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
		}
//		catch(NullPointerException e){
//			log.error(getTestCasename()+"Text provided as null...Please provide text");
//			Assert.fail("Text provided as null...Please provide text");
//		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to clear and enter '" + text + "' text in  "+ friendlyWebElementName + " - " + e);
			Assert.fail("Text " + text + " to be entered after " + " clear the  " + friendlyWebElementName + " was not found in DOM");
		}
	}

	/**
	 * Method - Safe Method for User Type, waits until the element is loaded and then enters some text
	 * @param locator locator
	 * @param text text to enter in Textbox
	 * @param optionWaitTime optionWaitTime
	 */
	public void safeType(By locator, String text,String friendlyWebElementName, int... optionWaitTime)
	{
		int waitTime=0;
		try
		{
			waitTime =  getWaitTime(optionWaitTime);
			if(isElementPresent(locator, waitTime))
			{
				scrollIntoElementView(locator,friendlyWebElementName);
				WebElement element=driver.findElement(locator);
				setHighlight(element);
				//element.click();
				element.sendKeys(text);
				log.info(getTestCasename()+"Entered - '" + text + " into " + friendlyWebElementName);
			}
			else
			{
				log.error(getTestCasename()+"Unable to enter " + text + " in  " + friendlyWebElementName+" in time - "+waitTime+" Seconds");
				Assert.fail("Unable to enter " + text + " in  " + friendlyWebElementName+" in time - "+waitTime+" Seconds");
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+"Text " + text + " to be entered in the   " + friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
			Assert.fail("Text " + text + " to be entered in the   " + friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+"Text " + text + " to be entered in the   " + friendlyWebElementName + " is not attached to the page document in time - "+waitTime+" - NoSuchElementException");
			Assert.fail("Text " + text + " to be entered in the   " + friendlyWebElementName + " is not attached to the page document in time - "+waitTime+" - NoSuchElementException");
		}
//		catch(NullPointerException e){
//			log.error(getTestCasename()+"some variables may failed to initialize... please check");
//			Assert.fail("some variables may failed to initialize... please check");
//		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to enter '" + text + "' text in   -"+ friendlyWebElementName + " - " + e);
			Assert.fail("Unable to enter '" + text + "' text in  -"+ friendlyWebElementName+" Some Exception");
		}
	}


	/**
	 * Method - Safe Method to Type Encrypted password, waits until the element is loaded, decrecpt the encrypted password and then enter into the password field
	 * @param locator locator
	 * @param encryptedText encryptedText to enter in Textbox
	 * @param optionWaitTime optionWaitTime
	 */
	public void safeTypePassword(By locator, String encryptedText,String friendlyWebElementName, int... optionWaitTime)
	{

		int waitTime=0;
		try
		{
			waitTime =  getWaitTime(optionWaitTime);
			if(isElementPresent(locator, waitTime))
			{
				scrollIntoElementView(locator,friendlyWebElementName);
				WebElement element=driver.findElement(locator);
				setHighlight(element);
				byte[] decryptedPasswordBytes = Base64.getDecoder().decode(encryptedText);
				String decryptedPassword = new String(decryptedPasswordBytes);
				element.sendKeys(decryptedPassword);
				log.info(getTestCasename()+"Entered - '" + decryptedPassword + " in the  - " + friendlyWebElementName);

			}
			else
			{
				log.error(getTestCasename()+"Encrypted Text " + encryptedText + " to be entered in the  " + friendlyWebElementName + " is not attached to the page document it time - "+waitTime);
				Assert.fail("Encrypted Text " + encryptedText + " to be entered in the " + friendlyWebElementName + " is not attached to the page document in time - "+waitTime);
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+"Password " + encryptedText + " to be entered in the " + friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
			Assert.fail("Password " + encryptedText + " to be entered in the " + friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+"Password " + encryptedText + " to be entered in the " + friendlyWebElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
			Assert.fail("Password " + encryptedText + " to be entered in the " + friendlyWebElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
		}
//		catch(NullPointerException e){
//			log.error(getTestCasename()+"some variables may failed to initialize... please check");
//			Assert.fail("some variables may failed to initialize... please check");
//		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to enter '"  + " - " + encryptedText + "' text in"+ friendlyWebElementName + " - " + e);
			Assert.fail("Unable to enter '" + encryptedText+ "' text in"+ friendlyWebElementName);
		}
	}


	/**
	 * Method - Safe Method for Radio button selection, waits until the element is loaded and then selects Radio button
	 * @param locator locator
	 * @param optionWaitTime optionWaitTime
	 */
	public void safeSelectRadioButton(By locator, String friendlyWebElementName,int... optionWaitTime)
	{
		int waitTime=0;
		try
		{
			waitTime =  getWaitTime(optionWaitTime);
			if(waitUntilClickable(locator, friendlyWebElementName,waitTime))
			{
				scrollIntoElementView(locator,friendlyWebElementName);
				WebElement element = driver.findElement(locator);
				setHighlight(element);
				element.click();
				log.info(getTestCasename()+"Clicked on '"+friendlyWebElementName);
			}
			else
			{
				log.error(getTestCasename()+"Unable to select Radio button '"+friendlyWebElementName+"' in time - "+waitTime);
				Assert.fail("Unable to select Radio button '"+friendlyWebElementName+"' in time - "+waitTime);
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
			Assert.fail(friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
			Assert.fail(friendlyWebElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to click on radio button  '" + friendlyWebElementName + "' - " + e);
			Assert.fail(friendlyWebElementName + " was not found in DOM"+" Some Exception");
		}
	}


	/**
	 * Method - Safe Method for checkbox selection, waits until the element is loaded and then selects checkbox
	 * @param locator locator
	 * @param optionWaitTime optionWaitTime
	 */
	public void safeCheck(By locator, String friendlyWebElementName,int... optionWaitTime)
	{
		int waitTime=0;
		try
		{
			waitTime =  getWaitTime(optionWaitTime);
			if(isElementPresent(locator, waitTime))
			{
				scrollIntoElementView(locator,friendlyWebElementName);
				WebElement checkBox = driver.findElement(locator);
				setHighlight(checkBox);
				if(checkBox.isSelected())
					log.info(getTestCasename()+"CheckBox '" +friendlyWebElementName + "' is already selected");
				else{
					checkBox.click();
					log.info(getTestCasename()+"Checkbox '"+friendlyWebElementName+"' is selected");
				}
			}
			else
			{
				log.error(getTestCasename()+friendlyWebElementName + " was not found in DOM in time - "+waitTime);
				Assert.fail(friendlyWebElementName + " was not found in DOM in time - "+waitTime);
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " is not attached to the page document in time - "+waitTime);
			Assert.fail(friendlyWebElementName + " is not attached to the page document in time - "+waitTime);
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
			Assert.fail(friendlyWebElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to check the checkbox'" + friendlyWebElementName + "' - " + e);
			Assert.fail(friendlyWebElementName + " was not found in DOM in time");
		}
	}




	/**
	 * Method - Safe Method for checkbox deselection, waits until the element is loaded and then deselects checkbox
	 * @param locator locator
	 * @param optionWaitTime optionWaitTime
	 */
	public void safeUnCheck(By locator,String friendlyWebElementName, int... optionWaitTime)
	{
		int waitTime=0;
		try
		{
			waitTime =  getWaitTime(optionWaitTime);
			if(isElementPresent(locator, waitTime))
			{
				scrollIntoElementView(locator,friendlyWebElementName);
				WebElement checkBox = driver.findElement(locator);
				setHighlight(checkBox);
				if(checkBox.isSelected()){
					checkBox.click();
					log.info(getTestCasename()+"Checkbox  '" + friendlyWebElementName + "'' " + "is deselected");
				}
				else
					log.info(getTestCasename()+"CheckBox '" + friendlyWebElementName + "' is already deselected");

			}
			else
			{
				log.error(getTestCasename()+friendlyWebElementName + " was not found in DOM in time - "+waitTime);
				Assert.fail(friendlyWebElementName + " was not found in DOM in time - "+waitTime);
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " is not attached to the page document in time - "+waitTime+" - StaleElementReferenceException");
			Assert.fail(friendlyWebElementName + " is not attached to the page document in time - "+waitTime+" - StaleElementReferenceException");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
			Assert.fail(friendlyWebElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to uncheck the'" + friendlyWebElementName + "' - " + e);
			Assert.fail("'"+friendlyWebElementName +"' was not found in DOM in time");
		}
	}



	/**
	 * Method - Safe Method for checkbox Selection or Deselection based on user input, waits until the element is loaded and then deselects/selects checkbox
	 * @param locator locator
	 * @param checkOption checkOption either select or deselect
	 * @param optionWaitTime optionWaitTime
	 */
	public void safeCheckByOption(By locator,boolean checkOption, String friendlyWebElementName,int... optionWaitTime)
	{
		int waitTime=0;
		try
		{
			waitTime =  getWaitTime(optionWaitTime);
			if(isElementPresent(locator, waitTime))
			{
				scrollIntoElementView(locator,friendlyWebElementName);
				WebElement checkBox = driver.findElement(locator);
				setHighlight(checkBox);
				if((checkBox.isSelected()==true && checkOption == false)||(checkBox.isSelected()==false && checkOption == true)){
					checkBox.click();
					log.info(getTestCasename()+friendlyWebElementName + " is checked or unchecked");
				}
				else{
					log.info(getTestCasename()+friendlyWebElementName + " is already deselected");
				}
			}
			else
			{
				log.error(getTestCasename()+friendlyWebElementName +" in time - "+waitTime);
				Assert.fail(friendlyWebElementName +" in time - "+waitTime);
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
			Assert.fail(friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
			Assert.fail(friendlyWebElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to check or uncheck " + friendlyWebElementName + "' - " + e);
			Assert.fail( friendlyWebElementName + " was not found in DOM");
		}
	}


	/**
	 * Method - Safe Method for getting checkbox value, waits until the element is loaded and then deselects checkbox
	 * @param locator locator
	 * @param friendlyWebElementName friendlyWebElementName
	 * @param optionWaitTime optionWaitTime
	 * @return - boolean (returns True when the checkbox is enabled else returns false)
	 */
	public boolean safeGetCheckboxValue(By locator, String friendlyWebElementName,int... optionWaitTime)
	{
		int waitTime =  0;
		boolean isSelected = false;
		try
		{
			waitTime =  getWaitTime(optionWaitTime);
			if(isElementPresent(locator, waitTime))
			{
				scrollIntoElementView(locator,friendlyWebElementName);
				WebElement checkBox = driver.findElement(locator);
				setHighlight(checkBox);
				if (checkBox.isSelected())	{
					isSelected = true;
					log.info(getTestCasename()+friendlyWebElementName +" is checked");
				}
			}
			else
			{
				log.error(getTestCasename()+"Unable to get the status of " + friendlyWebElementName);
				Assert.fail("Unable to get the status of " + friendlyWebElementName);
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
			Assert.fail(friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
			Assert.fail(friendlyWebElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to get the status of" + friendlyWebElementName +"-"+ e);
			Assert.fail("Unable to get the status of" + friendlyWebElementName);
		}
		return isSelected;
	}


	/**
	 * Purpose- For selecting multiple check boxes at a time
	 * @param waitTime optinal wait time
	 * @param locator locator
	 * @functionCall - SelectMultipleCheckboxs(MEDIUMWAIT, By.id("Checkbox1"),By.id("Checkbox2"), By.xpath("checkbox")); u can pass 'N' number of locators at a time
	 */
	public void safeSelectCheckboxes(int waitTime ,String friendlyWebElementName,By... locator)
	{
		By check = null;
		try
		{
			if(locator.length>0)
			{
				for(By currentLocator:locator)
				{
					check = currentLocator;
					waitUntilClickable(currentLocator, friendlyWebElementName,waitTime);
					scrollIntoElementView(currentLocator,friendlyWebElementName);
					WebElement checkBox = driver.findElement(currentLocator);
					setHighlight(checkBox);
					if(checkBox.isSelected())
						log.info(getTestCasename()+currentLocator + " is already selected");
					else{
						checkBox.click();
						log.info(getTestCasename()+friendlyWebElementName + " is selected");
					}
				}
			}
			else
			{
				log.error(getTestCasename()+"Expected atleast one locator as argument to safeSelectCheckboxes function");
				Assert.fail("Expected atleast one locator as argument to safeSelectCheckboxes function");
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
			Assert.fail(friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " was not found in DOM - NoSuchElementException");
			Assert.fail(friendlyWebElementName + " was not found in DOM - NoSuchElementException");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to select " + friendlyWebElementName + " - " + e);
			Assert.fail("Unable to select " + friendlyWebElementName);
		}
	}

	/**
	 * Purpose- For deselecting multiple check boxes at a time
	 * @param waitTime Optional wait time
	 * @param locator locator
	 * @functionCall - DeselectMultipleCheckboxs(MEDIUMWAIT, By.id("Checkbox1"),By.id("Checkbox2"), By.xpath("checkbox")); u can pass 'N' number of locators at a time
	 */
	public void safeDeselectCheckboxes(int waitTime,String friendlyWebElementName,By...locator)
	{
		By check = null;
		try
		{
			if(locator.length>0)
			{
				for(By currentLocator:locator)
				{
					check = currentLocator;
					waitUntilClickable(currentLocator,  friendlyWebElementName,waitTime);
					WebElement checkBox = driver.findElement(currentLocator);
					scrollIntoElementView(currentLocator,friendlyWebElementName);
					setHighlight(checkBox);
					if(checkBox.isSelected()){
						checkBox.click();
						log.info(getTestCasename()+friendlyWebElementName + " is deselected");
					}
					else
						log.info(getTestCasename()+friendlyWebElementName + " is already deselected");

				}
			}
			else
			{
				log.error(getTestCasename()+"Expected atleast one locator as argument to safeDeselectCheckboxes function");
				Assert.fail("Expected atleast one locator as argument to safeDeselectCheckboxes function");
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
			Assert.fail(friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
			Assert.fail(friendlyWebElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to deselect  " + friendlyWebElementName + " - " + e);
			Assert.fail("Unable to deselect " + friendlyWebElementName);
		}
	}



	/**
	 * Method - Safe Method for User Select option from Drop down by option name, waits until the element is loaded and then selects an option from drop down
	 * @param locator
	 * @param sOptionToSelect
	 * @param waitTime
	 * @return - boolean (returns True when option is selected from the drop down else returns false)
	 */
	public void safeSelectOptionInDropDown(By locator, String optionToSelect,String friendlyWebElementName, int... optionWaitTime)
	{
		try
		{
			List<WebElement> options = Collections.<WebElement>emptyList();
			int waitTime =  getWaitTime(optionWaitTime);
			if(isElementPresent(locator, waitTime))
			{
				scrollIntoElementView(locator,friendlyWebElementName);
				WebElement selectElement = driver.findElement(locator);
				setHighlight(selectElement);
				Select select = new Select(selectElement);
				//Get a list of the options
				options = select.getOptions();
				// For each option in the list, verify if it's the one you want and then click it
				if(!options.isEmpty())
				{
					for (WebElement option: options)
					{
						if (option.getText().contains(optionToSelect))
						{
							((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
							log.info(getTestCasename()+"Selected " + option + " from " + locator + " dropdown");
							break;
						}
					}
				}
			}
			else
			{
				log.error(getTestCasename()+"Unable to select " + optionToSelect + " from " + locator);
				Assert.fail("Unable to select " + optionToSelect + " from " + locator);
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+"Element with dropdown locator- " + locator + " or option to be selected -'"+optionToSelect+"' webelement is not attached to the page document");
			Assert.fail("Element with dropdown locator- " + locator + " or option to be selected -'"+optionToSelect+"' webelement is not attached to the page document");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+"Element with dropdown locator- " + locator + " or option to be selected -'"+optionToSelect+"' webelement was not found in DOM");
			Assert.fail("Element with dropdown locator- " + locator + " or option to be selected -'"+optionToSelect+"' webelement was not found in DOM");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to select " + optionToSelect + " from " + locator);
			Assert.fail("Unable to select " + optionToSelect + " from " + locator);
		}
	}
	
	public void safeDropdownClick(By locator, String optionToSelect) {
		List<WebElement> options = driver.findElements(locator);
		for (WebElement option: options)
		{
			if (option.getText().contains(optionToSelect))
			{
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
				log.info(getTestCasename()+"Selected " + option + " from " + locator + " dropdown");
				break;
			}
		}
	}


	/**
	 * Method - Defining Selenium locator for working with duplicate elements when only 1 is active at a given time
	 * @param locator locator
	 */
	public WebElement getActivelocatorInSet(By locator,String friendlyWebElementName)
	{
		setImplicitWait(IMPLICITWAIT);
		WebElement activeElem = null;
		int activeElemCount = 0;
		try
		{
			ArrayList<WebElement> elems = (ArrayList<WebElement>)driver.findElements(locator);
			for(int i = 0; i < elems.size(); i++)
			{
				if(elems.get(i).isDisplayed())
				{
					if(++activeElemCount>1)
						log.error(getTestCasename()+"More than 1 active visible locator found on page, expecting only 1");
					else
						activeElem = elems.get(i);
				}
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
			Assert.fail( friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " was not found in DOM - NoSuchElementException");
			Assert.fail(friendlyWebElementName + " was not found in DOM - NoSuchElementException");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to deselect  " + friendlyWebElementName + " - " + e);
			Assert.fail("Unable to deselect " + friendlyWebElementName);
		}
		return activeElem;
	}
	
	
	public void getListWebElementAssertion(By locator,String friendlyWebElementName)
	{
		setImplicitWait(IMPLICITWAIT);
		try
		{
			ArrayList<WebElement> elems = (ArrayList<WebElement>)driver.findElements(locator);
			for(int i = 0; i < elems.size(); i++)
			{
				if(elems.get(i).getText().equalsIgnoreCase(friendlyWebElementName))
				{
					elems.get(i).isDisplayed();
					log.info(getTestCasename()+locator+"contains the "+friendlyWebElementName);
					
				break;
					
				}
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
			Assert.fail( friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " was not found in DOM - NoSuchElementException");
			Assert.fail(friendlyWebElementName + " was not found in DOM - NoSuchElementException");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to deselect  " + friendlyWebElementName + " - " + e);
			Assert.fail("Unable to deselect " + friendlyWebElementName);
		}
		
	}


	/**
	 * Method - Safe Method for User Select option from Drop down by option index, waits until the element is loaded and then selects an option from drop down
	 * @param locator locator
	 * @param iIndexofOptionToSelect dropdown value based on the index
	 * @param optionWaitTime WaitTime
	 */
	public void safeSelectOptionInDropDownByIndexValue(By locator, int iIndexofOptionToSelect, String friendlyWebElementName,int... optionWaitTime)
	{
		int waitTime=0;
		try
		{
			waitTime = getWaitTime(optionWaitTime);
			if(isElementPresent(locator, waitTime))
			{
				scrollIntoElementView(locator,friendlyWebElementName);
				WebElement selectElement = driver.findElement(locator);
				setHighlight(selectElement);
				Select select = new Select(selectElement);
				select.selectByIndex(iIndexofOptionToSelect);
				log.info(getTestCasename()+"Selected dropdown item by index option:" + iIndexofOptionToSelect + " from " + friendlyWebElementName);
			}
			else
			{
				log.error(getTestCasename()+"Unable to select dropdown item by index option:" + iIndexofOptionToSelect + " from " + friendlyWebElementName);
				Assert.fail("Unable to select dropdown item by index option:" + iIndexofOptionToSelect + " from " + friendlyWebElementName);
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+"Dropdown item by index option:" + iIndexofOptionToSelect + " from " + friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
			Assert.fail("Dropdown item by index option:" + iIndexofOptionToSelect + " from " + friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
		}
		catch(NoSuchElementException e)
		{
			log.error(getTestCasename()+"Dropdown item by index option:" + iIndexofOptionToSelect + " from " + friendlyWebElementName + " is not found in DOM in time - "+waitTime+"- NoSuchElementException");
			Assert.fail("Dropdown item by index option:" + iIndexofOptionToSelect + " from " + friendlyWebElementName + " is not found in DOM in time - "+waitTime+"- NoSuchElementException");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to select dropdown item by index option:" + iIndexofOptionToSelect + " from " + friendlyWebElementName +" - " + e);
			Assert.fail("Unable to select dropdown item by index option:" + iIndexofOptionToSelect + " from " + friendlyWebElementName );
		}
	}

	/**
	 * Method - Safe Method for User Select option from Drop down by option value, waits until the element is loaded and then selects an option from drop down
	 * @param locator locator
	 * @param sValuefOptionToSelect Dropdown value based on the option
	 */
	public void safeSelectOptionInDropDownByValue(By locator, String sValuefOptionToSelect, String friendlyWebElementName,int... optionWaitTime)
	{
		int waitTime=0;
		try
		{
			waitTime = getWaitTime(optionWaitTime);
			if(isElementPresent(locator, waitTime))
			{
				scrollIntoElementView(locator,friendlyWebElementName);
				WebElement selectElement = driver.findElement(locator);
				setHighlight(selectElement);
				Select select = new Select(selectElement);
				select.selectByValue(sValuefOptionToSelect);
				log.info(getTestCasename()+"Selected dropdown item by value option:" + sValuefOptionToSelect + " from " + friendlyWebElementName);
			}
			else
			{
				log.error(getTestCasename()+"Unable to select dropdown item by value option:" + sValuefOptionToSelect + " from " + locator);
				Assert.fail("Unable to select dropdown item by value option:" + sValuefOptionToSelect + " from " + locator);
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+"Dropdown item by value option:" + sValuefOptionToSelect + " from " +  friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
			Assert.fail("Dropdown item by value option:" + sValuefOptionToSelect + " from " + friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+"Dropdown item by value option:" + sValuefOptionToSelect + " from " + friendlyWebElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
			Assert.fail("Dropdown item by value option:" + sValuefOptionToSelect + " from " + friendlyWebElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to select dropdown item by value option:" + sValuefOptionToSelect + " from " + friendlyWebElementName + " - " + e);
			Assert.fail("Unable to select dropdown item by value option:" + sValuefOptionToSelect + " from " + friendlyWebElementName );
		}
	}
	/**
	 * Method - Safe Method for User Select option from Drop down by option lable, waits until the element is loaded and then selects an option from drop down
	 * @param locator locator
	 * @param sVisibleTextOptionToSelect Dropdown value based on the visible text
	 * @param optionWaitTime
	 */
	public void SelectOptionInDropDownByVisibleText(By locator, String sVisibleTextOptionToSelect,String friendlyWebElementName, int... optionWaitTime)
	{
		int waitTime=0;
		
		
			waitTime = getWaitTime(optionWaitTime);
			if(isElementPresent(locator, waitTime))
			{
				scrollIntoElementView(locator,friendlyWebElementName);
				WebElement selectElement = driver.findElement(locator);
				setHighlight(selectElement);
				Select select = new Select(selectElement);
				select.selectByVisibleText(sVisibleTextOptionToSelect);
				log.info(getTestCasename()+"Selected dropdown item by visible text option:" + sVisibleTextOptionToSelect + " from " + friendlyWebElementName );
			}
			
		}

	/**
	 * Method - Safe Method for User Select option from Drop down by option lable, waits until the element is loaded and then selects an option from drop down
	 * @param locator locator
	 * @param sVisibleTextOptionToSelect Dropdown value based on the visible text
	 * @param optionWaitTime
	 */
	public void safeSelectOptionInDropDownByVisibleText(By locator, String sVisibleTextOptionToSelect,String friendlyWebElementName, int... optionWaitTime)
	{
		int waitTime=0;
		try
		{
			waitTime = getWaitTime(optionWaitTime);
			if(isElementPresent(locator, waitTime))
			{
				scrollIntoElementView(locator,friendlyWebElementName);
				WebElement selectElement = driver.findElement(locator);
				setHighlight(selectElement);
				Select select = new Select(selectElement);
				select.selectByVisibleText(sVisibleTextOptionToSelect);
				log.info(getTestCasename()+"Selected dropdown item by visible text option:" + sVisibleTextOptionToSelect + " from " + friendlyWebElementName );
			}
			else
			{
				log.error(getTestCasename()+"Unable to select dropdown item by visible text option:" + sVisibleTextOptionToSelect + " from " + friendlyWebElementName);
				Assert.fail("Unable to select dropdown item by visible text option:" + sVisibleTextOptionToSelect + " from " + friendlyWebElementName);
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+"Dropdown item by visible text option:" + sVisibleTextOptionToSelect + " from " + friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
			Assert.fail("Dropdown item by visible text option:" + sVisibleTextOptionToSelect + " from " + friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+"Dropdown item by visible text option:" + sVisibleTextOptionToSelect + " from " + friendlyWebElementName + " was not found in DOM - NoSuchElementException");
			Assert.fail("Dropdown item by visible text option:" + sVisibleTextOptionToSelect + " from " + friendlyWebElementName + " was not found in DOM - NoSuchElementException");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to select dropdown item by visible text option:" + sVisibleTextOptionToSelect + " from " + friendlyWebElementName + " - " + e);
			Assert.fail("Unable to select dropdown item by visible text option:" + sVisibleTextOptionToSelect + " from " + friendlyWebElementName );
		}
	}


	/**
	 * Method - Safe Method for User Select option from list menu, waits until the element is loaded and then selects an option from list menu
	 * @param locator locator
	 * @param sOptionToSelect select option from the list menu
	 * @param optionWaitTime wait time
	 */
	public void safeSelectListBox(By locator, String sOptionToSelect, String friendlyWebElementName,int... optionWaitTime)
	{
		int waitTime=0;
		try
		{
			waitTime =  getWaitTime(optionWaitTime);
			List<WebElement> options = Collections.<WebElement>emptyList();
			if(isElementPresent(locator, waitTime))
			{
				//First, get the WebElement for the select tag
				WebElement selectElement = driver.findElement(locator);
				setHighlight(selectElement);
				//Then instantiate the Select class with that WebElement
				Select select = new Select(selectElement);
				//Get a list of the options
				options = select.getOptions();
				if(!options.isEmpty())
				{
					boolean bExists = false;
					// For each option in the list, verify if it's the one you want and then click it
					for (WebElement option: options)
					{
						if (option.getText().contains(sOptionToSelect))
						{
							option.click();
							log.info(getTestCasename()+"Selected Listbox item: " + sOptionToSelect + " from " + friendlyWebElementName);
							bExists = true;
							break;
						}
					}
					if(!bExists)
					{
						log.error(getTestCasename()+"Unable to select " + sOptionToSelect + " from " + friendlyWebElementName);
						Assert.fail("Unable to select " + sOptionToSelect + " from " + friendlyWebElementName);
					}
				}
			}
			else
			{
				log.error(getTestCasename()+friendlyWebElementName +" is not displayed");
				Assert.fail(friendlyWebElementName +" is not displayed");
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+"Listbox item: " + sOptionToSelect + " from " + friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
			Assert.fail("Listbox item: " + sOptionToSelect + " from " + friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+"Listbox item: " + sOptionToSelect + " from " + friendlyWebElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
			Assert.fail("Listbox item: " + sOptionToSelect + " from " + friendlyWebElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to select " + sOptionToSelect + " from " + friendlyWebElementName +  " - " + e);
			Assert.fail("Unable to select " + sOptionToSelect + " from " + friendlyWebElementName );
		}
	}



	/**
	 * Method - Method to hover on an element based on locator using Actions,it waits until the element is loaded and then hovers on the element
	 * @param locator locator
	 * @param waitTime waittime
	 */
	public void mouseHover(By locator,String friendlyWebElementName,int waitTime)
	{
		try
		{
			if(isElementVisible(locator, waitTime))
			{
				Actions builder = new Actions(driver);
				WebElement HoverElement = driver.findElement(locator);
				builder.moveToElement(HoverElement).build().perform();
				log.info(getTestCasename()+"Hovered on " + friendlyWebElementName);
			}
			else
			{
				log.error(getTestCasename()+"Element was not visible to hover ");
				Assert.fail(friendlyWebElementName+" was not visible to hover ");
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
			Assert.fail(friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " was not found in DOM in time - "+waitTime);
			Assert.fail(friendlyWebElementName + " was not found in DOM in time - "+waitTime);
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to hover the cursor on " + friendlyWebElementName + " - " + e);
			Assert.fail("Unable to hover the cursor on " + friendlyWebElementName );
		}
	}


	/**
	 * Method - Method to hover on an element based on locator using Actions and click on given option,it waits until the element is loaded and then hovers on the element
	 * @param locator locator
	 * @param waitTime waittime
	 */
	public void mouseHoverAndSelectOption(By locator,By byOptionlocator,String friendly_HoverElementName,String friendly_OptionElementName,int waitTime)
	{
		try
		{
			if(isElementPresent(locator, waitTime))
			{
				Actions builder = new Actions(driver);
				WebElement HoverElement = driver.findElement(locator);
				builder.moveToElement(HoverElement).build().perform();
				try {
					builder.wait(4000);
				} catch (InterruptedException e) {
					log.error(getTestCasename()+"Exception occurred while waiting");
				}
				WebElement element = driver.findElement(byOptionlocator);
				element.click();
				log.info(getTestCasename()+"Hovered on "+friendly_HoverElementName+" and selected" + friendly_OptionElementName);
			}
			else
			{
				log.error(getTestCasename()+friendly_HoverElementName+" was not visible to hover ");
				Assert.fail(friendly_HoverElementName+" was not visible to hover ");
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+friendly_HoverElementName + "or"+ friendly_OptionElementName +"is not attached to the page document");
			Assert.fail(friendly_HoverElementName + "or"+ friendly_OptionElementName +"is not attached to the page document");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+friendly_HoverElementName + "or"+ friendly_OptionElementName + " was not found in DOM");
			Assert.fail( friendly_HoverElementName + "or"+ friendly_OptionElementName +" was not found in DOM");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to hover the cursor on " + friendly_HoverElementName + "or unable to select "+ friendly_OptionElementName  + " - " + e);
			Assert.fail("Unable to hover the cursor on " + friendly_HoverElementName + "or unable to select "+ friendly_OptionElementName);
		}
	}

	/**
	 * Method - Method to hover on an element based on locator using JavaScript snippet,it waits until the element is loaded and then hovers on the element
	 * @param locator locator
	 * @param Choice hover element
	 * @param waitTime waittime
	 */
	public void mouseHoverJScript(By locator,String Choice,String friendly_HoverElementName,int waitTime)
	{
		try
		{
			if(isElementPresent(locator, waitTime))
			{
				WebElement HoverElement = driver.findElement(locator);
				String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
				((JavascriptExecutor) driver).executeScript(mouseOverScript, HoverElement);
				// Thread.sleep(4000);
				log.info(getTestCasename()+"Hovered on element " + friendly_HoverElementName + " and selected the choice: " + Choice);
			}
			else
			{
				log.error(getTestCasename()+friendly_HoverElementName+" was not visible to hover ");
				Assert.fail(friendly_HoverElementName+" was not visible to hover ");
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+friendly_HoverElementName + " to be hovered on " + Choice + " is not attached to the page document - StaleElementReferenceException");
			Assert.fail(friendly_HoverElementName + " to be hovered on " + Choice + " is not attached to the page document - StaleElementReferenceException");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+friendly_HoverElementName + " to be hovered on " + Choice + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
			Assert.fail(friendly_HoverElementName + " to be hovered on " + Choice + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to hover on " + Choice + "from" + friendly_HoverElementName + " - " + e);
			Assert.fail("Unable to hover on " + Choice + " from " + friendly_HoverElementName);
		}
	}


	/**
	 * Method - Safe Method for User Click, waits until the element is loaded and then performs a click action
	 * @param locatorToClick locator to click
	 * @param locatorToCheck locartor to check
	 */
	public void safeClick(By locatorToClick,By locatorToCheck, String friendly_ClickElementName,int waitElementToClick,int waitElementToCheck )
	{
		boolean bResult = false;
		int iAttempts = 0;
		nullifyImplicitWait();
		WebDriverWait wait = new WebDriverWait(driver, waitElementToClick);
		WebDriverWait wait2 = new WebDriverWait(driver,waitElementToCheck);
		while(iAttempts < 3)
		{

			try
			{
				wait.until(ExpectedConditions.visibilityOfElementLocated(locatorToClick));
				wait.until(ExpectedConditions.elementToBeClickable(locatorToClick));
				WebElement element = driver.findElement(locatorToClick);

				if(element.isDisplayed())
				{
					setHighlight(element);
					element.click();
					waitForPageToLoad();
					waitForJQueryProcessing(waitElementToCheck);
					wait2.until(ExpectedConditions.visibilityOfElementLocated(locatorToCheck));
					WebElement elementToCheck = driver.findElement(locatorToCheck);
					if(elementToCheck.isDisplayed())
					{
						log.info(getTestCasename()+"Clicked on " + friendly_ClickElementName);
						break;
					}
					else
					{
						Thread.sleep(1000);
						continue;
					}
				}
			}
			catch(Exception e)
			{
				log.info(getTestCasename()+"Attempt: "+iAttempts +"\n Unable to click on" + friendly_ClickElementName + " - " + e);
			}
			iAttempts++;
		}
		if (!bResult)
		{
			Assert.fail("Unable to click on element " + locatorToClick);
		}
	}
	/**
	 * Method - Safe Method for User Click, waits until the element is loaded and then performs a click action
	 * @param locatorToClick locator to click
	 * @param locatorToCheck locartor to check
	 */
	public void safeSClick(By locator, String friendlyWebElementName,int waitTime)
	{
		nullifyImplicitWait();
		WebDriverWait wait = new WebDriverWait(driver,waitTime);

			try
			{
				wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
				wait.until(ExpectedConditions.elementToBeClickable(locator));
				WebElement element = driver.findElement(locator);

				if(element.isDisplayed())
				{
					setHighlight(element);
					element.click();
						log.info(getTestCasename()+"Clicked on " + friendlyWebElementName);
						
					}
			
				else
				{
					log.error(getTestCasename()+friendlyWebElementName + " is not clickable in time - "+waitTime+" Seconds");
					Assert.fail( friendlyWebElementName + " is not clickable in time - "+waitTime+" Seconds");
				}
			}
			catch(StaleElementReferenceException e)
			{
				log.error(getTestCasename()+friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
				Assert.fail(friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
			}
			catch (NoSuchElementException e)
			{
				log.error(getTestCasename()+friendlyWebElementName + " was not found in DOM in time - "+waitTime+" Seconds"+" - NoSuchElementException");
				Assert.fail(friendlyWebElementName + " was not found in DOM in time - "+waitTime+" Seconds"+" - NoSuchElementException");
			}
			catch(Exception e)
			{
				log.error(getTestCasename()+ friendlyWebElementName + " was not clickable" + " - " + e);
				Assert.fail(friendlyWebElementName + " was not found on the web page");
			}
		}
		







	/**
	 * Purpose- Method For performing drag and drop operations
	 * @param Sourcelocator,Destinationlocator source and destination locators
	 * @param waitTime waittime
	 */
	public void dragAndDrop(By Sourcelocator, By Destinationlocator, String friendly_SourceElementName,String friendly_DestinationElementName,int waitTime)
	{
		try
		{
			if(isElementPresent(Sourcelocator, waitTime))
			{
				WebElement source = driver.findElement(Sourcelocator);
				if(isElementPresent(Destinationlocator, waitTime))
				{
					WebElement destination = driver.findElement(Destinationlocator);
					Actions action = new Actions(driver);
					action.dragAndDrop(source, destination).build().perform();
					log.info(getTestCasename()+"Dragged the "+ friendly_SourceElementName + " and dropped in to " + friendly_DestinationElementName);
				}
				else
				{
					log.error(getTestCasename()+"Destination Element "+friendly_DestinationElementName+" was not displayed to drop");
					Assert.fail("Destination Element "+friendly_DestinationElementName+" was not displayed to drop");
				}
			}
			else
			{
				log.error(getTestCasename()+"Source Element "+friendly_SourceElementName+" was not displayed to drag");
				Assert.fail("Source Element "+friendly_SourceElementName+" was not displayed to drag");
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+friendly_SourceElementName + "or"+ friendly_DestinationElementName +"is not attached to the page document - StaleElementReferenceException");
			Assert.fail(friendly_SourceElementName + "or"+ friendly_DestinationElementName +"is not attached to the page document - StaleElementReferenceException");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+friendly_SourceElementName + "or"+ friendly_DestinationElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
			Assert.fail(friendly_SourceElementName + "or"+ friendly_DestinationElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Some exception occurred while performing drag and drop operation" + " - " + e);
			Assert.fail("Some exception occurred while performing drag and drop operation");
		}
	}

	/**
	 *
	 * Purpose- Method For waiting for an alert and acceptng it
	 * @param waitTime wait time
	 * @return returns true if alert is displayed and accepted, else returns false
	 */
	public boolean AlertWaitAndAccepted(int waitTime)
	{
		boolean bAlert = false;
		if(isAlertPresent(waitTime))
		{
			try
			{
				Alert alert =driver.switchTo().alert();
				try
				{
					Thread.sleep(2000);
				}
				catch (InterruptedException exception)
				{
					log.error(getTestCasename()+"Exception occured while waiting for an alert using thread sleep method ");
				}
				alert.accept();
				log.info(getTestCasename()+"Alert is displayed and accepted successfully");
				bAlert = true;
			}
			catch(NoAlertPresentException e)
			{

				bAlert = false;
				log.error(getTestCasename()+"Alert not present");
			}
		}
		else{
			log.error(getTestCasename()+"Alert not present in time - "+waitTime+" - NoAlertPresentException");
			bAlert=false;
		}
		return bAlert;
	}

	/**
	 * Method: for verifying if accept exists and accepting the alert
	 */
	public void acceptAlert()
	{
		try
		{
			Alert alert = driver.switchTo().alert();
			String sText = alert.getText();
			alert.accept();
			log.info(getTestCasename()+"Accepted the alert:"+ sText);
		}
		catch(NoAlertPresentException e)
		{
			log.error(getTestCasename()+"Alert is not displayed to accept." + " - NoAlertPresentException");
			Assert.fail("Alert is not displayed to accept." + " - NoAlertPresentException");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Alert is not displayed to accept." + " - "+e);
			Assert.fail("Unable to accept the alert.");
		}
	}

	/**
	 * Method: for verifying if accept exists and accepting the alert
	 * @return - String (returns text present on the Alert)
	 */
	public String getAlertMessage()
	{
		String sText = null;
		try
		{
			Alert alert = driver.switchTo().alert();
			sText = alert.getText();
			log.info(getTestCasename()+"Text present in the alert:"+ sText);
		}
		catch(NoAlertPresentException e)
		{
			log.error(getTestCasename()+"Alert is not displayed to accept." + " - " + e);
			Assert.fail("Alert is not displayed to accept.");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to read alert message." + " - " + e);
			Assert.fail("Unable to accept the alert.");
		}
		return sText;
	}

	/**
	 * Method: for verifying if accept exists and rejecting/dismissing the alert
	 */
	public void dismissAlert()
	{
		try
		{
			Alert alert = driver.switchTo().alert();
			String sText = alert.getText();
			alert.dismiss();
			log.info(getTestCasename()+"Dismissed the alert:"+ sText);
		}
		catch(NoAlertPresentException e)
		{
			log.error(getTestCasename()+"Alert is not displayed to dismiss.");
			Assert.fail("Alert is not displayed to dismiss.");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to dismiss the alert." + " - " + e);
			Assert.fail("Unable to accept the alert.");
		}

	}


	/**
	 * Purpose - To select the context menu option for the given element
	 * @param locator locator
	 * @param iOptionIndex option to select
	 * @param waitTime wait time
	 */
	public void safeSelectContextMenuOption(By locator, String friendlyWebElementName,int iOptionIndex, int waitTime)
	{
		try
		{
			if(isElementPresent(locator, waitTime))
			{
				selectContextMenuOption(locator, iOptionIndex);
			}
			else
			{
				log.error(getTestCasename()+friendlyWebElementName + "is not displayed to perform content menu operation by index: " + iOptionIndex);
				Assert.fail(friendlyWebElementName + "is not displayed to perform content menu operation by index: " + iOptionIndex);
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+"Context menu option by index: " + iOptionIndex + " for " + friendlyWebElementName + " is not attached to the page document");
			Assert.fail("Context menu option by index: " + iOptionIndex + " for " + friendlyWebElementName + " is not attached to the page document");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+"Context menu option by index: " + iOptionIndex + " for" + friendlyWebElementName + " was not found in DOM");
			Assert.fail("Context menu option by index: " + iOptionIndex + " for  " + friendlyWebElementName + " was not found in DOM");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to select context menu option by index: " + iOptionIndex + " for" + friendlyWebElementName + " - " + e);
			Assert.fail("Unable to select context menu option by index: " + iOptionIndex + " for " + friendlyWebElementName);
		}
	}


	private void selectContextMenuOption(By locator, int iOptionIndex)
	{
		WebElement Element = driver.findElement(locator);
		Actions _action = new Actions(driver);
		for (int count=1; count<=iOptionIndex; count++)
		{
			_action.contextClick(Element).sendKeys(Keys.ARROW_DOWN);
		}
		_action.contextClick(Element).sendKeys(Keys.RETURN).build().perform();
	}



	/**
	 * Method: for uploading file
	 * @return - boolean (returns True when upload is successful else returns false)
	 */
	public boolean uploadFile(By locator, String filePath, String friendlyWebElementName,int... optionWaitTime)
	{
		boolean hasTyped = false;
		try
		{
			int waitTime =  getWaitTime(optionWaitTime);
			if(isElementPresent(locator, waitTime))
			{
				scrollIntoElementView(locator,friendlyWebElementName);
				WebElement element=driver.findElement(locator);
				setHighlight(element);
				element.sendKeys(filePath);
				log.info(getTestCasename()+"Entered - '"+filePath+" into" + friendlyWebElementName);
				hasTyped = true;
			}
			else
			{
				log.error(getTestCasename()+friendlyWebElementName + " is not displayed");
				Assert.fail(friendlyWebElementName + " is not displayed");
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+friendlyWebElementName +"is not attached to the page document");
			Assert.fail(friendlyWebElementName +"is not attached to the page document");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " was not found in DOM");
			Assert.fail(friendlyWebElementName +" was not found in DOM");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to upload file - "+filePath+" in "+friendlyWebElementName + " - " + e);
			Assert.fail("Unable to upload file - "+filePath+" in "+friendlyWebElementName);
		}
		return hasTyped;
	}


	/**
	 * Method: for editing rich text editor which is in frame
	 * @param Framelocator Frame locator
	 * @param TextEditorlocator Text editor locator
	 * @param sText text
	 * @param waitTime wait time
	 * @return - boolean (returns True when editing is successful else return false)
	 */
	public boolean safeEditRichTextEditor(By Framelocator, By TextEditorlocator, String sText, String friendly_frameEleName,String friendly_TextEditEleName,int waitTime)
	{
		try
		{
			editTextEditor(Framelocator, TextEditorlocator, sText, friendly_frameEleName,friendly_TextEditEleName,waitTime);
		    return true;
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+"Element with " + friendly_TextEditEleName +"is not attached to the page document");
			Assert.fail("Element with " + friendly_TextEditEleName +"is not attached to the page document");
			return false;
		}
	    catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+friendly_TextEditEleName + " was not found in DOM");
			Assert.fail(friendly_TextEditEleName +" was not found in DOM");
			return false;
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to edit rich text editor -" + friendly_TextEditEleName  + " - " + e);
			Assert.fail("Unable to edit rich text editor - " + friendly_TextEditEleName);
			return false;
		}
	}


	private void editTextEditor(By frameLocator, By textEditorLocator, String sText,String friendly_frameEleName,String friendly_TextEditEleName, int waitTime)
	{
		if(isElementPresent(frameLocator, waitTime))
		{
			selectFrame(frameLocator,friendly_frameEleName,waitTime);
			if(isElementPresent(textEditorLocator, waitTime))
			{
				WebElement element = driver.findElement(textEditorLocator);
				element.click();
				setHighlight(element);
				element.sendKeys(sText);
				defaultFrame();
			}
			else
			{
				log.error(getTestCasename()+"Rich text editor - "+friendly_TextEditEleName+" is not displayed");
				Assert.fail("Rich text editor - "+friendly_TextEditEleName+" is not displayed");
			}
		}
		else
		{
			log.error(getTestCasename()+"Rich text editor Frame - "+friendly_frameEleName+" is not displayed");
			Assert.fail("Rich text editor Frame - "+friendly_frameEleName+" is not displayed");
		}
	}


	/**
	 * Method to enter text into the text editor which has no frame (Navigated to the text editor by tab sequence)
	 * @param sText text
	 * @param index index value
	 */
	public void safeEditTextBoxByTabSequence(String sText, int index)
	{
		try
		{
			//Copy the text to be entered into text editor to the clip board
			copyTextToClipboard(sText);
			Robot _robot = new Robot();
			//Navigate to the text editor using tab sequence
			for (int count=1; count<=index; count++)
			{
				_robot.keyPress(KeyEvent.VK_TAB);
			}
			//Paste the text(copied to the clip board) on text editor
			pasteCopiedText(_robot);

			log.info(getTestCasename()+"Text " + sText + " is entered into the text editor");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to edit rich text editor." + " - " + e);
			Assert.fail("Unable to edit rich text editor.");
		}
	}

	/**This method to enter a 'String with alpha numeric only' using robots class
	 * and to enter special character need to customize the method
	 *@param caseType case type
	 @param text text needs to be entered
	 @param locator locator
	 @param optionWaitTime  waittime

	 */
	public void safeTypeUsingRobot(By locator,String text,String caseType,String friendly_WebElementName,int... optionWaitTime)
	{
		try{

			int waitTime = getWaitTime(optionWaitTime);
			if(waitUntilClickable(locator,friendly_WebElementName, waitTime))
			{
				Robot robot = new Robot();
				scrollIntoElementView(locator,friendly_WebElementName);
				WebElement element = driver.findElement(locator);
				setHighlight(element);
				element.click();
				log.info(getTestCasename()+"Clicked on  " + friendly_WebElementName);
				String upperCase = text.toUpperCase();
				if (caseType.equalsIgnoreCase("lowercase")) {
					log.info(getTestCasename()+"Typing string in lowercase");
					for (int i = 0; i < upperCase.length(); i++) {
						String letter = Character.toString(upperCase.charAt(i));
						if (letter.contains(" ")) {
							robot.keyPress(KeyEvent.VK_SPACE);
							robot.keyRelease(KeyEvent.VK_SPACE);
						} else if (letter.contains(".")) {
							robot.keyPress(KeyEvent.VK_PERIOD);
							robot.keyRelease(KeyEvent.VK_PERIOD);
						} else if (letter.contains("@")) {
							robot.keyPress(KeyEvent.VK_SHIFT);
							robot.keyPress(KeyEvent.VK_2);
							robot.keyRelease(KeyEvent.VK_SHIFT);
							robot.keyRelease(KeyEvent.VK_2);
						} else {
							String code = "VK_" + letter;
							Field f = KeyEvent.class.getField(code);
							int keyEvent = f.getInt(null);
							robot.keyPress(keyEvent);
							robot.keyRelease(keyEvent);
						}

					}
				}
				if(caseType.equalsIgnoreCase("uppercase"))
				{
					log.info(getTestCasename()+"Typing string in uppercase");
					for(int i = 0; i < upperCase.length(); i++)
					{
						String letter = Character.toString(upperCase.charAt(i));
						String code = "VK_" + letter;
						Field f = KeyEvent.class.getField(code);
						int keyEvent = f.getInt(null);
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(keyEvent);
						robot.keyRelease(KeyEvent.VK_SHIFT);
						robot.keyRelease(keyEvent);
					}
				}
			}
			log.info(getTestCasename()+"text" +text+ "typed in " +locator);
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+"Text " + text + " to be entered in " + friendly_WebElementName + " is not attached to the page document");
			Assert.fail("Text " + text + " to be entered in " + friendly_WebElementName + " is not attached to the page document");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+"Text " + text + " to be entered in " + friendly_WebElementName + " was not found in DOM");
			Assert.fail("Text " + text + " to be entered in "  + friendly_WebElementName + " was not found in DOM");
		}

		catch(AWTException e)
		{
			log.error(getTestCasename()+"Unable to type space \t" + " - " + e);
			Assert.fail("Unable to type text using robot the error  message is\t" + " - " + e);
		}
		catch(NullPointerException e){
			log.error(getTestCasename()+"Text provided as null...Please provide text");
			Assert.fail("Text provided as null...Please provide text");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to type space " + " - "  + " - " + e);
			Assert.fail("Unable to type text using robot the error  message is\t" + " - " + e);
		}
	}


	/**
	 * Method to paste the text using key strokes
	 * @param robot robot reference
	 */
	private void pasteCopiedText(Robot robot)
	{
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}


	/**
	 * Method to copy the given text to clip board
	 * @param sText text to copy clipboard
	 */
	private void copyTextToClipboard(String sText)
	{
		StringSelection stringSelection = new StringSelection(sText);
		Clipboard _clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
		_clpbrd.setContents(stringSelection, null);
	}

	/**
	 * Method: for uploading file by using Robot class
	 */
	public void uploadFileRobot(By slocator, String sFileLocation,String friendly_WebElementName, int waitTime)
	{
		try
		{
			if(isElementPresent(slocator, waitTime))
			{
				copyTextToClipboard(sFileLocation);
				scrollIntoElementView(slocator,friendly_WebElementName);
				if(isElementClickable(slocator, waitTime))
				{
					Thread.sleep(2000);
					Actions builder = new Actions(driver);

					Action myAction = builder.click(driver.findElement(slocator))
							.release()
							.build();

					myAction.perform();
					Thread.sleep(15000);
					Robot robot = new Robot();
					pasteCopiedText(robot);
					robot.keyPress(KeyEvent.VK_ENTER);
					robot.keyRelease(KeyEvent.VK_ENTER);
					robot.keyPress(KeyEvent.VK_ENTER);
					robot.keyRelease(KeyEvent.VK_ENTER);
					Thread.sleep(5000);
					log.info(getTestCasename()+"File at location " + sFileLocation + " is Uploaded into " + friendly_WebElementName + " using Robot Functionality");
				}
				else
				{
					log.error(getTestCasename()+"Unable to click on " + friendly_WebElementName+" for uploading a file");
					Assert.fail("Unable to click on " + friendly_WebElementName+" for uploading a file");
				}
			}
			else
			{
				log.error(getTestCasename()+"Unable to upload file - "+sFileLocation+" into - "+friendly_WebElementName);
				Assert.fail("Unable to upload file - "+sFileLocation+" into - "+friendly_WebElementName);
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+friendly_WebElementName +"is not attached to the page document");
			Assert.fail(friendly_WebElementName +"is not attached to the page document");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+friendly_WebElementName + " was not found in DOM");
			Assert.fail(friendly_WebElementName +" was not found in DOM");
		}
		catch (Exception e)
		{
			log.error(getTestCasename()+"Unable to upload file - "+sFileLocation+" into - "+friendly_WebElementName + " - " + e);
			Assert.fail("Unable to upload file - "+sFileLocation+" into - "+friendly_WebElementName);
		}

	}

	/**
	 *
	 * TODO JavaScript method for clicking on an element
	 *
	 * @param locator - locator value by which element is recognized
	 * @param waitTime - Time to wait for an element
	 */
	public void safeJavaScriptClick(By locator, String friendlyWebElementName,int waitTime)
	{
		try
		{
			if(isElementPresent(locator, waitTime))
			{
			    //setImplicitWait(waitTime);
				log.info(getTestCasename()+"Clicking on element with " + locator+ " using java script click");
				//waitUntilClickable(locator,waitTime);
				scrollIntoElementView(locator,friendlyWebElementName);
				WebElement element = driver.findElement(locator);
				setHighlight(element);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			//	nullifyImplicitWait();
				log.info(getTestCasename()+"Clicked on " + friendlyWebElementName);
			}
			else
			{
				log.error(getTestCasename()+"Unable to click on " + friendlyWebElementName );
				Assert.fail("Unable to click on " + friendlyWebElementName );
			}
		}
		catch(StaleElementReferenceException e)
		{
			nullifyImplicitWait();
			log.error(getTestCasename()+friendlyWebElementName +"is not attached to the page document - StaleElementReferenceException");
			Assert.fail(friendlyWebElementName +"is not attached to the page document - StaleElementReferenceException");
		}
		catch (NoSuchElementException e)
		{
			nullifyImplicitWait();
			log.error(getTestCasename()+friendlyWebElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
			Assert.fail(friendlyWebElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
		}
		catch (Exception e)
		{
			nullifyImplicitWait();
			log.error(getTestCasename()+ friendlyWebElementName + " was not found in in the webpage - "+e);
			Assert.fail(friendlyWebElementName + " was not found in in the webpage");
		}
	}

	/**
	 *
	 * TODO JavaScript method for entering a text in a field
	 *
	 * @param locator - locator value by which text field is recognized
	 * @param sText - Text to be entered in a field
	 * @param waitTime - Time to wait for an element
	 */
	public void safeJavaScriptType(By locator,String sText, String friendlyWebElementName,int waitTime)
	{
		try
		{
			if(isElementPresent(locator, waitTime))
			{
				scrollIntoElementView(locator,friendlyWebElementName);
				WebElement element = driver.findElement(locator);
				setHighlight(element);
				((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', '"+sText+"');",element);
				log.info(getTestCasename()+"Entered text " + sText + " into " + friendlyWebElementName);
			}
			else
			{
				log.error(getTestCasename()+"Unable to enter " + sText + " into " + friendlyWebElementName );
				Assert.fail("Unable to enter " + sText + "into "+ friendlyWebElementName );
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+"Text " + sText + " to be entered into " + friendlyWebElementName +" is not attached to the page document - StaleElementReferenceException");
			Assert.fail("Text " + sText + " to be entered into" + friendlyWebElementName +" is not attached to the page document - StaleElementReferenceException");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+"Text " + sText + " to be entered into " + friendlyWebElementName + " was not found in DOM in time - NoSuchElementException");
			Assert.fail("Text " + sText + " to be entered into " + friendlyWebElementName + " was not found in DOM in time - NoSuchElementException");
		}
		catch (Exception e)
		{
			log.error(getTestCasename()+"Unable to enter '" + sText + "' text into -"+ friendlyWebElementName + " - " + e);
			Assert.fail("Unable to enter " + sText + " text into " + friendlyWebElementName);
		}
	}
	public void safeJavaScriptClearAndType(By locator, String sText, String sElementName, int optionWaitTime) {
		try {
			if (isElementPresent(locator,  optionWaitTime)) {
				setHighlight(getWebElement(locator));
				((JavascriptExecutor) driver).executeScript("arguments[0].value=arguments[1];", getWebElement(locator), sText);
				log.info(getTestCasename() + "- Cleared the field and entered -** " + sText + " ** in the element - "
						+ sElementName);
			} else {
				log.error(getTestCasename() + "- Unable to clear and enter " + sText + " in field " + sElementName);
				Assert.fail("Unable to clear and enter " + sText + " in field " + sElementName);
			}
		} catch (StaleElementReferenceException e) {
			log.error(getTestCasename() + "- Element for " + sElementName + " is not attached to the page document" );
			Assert.fail("Element for " + sElementName + " is not attached to the page document");
		} catch (NoSuchElementException e) {
			log.error(getTestCasename() + "- Element for " + sElementName + " was not found in DOM");
			Assert.fail("Element for " + sElementName + " was not found in DOM");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(getTestCasename() + "- Unable to clear and enter '" + sText + "' text in field with element -"
					+ sElementName);
			Assert.fail("Unable to clear and enter '" + sText + "' text in field with element -" + sElementName);
		}
	}

	/**
	 *
	 * TODO Safe method to get the attribute value
	 *
	 * @param locator - locator value by which an element is located
	 * @param sAttributeValue - attribute type
	 * @param waitTime - Time to wait for an element
	 * @return - returns the attribute value of type string
	 */
	public String safeGetAttribute(By locator,String sAttributeValue,String friendlyWebElementName, int waitTime)
	{
		String sValue = null;
		try
		{
			if(isElementPresent(locator, waitTime))
			{
				sValue = driver.findElement(locator).getAttribute(sAttributeValue);
				log.info(getTestCasename()+"Got attribute value of the type " + sAttributeValue + " is: " + sValue);
			}
			else
			{
				log.error(getTestCasename()+"Unable to find locator "+friendlyWebElementName+" in time - "+waitTime);
				Assert.fail("Unable to find locator "+friendlyWebElementName+" in time - "+waitTime);
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+friendlyWebElementName +"is not attached to the page document - StaleElementReferenceException");
			Assert.fail(friendlyWebElementName +"is not attached to the page document - StaleElementReferenceException");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
			Assert.fail(friendlyWebElementName +" was not found in DOMin time - "+waitTime+" - NoSuchElementException");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to get attribute value of type " + sAttributeValue + " from "+ friendlyWebElementName + " - " + e);
			Assert.fail(friendlyWebElementName + " was not found in DOM");
		}
		return sValue;
	}

	/**
	 *
	 * TODO Safe method to get text from an element
	 *
	 * @param locator - locator value by which an element is located
	 * @param waitTime - Time to wait for an element
	 * @return - returns the text value from element
	 */
	public String safeGetText(By locator,String friendlyWebElementName,int waitTime)
	{
		String sValue =null;
		try
		{
			if(isElementPresent(locator, waitTime))
			{
				sValue = driver.findElement(locator).getText();
			}
			else
			{
				Assert.fail("Unable to find "+ friendlyWebElementName+" in time - "+waitTime);
			}

		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+friendlyWebElementName +" is not attached to the page document - StaleElementReferenceException");
			Assert.fail(friendlyWebElementName +" is not attached to the page document - StaleElementReferenceException");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
			Assert.fail(friendlyWebElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to get the text from "+ friendlyWebElementName + " - " + e);
			Assert.fail("Unable to find "+ friendlyWebElementName);
		}
		return sValue;
	}

	/**
	 *
	 * TODO scroll method to scroll the page down until expected element is visible	 *
	 * @param locator - locator value by which an element is located
	 */
	public void scrollIntoElementView(By locator,String friendlyWebElementName)
	{
		try
		{
			//			WebElement element = ;
			((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",driver.findElement(locator));
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+friendlyWebElementName +" is not attached to the page document");
			Assert.fail(friendlyWebElementName +" is not attached to the page document");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " was not found in DOM");
			Assert.fail(friendlyWebElementName +" was not found in DOM");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to scroll the page to find "+ friendlyWebElementName + " - " + e);
			Assert.fail("Unable to scroll the page to find "+ friendlyWebElementName);
		}
	}

	/**
	 *
	 * TODO JavaScript Safe method to get the attribute value
	 *
	 * @param locator - locator value by which an element is located
	 * @param sAttributeValue - attribute type
	 * @param waitTime - Time to wait for an element
	 * @return - returns the attribute value of type string
	 */
	public String safeJavaScriptGetAttribute(By locator,String sAttributeValue,String friendlyWebElementName,int waitTime)
	{
		String sValue ="";
		try
		{
			if(isElementPresent(locator, waitTime))
			{
				final String scriptGetValue = "return arguments[0].getAttribute('"+sAttributeValue+"')";
				WebElement element = driver.findElement(locator);
				sValue = (String)((JavascriptExecutor) driver).executeScript(scriptGetValue,element);
				log.info(getTestCasename()+"Got attribute value of the type " + sAttributeValue + " is: " + sValue);
			}
			else
			{
				log.error(getTestCasename()+"Unable to find "+friendlyWebElementName);
				Assert.fail("Unable to find "+friendlyWebElementName);
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+"To get attribute value of type " + sAttributeValue + " from " + friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
			Assert.fail("To get attribute value of type " + sAttributeValue + " from " + friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+"To get attribute value of type " + sAttributeValue + " from " + friendlyWebElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
			Assert.fail("To get attribute value of type " + sAttributeValue + " from " + friendlyWebElementName + " was not found in DOM in time - "+waitTime+" - NoSuchElementException");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to get attribute value of type " + sAttributeValue + " from "+ friendlyWebElementName);
			Assert.fail("Unable to find "+friendlyWebElementName+"to get attribute " + sAttributeValue +" - " + e);
		}
		return sValue;
	}

	/**
	 *
	 * TODO Safe method to retrieve the option selected in the drop down list
	 *
	 * @param locator - locator value by which an element is located
	 * @param sWaitTime - Time to wait for an element
	 * @return - returns the option selected in the drop down list
	 */
	public String safeGetSelectedOptionInDropDown(By locator, String friendlyWebElementName,int sWaitTime)
	{
		String dropDownSelectedValue = null;

		try
		{
			//return getSelectedOptionInDropDown(locator, sWaitTime);
			if(isElementPresent(locator, sWaitTime))
			{
				Select dropDownName = new Select(driver.findElement(locator));
				//setHighlight(driver, dropDownName);
				dropDownSelectedValue = dropDownName.getFirstSelectedOption().getText();
				log.info(getTestCasename()+"Value " +  dropDownSelectedValue + " has been selected from " + friendlyWebElementName);
			}
			else
			{
				log.error(getTestCasename()+friendlyWebElementName+" is not displayed");
				Assert.fail(friendlyWebElementName+" is not displayed");
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+friendlyWebElementName +"is not attached to the page document");
			Assert.fail(friendlyWebElementName +"is not attached to the page document");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " was not found in DOM");
			Assert.fail(friendlyWebElementName +" was not found in DOM");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to retrieve value:"+friendlyWebElementName + " - " + e);
			Assert.fail("Unable to retrieve value:"+friendlyWebElementName);
		}
		return dropDownSelectedValue;
	}

	/**
	 *
	 * TODO Safe method to verify whether the element is exists in the list box or not
	 *
	 * @param locator - locator value by which an element is located
	 * @param sWaitTime - Time to wait for an element
	 * @return - returns 'true' if the mentioned value exists in the list box else returns 'false'
	 *
	 */
	public boolean safeVerifyListBoxValue(By locator, String value, String friendlyWebElementName,int sWaitTime)
	{
		boolean isExpected = false;
		try
		{
			if(isElementPresent(locator, sWaitTime))
			{
				WebElement listBox = driver.findElement(locator);
				java.util.List<WebElement> listBoxItems = listBox.findElements(By.tagName("li"));
				for(WebElement item : listBoxItems)
				{
					if(item.getText().equals(value))
						isExpected = true;
				}
				log.info(getTestCasename()+"Listbox item: " + value + " is existed in "+  friendlyWebElementName);
			}
			else
			{
				log.error(getTestCasename()+friendlyWebElementName+" is not displayed");
				Assert.fail(friendlyWebElementName+" is not displayed");
			}

		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+"Listbox item: " + value + " in " +  friendlyWebElementName + " is not attached to the page document");
			Assert.fail("Listbox item: " + value + " in " +  friendlyWebElementName + " is not attached to the page document");

		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+"Listbox item: " + value + " in " +  friendlyWebElementName + " was not found in DOM");
			Assert.fail("Listbox item: " + value + " in " +  friendlyWebElementName + " was not found in DOM");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to verify Listbox value: " + value + " in " + friendlyWebElementName + " - " + e);
			Assert.fail("Unable to verify Listbox value: " + value + " in " + friendlyWebElementName);
		}
		return isExpected;
	}

	/**
	 * Method for switching to frame using frame index No
	 *
	 * @param sFrameIndex
	 */
	public void selectFrame(int sFrameIndex)
	{
		try
		{
			log.info("waiting for frame with index " + sFrameIndex);
			driver.switchTo().frame(sFrameIndex);
			log.info("Successfully switched to frame with index " + sFrameIndex);
		}
		catch (NoSuchFrameException e)
		{
			log.error(getTestCasename() + " -Unable to locate frame with index " + sFrameIndex);
			Assert.fail("Unable to locate frame with index " + sFrameIndex);
		}
		catch (Exception e)
		{
			log.error(getTestCasename()+  " -Unable to locate to frame with index " + sFrameIndex);
			Assert.fail("Unable to locate frame with index " + sFrameIndex);
		}
	}

	/**
	 * Method for switching to frame using frame id
	 * @param frame frame id
	 */
	public void selectFrame(String frame)
	{
		try
		{
			driver.switchTo().frame(frame);
			log.info(getTestCasename()+"Navigated to frame with id " + frame);
		}
		catch(NoSuchFrameException e)
		{
			log.error(getTestCasename()+"Unable to locate frame with id " + frame);
			Assert.fail("Unable to locate frame with id " + frame );
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to navigate to frame with id " + frame + " - " + e);
			Assert.fail("Unable to navigate to frame with id " + frame );
		}
	}


	/**
	 * Method - Method for switching to frame in a frame
	 * @param ParentFrame parent frame id
	 * @param ChildFrame child frame id
	 */
	public void selectFrame(String ParentFrame, String ChildFrame)
	{
		try
		{
			driver.switchTo().frame(ParentFrame).switchTo().frame(ChildFrame);
			log.info(getTestCasename()+"Navigated to innerframe with id " + ChildFrame + "which is present on frame with id" + ParentFrame);
		}
		catch(NoSuchFrameException e)
		{
			log.error(getTestCasename()+"Unable to locate frame with id " + ParentFrame+" or "+ ChildFrame);
			Assert.fail("Unable to locate frame with id " + ParentFrame+" or "+ ChildFrame);
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to navigate to innerframe with id " + ChildFrame + "which is present on frame with id" + ParentFrame + " - " + e);
			Assert.fail("Unable to navigate to innerframe with id " + ChildFrame + "which is present on frame with id" + ParentFrame );
		}
	}


	/**
	 * Method - Method for switching to frame using any locator of the frame
	 * @param Framelocator frame locator
	 * @param friendlyWebElementName webelement name
	 * @param waitTime optional wait time
	 */
	public void selectFrame(By Framelocator, String friendlyWebElementName,int waitTime)
	{
		try
		{
			if(isElementPresent(Framelocator,waitTime))
			{
				WebElement Frame = driver.findElement(Framelocator);
				driver.switchTo().frame(Frame);
				log.info(getTestCasename()+"Navigated to frame - " + friendlyWebElementName);
			}
			else
			{
				log.error(getTestCasename()+"Unable to navigate to frame - " + friendlyWebElementName );
				Assert.fail("Unable to navigate to frame - " + friendlyWebElementName );
			}
		}
		catch(NoSuchFrameException e)
		{
			log.error(getTestCasename()+"Unable to locate frame -  " + friendlyWebElementName);
			Assert.fail("Unable to locate frame - " + friendlyWebElementName);
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to navigate to frame - " + friendlyWebElementName + " - " + e);
			Assert.fail("Unable to navigate to frame -  " + friendlyWebElementName);
		}
	}


	/**
	 * Method - Method for switching back to webpage from frame
	 */
	public void defaultFrame()
	{
		try
		{
			driver.switchTo().defaultContent();
			log.info(getTestCasename()+"Navigated to back to webpage from frame");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"unable to navigate back to main webpage from frame" + " - " + e);
			Assert.fail("unable to navigate back to main webpage from frame");
		}
	}


	/**
	 * Method: Gets a UI element attribute value and compares with expected value
	 * @param locator Locator value
	 * @param attributeName Attribute Name
	 * @param expected expected value
	 * @param waitTime optional wait time
	 * @return - Boolean (true if matches)
	 */
	public boolean getAttributeValue(By locator, String attributeName, String expected, boolean contains,String friendlyWebElementName, int... waitTime)
	{
		boolean bvalue = false;
		try
		{
			if(isElementPresent(locator, getWaitTime(waitTime))){
				String sTemp=driver.findElement(locator).getAttribute(attributeName);
				if(!(sTemp.contains(expected)^contains)){
					bvalue = true;
					log.info(getTestCasename()+"Got attribute value " + sTemp + " of type " + attributeName + " from the element "+ friendlyWebElementName + " and it is matched with expected " + expected);
				}
			}
			else{
				log.error(getTestCasename()+friendlyWebElementName + " was not found in DOM");
				Assert.fail(friendlyWebElementName +" was not found in DOM");
			}

		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+friendlyWebElementName +"is not attached to the page document");
			Assert.fail(friendlyWebElementName +"is not attached to the page document");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+friendlyWebElementName + " was not found in DOM");
			Assert.fail(friendlyWebElementName +" was not found in DOM");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to get attribute value of type " + attributeName + " from  "+ friendlyWebElementName + " - " + e);
			Assert.fail("Unable to get attribute value of type " + attributeName + " from "+ friendlyWebElementName);
		}
		return bvalue;
	}


	/**
	 * Method: Gets an UI element attribute value
	 * @param locator locator
	 * @param attributeName  name of the attribute
	 * @param waitTime optional wait time
	 * @return - String (Attribute Value)
	 */
	public String getAttributeValue(By locator, String attributeName, String friendlyWebElementName,int... waitTime)
	{
		String sValue=null;
		try
		{
			if(isElementPresent(locator, getWaitTime(waitTime))){
				sValue=driver.findElement(locator).getAttribute(attributeName);
				log.info(getTestCasename()+"Got attribute value of the type " + attributeName + " is: " + sValue);
			}
			else{
				log.error(getTestCasename()+friendlyWebElementName + " was not found in DOM");
				Assert.fail(friendlyWebElementName +" was not found in DOM");
			}

		}
		catch(StaleElementReferenceException e)
		{	log.error(getTestCasename()+"To get attribute value of type " + attributeName + " from  " + friendlyWebElementName + " is not attached to the page document");
		Assert.fail("To get attribute value of type " + attributeName + " from " + friendlyWebElementName + " is not attached to the page document");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+"To get attribute value of type " + attributeName + " from " + friendlyWebElementName + " was not found in DOM");
			Assert.fail("To get attribute value of type " + attributeName + " from " + friendlyWebElementName + " was not found in DOM");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to get attribute value of type " + attributeName + " from "+ friendlyWebElementName + " - " + e);
			Assert.fail("Unable to get attribute value of type " + attributeName + " from "+ friendlyWebElementName);
		}
		return sValue;
	}


	/**
	@Method Highlights on current working element or locator
	@param element web element
	 */
	public void setHighlight(WebElement element)
	{
		if(sys.getProperty("HighlightElements").equalsIgnoreCase("true"))
		{
			String attributevalue = "border:2px solid red;";
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			String getattrib = element.getAttribute("style");
			executor.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, attributevalue);
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				log.error(getTestCasename()+"Sleep interrupted - ");
			}
			executor.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, getattrib);
		}
	}

	public void setVerificationHighlight(By locator)
	{
		try
		{
			if(sys.getProperty("HighlightElements").equalsIgnoreCase("true"))
			{
				String attributevalue = "border:2px solid limegreen;";
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].setAttribute('style', arguments[1]);", driver.findElement(locator), attributevalue);
			}
		} catch (Exception e)
		{
			log.error(getTestCasename() + "- Element could not be highlighted");
		}
	}

	public void jsClickOnElement(String cssSelector)
	{
		try
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("var x = $(\'"+cssSelector+"\');");
			stringBuilder.append("x.click();");
			js.executeScript(stringBuilder.toString());
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"some exception occured while trying to click on locator with css:"+cssSelector+" using Jsclick" + " - " + e);
			Assert.fail("some exception occured while trying to click on locator with css:"+cssSelector+" using Jsclick");
		}
	}
	/**
	 *
	 * Method: To move to certain locator using sikuli
	 *
	 * @param sImagePath
	 * @param sLocatorName
	 * @param waitTime, time in seconds
	 * @Example safeMoveMouseUsingSikuli(ChooseFilesImagePath,"Choose Files Text",SHORTWAIT);
	 */
	/*public void safeMoveMouseUsingSikuli(String sImagePath, String sLocatorName,int waitTime)
	{
		try
		{
			Screen screen = new Screen();
			Settings.MinSimilarity = 0.87;
			screen.wait(sImagePath,waitTime);
			screen.mouseMove(sImagePath);
		}
		catch(FindFailed e)
		{
			log.error(getTestCasename()+"Unable to move to '"+sLocatorName+"' using sikuli, please check screenshot and image path"+"\n" + " - " + e.getMessage());
			Assert.fail("Unable to move to '"+sLocatorName+"' via mouse using sikuli, please check screenshot and image path");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to move to '"+sLocatorName+"' using sikuli, please check screenshot and image path"+"\n" + " - " + e.getMessage());
			Assert.fail("Unable to move to '"+sLocatorName+"' using sikuli, please check screenshot and image path");
		}
	}*/

	/**
	 *
	 * This method is used to paste the text in required field using sikuli
	 *
	 * @param sImagePath
	 * @param sText
	 * @param sLocatorName
	 * @param waitTime, time in seconds
	 */
	/*public void safePasteTextInFieldUsingSikuli(String sImagePath,String sText,String sLocatorName,int waitTime)
	{
		try
		{
			Screen screen = new Screen();
			Settings.MinSimilarity = 0.87;
			screen.wait(sImagePath,waitTime);
			screen.click(sImagePath);
			Thread.sleep(1000);
			copyTextToClipboard(sText);
			Thread.sleep(1000);
			screen.type("v", KeyModifier.CTRL);
			Thread.sleep(2000);
			log.info(getTestCasename()+"Pasted the text "+sText+" in '"+sLocatorName+"' field using sikuli");
		}
		catch(FindFailed e)
		{
			log.error(getTestCasename()+"Unable to enter/paste "+sText+" text in '"+sLocatorName+"' field using sikuli, please check screenshot and image path"+"\n" + " - " + e.getMessage());
			Assert.fail("Unable to enter/paste "+sText+" text in '"+sLocatorName+"' field using sikuli, please check screenshot and image path");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to enter/paste "+sText+" text in '"+sLocatorName+"' field using sikuli, please check screenshot and image path"+"\n" + " - " + e.getMessage());
			Assert.fail("Unable to enter/paste "+sText+" text in '"+sLocatorName+"' field using sikuli, please check screenshot and image path");
		}
	}*/

	/**
	 *
	 * This method is used to switch to windows based on provided number.
	 *
	 * @param num , Window number starting at 0
	 */
	public void switchToWindow(int num)
	{
		try
		{
			int numWindow = driver.getWindowHandles().size();
			String[] window = (String[])driver.getWindowHandles().toArray(new String[numWindow]);
			driver.switchTo().window(window[num]);
			log.info(getTestCasename()+"Navigated succesfsully to window with sepcified number: "+num);
		}
		catch(NoSuchWindowException e)
		{
			log.error(getTestCasename()+"Window with sepcified number "+num+" doesn't exists. Please check the window number or wait until the new window appears");
			Assert.fail("Window with sepcified number  "+num+"doesn't exists. Please check the window number or wait until the new window appears");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Some exception occured while switching to new window with number: "+num + " - " + e);
			Assert.fail("Some exception occured while switching to new window with number: "+num);
		}
	}

	/**
	 *
	 * This method is used to switch to windows based on provided window title
	 * @param title, Window title to which
	 * @param, waitTime optional wait time
	 *
	 **/

	public void switchToWindow(String title) {
		try {
			int numWindows = driver.getWindowHandles().size();
			int i = 0;
			while (numWindows < 2) {
				driver.wait(500);
				if (++i > 21) {
					break;
				}
			}
			if (numWindows >= 2) {

				boolean bFlag=false;
				for (String handle : driver.getWindowHandles()) {
					driver.switchTo().window(handle);
					if (driver.getTitle().contains(title)) {
						log.info(getTestCasename()+"Navigated succesfsully to new windowwith title "+title);
						bFlag = true;
						break;
					}
				}
				if(!bFlag){
					log.error(getTestCasename()+"Window with sepcified titlt "+title+" doesn't exists. Please check the window title or wait until the new window appears");
					Assert.fail("Window with sepcified number  "+title+"doesn't exists. Please check the window title or wait until the new window appears");
				}
			}
			else{
				log.info(getTestCasename()+"Can not switch to new window as there is only one window, wait until the new window appears");
			}
		}
		catch(NoSuchWindowException e)
		{
			log.error(getTestCasename()+"Can not switch to new window ");
			Assert.fail("Can not switch to new window ");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Some exception occured while switching to new window with number: " + e);
			Assert.fail("Some exception occured while switching to new window with number: ");
		}
	}

	/**
	 *
	 * This method is used to switch to windows based on provided unique element locater
	 * @param locator unique element locater
	 * @param, waitTime wait time for webelement
	 *
	 **/

	public void switchToWindow(By locator,String friendlyWebElementName,int...waitTime) {
		try {
			int numWindows = driver.getWindowHandles().size();
			int i = 0;
			while (numWindows < 2) {
				driver.wait(500);
				if (++i > 21) {
					break;
				}
			}
			if (numWindows >= 2) {
				boolean bFlag = false;
				for (String handle : driver.getWindowHandles()) {
					driver.switchTo().window(handle);
					if (isElementPresent(locator, getWaitTime(waitTime))) {
						log.info(getTestCasename()+"Navigated succesfsully to new window");
						bFlag = true;
						break;
					}
				}
				if (!bFlag) {
					log.error(getTestCasename()+"Window with " + friendlyWebElementName + " doesn't exists. Please check the locator or wait until the new window appears" );
					Assert.fail("Window with " + friendlyWebElementName + "doesn't exists. Please check the locator or wait until the new window appears" );
				}
			}
			else{
				log.info(getTestCasename()+"Can not switch to new window as there is only one window, wait until the new window appears");
			}
		}
		catch(NoSuchWindowException e)
		{
			log.error(getTestCasename()+"Can not switch to new window ");
			Assert.fail("Can not switch to new window ");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Some exception occured while switching to new window with number: " + e);
			Assert.fail("Some exception occured while switching to new window with number: ");
		}
	}

	/**
	 *
	 * This method is used to refresh the web page
	 *
	 */
	public void refresh()
	{
		try
		{
			driver.navigate().refresh();
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Some exception occured while refreshing the page, exception message: " + e);
			Assert.fail("Some exception occured while refreshing the page,");
		}
	}

	/**
	 *
	 * This method is used to navigate to back page
	 *
	 */
	public void navigateToBackPage()
	{
		try
		{
			driver.navigate().back();
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Some exception occured while navigating to back page, exception message: " + e);
			Assert.fail("Some exception occured while navigating to back page");
		}
	}

	/**
	 *
	 * This method is used to perform web page forward navigation
	 *
	 */
	public void navigateToForwardPage()
	{
		try
		{
			driver.navigate().forward();
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Some exception occured while forwarding to a page, exception message: " + e);
			Assert.fail("Some exception occured while forwarding to a page");
		}
	}

	/**
	 *
	 * This method is used to retrieve current url
	 *
	 * @return, returns current url
	 */
	public String getCurrentURL()
	{
		String sUrl = null;
		try
		{
			sUrl = driver.getCurrentUrl();
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Some exception occured while retriving current url, exception message: " + e);
			Assert.fail("Some exception occured while retriving current url");
		}

		return sUrl;
	}

	/**
	 *
	 * This method is used to retrieve current web page title
	 *
	 * @return , returns current web page title
	 */
	public String getTitle()
	{
		String sTitle = null;
		try
		{
			sTitle = driver.getTitle();
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Some exception occured while retriving title of the web page, exception message: " + e);
			Assert.fail("Some exception occured while retriving title of the web page");
		}
		return sTitle;
	}

	/**
	 * This method is used to Delete all cookies
	 */
	public void deleteAllCookies()
	{
		try
		{
			driver.manage().deleteAllCookies();
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Some exception occured while deleting all cookies, exception message: " + e);
			Assert.fail("Some exception occured while deleting all cookies");
		}
	}

	/**
	 *
	 * This method is used to retrieve page source of the web page
	 *
	 * @return , returns page source of the web page
	 */
	public String getPageSource()
	{
		String sPageSource = null;
		try
		{
			sPageSource = driver.getPageSource();
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Some exception occured while retriving page source, exception message: " + e);
			Assert.fail("Some exception occured while retriving page source");
		}
		return sPageSource;
	}

	/**
	 *
	 * This method is used to return number of locators found
	 *
	 * @param Locator locator
	 * @return , returns number of locators
	 */
	public int getLocatorCount(By Locator,String friendlyWebElementName)
	{
		int iCount = 0;
		try
		{
			iCount=driver.findElements(Locator).size();
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Some exception occured while retriving Locator count from "+friendlyWebElementName+" exception message: " + e);
			Assert.fail("Some exception occured while retriving Locator count from "+friendlyWebElementName);
		}
		return iCount;
	}

	/**
	 *
	 * This method is used to return list of WebElements matched by locator
	 *
	 * @param Locator locator
	 * @return list of WebElements matched by locator
	 */
	public List<WebElement> LocatorWebElements(By Locator,String friendlyWebElementName)
	{
		List<WebElement> list = null;
		try
		{
			list= driver.findElements(Locator);
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Some exception occured while retriving web elements from "+friendlyWebElementName+" exception message: " + e);
			Assert.fail("Some exception occured while retriving web elements from "+friendlyWebElementName);
		}
		return list;
	}

	/**
	 * This method waits for Specified URL to load and writes page load time to load URL to 'PageLoadTime_Summary.html' file.
	 * @param url - Need to pass the URL to be loaded
	 */
	public void navigateToURLandRetrivePageLoadTime(String url, int timeOutInSeconds) {
		long start = System.currentTimeMillis();
		driver.get(url);
		waitForPageToLoad(timeOutInSeconds);
		long finish = System.currentTimeMillis();
		long totalTime = finish - start;
		ReportSetup.Report_PageLoadTime(url, totalTime);
	}

	/**
	 * @param url navigation url value
	 * @param friendlyWebElementName webelement name
	 */
	public void safeNavigateTo(String url,String friendlyWebElementName) {

		try
		{
		driver.navigate().to(url);
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Some exception occured while navigating to "+friendlyWebElementName+" exception message: " + e);
			Assert.fail("Some exception occured while navigating to "+friendlyWebElementName);
		}
	}

	/**
	 * This method is used to return working locator based on the locators which we have passed
	 * @param iWaitTime , Need to pass the time to wait for each locator
	 * @param Locators, Need to pass the list of locators to be verified
	 * @return , returns correct working locator
	 */
	public By selectWorkingLocator(int iWaitTime, By... Locators)
	{
		By WorkingLocator = null;
		boolean bValue = false;
		for(By Locator: Locators)
		{
			if(isElementPresent(Locator, iWaitTime))
			{
				WorkingLocator = Locator;
				bValue = true;
				break;
			}
		}
		if(!bValue)
		{
			log.error(getTestCasename()+"None of the locators are visible");
			Assert.fail("None of the locators are visible");
		}
		return WorkingLocator;
	}
	/**
	 * Method: for editing rich text editor which is in frame using java script
	 * @param Framelocator Frame Locator
	 * @param TextEditorlocator text editor locator
	 * @param sText text
	 * @param waitTime wait time to wait for an webelement
	 */
	public void safeJavaScriptRichTextEditor(By Framelocator, By TextEditorlocator, String sText,String tagName,String friendly_frameEleName,String friendly_EditorEleName, int... waitTime)
	{
		try
		{
			selectFrame(Framelocator,friendly_frameEleName, MEDIUMWAIT);
			((JavascriptExecutor) driver).executeScript("document.getElementsByTagName('"+tagName+"')[0].innerHTML=arguments[0];",sText,"");
			defaultFrame();
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+"Element with " + friendly_EditorEleName +"is not attached to the page document exception message: " + e);
			Assert.fail("Element with " + friendly_EditorEleName +"is not attached to the page document exception message: " + e);
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+"Element " + friendly_EditorEleName + " was not found in DOM");
			Assert.fail("Element "+ friendly_EditorEleName +" was not found in DOM exception message: " + e);
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to edit rich text editor " +friendly_frameEleName+ TextEditorlocator);
			Assert.fail("Unable to edit rich text editor " + TextEditorlocator+"exception message: " + e);
		}
	}



	//******************************************************************************************************************************************************************
	//******************************************************************************************************************************************************************
	//******************************************************************************************************************************************************************




	/**
	 * Purpose - To click on a particular context menu option for the given element
	 * @param locator locator
	 * @param friendlyWebElementName WebElementName
	 * @param iOptionIndex index value to select
	 * @param waitTime wait time for a webelement
	 */
	public void safeClickContextMenuOption(By locator, String friendlyWebElementName,int iOptionIndex, int waitTime)
	{
		try
		{
			if(isElementPresent(locator, waitTime))
			{
				clickContextMenuOption(locator, iOptionIndex);
			}
			else
			{
				log.error(getTestCasename()+friendlyWebElementName + "has not displayed to perform content menu operation by index: " + iOptionIndex);
				Assert.fail(friendlyWebElementName + "has not displayed to perform content menu operation by index: " + iOptionIndex);
			}
		}
		catch(StaleElementReferenceException e)
		{
			log.error(getTestCasename()+"Context menu option by index: " + iOptionIndex + " for " + friendlyWebElementName + " is not attached to the page document");
			Assert.fail("Context menu option by index: " + iOptionIndex + " for " + friendlyWebElementName + " is not attached to the page document");
		}
		catch (NoSuchElementException e)
		{
			log.error(getTestCasename()+"Context menu option by index: " + iOptionIndex + " for" + friendlyWebElementName + " was not found in DOM");
			Assert.fail("Context menu option by index: " + iOptionIndex + " for  " + friendlyWebElementName + " was not found in DOM");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to click context menu option by index: " + iOptionIndex + " for" + friendlyWebElementName + " - " + e);
			Assert.fail("Unable to click context menu option by index: " + iOptionIndex + " for " + friendlyWebElementName);
		}
	}


	/**
	 * Method to press ENTER on a particular option available in the rightclick options
	 * @param locator locator
	 * @param iOptionIndex option needs to select
	 */


	private void clickContextMenuOption(By locator, int iOptionIndex)
	{
		WebElement Element = driver.findElement(locator);
		Actions _action = new Actions(driver);
		for (int count=1; count<=iOptionIndex; count++)
		{
			_action.contextClick(Element).sendKeys(Keys.ARROW_DOWN);
		}
		_action.contextClick(Element).sendKeys(Keys.ENTER).build().perform();
	}

	/**
	 * Method to press ENTER on a particular button that has no frame (Navigated to the button by tab sequence)
	 * @param index index value
	 */
	public void safePressButtonByTabSequence(int index)
	{
		try
		{

			Robot _robot = new Robot();
			//Navigate to the button using tab sequence

			for (int count=1; count<=index; count++)
			{
				_robot.keyPress(KeyEvent.VK_TAB);
			}

			//Pressed ENTER on the respective button
			_robot.keyPress(KeyEvent.VK_ENTER);
			log.info(getTestCasename()+"Clicked on the respective button");
		}
		catch(Exception e)
		{
			log.error(getTestCasename()+"Unable to click the button" + e);
			Assert.fail("Unable to click the button");
		}
	}

	public boolean safeEditRichTextEditor(By Framelocator, By TextEditorlocator, String sText,String failuremessage, int... waitTime)
	 {
	  try
	  {
	   ((JavascriptExecutor) driver).executeScript("document.getElementsByTagName('body')[0].innerHTML=arguments[0];",sText,"");
	   defaultFrame();
	   return true;
	  }
	  catch(StaleElementReferenceException e)
	  {
	   log.error(getTestCasename()+"Element with " + TextEditorlocator +"is not attached to the page document");
	   Assert.fail("Element with " + TextEditorlocator +"is not attached to the page document");
	   return false;
	  }
	  catch (NoSuchElementException e)
	  {
	   log.error(getTestCasename()+"Element " + TextEditorlocator + " was not found in DOM");
	   Assert.fail("Element "+ TextEditorlocator +" was not found in DOM");
	   return false;
	  }
	  catch(Exception e)
	  {
	   //   e.printStackTrace();
	   log.error(getTestCasename()+"Unable to edit rich text editor " +failuremessage+ TextEditorlocator);
	   Assert.fail("Unable to edit rich text editor " + TextEditorlocator);
	   return false;
	  }
	 }

	/**
	  * To wait for given time and stop the page load when element present on the page using Robotil
	  * @param waitTime - wait time in seconds
	  */
	 public void stopPageLoad(By locator, int waitTime) {
	  try {
	   if(isElementPresent(locator, waitTime)){
	    ((JavascriptExecutor) driver)
	    .executeScript("return window.stop();");
	   }
	  } catch (Exception e) {
	   log.error(getTestCasename()+"Cannot stop page laoding due to - " + e);
	   Assert.fail("Cannot stop page laoding ..");
	  }
	 }
	 /**
	  * Method - This method is used to attach the file by providing path of file and by Pressing Enter button.
	  * @param path - This is the path of Remote Machine File  which is to be attached.
	  */
		public void attachFileUsingRobotilInRemote(SessionId session_Id,String path){

			RobotilHelper	robotilhelper=new RobotilHelper();
			String ip=robotilhelper.getHostName(session_Id);
			log.info("File upload-"+ip +"--"+path);
			Robotil robotil=new Robotil(ip, 7777);

			int length = path.length();
			for(int i=0;i<length;i++){
				char filePath = path.charAt(i);
				char C = Character.toUpperCase(path.charAt(i));
				if(!(CharMatcher.anyOf("!@#$%^&*()_+{}|:<>?").matches(filePath))){
				robotil.pressAndReleaseKey(C);
					robotil.releaseKey(C);
				}

				else{
					switch (C){
					case ':':
						robotil.pressKey(KeyEvent.VK_SHIFT);
						robotil.pressAndReleaseKey(KeyEvent.VK_SEMICOLON);
						robotil.releaseKey(KeyEvent.VK_SHIFT);
						break;

					case '!':
						robotil.pressKey(KeyEvent.VK_SHIFT);
						robotil.pressAndReleaseKey(KeyEvent.VK_1);
						robotil.releaseKey(KeyEvent.VK_SHIFT);
						break;

					case '@':
						robotil.pressKey(KeyEvent.VK_SHIFT);
						robotil.pressAndReleaseKey(KeyEvent.VK_2);
						robotil.releaseKey(KeyEvent.VK_SHIFT);
						break;

					case '#':
						robotil.pressKey(KeyEvent.VK_SHIFT);
						robotil.pressAndReleaseKey(KeyEvent.VK_3);
						robotil.releaseKey(KeyEvent.VK_SHIFT);
						break;

					case '$':
						robotil.pressKey(KeyEvent.VK_SHIFT);
						robotil.pressAndReleaseKey(KeyEvent.VK_4);
						robotil.releaseKey(KeyEvent.VK_SHIFT);
						break;

					case '%':
						robotil.pressKey(KeyEvent.VK_SHIFT);
						robotil.pressAndReleaseKey(KeyEvent.VK_5);
						robotil.releaseKey(KeyEvent.VK_SHIFT);
						break;

					case '^':
						robotil.pressKey(KeyEvent.VK_SHIFT);
						robotil.pressAndReleaseKey(KeyEvent.VK_6);
						robotil.releaseKey(KeyEvent.VK_SHIFT);
						break;

					case '&':
						robotil.pressKey(KeyEvent.VK_SHIFT);
						robotil.pressAndReleaseKey(KeyEvent.VK_7);
						robotil.releaseKey(KeyEvent.VK_SHIFT);
						break;

					case '*':
						robotil.pressKey(KeyEvent.VK_SHIFT);
						robotil.pressAndReleaseKey(KeyEvent.VK_8);
						robotil.releaseKey(KeyEvent.VK_SHIFT);
						break;

					case '(':
						robotil.pressKey(KeyEvent.VK_SHIFT);
						robotil.pressAndReleaseKey(KeyEvent.VK_9);
						robotil.releaseKey(KeyEvent.VK_SHIFT);
						break;

					case ')':
						robotil.pressKey(KeyEvent.VK_SHIFT);
						robotil.pressAndReleaseKey(KeyEvent.VK_0);
						robotil.releaseKey(KeyEvent.VK_SHIFT);
						break;

					case '+':
						robotil.pressKey(KeyEvent.VK_SHIFT);
						robotil.pressAndReleaseKey(KeyEvent.VK_EQUALS);
						robotil.releaseKey(KeyEvent.VK_SHIFT);
						break;

					case '_':
						robotil.pressKey(KeyEvent.VK_SHIFT);
						robotil.pressAndReleaseKey(KeyEvent.VK_MINUS);
						robotil.releaseKey(KeyEvent.VK_SHIFT);
						break;

					case '[':
						robotil.pressKey(KeyEvent.VK_SHIFT);
						robotil.pressAndReleaseKey(KeyEvent.VK_OPEN_BRACKET);
						robotil.releaseKey(KeyEvent.VK_SHIFT);
						break;

					case ']':
						robotil.pressKey(KeyEvent.VK_SHIFT);
						robotil.pressAndReleaseKey(KeyEvent.VK_CLOSE_BRACKET);
						robotil.releaseKey(KeyEvent.VK_SHIFT);
						break;

					case '|':
						robotil.pressKey(KeyEvent.VK_SHIFT);
						robotil.pressAndReleaseKey(KeyEvent.VK_BACK_SLASH);
						robotil.releaseKey(KeyEvent.VK_SHIFT);
						break;

					case '<':
						robotil.pressKey(KeyEvent.VK_SHIFT);
						robotil.pressAndReleaseKey(KeyEvent.VK_COMMA);
						robotil.releaseKey(KeyEvent.VK_SHIFT);
						break;

					case '>':
						robotil.pressKey(KeyEvent.VK_SHIFT);
						robotil.pressAndReleaseKey(KeyEvent.VK_PERIOD);
						robotil.releaseKey(KeyEvent.VK_SHIFT);
						break;

					case '?':
						robotil.pressKey(KeyEvent.VK_SHIFT);
						robotil.pressAndReleaseKey(KeyEvent.VK_SLASH);
						robotil.releaseKey(KeyEvent.VK_SHIFT);
						break;
					}
				}
			}
			waitForSecs(3);
			robotil.pressAndReleaseKey(KeyEvent.VK_ENTER);
		}
		/**
		 * Method - This method is used to click on machine based on x,y Resolution using Robotil
		 * @param session_Id remote machine session id
		 * @param x - int x-resolution of currect screen
		 * @param y - int x-resolution of currect screen
		 */
		public void mouseClickUsingRobotil(SessionId session_Id,String ip,int x,int y)
		{
			try
			{
			RobotilHelper	robotilhelper=new RobotilHelper();
			Robotil robotil=new Robotil(robotilhelper.getHostName(session_Id), 7777);
			robotil.mouseClick(x,y,InputEvent.BUTTON1_MASK);
			robotil.releaseKey(InputEvent.BUTTON1_MASK);
			log.info("Mouse clicked on ("+x+","+y+") point using Robotil class");
			}
			catch(Exception e){
				log.error("some Exception occured while mouse click using robotil, please check grid node host name (make sure grid configuration) " +e.getStackTrace());
				Assert.fail("some Exception occured while mouse click using robotil, please check grid node host name (make sure grid configuration) "+e.getStackTrace());

			}
		}
		/**
		 * Method - This method is used to capture screenshot using Robotil
		 * @param session_Id remote machine session id
		 * @param screenshotPath - String path to store remote screenshot
		 */
		public void captureCurrectScreenUsingRobotil(SessionId session_Id,String screenshotPath)
		{
			try
			{
			RobotilHelper	robotilhelper=new RobotilHelper();
			Robotil robotil=new Robotil(robotilhelper.getHostName(session_Id), 7777);
			robotil.captureScreen(screenshotPath);
			}
			catch(Exception e){
				log.error("some Exception occured capturing remote screen using robotil, please check grid node host name (make sure grid configuration) " +e.getStackTrace());
				Assert.fail("some Exception occured capturing remote screen using robotil, please check grid node host name (make sure grid configuration) "+e.getStackTrace());

			}

		}
		/**
		 * Method - This method is used to invoke any appliction ex (nodepad.ex/firefox.exe etc...) using Robotil
		 * @param session_Id remote machine session id
		 * @param application - String application name with .exe extension
		 */
		public void invokeApplicationUsingRobotil(SessionId session_Id,String application)
		{
			try
			{
			RobotilHelper	robotilhelper=new RobotilHelper();
			Robotil robotil=new Robotil(robotilhelper.getHostName(session_Id), 7777);
			robotil.invokeApplication(application);
			}
			catch(Exception e){
				log.error("some Exception occured while invoking an application using robotil, please check grid node host name (make sure grid configuration) " +e.getStackTrace());
				Assert.fail("some Exception occured while invoking an application using robotil, please check grid node host name (make sure grid configuration) "+e.getStackTrace());

			}
		}
		/**
		 * Method: To click on certain locator using sikuli
		 * @param sImagePath  Image path
		 * @param sLocatorName  Locator name
		 * @param waitTime time in seconds
		 */
		public void safeClickUsingSikuli(String sImagePath, String sLocatorName,int waitTime)
		{
			try
			{
				Screen s=new Screen();
				Pattern image = new Pattern(sImagePath);
				image.similar((float) 0.7);
				s.wait(image, waitTime);
				Match m= s.find(image);
				m.highlight(1);
				m.click();
				log.info(getTestCasename()+"Locator field with name '"+sLocatorName+"' is Clicked using sikuli");

			}
			catch(FindFailed e)
			{
				log.error(getTestCasename()+"Locator with name '"+sLocatorName+"' is not displayed using sikuli, please check screenshot and image path");
				Assert.fail("Locator with name '"+sLocatorName+"' is not displayed using sikuli, please check screenshot and image path");
			}
			catch(Exception e)
			{
				log.error(getTestCasename()+"Locator with name '"+sLocatorName+"' is not displayed using sikuli, please check screenshot and image path" + " - " + e);
				Assert.fail("Locator with name '"+sLocatorName+"' is not displayed using sikuli, Some exception occured other than FindFailed, please check screenshot and image path");

			}
		}
		/**
		 * Method: To Verify an element/image
		 * @param sImagePath Image path
		 * @param sLocatorName locator name
		 * @param waitTime time in seconds
		 */
		public void safeVerifyUsingSikuli(String sImagePath, String sLocatorName,int waitTime)
		{
			try
			{
				Screen s=new Screen();
				Pattern image = new Pattern(sImagePath);
				image.similar((float) 0.7);
				s.wait(image, waitTime);
				Match m= s.find(image);
				m.highlight(1);
				log.info(getTestCasename()+"Locator field with name '"+sLocatorName+"' is displayed using sikuli");

			}
			catch(FindFailed e)
			{
				log.error(getTestCasename()+"Locator with name '"+sLocatorName+"' is not displayed using sikuli, please check screenshot and image path");
				Assert.fail("Locator with name '"+sLocatorName+"' is not displayed using sikuli, please check screenshot and image path");
			}
			catch(Exception e)
			{
				log.error(getTestCasename()+"Locator with name '"+sLocatorName+"' is not displayed using sikuli, please check screenshot and image path" + " - " + e);
				Assert.fail("Locator with name '"+sLocatorName+"' is not displayed using sikuli, Some exception occured other than FindFailed, please check screenshot and image path");

			}
		}
		/**
		 * Method: To type text into input field using Sikuli
		 * @param sImagePath Image path
		 * @param sLocatorName locator name
		 * @param waitTime time in seconds
		 */
		public void safeTypeUsingSikuli(String sImagePath, String sLocatorName,String strText,int waitTime)
		{
			try
			{
				Screen s=new Screen();
				Pattern image = new Pattern(sImagePath);
				image.similar((float) 0.7);
				s.wait(image, waitTime);
				Match m= s.find(image);
				m.highlight(1);
				m.type(strText);

				log.info(getTestCasename()+"Locator field with name '"+sLocatorName+"' is displayed and typed '"+strText+"' text using sikuli");
			}
			catch(FindFailed e)
			{
				log.error(getTestCasename()+"Locator with name '"+sLocatorName+"' is not displayed using sikuli, please check screenshot and image path");
				Assert.fail("Locator with name '"+sLocatorName+"' is not displayed using sikuli, please check screenshot and image path");
			}
			catch(Exception e)
			{
				log.error(getTestCasename()+"Locator with name '"+sLocatorName+"' is not displayed using sikuli, please check screenshot and image path" + " - " + e);
				Assert.fail("Locator with name '"+sLocatorName+"' is not displayed using sikuli, Some exception occured other than FindFailed, please check screenshot and image path");

			}
		}
		/**
		 * Method: To move mouse pointer on a specific element using Sikuli
		 * @param sImagePath Image path
		 * @param sLocatorName locator name
		 * @param waitTime time in seconds
		 */
		public void safeMouseMoveUsingSikuli(String sImagePath, String sLocatorName,int waitTime)
		{
			try
			{
				Screen s=new Screen();
				Pattern image = new Pattern(sImagePath);
				image.similar((float) 0.7);
				s.wait(image, waitTime);
				Match m= s.find(image);
				m.highlight(1);
				m.mouseMove();
				log.info(getTestCasename()+"Locator field with name '"+sLocatorName+"' is displayed and moved using sikuli");

			}
			catch(FindFailed e)
			{
				log.error(getTestCasename()+"Locator with name '"+sLocatorName+"' is not displayed using sikuli, please check screenshot and image path");
				Assert.fail("Locator with name '"+sLocatorName+"' is not displayed using sikuli, please check screenshot and image path");
			}
			catch(Exception e)
			{
				log.error(getTestCasename()+"Locator with name '"+sLocatorName+"' is not displayed using sikuli, please check screenshot and image path" + " - " + e);
				Assert.fail("Locator with name '"+sLocatorName+"' is not displayed using sikuli, Some exception occured other than FindFailed, please check screenshot and image path");

			}
		}

	/**
	 * Returns list of webelements and handles the exceptions if any
	 *
	 * @param locator
	 * @return List<webelement>
	 */
	public List<WebElement> getWebElements(By locator)
	{
		List<WebElement> elements = null;
		try
		{
			elements = driver.findElements(locator);
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("Some Exception occurred while retrieving the web elements");
		}
		return elements;
	}

	/**
	 * Returns webelement and handles the exceptions if any
	 *
	 * @param locator
	 * @return {@link WebElement}
	 */
	public WebElement getWebElement(By locator)
	{
		WebElement element = null;
		try
		{
			element = driver.findElement(locator);
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("Some Exception occured while retreiving the webelement");
		}
		return element;
	}
	
	

}
