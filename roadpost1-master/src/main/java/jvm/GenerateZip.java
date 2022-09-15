package jvm;

import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class GenerateZip {

	public static String RESULT_FILENAME = "Reports.png";
	public static String ZIP_Filename = "TestResults.zip";
	static WebDriver driver;
	public static String fileSeparator = System.getProperty("file.separator");
	static String sInputDirectoryToZip = System.getProperty("user.dir") + fileSeparator + "Automation Reports"
			+ fileSeparator + "LatestResults";
	static String sOutputDirectory = System.getProperty("user.dir") + fileSeparator + "Target" + fileSeparator
			+ "TestResults.zip";
	static String htmlFile = "file://" + System.getProperty("user.dir") + fileSeparator + "Automation Reports"
			+ fileSeparator + "LatestResults" + fileSeparator + "html" + fileSeparator + "index.html";
	static String newFile = System.getProperty("user.dir") + fileSeparator + "Automation Reports" + fileSeparator
			+ "LatestResults" + fileSeparator + "html" + fileSeparator;
	String fileSeperator = System.getProperty("file.separator");

	/**
	 * This method is to add Zip file along with Image.
	 */
	public String addZipAndImage() throws IOException {
		try {
			FileOutputStream fos = new FileOutputStream(sOutputDirectory);
			ZipOutputStream zos = new ZipOutputStream(fos);
			zos.setLevel(9);
			addFolder(zos, sInputDirectoryToZip, sInputDirectoryToZip);
			zos.close();
			// The below commented code was used to take ScreenShot and attach
			// to the mail body.
			// System.out.println("Tipped Successfully");
			// System.setProperty("webdriver.chrome.driver", getDriverPath());
			// ChromeOptions options = new ChromeOptions();
			// options.addArguments("--test-type", "start-maximized");
			// options.addArguments("--disable-extensions");
			// options.addArguments("disable-infobars");
			// driver = new ChromeDriver(options);
			// driver.get(htmlFile);
			// driver.manage().window().maximize();
			// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			// File scrFile = ((TakesScreenshot) driver)
			// .getScreenshotAs(OutputType.FILE);
			// FileUtils.copyFile(scrFile, new File(newFile+RESULT_FILENAME));
			// driver.quit();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sOutputDirectory;

	}

	/**
	 * This method is to add Folder.
	 * 
	 * @param zos
	 * @param folderName
	 * @param baseFolderName
	 * @throws Exception
	 */
	private static void addFolder(ZipOutputStream zos, String folderName, String baseFolderName) throws Exception {
		File f = new File(folderName);
		if (f.exists()) {
			if (f.isDirectory()) {
				if (!folderName.equalsIgnoreCase(baseFolderName)) {
					String entryName = folderName.substring(baseFolderName.length() + 1, folderName.length())
							+ File.separatorChar;
					ZipEntry ze = new ZipEntry(entryName);
					zos.putNextEntry(ze);
				}
				File f2[] = f.listFiles();
				for (int i = 0; i < f2.length; i++) {
					if (!(f2[i].toString()).contains("Videos"))
						addFolder(zos, f2[i].getAbsolutePath(), baseFolderName);
				}
			} else {
				// extract the relative name for entry purpose
				String entryName = folderName.substring(baseFolderName.length() + 1, folderName.length());
				ZipEntry ze = new ZipEntry(entryName);
				if (entryName.equals("html\\reportng.js") || entryName.equals("html\\sorttable.js")) {
				} else {
					zos.putNextEntry(ze);
					FileInputStream in = new FileInputStream(folderName);
					int len;
					byte buffer[] = new byte[1024];
					while ((len = in.read(buffer)) > 0) {
						zos.write(buffer, 0, len);
					}
					in.close();
					zos.closeEntry();
				}
			}
		} else {
			System.out.println("File or directory not found " + folderName);
		}
	}

	/**
	 * 
	 * This method returns the location of chromedriver based on Operating
	 * system he scripts executed
	 * 
	 * @return, returns chromedriver.exe file path
	 */
	public String getDriverPath() {

		String chromeLocation = System.getProperty("user.dir") + fileSeperator + "Resources" + fileSeperator + "Drivers"
				+ fileSeperator;

		if (System.getProperty("os.name").toLowerCase().contains("windows"))
			chromeLocation = chromeLocation + "chromedriver.exe";
		else if (System.getProperty("os.name").toLowerCase().contains("mac"))
			chromeLocation = chromeLocation + "chromedriver";

		return chromeLocation;

	}
}
