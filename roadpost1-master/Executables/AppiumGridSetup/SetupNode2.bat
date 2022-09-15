pushd %~dp0%..\..\ConfigFiles
appium --nodeconfig %~dp0%..\..\ConfigFiles\AppiumNodeConfig2.json -p 4724 -bp 6724 --chromedriver-port 4321

