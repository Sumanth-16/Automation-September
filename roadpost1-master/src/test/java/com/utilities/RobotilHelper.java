package com.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
//import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.SessionId;

import com.datamanager.ConfigManager;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RobotilHelper{
	private Logger	log=LogManager.getLogger("RobotilHelper");
	private String sModeOfExecution;
	private ConfigManager sys;
	/** 
	 * Purpose- To get the remote machine host/ip name
	 * @return hostname
	 */
	public String getHostName(SessionId session_Id)
	{
		String hostDetail = null;
		String hostName ="localhost";
		int port = 4444;
		String errorMsg = "Failed to acquire remote webdriver node and port info. Root cause: ";
 
		try {
			sys=new ConfigManager();
			sModeOfExecution=sys.getProperty("ModeOfExecution");
			if(sModeOfExecution.equals("Remote"))
			{
				HttpHost host = new HttpHost(hostName, port);
				HttpClient client=HttpClientBuilder.create().build();
				//DefaultHttpClient client = new DefaultHttpClient();
				URL sessionURL = new URL("http://" + hostName + ":" + port + "/grid/api/testsession?session=" + session_Id);
				System.out.println("URL is : "+sessionURL);
				BasicHttpEntityEnclosingRequest r = new BasicHttpEntityEnclosingRequest("POST", sessionURL.toExternalForm());
				HttpResponse response = client.execute(host, r);
				//JSONObject object = extractObject(response);
				//URL myURL = new URL(object.getString("proxyId"));
				JsonObject myjsonobject =extractObject(response);
				JsonElement url = myjsonobject.get("proxyId");
				System.out.println(url.getAsString());
				URL myURL = new URL(url.getAsString());
				if ((myURL.getHost() != null) && (myURL.getPort() != -1)) {
					hostDetail = myURL.getHost();
				}
			}else
			{
				InetAddress ipAddr;
				try {
					ipAddr = InetAddress.getLocalHost();
					hostDetail=ipAddr.getHostAddress();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
 
		} catch (Exception e) {
			log.error("Some Exception occured while get grid/cloud host name,please make sure grid configuration " +e.getStackTrace());
			//Assert.fail("Some Exception occured while get grid/cloud host name, please make sure grid configuration "+e.getStackTrace());
		}
		return hostDetail;
	}
	private static JsonObject extractObject(HttpResponse resp) throws IOException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
		StringBuffer s = new StringBuffer();
		String line;
		while ((line = rd.readLine()) != null) {
			s.append(line);
		}
		rd.close();
		JsonParser parser = new JsonParser();
		return (JsonObject)parser.parse(s.toString());
	}
}