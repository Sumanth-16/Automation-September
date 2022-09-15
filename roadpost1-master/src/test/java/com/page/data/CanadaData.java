package com.page.data;

import com.datamanager.ConfigManager;

public class CanadaData {
	private ConfigManager canadaData = new ConfigManager("Canada");
	public final String canadaNameOnCC = canadaData.getProperty("Canada.NameOnCC");
	public final String canadaCCNo = canadaData.getProperty("Canada.CCNo");
	public final String canadaExpiryMonth = canadaData.getProperty("Canada.ExpiryMonth");
	public final String canadaExpiryYear = canadaData.getProperty("Canada.ExpiryYear");
	public final String canadaCVV = canadaData.getProperty("Canada.CVV");
	public final String canadaFirstName = canadaData.getProperty("Canada.FirstName");
	public final String canadaLastName = canadaData.getProperty("Canada.LastName");
	public final String canadaStreetAddress = canadaData.getProperty("Canada.StreetAddress");
	public final String canadaCity = canadaData.getProperty("Canada.City");
	public final String canadaState = canadaData.getProperty("Canada.State");
	public final String canadaZip = canadaData.getProperty("Canada.Zip");
	public final String canadaPhoneNo = canadaData.getProperty("Canada.PhoneNo");

}
