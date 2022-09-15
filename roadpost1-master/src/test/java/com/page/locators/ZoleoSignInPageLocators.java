package com.page.locators;

import org.openqa.selenium.By;

public interface ZoleoSignInPageLocators 
{	 By ZOLEO_LOGO =By.xpath("//div//img[@alt='ZOLEO Global Satellite Communicator']"); 
	 By LOGIN_SIGNUP_BTN=By.xpath("//a[text()='Log In / Sign Up']");
     By NEXT_BUTTON=By.xpath("//a[text()='Next']");
     By ACTIVATE=By.xpath("//a[@class='button-activate']");
	 By COUNTRY_LOGO=By.xpath("(//ul//li[@class='my-region']//img)[6]");
	 By COUNTRY_DROPDOWN=By.xpath("//select[@id='country']");
	 By COUNTRY_CHECKBOX=By.xpath("//div[@class='field choice']//input");
	 By COUNTRY_SAVE=By.xpath("//button[@title='Save']/span");
	 
}