/* ************************************** PURPOSE **********************************

 - This class contains all Generic methods which are not related to specific application
 */


package com.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

import com.datamanager.ConfigManager;
import com.testng.Assert;

import nl.flotsam.xeger.Xeger;


public class UtilityMethods 
{

	private static ConfigManager sys = new ConfigManager();
	private static Logger log = LogManager.getLogger("UtilityMethods");
	private static String fileSeperator = System.getProperty("file.separator");
	private static Thread thread;
	//ip address with regular expression
	private static final String IPADDRESS_PATTERN = 
			"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
					"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
					"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
					"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	/**
	 * Purpose - to get the system name
	 * @return - String (returns system name)
	 */
	public static String machineName()
	{
		String sComputername = null;
		try 
		{
			sComputername=InetAddress.getLocalHost().getHostName();
		} 
		catch (UnknownHostException e) 
		{
			log.error("Unable to get the hostname..."+ e.getCause());
		}
		return sComputername;
	}

	/**
	 * TODO To get the entire exception stack trace
	 * 
	 * @return returns the stack trace
	 */
	public static String getStackTrace()
	{
		String trace = "";
		int i;
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		for(i=0;i<stackTrace.length;i++)
		{
			trace = trace+"\n"+stackTrace[i];
		}
		return trace;
	}
	/**
	 * TODO To get the entire exception stack trace from TestNG Listners
	 * 
	 * @return returns the stack trace
	 */
	public static String getStackTraceFromListners(Throwable errorMessage)
	{
		String trace = "";
		int i;
		StackTraceElement[] stackTrace = errorMessage.getStackTrace();
		for(i=0;i<5;i++)
		{
			trace = trace+"\n"+stackTrace[i];
		}
		return trace;
	}

	/**
	 * Purpose - to get current date and time
	 * @return - String (returns date and time)
	 */
	public static String getCurrentDateTime()
	{
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("dd.MMM.yyyy_HH.mm.ss.SSS");
		return formatter.format(currentDate.getTime());
	}

	/**
	 * Purpose - To convert given time in "yyyy-MM-dd-HH:mm:ss" to IST time
	 * @return date in String format
	 */

	public static String convertToISTTime(String origTime) 
	{

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
		TimeZone obj = TimeZone.getTimeZone("GMT");
		formatter.setTimeZone(obj);
		try 
		{
			Date date = formatter.parse(origTime);
			formatter = new SimpleDateFormat("dd-MMM-yyyy:HH.mm.ss");
			//System.out.println(date);
			return formatter.format(date);
		} catch (ParseException e) {
			log.error("Cannot parse given date .." + origTime);
			log.info("returning current date and time .." + origTime);
		}
		return getCurrentDateTime();
	}    

	/**
	 * Purpose - to display message box
	 * @param infoMessage message
	 * @param location location
	 */
	public static void infoBox(String infoMessage, String location)
	{
		JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + location, JOptionPane.INFORMATION_MESSAGE);
	}


	/**
	 * Purpose - to stop the execution
	 */
	public static void suspendRun(String suspendRunImagePath) 
	{
		Assert.fail("TEST RUN HAS BEEN SUSPENDED");
	}



	/**
	 * Purpose - to generate the random number which will be used while saving a screenshot
	 * @return - returns a random number
	 */
	public static int getRandomNumber()
	{
		Random rand = new Random();
		return rand.nextInt();
	}

	/**
	 * TODO Typecasts the wait time values from String to integer
	 *
	 * @param WaitType time for wait
	 * @return returns value of wait time in integer
	 */
	public static int getWaitTime(String WaitType)
	{
		int iSecondsToWait = 15;
		String wait;
		if(WaitType!=null&&!WaitType.equalsIgnoreCase(""))
		{
			wait = sys.getProperty(WaitType);
		}
		else
		{
			log.error("WaitType cannot be empty...defaulting to MEDIUM WAIT");
			wait = sys.getProperty("WAIT.MEDIUM");
		}
		try
		{
			iSecondsToWait = Integer.parseInt(wait);
		}
		catch(NumberFormatException e)
		{
			log.error("Please check the config file. Wait values must be a number...defaulting to 15 seconds");
		} 		
		return iSecondsToWait;		
	}





	/*
	 * Method: To get caller method and class names
	 */
	/*	public static void preserveMethodName()
	{
		Throwable t = new Throwable(); 
		StackTraceElement[] elements = t.getStackTrace();
		String callerClassName = elements[1].getClassName();
		String callerMethodName = elements[1].getMethodName(); 
		String sMethod = "\"" + callerMethodName + "\"" + " method from " + callerClassName + " class";
		ReportHelper.setsMethodName(sMethod);		
	}*/

	/**
	 * 
	 * TODO method to run balloon popup process/method in a separate thread.
	 *
	 */
	public static void currentRunningTestCaseBalloonPopUp(final String sTestName)
	{
		thread = new Thread() 
		{
			public void run()
			{
				BalloonPopUp.currentRunningTestCase(sTestName);
			}			
		};
		thread.start();
	}

	/**
	 * 
	 * TODO method to verify if the balloon pop process/method is ended or not.
	 */
	public static void verifyPopUp()
	{
	/*	try
		{
			thread.join();
		} catch (InterruptedException e) {
			log.error("balloon popup thread Interrupted"+e.getStackTrace());
		} */
	}


	/**
	 * 
	 * TODO Gives the name of operating system your are currently working on
	 * 
	 * @return returns the OS name
	 */
	public static String getOSName()
	{
		return System.getProperty("os.name");

	}	

	/**
	 * 
	 * TODO Gives the seperator value according to Operation System
	 * 
	 * @return returns the seperator with respect to Operation System
	 */
	public static String getFileSeperator()
	{		
		return System.getProperty("file.separator");
	}

	public static String generateStringFromRegEx(String regEx)
	{
		Xeger generator = new Xeger(regEx);
		return generator.generate();
	}

	/**
	 * 
	 * This method is used to delete all files and folders from specified directory path
	 *
	 * @param sFolderPath , Need to pass the fodler.directory path
	 */
	public void deleteAllExistingFilesOrFoldersFromSpecifiedDirectory(String sFolderPath)
	{
		File folder = new File(sFolderPath);
		try
		{
			FileUtils.cleanDirectory(folder);
			log.info("All files/Folders from specified folder is deleted successfully:"+sFolderPath);
		} 
		catch (IOException exception)
		{
			log.error("Unable to delete files/Folders from specified folder:"+sFolderPath);
			Assert.fail("IO Exception occured while trying to delete files/Fodlers from specified folder, Please close the files if they are opened: "+exception.getMessage());
		}
		catch (Exception exception)
		{
			log.error("Unable to delete files/Folders from specified folder:"+sFolderPath);
			Assert.fail("Some Exception occured while trying to delete files/Fodlers from specified folder, Please close the files if they are opened: "+exception.getMessage());
		}
	}

	/**
	 * This method is used to generate alert stating that Captcha should be entered manually within 30 secs.
	 */
	public void waitForCaptcha(WebDriver driver,int timeInSeconds)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("alert('Please enter captcha manually. Captcha should be entered within "+timeInSeconds+" secs');");

		try {
			Thread.sleep(timeInSeconds*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}



	/**
	 * Validate ip address with regular expression
	 * @param ip ip address for validation
	 * @return true valid ip address, false invalid ip address
	 */
	public static boolean validateIP(final String ip){		  
		return Pattern.compile(IPADDRESS_PATTERN).matcher(ip).find();
	}

	public static File[] fileList(String strDirPath, final String strFileType) {
		return (new File(strDirPath)).listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return (pathname.isFile()&pathname.getAbsolutePath().endsWith(strFileType));
			}
		});
	}

	/**
	 * Validate input value is digit or not
	 * @param str input string value
	 * @return true value is digit, false non digit
	 */
	public static boolean isNumeric(String str)
	{
		for (char c : str.toCharArray())
		{
			if (!Character.isDigit(c)) return false;
		}
		return true;
	}
	/**
	 * Purpose - This method will get the video Url
	 * @param driver RemoteWebDriver
	 * @return session id and video url
	 */
	public static String[] getNodevideoUrl(RemoteWebDriver driver)
	{
		URL videoUrl = null;
		SessionId sessionId =  driver.getSessionId();
		try {
			if(driver != null) {
				URL remoteServer = ((HttpCommandExecutor) driver.getCommandExecutor()).getAddressOfRemoteServer();
				videoUrl = new URL(remoteServer, "/grid/admin/HubVideoDownloadServlet/?sessionId=" + sessionId);
				System.out.println("Video URL is : "+videoUrl);
			}
		}
		catch (Throwable e) {
			e.printStackTrace();
			String message = e.getClass().getName();
			throw new RuntimeException("Test failed due to exception: " + message + ". Video available at " + videoUrl, e);
		}
		String[] data={sessionId.toString(),videoUrl.toString()};
		return data;
	}
	/**
	 * Purpose - This method will get the filename of the video saved in temp folder
	 * @param sessionId - provide session id
	 * @return filename
	 */
	public static String getNodeVideoFileName(String sessionId){
		String fileName = null;
		String filePath=System.getProperty("user.dir")+fileSeperator+"Resources"+fileSeperator+sys.getProperty("FileName");
		BufferedReader inputReader = null;
		try {
			Thread.sleep(5000);
			inputReader = new BufferedReader(new FileReader(new File(filePath)));
			String line = null;
			while(true)
			{
				line=inputReader.readLine();
				if (line == null) {
					break; 
				}
				if(line.contains("Successfully retrieved video for session: "+sessionId))
				{
					String[] content=line.split(": ");
					String hubfilePath[]=content[2].split("Temp");
					fileName=hubfilePath[1].substring(1);
					break;
				}

			}
		}
		catch (IOException e) {
			log.info("The file " + fileName + " not found ");
			e.printStackTrace();
		}
		catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		return fileName;
	}
	
	/**
	 * This method will copy the video from Temp folder to the desired folder 
	 * @param fileName - provide filename that is to be copied
	 * @param destLoc - provide destination loaction to which file has to be copied
	 * @param TestName - 
	 */
	public static String copyNodeVideoFile(String fileName,String destLoc,String TestName,String dateAndTime)
	{
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		String renamewithTC=null;
		String nodeVideoStorageloc=System.getProperty("java.io.tmpdir");
		String srcFilePath=nodeVideoStorageloc+fileName;
		File srcFile=new File(srcFilePath);
		try {
		
			FileUtils.copyFileToDirectory(srcFile,new File(destLoc));
			String destFilePath=destLoc+fileName;
			File desFile=new File(destFilePath);
			if(desFile.exists())
			{
				renamewithTC=destLoc+TestName+dateAndTime+".webm";
				desFile.renameTo(new File(renamewithTC));
			}

		} catch (IOException e) {
			log.info("The file " + fileName + " not found ");
			e.printStackTrace();
		}
		return renamewithTC;
	}

	
	/**
	 * Used to convert string into base 64 encode format
	 * @param inputString input string
	 * @return returned base 64 encode string
	 */
	 public static String getBase64EncodeString(String inputString){
		   String base64encodedString = "";
		   try{
			   base64encodedString= Base64.getEncoder().encodeToString(inputString.getBytes("utf-8"));
		   }catch(UnsupportedEncodingException e){
			    log.error("Unable to encode the given string into base 64 encode format"+e.getStackTrace());
				Assert.fail("Unable to encode the given string into base 64 encode format"+e.getStackTrace());
		   }
			return base64encodedString;
	 }	 

	/**
	 * Used to convert string into base 64 encode format
	 * @param inputString  input string
	 * @return returned browser name string
	 */
	public static String getBrowserName(String inputString) {
		String browserName = "";
		try {
			if (inputString.contains("Chrome")) {
				browserName = "Chrome";
			} else if (inputString.contains("MSIE")) {
				browserName = "Internet Explorer";
			} else
				browserName = "Firefox";
		} catch (Exception e) {
			log.error("Unable to find current browser"+ e.getStackTrace());
			Assert.fail("Unable to find current browser"+ e.getStackTrace());
		}
		return browserName;
	}
	/**
	 * Replacing run time variables
	 * @param strInputString input string value
	 * @param replace_Value value is digit, false non digit
	 * @return replaced string
	 */
	 public static String replaceVariablesWithRuntimeProperties(String strInputString, String replace_Value){

			String guess = "$";
			
			try{
				log.info("replacing variables");
			while(strInputString.indexOf(guess)>=0){
				int startingindex=strInputString.indexOf(guess);
				int Endingindex=strInputString.indexOf("}")+1;
				while(Endingindex<startingindex){
					Endingindex=strInputString.indexOf("}",Endingindex)+1;
				}
				String substr=strInputString.substring(startingindex, Endingindex);
				strInputString=strInputString.replace(substr,replace_Value);
			}
			log.info(strInputString);
			return strInputString;
			}
			catch(Exception e){
				log.error("Error while replacing values");
				return strInputString;
			}
		}
	/**
	  * Used to get date and time on specific format basis
	  * @param format_Needed input string value of format
	  * @return fomated date and time string
	  */
		public static String getDateTimeInSpecificFormat(String format_Needed)
		{
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format_Needed);
				LocalDateTime localDate = LocalDateTime.now();
				return dtf.format(localDate);
				 
		}
		
}