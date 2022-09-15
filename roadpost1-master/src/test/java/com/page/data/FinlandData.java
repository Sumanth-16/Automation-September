package com.page.data;

import com.datamanager.ConfigManager;

public class FinlandData 
{
	private ConfigManager finlandData = new ConfigManager("Finland");
	public final String  finlandPhoneNo = finlandData.getProperty("Finland.PhoneNo");
	public final String  finlandStreetAddress = finlandData.getProperty("Finland.StreetAddress");
	public final String  finlandCity = finlandData.getProperty("Finland.City");
	public final String  finlandZip = finlandData.getProperty("Finland.Zip");
	public final String  finlandNameOnCC = finlandData.getProperty("Finland.NameOnCC");
	public final String  finlandCCNo= finlandData.getProperty("Finland.CCNo");
	public final String  finlandExpiryDate = finlandData.getProperty("Finland.ExpiryDate");
	public final String  finlandCVV = finlandData.getProperty("Finland.CVV");
	
	
}
