package com.page.locators;

import org.openqa.selenium.By;

public interface ZoleoActivationPageLocators 
{
	 
	 By ACTIVATION_SUCCESS = By.xpath("//Span[text()='Activation submitted.']");
	 By ACTIVATED_IMEI=By.xpath("(//div[@class='sk-columns six']//span)[1]");
	 By ACTIVATED_MONTHLY_PLAN=By.xpath("(//div[@class='sk-columns six']//span)[2]");
	 By MY_ACCOUNT=By.xpath("//span[text()='My Account']");
	 
}