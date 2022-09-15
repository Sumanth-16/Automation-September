package jvm;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class CopyReportNGReports {
	static String reportDirectoryPath = System.getProperty("user.dir") + File.separator + "Automation Reports"
			+ File.separator + "LatestResults" + File.separator + "html" + File.separator;
	static String userDir=System.getProperty("user.dir");
	public static void main(String[] args) {
		String sSource = System.getProperty("user.dir") + File.separator + "target" + File.separator
				+ "surefire-reports" + File.separator + "html";
		String sDest = System.getProperty("user.dir") + File.separator + "Automation Reports" + File.separator
				+ "LatestResults" + File.separator + "html";
		String xml = System.getProperty("user.dir") + File.separator + "target" + File.separator + "surefire-reports"
				+ File.separator + "xml";
		String testngResults = System.getProperty("user.dir") + File.separator + "target" + File.separator
				+ "surefire-reports" + File.separator + "testng-results.xml";
		String logsrcfile = System.getProperty("user.dir") + File.separator + "Automation Reports" + File.separator + "Logs.log";
		//String logsrcfile = System.getProperty("user.dir") + File.separator + "target" + File.separator + "Log.log";
		File source = new File(sSource);
		File dest = new File(sDest);
		if (!dest.exists()) { 
			System.out.println("Automation Folder is created using CopyReportSetUp class");
			dest.mkdir();
		}
		try {
			FileUtils.copyDirectory(source, dest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		source = new File(xml);
		try {
			FileUtils.copyDirectory(source, new File(System.getProperty("user.dir") + File.separator
					+ "Automation Reports" + File.separator + "LatestResults/xml"));
			//FileUtils.copyDirectory(source, new File(System.getProperty("user.dir") + File.separator
				//	+ "Automation Reports" + File.separator + "LatestResults/xml"));
			FileUtils.copyFileToDirectory(new File(logsrcfile), new File(System.getProperty("user.dir") + File.separator
					+ "Automation Reports" + File.separator + "LatestResults"+File.separator));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		source = new File(testngResults);
		try {
			FileUtils.copyFileToDirectory(source, new File(System.getProperty("user.dir") + File.separator
					+ "Automation Reports" + File.separator + "LatestResults"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			
			String osName = System.getProperty("os.name").toLowerCase();
			if (osName.contains("linux")) {
				
				  Runtime.getRuntime() .exec("/usr/bin/google-chrome --new-window " +userDir+File.separator+"Automation"+"%20"+"Reports"
				  +File.separator+"LatestResults"+File.separator+"html"+
				  File.separator+"index.html");
				
				  

			} else if (osName.contains("windows")) {


				Runtime.getRuntime().exec("cmd.exe /c start firefox -new-window \"" + reportDirectoryPath + "\\index.html" + "\"");

			}

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
