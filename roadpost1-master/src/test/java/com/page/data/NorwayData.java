package com.page.data;

import com.datamanager.ConfigManager;

public class NorwayData 
{
	private ConfigManager norwayData = new ConfigManager("Norway");
	public final String  norwayPhoneNo = norwayData.getProperty("Norway.PhoneNo");
	public final String  norwayStreetAddress = norwayData.getProperty("Norway.StreetAddress");
	public final String  norwayCity = norwayData.getProperty("Norway.City");
	public final String  norwayZip = norwayData.getProperty("Norway.Zip");
	public final String  norwayNameOnCC = norwayData.getProperty("Norway.NameOnCC");
	public final String  norwayCCNo= norwayData.getProperty("Norway.CCNo");
	public final String  norwayExpiryDate = norwayData.getProperty("Norway.ExpiryDate");
	public final String  norwayCVV = norwayData.getProperty("Norway.CVV");
	
	
}
