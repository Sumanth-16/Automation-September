/*************************************** PURPOSE **********************************

 - This class contains all methods related to HTML reporting
*/
package jvm;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class ReportSetup {
	static Properties properties = new Properties();
	private static String fileSeperator = System.getProperty("file.separator");
	private static Logger log = LogManager.getLogger("ReportSetup");
	private static boolean isDirCreated = true;
	private static String message;
	static FileInputStream fis;

	public static void main(String[] args) {
		try {
			fis = new FileInputStream(
					System.getProperty("user.dir") + fileSeperator + "ConfigFiles" +File.separator+ "Sys.properties");
			properties.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int count = Integer.parseInt(properties.getProperty("NumOfReports"));
		keepLatestReportsBasedOnGivenCountAndRemoveOldReports(count);
		createFolderStructure();
	}

	/**
	 * This method setup's reporting environment i.e., creating a root folder
	 * and destination folder for storing report information
	 */
	public static void createFolderStructure() {
		createReportsFolder();
		createLatestResultsFolder();
		createMediaFolders();
		if(new File("Automation Reports/Log.log").exists()){
			new File("Automation Reports/Log.log").delete();
		}

	}

	/**
	 * This method creates 'Automation Reports' directory if it does not exist
	 */
	public static void createReportsFolder() {
		File file = new File(getReportsPath());
		if (!file.exists()) {
			System.out.println("Automation Folder is created using ReportSetUp class");
			log.info("Automation Folder is created using ReportSetUp class");
			isDirCreated = file.mkdir();
		}
		if (!isDirCreated) {
			message = "\n Exception occured while creating 'Automation Results' directory";
			log.error("Check folder permissions of Project Directory..." + message);
			Assert.fail("Check folder permissions of Project Directory..." + message);
		}
	}

	/**
	 * Purpose - to get current date and time
	 * 
	 * @return - String (returns date and time)
	 */
	public static String getCurrentDateTime() {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy:HH.mm.ss");
		String dateNow = formatter.format(currentDate.getTime());
		return dateNow;
	}

	/**
	 * This method creates 'Latest Reports' directory if it does not exist if
	 * directory exists it renames to Results_on_<currentDataTime> folder name
	 * and creates 'Latest Reports' directory
	 */
	public static void createLatestResultsFolder() {
		try {
			File latestResults = new File(getLatestResultsPath());
			if (latestResults.exists()) {
				Path p = Paths.get(getLatestResultsPath());
				BasicFileAttributes view;

				view = Files.getFileAttributeView(p, BasicFileAttributeView.class).readAttributes();
				String fCreationTime = view.creationTime().toString();
				String istTime = convertToISTTime(fCreationTime.split("\\.")[0].replace("T", "-"));
				String oldFolder = getReportsPath() + fileSeperator + "Results_on_" + istTime.replace(":", "_at_");
				File oldResults = new File(oldFolder);
				latestResults.renameTo(oldResults);

			}
			isDirCreated = latestResults.mkdir();
			if (!isDirCreated) {
				message = "\n Exception occured while creating 'Latest Results' directory";
				log.error("Check folder permissions of Project Directory..." + message);
				Assert.fail("Check folder permissions of Project Directory..." + message);
			}
		} catch (IOException e) {
			log.error(
					"Exception occured while creating 'Latest Results' directory or unable to rename current 'Latest Results' directory "
							+ e.getCause());
			Assert.fail(
					"Exception occured while creating 'Latest Results' directory or unable to rename current 'Latest Results' directory "
							+ e.getCause());
		}
	}

	/**
	 * Purpose - To convert given time in "yyyy-MM-dd-HH:mm:ss" to IST time
	 * 
	 * @returns date in String format
	 * @throws Exception
	 */

	public static String convertToISTTime(String origTime) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
		TimeZone obj = TimeZone.getTimeZone("GMT");
		formatter.setTimeZone(obj);
		try {
			Date date = formatter.parse(origTime);
			formatter = new SimpleDateFormat("dd-MMM-yyyy:HH.mm.ss");
			return formatter.format(date);
		} catch (ParseException e) {
			log.error("Cannot parse given date .." + origTime);
			log.info("returning current date and time .." + origTime);
		}
		return getCurrentDateTime();
	}

	/**
	 * This method creates 'videos, screenshots' directory if they does not
	 * exist
	 */
	public static void createMediaFolders() {
		File videosFolder = new File(getVideosPath());
		if (!videosFolder.exists()) {
			isDirCreated = videosFolder.mkdir();
		}
		if (!isDirCreated) {
			message = "\n Exception occured while creating 'Latest Results/videos' directory";
			log.error("Check folder permissions of Project Directory..." + message);
			Assert.fail("Check folder permissions of Project Directory..." + message);
		}
		File imagesFolder = new File(getImagesPath());
		if (!imagesFolder.exists()) {
			isDirCreated = imagesFolder.mkdir();
		}
		if (!isDirCreated) {
			message = "\n Exception occured while creating 'Latest Results/screenshots' directory";
			log.error("Check folder permissions of Project Directory..." + message);
			Assert.fail("Check folder permissions of Project Directory..." + message);
		}
	}

	/**
	 * @return - This method returns path to the folder where screen recordings
	 *         are stored
	 */
	public static String getVideosPath() {
		return getLatestResultsPath() + fileSeperator + "Videos";
	}

	/**
	 * @return - This method returns path to the folder where screenshots are
	 *         stored
	 */
	public static String getImagesPath() {
		return getLatestResultsPath() + fileSeperator + "Screenshots";
	}

	/**
	 * @return - This method returns path to the folder where latest results are
	 *         stored
	 */
	public static String getLatestResultsPath() {
		return getReportsPath() + fileSeperator + "LatestResults";
	}

	/**
	 * @return - This method returns the path to the root of reports folder
	 */
	public static String getReportsPath() {
		return System.getProperty("user.dir") + fileSeperator + "Automation Reports";

	}

	/**
	 * Purpose - Removes all existing previous automation reports except the
	 * latest reports
	 * 
	 * @param count
	 *            - provide the count no of reports that has to be present
	 */
	public static void keepLatestReportsBasedOnGivenCountAndRemoveOldReports(int count) {
		try {

			File dir = new File(getReportsPath());
			if (dir.exists()) {
				FileFilter fileFilter = new WildcardFileFilter("*Results_on_*");
				File[] files = dir.listFiles(fileFilter);
				if (files.length > 0) {
					/** The newest file comes first **/
					Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
					if (count < files.length) {
						for (int i = count; i < files.length; i++) {
							deleteFile(files[i]);
						}
					} else
						log.info("Files avilable in the folder are less than " + count);

				}
			}
		} catch (Exception e) {
			log.error("File you are searching is not found in downloaded folder");
		}
	}

	private static void deleteFile(File tempFile) {
		try {
			if (tempFile.isDirectory()) {
				File[] entries = tempFile.listFiles();
				for (File currentFile : entries) {
					deleteFile(currentFile);
				}
				tempFile.delete();
			} else {
				tempFile.delete();
			}

		} catch (Exception e) {
			log.error("Could not DELETE file: " + tempFile.getPath(), e);
		}
	}
}
