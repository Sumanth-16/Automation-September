package com.page.locators;

import org.openqa.selenium.By;

public interface ZoleoSignUpPageLocators 
{
	 By EMAILADDRESS_SIGNUP = By.xpath("(//input[@name='username'])[1]");
	 By PHONE_NUMBER_SIGNUP=By.xpath("(//div//input[@type='tel'])[1]");
	 By GIVEN_NAME=By.xpath("(//div//input[@name='requiredAttributes[given_name]'])[1]");
	 By FAMILY_NAME=By.xpath("(//div//input[@name='requiredAttributes[family_name]'])[1]");
	 By PASSWORD_SIGNUP = By.xpath("(//input[@name='password'])[1]");
	 By ACCOUNT_SIGNUP_BUTTON = By.xpath("(//button[@name='signUpButton'])[1]");
	 By OTP_ERROR_MSG=By.xpath("//p[@id='errorMessage']");
	 By VERIFICATION_CODE=By.xpath("//input[@id='verification_code']");
	 By CONFIRM_ACCOUNT=By.xpath("//div//button[@name='confirm']");
	 By RESEND_OTP=By.xpath("//button[@name='resend']");
	 
}