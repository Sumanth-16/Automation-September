package com.page.data;

import com.datamanager.ConfigManager;

public class UKData 
{
	private ConfigManager ukData = new ConfigManager("UK");
	public final String  ukPhoneNo = ukData.getProperty("UK.PhoneNo");
	public final String  ukStreetAddress = ukData.getProperty("UK.StreetAddress");
	public final String  ukCity = ukData.getProperty("UK.City");
	public final String  ukZip = ukData.getProperty("UK.Zip");
	public final String  ukNameOnCC = ukData.getProperty("UK.NameOnCC");
	public final String  ukCCNo= ukData.getProperty("UK.CCNo");
	public final String  ukExpiryDate = ukData.getProperty("UK.ExpiryDate");
	public final String  ukCVV = ukData.getProperty("UK.CVV");
	
	
}
