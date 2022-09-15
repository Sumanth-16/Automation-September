package com.jetbrains.youtrack;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import com.datamanager.ConfigManager;
import com.datamanager.ExcelManager;
import com.datamanager.JSONManager;
import com.utilities.UtilityMethods;

public class APIHelpers{
	public ExcelManager excel;
	public ConfigManager app_FileData=new ConfigManager("app");
	JSONManager jsonObj;
	private static HttpClient client=null;
	public APIHelpers() {

		jsonObj=new JSONManager();
	} 
	public String time_Stamp;
	private String responseBody;
	private static Logger log = LogManager.getLogger(APIHelpers.class);
//	private  static char cQuote = '"';
//	private String fileseperator=File.separator;
	/**
	 * Purpose - Enum for the type of request
	 */
	public enum RequestType {
		POST, PUT, DELETE, GET, TRACE
	}
	/**
	 * Purpose - Enum  to get the status
	 *  enum returns the corresponding status phrase and status code
	 */
	public enum Status {

		OK("200,OK"), CREATED(
				"201,Created"),NOT_FOUND("404,Not Found"),UNAUTHORIZED(
						"401,Unauthorized"),INTERNAL_SERVER_ERRROR("500,Internal Server Error"),BAD_REQUEST("400,Bad Request");
		String statusCode;
		public String getAction() {
			return this.statusCode;
		}
		private Status(String statusCode) {
			this.statusCode = statusCode;
		}
	}

	/**
	 * Purpose - To initialize Http client
	 * @return HttpClient
	 */

	public HttpClient getClient() {
		try {
			log.info("initializing HttpClient");
			return HttpClientBuilder.create().build();
		} catch (Exception e) {
			log.error("error while initialising Httpcient");
			return null;
		}
	}

	/**
	 * Purpose - To perform http requst
	 * @param strURL - End point URL
	 * @param requestType - API method(GET/PUT/POST/DELETE/TRACE)
	 * @return HttpRequestBase
	 */
	public HttpRequestBase getHttpMethod(String strURL, RequestType requestType) {
		try{
			log.info("initialising request type : " + requestType);
			switch (requestType) {
			case POST:
				return new HttpPost(strURL);
			case PUT:
				return new HttpPut(strURL);
			case GET:
				return new HttpGet(strURL);
			case DELETE:
				return new HttpDelete(strURL);
			case TRACE:
				return new HttpTrace(strURL);

			default:
				log.error("No http method of type :- " + requestType);
				Assert.fail("No http method of type :- " + requestType);
				return null;
			}
		}
		catch(Exception e){
			log.error("Exception while initializing HTTP Request method" + requestType+UtilityMethods.getStackTrace());
			Assert.fail("Exception while initializing HTTP Request method" + requestType+e.getMessage());
			return null;
		}

	}

	/**
	 * Purpose - To get the status of response 
	 * @param response - Response need to validate
	 * @return String[] with Status Code and Status Message
	 */
	public String[] getStatus(HttpResponse response) {
		try {
			log.info("get status from response");
			String actStatusMessage = response.getStatusLine()
					.getReasonPhrase();
			int actStatusCode = response.getStatusLine().getStatusCode();
			String[] actStatus = { actStatusMessage,
					String.valueOf(actStatusCode) };
			log.debug("Status code : " + actStatusCode);
			log.debug("Status Phrase : " + actStatusMessage);
			return actStatus;
		} catch (Exception e) {
			log.error("error while getting actual status : "
					+ e.getStackTrace());
			Assert.fail("error while getting actual status : " + e.getCause());
			return null;
		}
	}

	/**
	 * Purpose - To post the Http post Request
	 * @param Parameters - List of NameValuePairs
	 * @throws UnsupportedEncodingException exception type
	 */
	public void postParamsHttpPost(List<NameValuePair> Parameters,
			HttpPost httpPost)
					throws UnsupportedEncodingException {
		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(Parameters);
		httpPost.setEntity(formEntity);
	}
	/**
	 * Purpose - To post the http put request
	 * Purpose - To post the Http post Request
	 * @param Parameters - List of NameValuePairs
	 * @throws UnsupportedEncodingException exception type
	 */
	public void postParamsHttpPut(List<NameValuePair> Parameters,
			HttpPut httpPut)
					throws UnsupportedEncodingException {
		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(Parameters);
		httpPut.setEntity(formEntity);
	}

	/**
	 * Purpose - To add header
	 * @param headers -- each header should be in 'key:value' format
	 * @param httpmethod
	 *            (HttpGet, HttpPost, HttpPut ......) method add headers to
	 *            HttpRequestBase
	 */
	public void addHeader(HttpRequestBase httpmethod, List<String> headers) {
		try {
			log.info("adding headers");
			for(int i=0;i<headers.size();i++){
				httpmethod.addHeader(headers.get(i).split(":")[0].trim(), headers.get(i).split(":")[1].trim());
				log.info("header "+headers.get(i).split(":")[0]+":"+headers.get(i).split(":")[1]+" is added");
			}
		} catch (Exception e) {

			log.error("Exception while adding header(s)"+UtilityMethods.getStackTrace());
			Assert.fail("Exception while adding header(s)"+e.getMessage());
		}

	}
	/**
	 * Purpose - To execute
	 * @param client client object which need to execute
	 * @param httpMethod
	 *            (HttpGet, HttpPost, HttpPut ......) method add headers to
	 *            HttpRequestBase
	 * @return HttpResponse - it returns HttpResponse response of the executed request
	 */
	public HttpResponse execute(HttpClient client, HttpRequestBase httpMethod) {

		try {
			log.info("Executing : " + httpMethod.getMethod() + " Request");
			//			statusCode.append(responce.getStatusLine());
			return client.execute(httpMethod);
		}catch(UnknownHostException e){
			Assert.fail("Unable to contact host \nURL : "+httpMethod.getURI());
			log.error("Unable to contact host \nURL : "+httpMethod.getURI()+UtilityMethods.getStackTrace());
			return null;
		}catch (Exception e) {
			try{
				log.error("error while executing the request"+httpMethod.getURI()+"\n"+UtilityMethods.getStackTrace());
				Assert.fail("error while executing the request"+httpMethod.getURI()+"\n"+e.getMessage());
			}
			catch(Exception e1)
			{
				System.out.println(e1);
			}
			return null;
		}
	}


	/**
	 * Purpose - To provide the credentials
	 * @param strPassword - provide password to HttpClient
	 * @param strUserName - provide username to HttpClient
	 * @return HttpClient with credentials
	 */
	public HttpClient provideCredentials(String strUserName,String strPassword) {
		try {
			log.info("providing credentials for authentication");
			log.debug("USERNAME :- " + strUserName + " PASSSWORD :- " + strPassword);
			CredentialsProvider provider = new BasicCredentialsProvider();
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
					strUserName, strPassword);
			provider.setCredentials(AuthScope.ANY, credentials);
			return HttpClientBuilder.create()
					.setDefaultCredentialsProvider(provider).build();
		} catch (Exception e) {
			log.error("error while providing Credentials for authentication"+UtilityMethods.getStackTrace());
			Assert.fail("error while providing Credentials for authentication"+e.getMessage());
			return null;
		}
	}

	public String queryParams__New(String[][] queryParams) {
		
 		StringBuffer query = new StringBuffer();
	    System.out.println(queryParams.length);
		try{
		if ((null != queryParams)&(queryParams.length!=0))
		{
			for (int i = 0,k=1; i < queryParams.length-1; i++,k++) 
			{
				for(int j=0;j<queryParams.length;j++){

				query.append("&").append(queryParams[i][j]+ "="+queryParams[k][j]);
				}
			}
		} 
		}catch(Exception e)
		{		
			System.out.println(e);
		}
		query.deleteCharAt(0);
		return "?" + query.toString();
	}
	public String queryParamsDataProviders(List<String> col_headres,String[] parameters) {
		StringBuffer query = new StringBuffer();
		//		System.out.println((null == queryParams));
		//		System.out.println((null == queryParams)||(queryParams.length==0));
		//		int j=3;
		try{

			{
				for (int k = 0; k < col_headres.size(); k++) 
				{
					//					String parameter=queryParams[i].replace("parameter_", "");
					query.append("&").append(col_headres.get(k)+ "="+ parameters[k]);
				}
			}
			query.deleteCharAt(0);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return "?" + query.toString();
	}


	/**
	 * Purpose - To execute http request method
	 * @param strUserName - Provide username
	 * @param strPassword - provide password
	 * @param strURL - pass the url
	 * @param req_Method - request method
	 * @param headers - required API headers
	 * @param parameters -  request parameters
	 * @return HashMap<String> - returns status code, status message and body
	 */
	@SuppressWarnings("deprecation")
	public HashMap<String, String> executeRequest(String strUserName,
			String strPassword, String strURL, List<String> headers,
			String req_Method, boolean isParamsRequired, String parameters,boolean isCookiesRequired,
			String screenshot,String... request_bodyFile_And_Params) {
//		HttpClient client = null;
		HttpResponse response = null;
	//	HttpGet get = null;
	//	HttpPost post = null;
	//	HttpPut put = null;
	//	HttpDelete delete = null;
		HashMap<String, String> responseMap = new HashMap<>();
		
		String endPoint;
		
		try{
			endPoint=strURL;
			if (client==null){
				
				  client = new DefaultHttpClient();
				    ClientConnectionManager mgr = client.getConnectionManager();
				    HttpParams params = client.getParams();
				    client = new DefaultHttpClient(new ThreadSafeClientConnManager(params, 

				            mgr.getSchemeRegistry()), params);
//				client = new DefaultHttpClient();
			}
			if(isParamsRequired){
				endPoint=endPoint+parameters.trim();
			}
			switch (req_Method) {
			case "GET":
				HttpGet get = (HttpGet) getHttpMethod(endPoint, RequestType.GET);
				if(headers!=null){
					addHeader(get, headers);
				}
				response=execute(client, get);
				break;
			case "POST":
				HttpPost post = (HttpPost) getHttpMethod(endPoint, RequestType.POST);
				if(headers!=null){
					addHeader(post, headers);
				     }
				if(screenshot!=null){
				MultipartEntityBuilder builder = MultipartEntityBuilder.create();
				builder.addTextBody("field1", "yes", ContentType.APPLICATION_OCTET_STREAM);
				File f = new File(screenshot);
				builder.addBinaryBody(
				    "file", new FileInputStream(f),
				    ContentType.APPLICATION_OCTET_STREAM,
				    f.getName()
				);
				HttpEntity multipart = builder.build();
				post.setEntity(multipart);
				}
				if(request_bodyFile_And_Params.length>0){
				String strBody_post = getRequestBody(request_bodyFile_And_Params);
				strBody_post=strBody_post.replaceAll("\n","").replaceAll("\r","");
				strBody_post=strBody_post.replaceAll("\\p{Cc}", "");
				post.setEntity(new StringEntity(strBody_post));
					}
				response=execute(client, post);
				break;
			case "PUT":
				HttpPut put = (HttpPut) getHttpMethod(endPoint, RequestType.PUT);
				if(headers!=null){
				addHeader(put, headers);
			     }
				if(request_bodyFile_And_Params.length>0){
				String strBody_Put = getRequestBody(request_bodyFile_And_Params);
				put.setEntity(new StringEntity(strBody_Put));
				}
				response=execute(client, put);
				break;
			case "DELETE":
				HttpDelete delete = (HttpDelete) getHttpMethod(endPoint, RequestType.DELETE);
				if(headers!=null){
					addHeader(delete, headers);
				}
				response=execute(client, delete);
				break;

			default:
				log.info("Unknown http request method, please check requested method");
				Assert.fail("Unknown http request method, please check requested method");
				break;
			}
		}
		catch(Exception e)
		{
			log.error("Some Exception occured while execute Get request "+e);
			Assert.fail("Some Exception occured while execute Get request "+e.getStackTrace());
		}
		responseMap.put("status code", String.valueOf(response.getStatusLine().getStatusCode()));
		responseMap.put("status message", String.valueOf(response.getStatusLine().getReasonPhrase()));
		responseMap.put("response body",getResponseBody(response));
		if(isCookiesRequired){
			CookieStore cookieStore = ((AbstractHttpClient) client).getCookieStore();
			// get Cookies
			List<Cookie> cookies = cookieStore.getCookies();
			responseMap.put("response Cookies",cookies.toString());
		}
		return responseMap;
	}
	public HashMap<String, String> getcookies(String cookies)
	{
		HashMap<String, String> sessionID_Principal = new HashMap<>();
		String []cookieArr=cookies.split(",");
		try{
			 if (cookieArr[0].contains("JSESSIONID")) 
			 {
		    	  String session_ID[]=cookieArr[0].split("value");
		    	  sessionID_Principal.put("JSession_ID", session_ID[1].split("\\]")[0].split(":")[1]);
			}else
			 Assert.fail("Session id is not displayed from cookies list");
		      
		      if (cookieArr[1].contains("jetbrains.charisma.main.security.PRINCIPAL")) {
		    	  String principal[]=cookieArr[1].split("value");
		    	  sessionID_Principal.put("principal", principal[1].split("\\]")[0].split(":")[1]);
			}else
				Assert.fail("jetbrains.charisma.main.security.PRINCIPAL is not displayed from cookies list");
			
		}
		catch(Exception e){
			Assert.fail("Some exception is occured while retriving cookies"+e.getStackTrace());
			
		}
		return sessionID_Principal;
	}
	/**
	 * Purpose - request body before execute 
	 * @param - request_Body_Data  -parameters values change with body parameters
	 * @return -String  requested body
	 */
	private  String getRequestBody(String ... request_Body_Data) 
	{
		String strLine;
		String strRequestBody ="";
		try{
			String req_Body_File_Location=request_Body_Data[0];
			FileInputStream fstream = new FileInputStream(req_Body_File_Location);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			// Read File Line By Line

			while ((strLine = br.readLine()) != null)
			{
				// Print the content on the console
				strRequestBody = strRequestBody + strLine;
			}
			strRequestBody = strRequestBody.trim();
			if(request_Body_Data.length>1)
			strRequestBody = updateRequestParams(strRequestBody,request_Body_Data);
			// Close the input stream
			in.close();
		}
		catch(IOException  e)
		{
			log.error(" Exception is occured, while reading body from specified location, please check request body file path "+e.getStackTrace());
			Assert.fail("Exception is occured, while reading body from specified location, please check request body file path "+e.getStackTrace());
		}
		return strRequestBody;
	}
	/**
	 * Purpose - To change/Modify the request body before execute 
	 * @param - String - body of the given API
	 * @param - String - parameter values change with body parameters 
	 * @return - String Updated/requested body
	 */
/*	private  String updateRequestParams(String strRequestBody,String[] request_Body_Data) 
	{
		JSONManager jsonObj=new JSONManager();
		String root_Node=request_Body_Data[1];
		String addres_Key=request_Body_Data[2];
		String updateValue=request_Body_Data[3];
		List<String> valuee = jsonObj.getValueFromArrayJsonKey(strRequestBody,root_Node,addres_Key);
		String old_Address = valuee.get(0);
		strRequestBody = strRequestBody.replaceAll(old_Address,updateValue);
		return strRequestBody;
	}*/
	private  String updateRequestParams(String strRequestBody,String[] request_Body_Data) throws UnsupportedEncodingException 
	{
		try{
		List<String> old_Values = new ArrayList<>();
		JSONManager jsonObj=new JSONManager();
		List<String> valuee = jsonObj.getValueFromSimpleJsonKey(strRequestBody,"fields");
		List<String> valuee1_SummaryDecription = jsonObj.getValueFromSimpleJsonKey(valuee.get(0),"summary","description");
		List<String> valuee2 = jsonObj.getValueFromSimpleJsonKey(valuee.get(0), "project", "issuetype");
		List<String> valuee3_project_Key = jsonObj.getValueFromSimpleJsonKey(valuee2.get(0), "key");
		List<String> valuee4_name = jsonObj.getValueFromSimpleJsonKey(valuee2.get(1), "name");
		
		old_Values.add(valuee3_project_Key.get(0));
		old_Values.add(valuee1_SummaryDecription.get(0));
		old_Values.add(valuee1_SummaryDecription.get(1));
		old_Values.add(valuee4_name.get(0));
		for (int i = 0; i < 4; i++) {
			strRequestBody = strRequestBody.replaceAll(old_Values.get(i),URLEncoder.encode(request_Body_Data[i+1]));
		}
		}
		catch(Exception e){
			log.error("Some exception occured while updating request body"+e.getStackTrace());
		}
		
		return java.net.URLDecoder.decode(strRequestBody, "UTF-8");
	}
	/**
	 * Purpose - To verify status
	 * @param - Actual_Code - actual response code
	 * @param expected_Code - expected enum status
	 * 
	 */

	public void verifyStatusCode(String Actual_Code,String expected_Code) {
		log.info("verifying Status code");
		Assert.assertEquals(Actual_Code, expected_Code, "Status code does not matched");

	}
	public void verifyStatusMessage(String Actual_Message,String expected_Message) {
		log.info("verifying Status message");
		Assert.assertEquals(Actual_Message, expected_Message, "Status messages does not matched");

	}

	/**
	 * Purpose - To get response body 
	 * @param response response object
	 * @return  String Response body
	 */
	public String getResponseBody(HttpResponse response){
		try{
			InputStreamReader in = new InputStreamReader(response.getEntity().getContent());
			BufferedReader br=new BufferedReader(in);
			StringBuilder sb = new StringBuilder();
			String responseLine;
			while ((responseLine=br.readLine())!=null) 
			{
				sb.append(responseLine + "\n");
			}
			responseBody=sb.toString();
			return responseBody;
		}
		
		catch(Exception e){
			return null;
		}

	}
	/**
	 * Purpose - To return response value
	 * @param responseBody response object
	 * @param  keys keys from the JSon body
	 * @return  String Response values
	 */
	public  List<String> getValuesforkeys(String responseBody,String ... keys){
	//	List<String> valuee = jsonObj.getValueFromSimpleJsonKey(responseBody, keys);
		return jsonObj.getValueFromSimpleJsonKey(responseBody, keys);
	}		
}