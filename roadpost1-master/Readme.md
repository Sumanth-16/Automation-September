# Selenium Java Framework

> Automation framework developed using Java + Selenium WebDriver + TestNG with Page Object Model which can be used across different web based applications in both mobile and desktop browsers.

With this framework in place, whenever we need to automate a web based application, we would not need to start from scratch

![HitCount](http://hits.dwyl.io/praveenlakkimsetti/seleniumjavaframework.svg)
![Maintenance](https://img.shields.io/badge/Maintained%3F-yes-green.svg)
![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)
<a href="http://gitlab.zenq.com/praveen.lakkimsetti/seleniumjavaframework/commits/master"><img alt="pipeline status" src="http://gitlab.zenq.com/praveen.lakkimsetti/seleniumjavaframework/badges/master/pipeline.svg" /></a>
[![Ask Me Anything !](https://img.shields.io/badge/Ask%20me-anything-1abc9c.svg)](mailto:venkatasai.annam@zenq.com?Subject=Selenium_Java_Concern)

---
## Available Sample Scripts

 - Web Application
 - Mobile Application
 - Data Driven

*** For AUT info please refer `ConfigFiles/App.properties`

---
## Features

 - Auto emailable test reports
 - ARES Dashboard
 - Balloon Pop-Up
 - Batch file execution
 - Local Dashboard (Run time report)
 - Logging
 - Data Driven tests
 - Listeners
 - Object Repository
 - Reporting
 - Selenium Grid implementation
 - Utilities
 - Test Rail Management Tool
 - Slack And MS Teams Integration with Jenkins
 - Zephyr cloud for jira integration
 - Slack Integration without CI Tool
 - Teams Integration without CI Tool
 
 __Details:__

 - _Batch File Execution_
   User can execute suites by simply executing batch files:
    - Windows: Execute the tests by double click on a `RunTests.bat` file
    - Mac: Execute the tests by double click on a `RunTests.sh` file
    - Linux: Execute `sh RunTests` command in terminal (root folder)

 - _Logging_
    - Log4j is configured to capture the test execution logs
    - Configuration file is located at `ConfigFiles/log4j.xml`
    - Execution log is captured in the `AutomationReports/Log.log`

 - _Auto Emailable Test reports_
    - Has ability to prepare the test report for email body
    - Sends the email with test report to the mentioned recipients in `Sys.properties` (Refer line#46&47) 

 - _Reporting_
    - The framework generates `index.html` report. It resides in the same `target\surefire-reports` folder
    - This reports gives the link to all the different component of the TestNG reports like Groups & Reporter Output
    - On clicking these will display detailed descriptions of execution.
    - You can find `emailable-report.html` from `target\surefire-reports` to email the test reports
    - As this is a html report you can open it with browser
    - Features of report
       - Test summary report (Pass, Fail, Skipped, Duration, Percentage, Charts and Suite level stats)
       - Detailed Log output
       - Video recordings of tests
       - Screenshots for failed test
       - Detailed exception stacktrace of failed tests

 - _Selenium Grid Implementation_
    - Has the ability to configure the Selenium Grid with easy batch file setup
    - SetUp Files can be found in `Executables\Grid\Cmd_setup` directory
      - Change Selenium Jar versions if needed

 - _Data Driven Tests_
    - Has ability to execute the tests using the data obtained from Excel, XML, JSON…
    - Utilities added to perform common excel operations .
    - Sample tests to refer – `src/test/java/com/testsuite/dataprovider/DataDrivenTestWithExcel.java`

 - _Local Dashboard (Run time report)_
    - Ability to view the current live test execution on local or remote machines
    - Works with linear and grid mode
    - For reference <a href="https://i.ibb.co/FBDqcCN/live-dashboard.gif" target="_blank">watch video</a>

 - _ARES Dashboard_
    - Ability to view the current live test execution on local or remote machines.
    - Works with linear and grid mode
    - For reference <a href="https://i.ibb.co/tq99n8c/dashboard-overview.gif" target="_blank">watch video</a>

 - _Object Repository_
    - Maintained with the help of Page object model.
    - Java Interfaces are used to store the Locators/Objects

 - _Utilities_
    - Different Utilities exist to read Excel, JSON, XML, PDF, Properties files	

 - _Listeners_
    - `Web Listener` - To observe and log the browser actions.
    - `Test Listener` -  To do some actions like taking _failure screenshot_, updating _run time report_ based on test results
	  - Like _On Failure_, _On Success_, _On Start_ etc.,

 - _Balloon Pop-Up_
   - This framework displays window Balloon Pop-ups with running test case name
   
   _Test Rail Management Tool_
   - Updates the Test Result into Test Rail Tool with Execution Progress.Please Click on the [link](Resources/Documentation/Test rail Integration document with Selenium Java framework.pdf) for more information.

- _Slack And MS Teams Integration with Jenkins_
   - Ability to view Jenkins Build Notifications in Slack and Microsoft Teams.Please Click on the [link](Resources/Documentation/Jenkins_Integration_with_Slack,MSTeams.pdf) for more information.

- _Zephyr cloud for jira Integration_
   - Updates test results after execution as per the specified Test Cycle through ZAPI in Zephyr cloud for jira tool.Please Click on the [link](Resources/Documentation/ZephyrCloudDocumentation.pdf) for more information.

- _Slack Integration without CI Tool_
   - Ability to view Build Notifications in Slack without CI Tool.Please Click on the [link](Resources/Documentation/Slack_Integration_document_with_Selenium_Java_framework.pdf) for more information.

- _Teams Integration without CI Tool_
   - Ability to view Build Notifications in Teams without CI Tool.Please Click on the [link](Resources/Documentation/Teams_Integration_document_with_Selenium_Java_framework.pdf) for more information.
---
## Prerequisites

 - __Java__
    - Version: `jdk-1.7` or higher
	- Website: [link](https://java.com/en/)

 - __Apache Maven__
    - Version: `3` or higher
	- Website: [link](http://maven.apache.org/)
	- Setup Help [link_1](http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html), [link_2](http://www.tutorialspoint.com/maven/maven_environment_setup.htm)

 - __TestNG__
   - Version: `6` or higher
	- Website: [link](https://testng.org/doc/index.html)

*** User can install required `jar's` by executing command present in `CommandToInstallLocalLibs.txt` file

Note: `POM.xml` picks required `.jars` present under `Resources/lib` folder

---
## Steps to use

__Creating Tests__
 - Clone project from repo
    - `git clone http://gitlab.zenq.com/praveen.lakkimsetti/seleniumjavaframework.git`
 - Import project as existing Maven project in any Java IDE
    - You can run the program using any IDE like Eclipse or IntelliJ or from Jenkins
	- Ensure Maven plugin is available
 - Add tests under `src/test/java/testsuite` directory
 - Add the test names in `TestNGSuites/TestNG.xml` file
 
__Execution:__
 - Double click on `RunTests.bat` to run the tests.

__What happens when a build is triggered...__
 - Batch file will add the maven to the environment variables.
 - Build is triggered through Maven by using the command `mvn clean install`    
 - It creates the latest results folder in Automation Reports directory to store current execution results.
 - It triggers the testNG.xml to execute tests. 
    - _If any tests failed:_
	   - Failure screenshots will be captured with respective `test name` + `timestamp`
	   - Captured screenshot stored under `AutomationReports\LatestResults\Screenshots`
 - Once the test execution is completed
    - ReportNG reports will be generated under `target\surefire-reports\html` folder
    - Generated ReportNG reports will be moved to `AutomationReports/LatestResults` directory.
 - An Email report with pass and failure results will be sent to respective recipients mentioned in `ConfigFiles/Sys.properties` file.

*** Users can also `enable` or `disable` features like _ARES Dashboard_, _LiveDashboard_ etc., features by modifying respective variables in `sys.properties` file

---
## For More info

 - Selenium: [Website](https://www.seleniumhq.org/) | [Repository](https://github.com/SeleniumHQ/selenium) | [Community](https://stackoverflow.com/questions/tagged/selenium-webdriver) | [Mailing-list](https://groups.google.com/forum/#!forum/selenium-users) | [Blogs](http://it-kosmopolit.de/Selenium/blog/selenium-blogs/selenium_blogs.php)
 - [Awesome-selenium list](https://github.com/christian-bromann/awesome-selenium)
 - [Xpath and CSS cheat-sheet](https://devhints.io/xpath)
 - [Xpath Syntax on W3Schools](https://www.w3schools.com/xml/xpath_syntax.asp)
 - [CSS Selector](https://developer.mozilla.org/en-US/docs/Learn/CSS/Introduction_to_CSS/Simple_selectors)

---
## Contribution

 - Found `issue` report us [here](http://gitlab.zenq.com/praveen.lakkimsetti/seleniumjavaframework/issues)
 - Do you have fix for any existing problem, submit `pull request`

---
## Who do I talk to?

 - Owner - `Praveen Kumar Lakkimsetti`
 - Active User - `Venkatsai Annam`

---
## COPYRIGHTS and LICENSE

- **Strictly for Office Use**
- Copyright 2019 - ©<a href="http://www.zenq.com/" target="_blank"> ZenQ </a>

 [![ZenQ](http://www.zenq.com/wp-content/uploads/2018/04/main-logo.png)](http://www.zenq.com/)