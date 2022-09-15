package com.page.data;

import com.datamanager.ConfigManager;

public class USData {
	private ConfigManager usData = new ConfigManager("US");
	public final String usNameOnCC = usData.getProperty("US.NameOnCC");
	public final String usCCNo = usData.getProperty("US.CCNo");
	public final String usExpiryMonth = usData.getProperty("US.ExpiryMonth");
	public final String usExpiryYear = usData.getProperty("US.ExpiryYear");
	public final String usCVV = usData.getProperty("US.CVV");
	public final String usFirstName = usData.getProperty("US.FirstName");
	public final String usLastName = usData.getProperty("US.LastName");
	public final String usStreetAddress = usData.getProperty("US.StreetAddress");
	public final String usCity = usData.getProperty("US.City");
	public final String usState = usData.getProperty("US.State");
	public final String usZip = usData.getProperty("US.Zip");
	public final String usPhoneNo = usData.getProperty("US.PhoneNo");

}
