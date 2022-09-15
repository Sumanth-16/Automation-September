package com.page.data;

import com.datamanager.ConfigManager;

public class AUSData {
	private ConfigManager ausData = new ConfigManager("AUS");
	public final String ausNameOnCC = ausData.getProperty("AUS.NameOnCC");
	public final String ausCCNo = ausData.getProperty("AUS.CCNo");
	public final String ausExpiryMonth = ausData.getProperty("AUS.ExpiryMonth");
	public final String ausExpiryYear = ausData.getProperty("AUS.ExpiryYear");
	public final String ausCVV = ausData.getProperty("AUS.CVV");
	public final String ausFirstName = ausData.getProperty("AUS.FirstName");
	public final String ausLastName = ausData.getProperty("AUS.LastName");
	public final String ausStreetAddress = ausData.getProperty("AUS.StreetAddress");
	public final String ausCity = ausData.getProperty("AUS.City");
	public final String ausState = ausData.getProperty("AUS.State");
	public final String ausZip = ausData.getProperty("AUS.Zip");
	public final String ausPhoneNo = ausData.getProperty("AUS.PhoneNo");

}
