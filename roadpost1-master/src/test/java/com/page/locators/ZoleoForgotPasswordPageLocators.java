package com.page.locators;

import org.openqa.selenium.By;

public interface ZoleoForgotPasswordPageLocators 
{
	 By FORGOT_EMAILADDRESS = By.xpath("//input[@id='username']");
	 By RESET_MY_PASSWORD_BTN = By.xpath("//button[@name='reset_my_password']");
	 By FORGOT_PASSWORD_CODE=By.xpath("//input[@id='forgot_password_code']");
	 By NEW_PASSWORD=By.xpath("//input[@id='new_password']");
	 By CONFIRM_PASSWORD=By.xpath("//input[@id='confirm_password']");
	 By CHANGE_PASSWORD_BUTTON=By.xpath("//button[@name='reset_password']");
}