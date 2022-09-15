package com.page.locators;

import org.openqa.selenium.By;

import com.page.data.ZoleoData;

public interface GMAILLocators 
{
	
	By GMAIL_USERNAME=By.xpath("//input[@type='email']");
	By SIGN_IN_BUTTON=By.xpath("(//a[text()='Sign in'])[1]");
	 By GMAIL_PASSWORD=By.xpath("//input[@name='passwd']"); 
	 By GMAIL_NEXT=By.xpath("//input[@type='submit']");
	 By GMAIL_INBOX_MSG=By.xpath("(//div[@class='xCI71 hoYNt']//span[text()='Your verification code'])[1]");
	 By GMAIL_INBOX_MSG_OLD=By.xpath("//tr[@id=':2m']");
	 By GMAIL_INBOX_BACK_BUTTON=By.xpath("(//div[@class='G-atb D E']//div//div[@class='asa']//div)[1]");
	 
	 
	 
}