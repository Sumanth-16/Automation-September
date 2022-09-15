package com.page.data;

import com.datamanager.ConfigManager;

public class DenmarkData 
{
	private ConfigManager denmarkData = new ConfigManager("Denmark");
	public final String  denmarkPhoneNo = denmarkData.getProperty("Denmark.PhoneNo");
	public final String  denmarkStreetAddress = denmarkData.getProperty("Denmark.StreetAddress");
	public final String  denmarkCity = denmarkData.getProperty("Denmark.City");
	public final String  denmarkZip = denmarkData.getProperty("Denmark.Zip");
	public final String  denmarkNameOnCC = denmarkData.getProperty("Denmark.NameOnCC");
	public final String  denmarkCCNo= denmarkData.getProperty("Denmark.CCNo");
	public final String  denmarkExpiryDate = denmarkData.getProperty("Denmark.ExpiryDate");
	public final String  denmarkCVV = denmarkData.getProperty("Denmark.CVV");
	
	
}
