package com.testng;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.datamanager.ConfigManager;

public class Retry implements IRetryAnalyzer
{
	private int retryCount = 0;
	private int maxRetryCount = Integer.parseInt(new ConfigManager().getProperty("RetryCount"));
	
	/**
	 * This method is used to retry the failed test case again
	 */
	public boolean retry(ITestResult result) 
	{
		if(retryCount < maxRetryCount) 
		{ 
			retryCount++; 
			return true; 
		} 
		return false; 
	}

} 