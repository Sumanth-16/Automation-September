pushd %~dp0%..\..\ConfigFiles
appium --nodeconfig %~dp0%..\..\ConfigFiles\AppiumNodeConfig1.json -p 4723 -bp 6723 --chromedriver-port 1234

