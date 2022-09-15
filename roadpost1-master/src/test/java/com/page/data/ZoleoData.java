package com.page.data;

import com.datamanager.ConfigManager;

public class ZoleoData 
{
	private ConfigManager appData = new ConfigManager("App");
	public final String zoleoURL = appData.getProperty("App.URL");
	public final String countryRegion=appData.getProperty("App.CountryRegion");
	public final String emailAddress = appData.getProperty("App.Username");
	public final String otpemailaddress=appData.getProperty("App.OTPUsername");
	public final String password = appData.getProperty("App.Password");
	public final String signupemail = appData.getProperty("App.SignUpEmail");
	public final String otpsignup = appData.getProperty("App.OTPSignUp");
	public final String signupphoneno = appData.getProperty("App.SignUpPhoneNo");
	public final String givenname = appData.getProperty("App.GivenName");
	public final String familyname = appData.getProperty("App.FamilyName");
	public final String signuppassword = appData.getProperty("App.SignUpPassword");
	public final String myAccount = appData.getProperty("App.MyAccount");
	public final String imei = appData.getProperty("App.IMEI");
	public final String serialno = appData.getProperty("App.SerialNumber");
	public final String devicenickname = appData.getProperty("App.DeviceNickName");
	public final String zoleoemailaddress= appData.getProperty("App.ZoleoEmailAddress");
	public final String sos1fullname= appData.getProperty("App.SOS1FullName");
	public final String	sos1email= appData.getProperty("App.SOS1E-Mail");
	public final String sos1mobileno= appData.getProperty("App.SOS1MobileNo");
	public final String	sos2fullname= appData.getProperty("App.SOS2FullName");
	public final String	sos2email= appData.getProperty("App.SOS2E-Mail");
	public final String	sos2mobileno= appData.getProperty("App.SOS2MobileNo");
	public final String	checkincontactfullname= appData.getProperty("App.CheckInContactFullName");
	public final String	checkincontactemail= appData.getProperty("App.CheckInContactE-Mail");
	public final String	checkincontactmobileno= appData.getProperty("App.CheckInContactMobileNo");
	public final String firstname=appData.getProperty("App.FirstName");
	public final String	lastname=appData.getProperty("App.LastName");
	public final String	streetaddress=appData.getProperty("App.StreetAddress");
	public final String	city=appData.getProperty("App.City");
	public final String postalcode=appData.getProperty("App.PostalCode");
	public final String checkoutphoneno=appData.getProperty("App.CheckoutPhoneNo");
	public final String nameoncard=appData.getProperty("App.NameOnCard");
	public final String creditcardno=appData.getProperty("App.CreditCardNo");
	public final String cvvno=appData.getProperty("App.CVVNo");
	public final String csurl=appData.getProperty("App.CSURL");
	public final String csusername=appData.getProperty("App.CSUSERNAME");
	public final String cspassword=appData.getProperty("App.CSPASSWORD");
	public final String emailurl=appData.getProperty("App.EmailURL");
	public final String emailusername=appData.getProperty("App.EmailUsername");
	public final String emailpassword=appData.getProperty("App.EmailPassword");
	public final String country=appData.getProperty("App.Country");
	public final String noBillingAddressText=appData.getProperty("App.NOBillingAddressText");
	public final String communicationpreferenceText=appData.getProperty("App.CommunicationPreferenceText");
}
