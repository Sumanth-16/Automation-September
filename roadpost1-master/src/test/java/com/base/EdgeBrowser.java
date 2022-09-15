package com.base;

import com.datamanager.ConfigManager;
import com.testng.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * This class defines all methods required to initialize edgeDriver So far
 * only one method is written to initialize with default settings
 */
public class EdgeBrowser implements IBrowser {
    ConfigManager sys = new ConfigManager();
    private Logger log = LogManager.getLogger("EdgeBrowser");
    private WebDriver driver;
    private String fileSeperator = System.getProperty("file.separator");

    /**
     * This method initiates edge browser and returns the driver object
     *
     * @return , returns the driver object after initiating edge browser
     */
    public WebDriver init() {
        driver = initEdgeDriver();
        return driver;
    }

    /**
     * This method initiates edge browser with default profile and returns the
     * driver object
     *
     * @return , returns the driver object after initiating edge browser
     */
    private WebDriver initEdgeDriver() {
        log.info("Launching Edge with new profile..");
        System.setProperty("webdriver.edge.driver", getDriverPath());
        return new EdgeDriver();
    }


    /**
     * This method returns the location of edgedriver based on Operating
     * system he scripts executed
     *
     * @return, returns edgedriver.exe file path
     */
    private String getDriverPath() {

        String edgeLocation = System.getProperty("user.dir") + fileSeperator
                + "Resources" + fileSeperator + "Drivers" + fileSeperator;
        edgeLocation = edgeLocation + "MicrosoftWebDriver.exe";
        return edgeLocation;

    }


    /**
     * Method to get file download path location
     *
     * @return - returns file download path
     */
    private String getDownloadLocation() {
        String DownloadPath = System.getProperty("user.dir") + fileSeperator
                + "Downloaded Files";
        return DownloadPath;
    }
}
