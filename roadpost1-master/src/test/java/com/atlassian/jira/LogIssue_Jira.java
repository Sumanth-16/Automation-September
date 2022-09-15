package com.atlassian.jira;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datamanager.ConfigManager;
import com.jetbrains.youtrack.APIHelpers;
import com.passworddecoder.PasswordDecorder;
import com.utilities.UtilityMethods;

public class LogIssue_Jira {
	
	private APIHelpers apiHelpers;
	//HashMap<String, String> session_ID_Principal;
	private String issueSummary=null;
	private String issueDescription=null;
	private String created_Issue_Id="";
	private String base64Encode="";
	private ConfigManager sys=new ConfigManager();
	private Logger log = LogManager.getLogger("LogIssue_Jira");
	private String fileSeperator = System.getProperty("file.separator");
	private String requestBody_filePath = System.getProperty("user.dir") + fileSeperator+"test-src" + fileSeperator + "com" + fileSeperator+"atlassian"+fileSeperator+
			"jira"+fileSeperator+"Jira_CreateIssue_reqBody.txt";
	/** 
	 * Purpose - This method is used to create an issue in Jira using REST API
	 * @param testName currently running test name
	 * @param issue_Summary - summary/title of the issue
	 * @param error_Trace - issue error trace
	 * @param screenShot - issue screenshot to attach
	 * */
	public void createIssueInZira(String testName,String browserName,String issue_Summary,String error_Trace,String screenShot){
		
		try{
			if(issue_Summary!=null){
				if(issue_Summary.length()<230&&!issue_Summary.equalsIgnoreCase("")){
				if(!sys.getProperty("Api.projectkey").trim().equalsIgnoreCase("")&&!sys.getProperty("Api.issueType").trim().equalsIgnoreCase("")){
			
			apiHelpers = new APIHelpers();
			base64Encode=UtilityMethods.getBase64EncodeString(sys.getProperty("Api.userName")+":"+PasswordDecorder.passwordDecrypt(sys.getProperty("Api.password")));
			List<String> headers = new ArrayList<>();
			headers.add("Content-Type:application/json");
			headers.add("Authorization:Basic " +base64Encode);
			issueSummary="[Automation Issue]:"+browserName+" - "+issue_Summary;
			issueDescription="The issue '"+issue_Summary+"' is produced in tese case '"+testName+" '\n"+" Error Trace - "+ "\n"+error_Trace;
			String create_Issue_endPoint=""+sys.getProperty("Api.API_BaseURL_Jira")+""+sys.getProperty("Api.createIssue_JiraEndPoint");
			HashMap<String, String> response = apiHelpers.executeRequest(null,
					null, create_Issue_endPoint, headers, "POST", false,null,false,null,requestBody_filePath,sys.getProperty("Api.projectkey"),issueSummary,
					issueDescription,sys.getProperty("Api.issueType"));
			apiHelpers.verifyStatusCode(response.get("status code"), "201");
			apiHelpers.verifyStatusMessage(response.get("status message"), "Created");
			List<String> res_Valuee=apiHelpers.getValuesforkeys(response.get("response body"), "id");
			created_Issue_Id=res_Valuee.get(0);
			createIssueInZiraWithScreenshot(screenShot);
			
			}
			else
			{
				log.error("Jira Itegration: Project key or issue type not correct, Please check the values in 'Sys.properties' file");
			}
			}
			else
			{
				log.error("Jira Itegration: Issue summary can't be 'blank' and not more than 235 characters, Please check issue summary");	
			}
			}else
			{
				log.error("Jira Itegration: Issue summary can't be 'null', Please check issue summary");	
			}
		}catch(Exception e){
			log.error("Some exception is occured while creating an issue in jira");
		}
	}
	/** 
	 * Purpose - This method is used to attach error screenshot to already created issue (above) in Jira using REST API
	 * @param screenShot - file path of error screenshot
	 * */
	private void createIssueInZiraWithScreenshot(String screenShot) {
		
		List<String> headers = new ArrayList<>();
		headers.add("X-Atlassian-Token:no-check");
		headers.add("Authorization:Basic " +base64Encode);
		String create_Screenshot_endPoint=""+sys.getProperty("Api.API_BaseURL_Jira")+""+sys.getProperty("Api.attachment_JiraEndPoint");
		if(create_Screenshot_endPoint.contains("$"))
		{
			create_Screenshot_endPoint=UtilityMethods.replaceVariablesWithRuntimeProperties(create_Screenshot_endPoint, created_Issue_Id);
		}
		HashMap<String, String> response = apiHelpers.executeRequest(null,
				null, create_Screenshot_endPoint, headers, "POST", false,null,false,screenShot);
		apiHelpers.verifyStatusCode(response.get("status code"), "200");
		apiHelpers.verifyStatusMessage(response.get("status message"), "OK");
		}
}
