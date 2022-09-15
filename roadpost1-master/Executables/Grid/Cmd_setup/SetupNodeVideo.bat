::Run this file if you would like to record video in Grid execution
pushd %~dp0%..\..\..\Resources\lib
set SELSERV="selenium-server-standalone-*.jar"
for /f %%i in (%SELSERV%) do set SELSERV=%%~fi
start java -Dwebdriver.chrome.driver="../../Resources/Drivers/chromedriver.exe" -Dwebdriver.gecko.driver="../../Resources/Drivers/geckodriver.exe" -cp selenium-video-node-2.2.jar;"%SELSERV%" org.openqa.grid.selenium.GridLauncherV3 -servlets com.aimmac23.node.servlet.VideoRecordingControlServlet -proxy com.aimmac23.hub.proxy.VideoProxy -role node -nodeConfig "..\..\ConfigFiles\NodeConfig.json"
pause

