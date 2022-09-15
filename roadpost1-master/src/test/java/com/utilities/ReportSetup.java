
package com.utilities;

import com.testng.Assert;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReportSetup {
    private static String fileSeperator = System.getProperty("file.separator");
    private static Logger log = LogManager.getLogger("ReportSetup");
    private static boolean isDirCreated = true;
    private static String message;
    private static String sBuff;
    private static FileWriter fW;
    private static String sPageLoadfilepath;

    public ReportSetup() {
    }

    public static void createFolderStructure() {
        createReportsFolder();
        createLatestResultsFolder();
        createMediaFolders();
    }

    public static void createReportsFolder() {
        File file = new File(getReportsPath());
        if (!file.exists()) {
            isDirCreated = file.mkdir();
        }

        if (!isDirCreated) {
            message = "\n Exception occured while creating 'Automation Results' directory";
            log.error("Check folder permissions of Project Directory..." + message);
            Assert.fail("Check folder permissions of Project Directory..." + message);
        }

    }

    public static void createLatestResultsFolder() {
        try {
            File latestResults = new File(getLatestResultsPath());
            if (latestResults.exists()) {
                Path p = Paths.get(getLatestResultsPath());
                BasicFileAttributes view = ((BasicFileAttributeView)Files.getFileAttributeView(p, BasicFileAttributeView.class)).readAttributes();
                String fCreationTime = view.creationTime().toString();
                String istTime = UtilityMethods.convertToISTTime(fCreationTime.split("\\.")[0].replace("T", "-"));
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
        } catch (IOException var7) {
            log.error("Exception occured while creating 'Latest Results' directory or unable to rename current 'Latest Results' directory " + var7.getCause());
            Assert.fail("Exception occured while creating 'Latest Results' directory or unable to rename current 'Latest Results' directory " + var7.getCause());
        }

    }

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

    public static String getVideosPath() {
        return getLatestResultsPath() + fileSeperator + "Videos";
    }

    public static String getImagesPath() {
        return getLatestResultsPath() + fileSeperator + "Screenshots";
    }

    public static String getLatestResultsPath() {
        return getReportsPath() + fileSeperator + "LatestResults";
    }

    public static String getReportsPath() {
        return System.getProperty("user.dir") + fileSeperator + "Automation Reports";
    }

    public static void Report_PageLoadTime(String sSource, String sDestination, long sPageLoadTime) {
        sPageLoadfilepath = getLatestResultsPath() + fileSeperator + "PageLoadTime_Summary.html";
        String sMessage = "Page load time taken while navigating from '" + sSource + "' to '" + sDestination + "'";
        File file = new File(sPageLoadfilepath);
        if (file.exists()) {
            try {
                fW = new FileWriter(file, true);
                Write(sMessage, sPageLoadTime);
                log.info(sMessage + " has been logged in report");
            } catch (Exception var8) {
                System.out.println("exception =" + var8.getMessage());
                log.error(sMessage + " has not been logged in report");
            }
        } else {
            sBuff = "";

            try {
                fW = new FileWriter(file);
                Write(sMessage, sPageLoadTime);
                log.info(sMessage + " has been logged in report");
            } catch (Exception var7) {
                System.out.println("exception =" + var7.getMessage());
                log.error(sMessage + " has not been logged in report");
            }
        }

    }

    private static void Write(String sMessage, long sPageLoadTime) {
        try {
            if (sBuff == "done") {
                fW.write("<tr><td align=center><b>" + sMessage + "</b></td><td align=center ><b>" + sPageLoadTime + "</b></td></tr>");
                fW.close();
            } else {
                fW.write("<html><style type=text/css>table {font-size: 100%;}p{color:brown;text-align:center;font-family:verdana;font-weight:bold;font-size=12px;}</style><body><center><font color=blue face=Verdana>");
                fW.write("<body <style type=text/css></style><body><center><font color=blue face=Verdana size=4> <b> PAGE LOAD TIME </b><br/><br/>");
                fW.write("<table align=center border=1 width=100%><col width=500/><col width=50/><tr><th bgcolor=gray>Page Navigation &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th><th bgcolor=gray>Page Load Time (in MilliSeconds)</th></tr></font>");
                fW.write("<tr><td align=center><b>" + sMessage + "</b></td><td align=center ><b>" + sPageLoadTime + "</b></td></tr>");
                fW.close();
                sBuff = "done";
            }
        } catch (Exception var4) {
            log.error("Error message is :" + var4);
        }

    }

    public static void Report_PageLoadTime(String sURL, long sPageLoadTime) {
        sPageLoadfilepath = getLatestResultsPath() + fileSeperator + "PageLoadTime_Summary.html";
        String sMessage = "Page load time taken for navigating to '" + sURL + "' url";
        File file = new File(sPageLoadfilepath);
        if (file.exists()) {
            try {
                fW = new FileWriter(file, true);
                Write(sMessage, sPageLoadTime);
                log.info(sMessage + " has been logged in report");
            } catch (Exception var7) {
                System.out.println("exception =" + var7.getMessage());
                log.error(sMessage + " has not been logged in report");
            }
        } else {
            sBuff = "";

            try {
                fW = new FileWriter(file);
                Write(sMessage, sPageLoadTime);
                log.info(sMessage + " has been logged in report");
            } catch (Exception var6) {
                System.out.println("exception =" + var6.getMessage());
                log.error(sMessage + " has not been logged in report");
            }
        }

    }
}
