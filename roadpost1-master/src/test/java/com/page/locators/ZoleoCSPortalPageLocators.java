package com.page.locators;

import org.openqa.selenium.By;

public interface ZoleoCSPortalPageLocators
{	 By CS_LOGO =By.xpath("//div[@class='card-header']/h4//b[text()='Customer Service Portal']"); 
	 By CS_EMAIL=By.xpath("//input[@type='email']");
	 By CS_PASSWORD=By.xpath("//input[@type='password']");
	 By CS_LOGIN_BUTTON=By.xpath("//button[text()='Log in']");
	 By CS_DASHBOARD=By.xpath("//a[@href='/Admin']//i");
	 By CS_SUBSCRIPTION=By.xpath("//a[@href='/Admin/DeviceSubscription/List']//i");
	 By CS_DEVICESUBSCRIPTION=By.xpath("//a[contains(@href,'/Admin/DeviceSubscription/Edit')]");
}