package com.page.data;

import com.datamanager.ConfigManager;

public class SwedenData 
{
	private ConfigManager swedenData = new ConfigManager("Sweden");
	public final String  swedenPhoneNo = swedenData.getProperty("Sweden.PhoneNo");
	public final String  swedenStreetAddress = swedenData.getProperty("Sweden.StreetAddress");
	public final String  swedenCity = swedenData.getProperty("Sweden.City");
	public final String  swedenZip = swedenData.getProperty("Sweden.Zip");
	public final String  swedenNameOnCC = swedenData.getProperty("Sweden.NameOnCC");
	public final String  swedenCCNo= swedenData.getProperty("Sweden.CCNo");
	public final String  swedenExpiryDate = swedenData.getProperty("Sweden.ExpiryDate");
	public final String  swedenCVV = swedenData.getProperty("Sweden.CVV");
	
	
}
