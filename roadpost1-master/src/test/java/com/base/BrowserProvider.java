package com.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * Class which provides the run-time object for Browser Classes
 */
public class BrowserProvider {
	private Logger log = LogManager.getLogger("BrowserProvider");
//	ConfigManager sys = new ConfigManager();

	/**
	 * @param browserName  need to pass the Browser name
	 * @return browser class's object referring IBrowser Interface
	 * 
	 *         Method which creates the run-time object for Browser Classes
	 *         depending on the input of browserName,If invalid browser name is
	 *         passed, by default it'll set chrome browser
	 */

	protected IBrowser getBrowserInstance(String browserName) {
		if (browserName == null) {
			log.info("The Browser Name is provided as Null,Please provide the correct BrowserName in testNg.xml");
			return null;
		}

		switch(browserName){
			case "chrome":
				log.info("The Browser Name is provided as Chrome");
				return new ChromeBrowser();

			case "firefox":
				log.info("The Browser Name is provided as Firefox");
				return new FirefoxBrowser();

			case "iexplore":
				log.info("The Browser Name is provided as IE");
				return new IeBrowser();

			case "safari":
				log.info("The Browser Name is provided as Safari");
				return new SafariBrowser();

			case "headless":
				log.info("The Browser Name is provided as Headless");
				return new GhostDriver();

			case "mobileChrome":
				log.info("The Browser Name is provided as chrome in android mobile");
				return new MobileClass();

			case "mobileSafari":
				log.info("The Browser Name is provided as safari in ios mobile");
				return new MobileClass();

			case "edge":
				log.info("The Browser Name is provided as safari in ios mobile");
				return new EdgeBrowser();

			default:
				log.error("browser : " + browserName
						+ " is invalid, Launching Firefox as browser of choice..");
				return new ChromeBrowser();
		}
	}
}