package com.jetbrains.youtrack;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.datamanager.ConfigManager;

public class LogIssue {
	
	private APIHelpers apiHelpers;
	private HashMap<String, String> session_ID_Principal;
	private String issueTitle=null;
	private String issueDescription=null;
	private ConfigManager sys=new ConfigManager();
	
	public void logInYouTrack(){
		apiHelpers = new APIHelpers();
		List<String> headers = new ArrayList<>();
		headers.add("Content-Type:application/x-www-form-urlencoded");
		headers.add("Cookie: ");
		String request_Params="?login="+sys.getProperty("youTrackAccountID")+"&password="+sys.getProperty("youTrackAccountPassword");
		String logIn_endPoint=""+sys.getProperty("Api.youTrackURL")+""+sys.getProperty("Api.youTrackLoginEndPoint");
		HashMap<String, String> response = apiHelpers.executeRequest(null,
				null, logIn_endPoint, headers, "POST", true,request_Params,true,null);
		apiHelpers.verifyStatusCode(response.get("status code"), "200");
		apiHelpers.verifyStatusMessage(response.get("status message"), "OK");
		session_ID_Principal=apiHelpers.getcookies(response.get("response Cookies"));
	}
	@SuppressWarnings("deprecation")
	public void createIssueInYouTrack(String testName,String error_Msg,String erro_Trace,String screenShot) {
		if(sys.getProperty("issueLogToYouTrack").equalsIgnoreCase("true"))
		{
		logInYouTrack();
		List<String> headers = new ArrayList<>();
		headers.add("$Version:0");
		headers.add("JSESSIONID:"+session_ID_Principal.get("JSession_ID").trim());
		headers.add("$Path:/");
		headers.add("jetbrains.charisma.main.security.PRINCIPAL:"+session_ID_Principal.get("principal").trim());
		headers.add("Content-Type:application/x-www-form-urlencoded");
			issueTitle=URLEncoder.encode("[Automation Issue]:-"+error_Msg);
			issueDescription=URLEncoder.encode("The issue '"+error_Msg+"' is produced in tese case - '"+testName+"'\n"+"Error Trace - "+erro_Trace);
		String request_Params="?project="+sys.getProperty("youTrack.Project")+"&summary="+issueTitle+"&description="+issueDescription;
		String createIssue_endPoint=""+sys.getProperty("Api.youTrackURL")+""+sys.getProperty("Api.youTrackCreateIssueEndPoint");
		HashMap<String, String> response = apiHelpers.executeRequest(null,
				null, createIssue_endPoint, headers, "POST", true,request_Params,false,screenShot);
		apiHelpers.verifyStatusCode(response.get("status code"), "200");
		apiHelpers.verifyStatusMessage(response.get("status message"), "OK");
		}
	}
}
