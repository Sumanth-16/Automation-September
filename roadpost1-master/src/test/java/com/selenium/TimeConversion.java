package com.selenium;

import javax.xml.bind.SchemaOutputResolver;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class TimeConversion {

	private static String fileSeparator = System.getProperty("file.separator");
	private static String pathToFailures = System.getProperty("user.dir") + fileSeparator + "ConfigFiles" + fileSeparator
			+ "FailedTestCases.properties";
	private static String actual_overviewlineitem = ("<table class=\"overviewTable\"><tr><th colspan=\"6\" class = \"header suite\">SUMMARY</th></tr>");
	private static StringBuilder expected_overviewlineitem = new StringBuilder()
			.append("<table class=\"overviewTable\"><tr><th colspan=\"7\" class = \"header suite\">SUMMARY</th></tr>\n")
			.append("<tr class=\"columnHeadings\">\n").append(" <td>&nbsp;</td>\n")
			.append(" <th style=\"width:100px\">Execution Duration</th> \n")
			.append(" <th style=\"width:100px\">Passed</th> \n  <th style=\"width:100px\">Skipped</th> \n  <th style=\"width:100px\">Failed</th> \n <th style=\"width:100px\">Pass Rate</th> \n");
	private static String totallineitem = ("<td colspan=\"2\" class=\"totalLabel\">Total</td>");
	private static String passrateitem = ("<td class=\"passRate suite\">");

	private static FileInputStream fis;
	private static Properties failures_file = new Properties();

	public static void main(String[] args) throws IOException, InterruptedException {

		FileReader fr = null;
		FileWriter fw = null;
		fis = new FileInputStream(pathToFailures);
		failures_file.load(fis);

		String fOverviewPath = System.getProperty("user.dir") + fileSeparator + "Automation Reports" + fileSeparator
				+ "Reports" + fileSeparator + "html" + fileSeparator + "overview.html";
		String fTimeConvertedPath = System.getProperty("user.dir") + fileSeparator + "Automation Reports"
				+ fileSeparator + "Reports" + fileSeparator + "html" + fileSeparator + "TimeConverted.html";
		File fOverview = new File(fOverviewPath);
		File fTimeConverted = new File(fTimeConvertedPath);
		String duration = failures_file.getProperty("ExecutionTime");

		try {
			fr = new FileReader(fOverview);
			fw = new FileWriter(fTimeConverted);

			BufferedReader br = new BufferedReader(fr);
			String line;
			int linecount = 0, skiplinecount = 0, totalcount = 0, passrateitemcount = 0;
			boolean flag = false;
			while ((line = br.readLine()) != null) {
				linecount++;
				if (line.contains("class=\"duration\"")) {
					fw.write(line + "\n");
					fw.write(timeConversion(br.readLine()) + "\n");

				} else if (line.contains(actual_overviewlineitem)) {
					fw.write(expected_overviewlineitem + "\n");
					skiplinecount = linecount;
					flag = true;

				} else if (line.contains(totallineitem)) {

					totalcount++;
					if (totalcount == 2) {
						fw.write("<td colspan=\"1\" class=\"totalLabel\">Total</td>" + "\n"
								+ "<td class=\"duration\"><b>" + duration.toUpperCase() + "</b></td>");
					} else {
						fw.write(line + "\n");
					}
				} else if (line.contains(passrateitem)) {
					passrateitemcount++;
					if (passrateitemcount == 2)
						fw.write("<td class=\"passRate\"> \n");
					else
						fw.write(line + "\n");

				}

				else {
					if ((linecount > skiplinecount && linecount <= skiplinecount + 7) && flag == true)
						//continue;
						System.out.println("");
					else
						fw.write(line + "\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(fr);
			close(fw);
		}

		fOverview.delete();
		Path source = fTimeConverted.toPath();
		try {
			Files.move(source, source.resolveSibling("overview.html"));
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public static void close(Closeable stream) {
		try {
			if (stream != null) {
				stream.close();
			}
		} catch (IOException e) {
			// log.error(e.getMessage());
		}
	}

	private static String timeConversion(String s2) {

		String s1 = s2.split("s")[0].trim();
		double d = Double.parseDouble(s1);
		int t = (int) d;

		if (t < 60) {
			return "0H:0M:" + t + " S";
		} else if (t >= 60 && t < 3600) {
			int minu = t / 60;
			int s = t % 60;
			return "0H:" + minu + "M:" + s + " S";
		} else {
			int hours = t / 3600;
			int minu = (t % 3600) / 60;
			int s = ((t % 3600) % 60);
			return hours + "H:" + minu + "M:" + s + " S";
		}
	}

}
