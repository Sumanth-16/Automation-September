package com.TeamsIntegration;

import com.datamanager.ConfigManager;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;

//import javafx.scene.control.Hyperlink;

public class MicrosoftTeams {
	private static ConfigManager sys = new ConfigManager();
	static String teamsMessage="";
	public static IncomingWebhookRequest forUrl(Webhook webhook) {
		return new IncomingWebhookRequest(webhook);
	}

public static void sendTeamsMessage(String time) throws UnirestException {
	IncomingWebhookRequest incomingWebhookRequest= MicrosoftTeams.forUrl(new Webhook() {
		public String getUrl() {
			return sys.getProperty("TeamsWebhookUrl");
		}
	});
	String methodName=Thread.currentThread().getStackTrace()[2].getMethodName();
	if(methodName.equalsIgnoreCase("beforeSuite")) {
		teamsMessage="<b>Build Details</b>\n\rTest Execution Started at " + time;
	}
	else if(methodName.equalsIgnoreCase("AddLogFileToReport")) {
		teamsMessage=teamsMessage+"\n\rTest Execution Ended at " + time;
	}
}

	public static void testCasesCountTeams(int totalCount,int passCount,int failCount,int skipCount) throws UnirestException, IOException, ParseException, URISyntaxException {
		IncomingWebhookRequest incomingWebhookRequest= MicrosoftTeams.forUrl(new Webhook() {
			public String getUrl() {
				return sys.getProperty("TeamsWebhookUrl");
			}
		});
		String url=System.getProperty("user.dir")+"/Automation Reports/LatestResults/html/index.html";
		url=url.replace("\\","/");
		String value="<b>Overall Summary:</b>\rTotal : "+totalCount+", Passed : "+passCount+", Failed : "+failCount+", Skipped : "+skipCount+"\n\rReference : "+url;
		teamsMessage=teamsMessage+"\n\r"+value;
		incomingWebhookRequest.sendMessage(teamsMessage);
	}

}
