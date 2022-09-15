package com.page.data;

import com.datamanager.ConfigManager;

public class NZData {
	private ConfigManager nzData = new ConfigManager("NZ");
	public final String nzNameOnCC = nzData.getProperty("NZ.NameOnCC");
	public final String nzCCNo = nzData.getProperty("NZ.CCNo");
	public final String nzExpiryMonth = nzData.getProperty("NZ.ExpiryMonth");
	public final String nzExpiryYear = nzData.getProperty("NZ.ExpiryYear");
	public final String nzCVV = nzData.getProperty("NZ.CVV");
	public final String nzFirstName = nzData.getProperty("NZ.FirstName");
	public final String nzLastName = nzData.getProperty("NZ.LastName");
	public final String nzStreetAddress = nzData.getProperty("NZ.StreetAddress");
	public final String nzCity = nzData.getProperty("NZ.City");
	public final String nzState = nzData.getProperty("NZ.State");
	public final String nzZip = nzData.getProperty("NZ.Zip");
	public final String nzPhoneNo = nzData.getProperty("NZ.PhoneNo");

}
