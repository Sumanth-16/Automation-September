package com.page.locators;

import org.openqa.selenium.By;

public interface ZoleoLogInPageLocators 
{
	 By EMAILADDRESS_FIELD = By.xpath("(//input[@name='username'])[2]");
	 By PASSWORD_FIELD = By.xpath("(//input[@name='password'])[2]");
	 By LOGIN_BTN=By.xpath("(//input[@name='signInSubmitButton'])[2]");
	 By SIGNUP_BTN=By.xpath("(//a[text()='Sign up'])[1]");
	 By FORGOT_PASSWORD_BTN=By.xpath("(//a[text()='Forgot your password?'])[2]");
}