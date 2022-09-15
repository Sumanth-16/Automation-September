package com.selenium;

import com.datamanager.ConfigManager;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class SlackIntegration {
   private static ConfigManager sys = new ConfigManager();

    public static void sendMessage(String time) throws ParseException, IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(sys.getProperty("slackWebhookUrl"));
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        String value = null;
        String inputJson=null;
        String methodName=Thread.currentThread().getStackTrace()[2].getMethodName();
        if(methodName.equalsIgnoreCase("beforeSuite")) {
            value = "Test Execution Started at " + time;
            inputJson="{\"text\":\"*Build Details*\n"+value+"\"}";
        }
        else if(methodName.equalsIgnoreCase("AddLogFileToReport")) {
            value = "Test Execution Ended at " + time;
            inputJson="{\"text\":\""+value+"\"}";
        }

        System.out.println(inputJson);
        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(inputJson);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testCasesCount(int totalCount,int passCount,int failCount,int skipCount) throws URISyntaxException, MalformedURLException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(sys.getProperty("slackWebhookUrl"));
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        String url=System.getProperty("user.dir")+"/Automation Reports/LatestResults/html/index.html";
        url=url.replace("\\","/");
        String value="Total : "+totalCount+", Passed : "+passCount+", Failed : "+failCount+", Skipped : "+skipCount+"\nReference : "+url;
        String inputJson="{\"text\":\"*Overall Summary:*  "+value+"\"}";
        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(inputJson);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}