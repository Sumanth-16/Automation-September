package com.TeamsIntegration;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import java.net.URL;

public class IncomingWebhookRequest {
	private Webhook webhookDetails;

	public IncomingWebhookRequest(Webhook webhookDetails) {
		this.webhookDetails = webhookDetails;
	}
	
	public void sendMessage(String text) throws UnirestException {
		String url = webhookDetails.getUrl();
		JSONObject object = new JSONObject();
		object.put("text", text);
		Unirest.post(url).header("Content-Type", "application/json").body(object).asString();
	}

	public void sendMessage(URL url1) throws UnirestException {
		String url = webhookDetails.getUrl();
		JSONObject object = new JSONObject();
		object.put("link", url1);
		Unirest.post(url).header("Content-Type", "application/json").body(object).asString();
	}

}
