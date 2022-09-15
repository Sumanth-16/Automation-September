package com.jarconversions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class is used to launch hub that records video .
 * If you wish to launch hub with desired version of jar files(Selenium-video-node & Selenium-server-standalone),
 * Please change the Version in the command passed to ProcessBuilder
 * @author archana.suraparaju
 */

public class LaunchHub {
	private static String fileSeperator = System.getProperty("file.separator");
	private static String userdir=System.getProperty("user.dir");

	public static void main(String []args) throws IOException{
		try {
			String filePath=new File(userdir).getParent()+fileSeperator;
			File file=new File(filePath+"HubLog.txt" );
			ProcessBuilder builder = new ProcessBuilder(
					"cmd.exe", "/c", "java -cp selenium-video-node-2.2.jar;selenium-server-standalone-3.4.0.jar org.openqa.grid.selenium.GridLauncherV3 -servlets com.aimmac23.hub.servlet.HubVideoDownloadServlet -role hub");
										
			builder.redirectErrorStream(true);
			Process p =  builder.start();
			FileOutputStream fos =  new FileOutputStream(file);
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while (true) {
				line = r.readLine();
				if (line == null) {
					break; 
				}
				if(line.contains("Error: Could not find or load main class org.openqa.grid.selenium.GridLauncher")){
					System.out.println("Unable to access jar files 'Selenium Standalone jar -3.4.0'... if you would like to update/degrade selelenium jar version,\nkindly change selenium version in 'com.jarconversions/LauchHub.java' and convert into 'Runnable jar' file with naming 'SetupHub.jar' and replace it in 'lib' folder. ");
					break;
				}
				System.out.println(line);
				byte[] contentInBytes = line.getBytes();
				fos.write(contentInBytes);
				fos.flush();
				fos.write('\n');
			}
		} catch(IOException e){
			System.out.println("Execption occurred while launching hub");
			e.printStackTrace();
		} 
	}
}
